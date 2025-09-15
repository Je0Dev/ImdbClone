package com.example.Imdb_Clone.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 1000)
    private String description;
    private LocalDate releaseDate;
    private LocalDate endDate;

    // We can reuse the same relationships as Movie
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "series_genre", joinColumns = @JoinColumn(name = "series_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Episode> episodes = new HashSet<>();
}
