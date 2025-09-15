package com.example.Imdb_Clone.controller;

import com.example.Imdb_Clone.model.Series;
import com.example.Imdb_Clone.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SeriesController {

    @Autowired
    private SeriesRepository seriesRepository;

    @GetMapping
    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    @PostMapping
    public Series createSeries(@RequestBody Series series) {
        return seriesRepository.save(series);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Series> getSeriesById(@PathVariable Long id) {
        return seriesRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
