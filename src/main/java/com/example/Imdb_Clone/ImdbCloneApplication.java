package com.example.Imdb_Clone;

import com.example.Imdb_Clone.model.*;
import com.example.Imdb_Clone.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ImdbCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImdbCloneApplication.class, args);
    }

    /**
     * This bean runs on application startup and is used to seed the database with
     * initial data.
     * It ensures that roles, users, and sample content exist for development and
     * testing.
     * It relies on the Flyway migration script (V1) to have already created the
     * tables and initial roles.
     */
    @Bean

    CommandLineRunner commandLineRunner(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            GenreRepository genreRepository,
            ActorRepository actorRepository,
            DirectorRepository directorRepository,
            MovieRepository movieRepository,
            SeriesRepository seriesRepository,
            EpisodeRepository episodeRepository) {
        return args -> {
            // --- 1. Find Foundational Roles (created by Flyway) ---
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: ROLE_USER is not found in the database."));
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: ROLE_ADMIN is not found in the database."));

            // --- 2. Create an Admin User (if it doesn't exist) ---
            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("adminpass"));
                adminUser.setRoles(Set.of(userRole, adminRole));
                userRepository.save(adminUser);
                System.out.println("---- Admin user created! ----");
            }

            // --- 3. Seed Sample Genres, Actors, and Directors ---
            Genre action = new Genre();
            action.setName("Action");
            Genre sciFi = new Genre();
            sciFi.setName("Sci-Fi");
            Genre drama = new Genre();
            drama.setName("Drama");
            genreRepository.saveAll(List.of(action, sciFi, drama));

            Actor leo = new Actor();
            leo.setName("Leonardo DiCaprio");
            Actor keanu = new Actor();
            keanu.setName("Keanu Reeves");
            actorRepository.saveAll(List.of(leo, keanu));

            Director chris = new Director();
            chris.setName("Christopher Nolan");
            Director lana = new Director();
            lana.setName("Lana Wachowski");
            directorRepository.saveAll(List.of(chris, lana));

            // --- 4. Seed Sample Movies ---
            Movie movie1 = new Movie();
            movie1.setTitle("Inception");
            movie1.setDescription("A thief who steals corporate secrets through the use of dream-sharing technology.");
            movie1.setReleaseDate(LocalDate.of(2010, 7, 16));
            movie1.setPosterUrl("https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg");
            movie1.setGenres(Set.of(action, sciFi));
            movie1.setActors(Set.of(leo));
            movie1.setDirectors(Set.of(chris));

            Movie movie2 = new Movie();
            movie2.setTitle("The Matrix");
            movie2.setDescription(
                    "A computer hacker learns from mysterious rebels about the true nature of his reality.");
            movie2.setReleaseDate(LocalDate.of(1999, 3, 31));
            movie2.setPosterUrl("https://image.tmdb.org/t/p/w500/f89JxwcxYyKZzdmFMLQFpaapgp.jpg");
            movie2.setGenres(Set.of(action, sciFi));
            movie2.setActors(Set.of(keanu));
            movie2.setDirectors(Set.of(lana));
            movieRepository.saveAll(List.of(movie1, movie2));

            // --- 5. Seed a Sample Series with Episodes ---
            Series breakingBad = new Series();
            breakingBad.setTitle("Breaking Bad");
            breakingBad.setDescription(
                    "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine to secure his family's future.");
            breakingBad.setReleaseDate(LocalDate.of(2008, 1, 20));
            breakingBad.setEndDate(LocalDate.of(2013, 9, 29));
            breakingBad.setGenres(Set.of(drama));
            seriesRepository.save(breakingBad);

            Episode episode1 = new Episode();
            episode1.setTitle("Pilot");
            episode1.setSeasonNumber(1);
            episode1.setEpisodeNumber(1);
            episode1.setSeries(breakingBad);

            Episode episode2 = new Episode();
            episode2.setTitle("Cat's in the Bag...");
            episode2.setSeasonNumber(1);
            episode2.setEpisodeNumber(2);
            episode2.setSeries(breakingBad);
            episodeRepository.saveAll(List.of(episode1, episode2));

            System.out.println("---- Sample data seeding complete! ----");
        };
    }
}
