package com.example.Imdb_Clone.controller;

import com.example.Imdb_Clone.dto.RatingRequestDto;
import com.example.Imdb_Clone.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies/{movieId}/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<Void> addOrUpdateRating(
            @PathVariable Long movieId,
            @Valid @RequestBody RatingRequestDto ratingRequest,
            Authentication authentication) {

        String username = authentication.getName();
        ratingService.addOrUpdateRating(movieId, username, ratingRequest.getValue());
        return ResponseEntity.ok().build();
    }
}
