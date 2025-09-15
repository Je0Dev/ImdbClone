package com.example.Imdb_Clone.model;

import jakarta.persistence.*; // Make sure to import everything from jakarta.persistence
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000) // Let's give the description more space
    private String description;

    private LocalDate releaseDate;
    private String posterUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    // Add relationship to Actors
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_actor", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors = new HashSet<>();

    // Add relationship to Directors
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_director", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "director_id"))
    private Set<Director> directors = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Handles the "forward" part of the reference, the one that gets serialized.
    private Set<Review> reviews = new HashSet<>();
}
