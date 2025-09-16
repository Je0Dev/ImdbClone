package com.example.Imdb_Clone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OmdbMovieDto {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("Released")
    private String released;

    @JsonProperty("Response")
    private String response; // This will be "True" or "False"

    @JsonProperty("Error")
    private String error; // This will contain an error message if Response is "False"
}
