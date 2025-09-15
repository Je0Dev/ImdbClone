package com.example.Imdb_Clone.controller;

import com.example.Imdb_Clone.dto.MovieResponseDto;
import com.example.Imdb_Clone.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping("/movies/{movieId}")
    public ResponseEntity<Void> addMovieToWatchlist(@PathVariable Long movieId, Authentication authentication) {
        watchlistService.addMovieToWatchlist(authentication.getName(), movieId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity<Void> removeMovieFromWatchlist(@PathVariable Long movieId, Authentication authentication) {
        watchlistService.removeMovieFromWatchlist(authentication.getName(), movieId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Set<MovieResponseDto>> getMyWatchlist(Authentication authentication) {
        return ResponseEntity.ok(watchlistService.getWatchlist(authentication.getName()));
    }
}
