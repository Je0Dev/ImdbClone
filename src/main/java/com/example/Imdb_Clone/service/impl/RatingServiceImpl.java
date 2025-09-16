package com.example.Imdb_Clone.service.impl;

import com.example.Imdb_Clone.exception.ResourceNotFoundException;
import com.example.Imdb_Clone.model.Movie;
import com.example.Imdb_Clone.model.Rating;
import com.example.Imdb_Clone.model.User;
import com.example.Imdb_Clone.repository.MovieRepository;
import com.example.Imdb_Clone.repository.RatingRepository;
import com.example.Imdb_Clone.repository.UserRepository;
import com.example.Imdb_Clone.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Override
    @Transactional
    public void addOrUpdateRating(Long movieId, String username, int value) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found: " + movieId));

        Rating rating = ratingRepository.findByUserAndMovie(user, movie)
                .orElse(new Rating());

        rating.setUser(user);
        rating.setMovie(movie);
        rating.setValue(value);

        ratingRepository.save(rating);
    }
}
