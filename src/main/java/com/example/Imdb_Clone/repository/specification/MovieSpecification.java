package com.example.Imdb_Clone.repository.specification;

import com.example.Imdb_Clone.model.Movie;
import org.springframework.data.jpa.domain.Specification;

public class MovieSpecification {

    public static Specification<Movie> titleContains(String title) {
        return (root, query, criteriaBuilder) -> title == null ? null
                : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Movie> hasGenre(String genreName) {
        return (root, query, criteriaBuilder) -> {
            if (genreName == null) {
                return null;
            }
            // This requires a join to the genres table
            return criteriaBuilder.equal(root.join("genres").get("name"), genreName);
        };
    }
}
