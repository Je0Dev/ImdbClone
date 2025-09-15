package com.example.Imdb_Clone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class MovieRequestDto {

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    private String posterUrl;

    @NotEmpty(message = "At least one genre is required")
    private Set<Long> genreIds;

    @NotEmpty(message = "At least one actor is required")
    private Set<Long> actorIds;

    @NotEmpty(message = "At least one director is required")
    private Set<Long> directorIds;
}
