package com.example.Imdb_Clone.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

// DTO for sending movie data to the client
@Data
public class MovieResponseDto {
    private double averageRating;
    private Integer userRating; // Use Integer to allow null if user hasn't rated
    private Set<ReviewDto> reviews;
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String posterUrl;
    private Set<String> genres;
    private Set<String> actors;
    private Set<String> directors;
}
