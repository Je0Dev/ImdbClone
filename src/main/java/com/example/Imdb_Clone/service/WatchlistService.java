package com.example.Imdb_Clone.service;

import com.example.Imdb_Clone.dto.MovieResponseDto;

import java.util.Set;

public interface WatchlistService {
    void addMovieToWatchlist(String username, Long movieId);

    void removeMovieFromWatchlist(String username, Long movieId);

    Set<MovieResponseDto> getWatchlist(String username);
}
