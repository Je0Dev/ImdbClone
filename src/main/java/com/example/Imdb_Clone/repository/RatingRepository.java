package com.example.Imdb_Clone.repository;

import com.example.Imdb_Clone.model.Movie;
import com.example.Imdb_Clone.model.Rating;
import com.example.Imdb_Clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    /**
     * Finds a rating for a specific movie by a specific user.
     * This is used to check if a user has already rated a movie.
     */
    Optional<Rating> findByUserAndMovie(User user, Movie movie);
}
