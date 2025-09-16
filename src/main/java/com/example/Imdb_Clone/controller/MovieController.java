package com.example.Imdb_Clone.controller;

import com.example.Imdb_Clone.dto.MovieRequestDto;
import com.example.Imdb_Clone.dto.MovieResponseDto;
import com.example.Imdb_Clone.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

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

    // --- THIS IS THE NEW ENDPOINT ---
    @GetMapping("/search")
    public Page<MovieResponseDto> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            Pageable pageable) {
        return movieService.searchMovies(title, genre, pageable);
    }
    // ---------------------------------

    @PostMapping
    public ResponseEntity<MovieResponseDto> createMovie(@RequestBody MovieRequestDto movieRequestDto) {
        MovieResponseDto createdMovie = movieService.createMovie(movieRequestDto);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @PostMapping("/import")
    public ResponseEntity<MovieResponseDto> importMovie(@RequestParam String title) {
        MovieResponseDto importedMovie = movieService.importMovieFromOmdb(title);
        return new ResponseEntity<>(importedMovie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDto> updateMovie(@PathVariable Long id,
            @RequestBody MovieRequestDto movieRequestDto) {
        MovieResponseDto updatedMovie = movieService.updateMovie(id, movieRequestDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
