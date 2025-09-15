package com.example.Imdb_Clone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewRequestDto {
    @NotBlank(message = "Review text cannot be blank.")
    @Size(min = 10, max = 5000, message = "Review must be between 10 and 5000 characters.")
    private String text;
}
