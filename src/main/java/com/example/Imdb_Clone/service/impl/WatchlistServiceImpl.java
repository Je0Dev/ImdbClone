package com.example.Imdb_Clone.service.impl;

import com.example.Imdb_Clone.dto.MovieResponseDto;
import com.example.Imdb_Clone.exception.ResourceNotFoundException;
import com.example.Imdb_Clone.model.Movie;
import com.example.Imdb_Clone.model.User;
import com.example.Imdb_Clone.repository.MovieRepository;
import com.example.Imdb_Clone.repository.UserRepository;
import com.example.Imdb_Clone.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistServiceImpl implements WatchlistService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Override
    @Transactional
    public void addMovieToWatchlist(String username, Long movieId) {
        User user = findUserByUsername(username);
        Movie movie = findMovieById(movieId);
        user.getWatchlist().add(movie);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeMovieFromWatchlist(String username, Long movieId) {
        User user = findUserByUsername(username);
        Movie movie = findMovieById(movieId);
        user.getWatchlist().remove(movie);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<MovieResponseDto> getWatchlist(String username) {
        User user = findUserByUsername(username);
        Set<Movie> watchlistMovies = Optional.ofNullable(user.getWatchlist()).orElse(Collections.emptySet());

        return watchlistMovies.stream()
                .map(this::convertMovieToSimpleDto)
                .collect(Collectors.toSet());
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    private Movie findMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found: " + movieId));
    }

    // A simple converter for the watchlist view to avoid circular dependencies
    private MovieResponseDto convertMovieToSimpleDto(Movie movie) {
        MovieResponseDto dto = new MovieResponseDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setPosterUrl(movie.getPosterUrl());
        return dto;
    }
}
