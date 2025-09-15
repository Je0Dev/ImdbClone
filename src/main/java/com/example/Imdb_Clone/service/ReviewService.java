package com.example.Imdb_Clone.service;

import com.example.Imdb_Clone.dto.ReviewRequestDto;
import com.example.Imdb_Clone.model.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(Long movieId, String username, ReviewRequestDto reviewRequest);

    List<Review> getReviewsForMovie(Long movieId);

    Review addReviewInternal(Long movieId, String username, ReviewRequestDto reviewRequest);
}
