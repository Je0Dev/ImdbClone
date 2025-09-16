package com.example.Imdb_Clone.service;

import com.example.Imdb_Clone.dto.OmdbMovieDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.Imdb_Clone.config.properties.OmdbProperties;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OmdbService {

    private static final Logger logger = LoggerFactory.getLogger(OmdbService.class);
    private final RestTemplate restTemplate;
    private final OmdbProperties omdbProperties;

    @Value("${omdb.api.key}")
    private String apiKey;

    private static final String OMDB_URL = "http://www.omdbapi.com/";

    public OmdbMovieDto fetchMovieByTitle(String title) {
        String url = String.format("%s?t=%s&apikey=%s", OMDB_URL, title, omdbProperties.getKey());
        logger.info("Fetching movie from OMDb with URL: {}", url);

        try {
            OmdbMovieDto response = restTemplate.getForObject(url, OmdbMovieDto.class);
            logger.info("Received response from OMDb: {}", response);
            return response;
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error fetching from OMDb: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching from OMDb", e);
            return null;
        }
    }
}
