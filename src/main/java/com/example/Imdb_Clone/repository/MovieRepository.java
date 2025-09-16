package com.example.Imdb_Clone.repository;

import com.example.Imdb_Clone.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    // This query efficiently fetches movies and their ratings to avoid the N+1
    // problem.
    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.ratings")
    Page<Movie> findAllWithRatings(Pageable pageable);

    // This query efficiently fetches a single movie and its ratings.
    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.ratings WHERE m.id = :id")
    Optional<Movie> findByIdWithRatings(@Param("id") Long id);
}
