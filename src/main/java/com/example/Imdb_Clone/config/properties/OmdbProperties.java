package com.example.Imdb_Clone.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "omdb.api")
public class OmdbProperties {
    private String key;
}
