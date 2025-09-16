package com.example.Imdb_Clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.example.Imdb_Clone.model.*;
import com.example.Imdb_Clone.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import com.example.Imdb_Clone.config.properties.JwtProperties;
import com.example.Imdb_Clone.config.properties.OmdbProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

// This annotation explicitly disables Flyway, removing all related errors.
@SpringBootApplication(exclude = FlywayAutoConfiguration.class)
@EnableConfigurationProperties({ JwtProperties.class, OmdbProperties.class })
public class ImdbCloneApplication {

    public static void main(String[] args) {
        // Set the property HERE to force Hibernate to 'update' the database.
        System.setProperty("spring.jpa.hibernate.ddl-auto", "update");
        System.setProperty("spring.jpa.show-sql", "true");
        System.setProperty("spring.h2.console.enabled", "true");
        System.setProperty("spring.h2.console.path", "/h2-console");
        System.setProperty("spring.h2.console.settings.web-allow-others", "true");
        SpringApplication.run(ImdbCloneApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            GenreRepository genreRepository,
            ActorRepository actorRepository,
            DirectorRepository directorRepository,
            MovieRepository movieRepository) {
        return args -> {
            // --- 1. Create Foundational Roles & Admin User ---
            Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_USER");
                return roleRepository.save(role);
            });
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_ADMIN");
                return roleRepository.save(role);
            });

            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("adminpass"));
                adminUser.setRoles(Set.of(userRole, adminRole));
                userRepository.save(adminUser);
                System.out.println("---- Admin user created! ----");
            }

            // --- 2. Seed Sample Movie Data (Only if database is empty) ---
            if (movieRepository.count() == 0) {
                System.out.println("---- No movies found. Seeding sample data... ----");

                // Seed Genres
                Genre action = genreRepository.save(new Genre());
                Genre sciFi = genreRepository.save(new Genre());
                Genre drama = genreRepository.save(new Genre());

                // Seed Actors
                Actor leo = actorRepository.save(new Actor());
                Actor keanu = actorRepository.save(new Actor());

                // Seed Directors
                Director chris = directorRepository.save(new Director());
                Director lana = directorRepository.save(new Director());

                // Seed Movies
                Movie movie1 = new Movie();
                movie1.setTitle("Inception");
                movie1.setDescription(
                        "A thief who steals corporate secrets through the use of dream-sharing technology.");
                movie1.setReleaseDate(LocalDate.of(2010, 7, 16));
                movie1.setPosterUrl("https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg");
                movie1.setGenres(Set.of(action, sciFi));
                movie1.setActors(Set.of(leo));
                movie1.setDirectors(Set.of(chris));
                movieRepository.save(movie1);

                Movie movie2 = new Movie();
                movie2.setTitle("The Matrix");
                movie2.setDescription(
                        "A computer hacker learns from mysterious rebels about the true nature of his reality.");
                movie2.setReleaseDate(LocalDate.of(1999, 3, 31));
                movie2.setPosterUrl("https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg");
                movie2.setGenres(Set.of(action, sciFi));
                movie2.setActors(Set.of(keanu));
                movie2.setDirectors(Set.of(lana));
                movieRepository.save(movie2);

                System.out.println("---- Sample data seeded successfully! ----");
            }
        };
    }
}
