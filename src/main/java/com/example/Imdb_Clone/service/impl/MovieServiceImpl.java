package com.example.Imdb_Clone.service.impl;

import com.example.Imdb_Clone.dto.MovieRequestDto;
import com.example.Imdb_Clone.dto.MovieResponseDto;
import com.example.Imdb_Clone.dto.OmdbMovieDto;
import com.example.Imdb_Clone.dto.ReviewDto;
import com.example.Imdb_Clone.exception.ResourceNotFoundException;
import com.example.Imdb_Clone.model.Actor;
import com.example.Imdb_Clone.model.Director;
import com.example.Imdb_Clone.model.Genre;
import com.example.Imdb_Clone.model.Movie;
import com.example.Imdb_Clone.repository.ActorRepository;
import com.example.Imdb_Clone.repository.DirectorRepository;
import com.example.Imdb_Clone.repository.GenreRepository;
import com.example.Imdb_Clone.repository.MovieRepository;
import com.example.Imdb_Clone.service.MovieService;
import com.example.Imdb_Clone.service.OmdbService;

import com.example.Imdb_Clone.repository.specification.MovieSpecification;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private OmdbService omdbService;

    @Override
    @Transactional(readOnly = true)
    public Page<MovieResponseDto> getAllMovies(Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        return moviePage.map(this::convertToDto); // Use .map() on the Page object
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MovieResponseDto> getMovieById(Long id) {
        return movieRepository.findById(id).map(this::convertToDto);
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
        return convertToDto(savedMovie);
    }

    // A private helper method to handle the conversion from Entity to DTO
    private MovieResponseDto convertToDto(Movie movie) {
        MovieResponseDto dto = new MovieResponseDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setPosterUrl(movie.getPosterUrl());

        dto.setGenres(movie.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toSet()));
        dto.setActors(movie.getActors().stream()
                .map(Actor::getName)
                .collect(Collectors.toSet()));
        dto.setDirectors(movie.getDirectors().stream()
                .map(Director::getName)
                .collect(Collectors.toSet()));

        dto.setReviews(movie.getReviews().stream().map(review -> {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(review.getId());
            reviewDto.setText(review.getText());
            reviewDto.setUsername(review.getUser().getUsername());
            reviewDto.setCreatedAt(review.getCreatedAt());
            return reviewDto;
        }).collect(Collectors.toSet()));

        return dto;
    }
    // Note: Implementations for update and delete would follow a similar pattern.

    @Override
    public MovieResponseDto updateMovie(Long id, MovieRequestDto movieRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMovie'");
    }

    @Override
    public void deleteMovie(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMovie'");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovieResponseDto> searchMovies(String title, String genre, Pageable pageable) {
        Specification<Movie> spec = Specification
                .where(MovieSpecification.titleContains(title))
                .and(MovieSpecification.hasGenre(genre));

        return movieRepository.findAll(spec, pageable).map(this::convertToDto);
    }

    public MovieResponseDto movieToMovieResponseDto(Movie movie) {
        // Convert the Movie object to a MovieResponseDto object
        MovieResponseDto movieResponseDto = new MovieResponseDto();
        movieResponseDto.setId(movie.getId());
        movieResponseDto.setTitle(movie.getTitle());
        // Set other properties of the MovieResponseDto object
        return movieResponseDto;
    }

    @Override
    @Transactional
    public MovieResponseDto importMovieFromOmdb(String title) {
        OmdbMovieDto omdbMovie = omdbService.fetchMovieByTitle(title);

        if (omdbMovie == null || "False".equals(omdbMovie.getResponse())) {
            throw new ResourceNotFoundException("Movie not found in OMDb with title: " + title);
        }

        Movie movie = new Movie();
        movie.setTitle(omdbMovie.getTitle());
        movie.setDescription(omdbMovie.getPlot());
        movie.setPosterUrl(omdbMovie.getPoster());

        // Handle date conversion with a specific format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        try {
            LocalDate releaseDate = LocalDate.parse(omdbMovie.getReleased(), formatter);
            movie.setReleaseDate(releaseDate);
        } catch (Exception e) {
            // Handle cases where the date format is unexpected or null
            // For now, we'll leave it null
        }

        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie); // Reuse our existing conversion method
    }
}
