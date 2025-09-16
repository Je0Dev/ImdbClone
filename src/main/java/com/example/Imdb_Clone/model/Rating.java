package com.example.Imdb_Clone.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rating", // Changed from "rating" to avoid potential conflicts, good practice
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "user_id", "movie_id" })
        })
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    // THIS IS THE FIX: We are telling JPA to name this column "rating_value" in the
    // database.
    @Column(name = "rating_value", nullable = false)
    private int value;
}
