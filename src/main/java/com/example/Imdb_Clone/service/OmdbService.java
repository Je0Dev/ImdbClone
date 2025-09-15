package com.example.Imdb_Clone.service;

import com.example.Imdb_Clone.dto.OmdbMovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OmdbService {

    private final RestTemplate restTemplate;

    @Value("${omdb.api.key}")
    private String apiKey;

    private final String OMDB_URL = "http://www.omdbapi.com/";

    public OmdbMovieDto fetchMovieByTitle(String title) {
        String url = String.format("%s?t=%s&apikey=%s", OMDB_URL, title, apiKey);
        return restTemplate.getForObject(url, OmdbMovieDto.class);
    }
}
