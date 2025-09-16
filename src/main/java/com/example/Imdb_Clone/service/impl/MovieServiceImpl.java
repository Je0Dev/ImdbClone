package com.example.Imdb_Clone.service.impl;

import com.example.Imdb_Clone.dto.MovieRequestDto;
import com.example.Imdb_Clone.dto.MovieResponseDto;
import com.example.Imdb_Clone.dto.OmdbMovieDto;
import com.example.Imdb_Clone.dto.ReviewDto;
import com.example.Imdb_Clone.exception.ResourceNotFoundException;
import com.example.Imdb_Clone.model.*;
import com.example.Imdb_Clone.repository.*;
import com.example.Imdb_Clone.repository.specification.MovieSpecification;
import com.example.Imdb_Clone.service.MovieService;
import com.example.Imdb_Clone.service.OmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    // --- Dependencies Injected via Constructor ---
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final UserRepository userRepository;
    private final OmdbService omdbService;

    @Override
    @Transactional(readOnly = true)
    public Page<MovieResponseDto> getAllMovies(Pageable pageable) {
        // Using a standard findAll and a DTO conversion that doesn't require extra
        // queries
        return movieRepository.findAll(pageable).map(movie -> convertToDto(movie, null));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MovieResponseDto> getMovieById(Long id) {
        User currentUser = getCurrentUser().orElse(null);
        // Use the efficient query that fetches ratings and reviews along with the movie
        return movieRepository.findByIdWithRatings(id)
                .map(movie -> convertToDto(movie, currentUser));
    }

    @Override
    @Transactional
    public MovieResponseDto createMovie(MovieRequestDto movieRequestDto) {
        Movie movie = new Movie();
        movie.setTitle(movieRequestDto.getTitle());
        movie.setDescription(movieRequestDto.getDescription());
        movie.setReleaseDate(movieRequestDto.getReleaseDate());
        movie.setPosterUrl(movieRequestDto.getPosterUrl());

        Set<Genre> genres = new HashSet<>(genreRepository.findAllById(movieRequestDto.getGenreIds()));
        Set<Actor> actors = new HashSet<>(actorRepository.findAllById(movieRequestDto.getActorIds()));
        Set<Director> directors = new HashSet<>(directorRepository.findAllById(movieRequestDto.getDirectorIds()));

        movie.setGenres(genres);
        movie.setActors(actors);
        movie.setDirectors(directors);

        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie, null);
    }

    @Override
    @Transactional
    public MovieResponseDto updateMovie(Long id, MovieRequestDto movieRequestDto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));

        movie.setTitle(movieRequestDto.getTitle());
        movie.setDescription(movieRequestDto.getDescription());
        movie.setReleaseDate(movieRequestDto.getReleaseDate());
        movie.setPosterUrl(movieRequestDto.getPosterUrl());

        Movie updatedMovie = movieRepository.save(movie);
        User currentUser = getCurrentUser().orElse(null);
        return convertToDto(updatedMovie, currentUser);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovieResponseDto> searchMovies(String title, String genre, Pageable pageable) {
        Specification<Movie> spec = Specification
                .where(MovieSpecification.titleContains(title))
                .and(MovieSpecification.hasGenre(genre));
        return movieRepository.findAll(spec, pageable).map(movie -> convertToDto(movie, null));
    }

    @Override
    @Transactional
    public MovieResponseDto importMovieFromOmdb(String title) {
        OmdbMovieDto omdbMovie = omdbService.fetchMovieByTitle(title);
        if (omdbMovie == null || !"True".equals(omdbMovie.getResponse())) {
            throw new ResourceNotFoundException("Movie not found in OMDb with title: " + title);
        }

        Movie movie = new Movie();
        movie.setTitle(omdbMovie.getTitle());
        movie.setDescription(omdbMovie.getPlot());
        movie.setPosterUrl(omdbMovie.getPoster());

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
            movie.setReleaseDate(LocalDate.parse(omdbMovie.getReleased(), formatter));
        } catch (Exception e) {
            // Ignore date parsing errors
        }

        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie, null);
    }

    // --- Private Helper Methods ---

    private Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            return Optional.empty();
        }
        return userRepository.findByUsername(authentication.getName());
    }

    /**
     * Converts a Movie entity to a DTO. This is the single, null-safe converter.
     */
    private MovieResponseDto convertToDto(Movie movie, User currentUser) {
        MovieResponseDto dto = new MovieResponseDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setPosterUrl(movie.getPosterUrl());

        dto.setGenres(Optional.ofNullable(movie.getGenres()).orElse(Collections.emptySet()).stream()
                .map(Genre::getName).collect(Collectors.toSet()));
        dto.setActors(Optional.ofNullable(movie.getActors()).orElse(Collections.emptySet()).stream()
                .map(Actor::getName).collect(Collectors.toSet()));
        dto.setDirectors(Optional.ofNullable(movie.getDirectors()).orElse(Collections.emptySet()).stream()
                .map(Director::getName).collect(Collectors.toSet()));

        Set<Review> reviews = Optional.ofNullable(movie.getReviews()).orElse(Collections.emptySet());
        dto.setReviews(reviews.stream()
                .filter(review -> review.getUser() != null)
                .map(review -> {
                    ReviewDto reviewDto = new ReviewDto();
                    reviewDto.setId(review.getId());
                    reviewDto.setText(review.getText());
                    reviewDto.setUsername(review.getUser().getUsername());
                    reviewDto.setCreatedAt(review.getCreatedAt());
                    return reviewDto;
                }).collect(Collectors.toSet()));

        Set<Rating> ratings = Optional.ofNullable(movie.getRatings()).orElse(Collections.emptySet());
        double avgRating = ratings.stream()
                .mapToInt(Rating::getValue)
                .average()
                .orElse(0.0);
        dto.setAverageRating(avgRating);

        if (currentUser != null) {
            ratings.stream()
                    .filter(rating -> rating.getUser() != null && rating.getUser().getId().equals(currentUser.getId()))
                    .findFirst()
                    .ifPresent(rating -> dto.setUserRating(rating.getValue()));
        }

        return dto;
    }
}
