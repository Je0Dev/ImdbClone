package com.example.Imdb_Clone.service;

public interface RatingService {
    void addOrUpdateRating(Long movieId, String username, int value);
}
