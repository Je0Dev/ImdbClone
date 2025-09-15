package com.example.Imdb_Clone.repository;

import com.example.Imdb_Clone.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    // Add additional methods if needed
}
