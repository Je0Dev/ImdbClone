package com.example.Imdb_Clone.controller;

import com.example.Imdb_Clone.dto.MovieRequestDto;
import com.example.Imdb_Clone.dto.MovieResponseDto;
import com.example.Imdb_Clone.dto.OmdbMovieDto;
import com.example.Imdb_Clone.model.Movie;
import com.example.Imdb_Clone.repository.MovieRepository;
import com.example.Imdb_Clone.service.MovieService;
import com.example.Imdb_Clone.service.OmdbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private OmdbService omdbService;

    @Autowired
    private MovieService movieService;

    @GetMapping
    public Page<MovieResponseDto> getAllMovies(Pageable pageable) {
        return movieService.getAllMovies(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MovieResponseDto createMovie(@Valid @RequestBody MovieRequestDto movieRequestDto) {
        return movieService.createMovie(movieRequestDto);
    }
    // Endpoints for PUT and DELETE would also use DTOs

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

    @GetMapping("/search")
    public Page<MovieResponseDto> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            Pageable pageable) {
        return movieService.searchMovies(title, genre, pageable);
    }

    @PutMapping("path/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        // TODO: process PUT request

        return entity;
    }

    @PostMapping("/import")
    public ResponseEntity<MovieResponseDto> importMovieFromOmdb(@RequestParam String title) {
        MovieResponseDto savedMovieDto = movieService.importMovieFromOmdb(title);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovieDto);
    }

}
