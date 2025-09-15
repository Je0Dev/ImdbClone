package com.example.Imdb_Clone.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long id;
    private String text;
    private String username;
    private LocalDateTime createdAt;
}
