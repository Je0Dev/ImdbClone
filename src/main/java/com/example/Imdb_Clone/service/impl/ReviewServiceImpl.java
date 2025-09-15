package com.example.Imdb_Clone.service.impl;

import com.example.Imdb_Clone.dto.ReviewRequestDto;
import com.example.Imdb_Clone.exception.ResourceNotFoundException;
import com.example.Imdb_Clone.model.Movie;
import com.example.Imdb_Clone.model.Review;
import com.example.Imdb_Clone.model.User;
import com.example.Imdb_Clone.repository.MovieRepository;
import com.example.Imdb_Clone.repository.ReviewRepository;
import com.example.Imdb_Clone.repository.UserRepository;
import com.example.Imdb_Clone.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public Review addReviewInternal(Long movieId, String username, ReviewRequestDto reviewRequest) {
        // Find the movie or throw an exception
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + movieId));

        // Find the user or throw an exception
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // Create and save the new review
        Review review = new Review();
        review.setMovie(movie);
        review.setUser(user);
        review.setText(reviewRequest.getText());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForMovie(Long movieId) {
        // Verify the movie exists
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Movie not found with id: " + movieId);
        }
        return reviewRepository.findByMovieId(movieId);
    }

    @Override
    public Review addReview(Long movieId, String username, ReviewRequestDto reviewRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addReview'");
    }
}
