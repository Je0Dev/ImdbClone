package com.example.Imdb_Clone.service;

import com.example.Imdb_Clone.dto.MovieRequestDto;
import com.example.Imdb_Clone.dto.MovieResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface MovieService {

    Page<MovieResponseDto> getAllMovies(Pageable pageable);

    Optional<MovieResponseDto> getMovieById(Long id);

    MovieResponseDto createMovie(MovieRequestDto movieRequestDto);

    MovieResponseDto updateMovie(Long id, MovieRequestDto movieRequestDto);

    MovieResponseDto importMovieFromOmdb(String title);

    Page<MovieResponseDto> searchMovies(String title, String genre, Pageable pageable);

    void deleteMovie(Long id);
}
