package com.example.Imdb_Clone.repository;

import com.example.Imdb_Clone.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Find all reviews associated with a specific movie ID
    List<Review> findByMovieId(Long movieId);
}
