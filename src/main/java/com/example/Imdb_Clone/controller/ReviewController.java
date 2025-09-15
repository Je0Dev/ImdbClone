package com.example.Imdb_Clone.controller;

import com.example.Imdb_Clone.dto.ReviewRequestDto;
import com.example.Imdb_Clone.model.Review;
import com.example.Imdb_Clone.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies/{movieId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> addReview(
            @PathVariable Long movieId,
            @Valid @RequestBody ReviewRequestDto reviewRequest) {

        // Get the currently authenticated user's name
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Call the service to create and save the review
        Review savedReview = reviewService.addReview(movieId, currentUsername, reviewRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviewsForMovie(@PathVariable Long movieId) {
        List<Review> reviews = reviewService.getReviewsForMovie(movieId);
        return ResponseEntity.ok(reviews);
    }
}
