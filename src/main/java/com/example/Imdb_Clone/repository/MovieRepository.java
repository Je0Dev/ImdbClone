package com.example.Imdb_Clone.repository;

import com.example.Imdb_Clone.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository // 1. Marks this interface as a Spring Data repository.
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    // ...
}
