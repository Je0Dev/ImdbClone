-- This script creates the entire initial database schema for the IMDb Clone application.

-- Core Content Tables
CREATE TABLE genre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE actor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    birth_date DATE,
    biography VARCHAR(255)
);

CREATE TABLE director (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    birth_date DATE,
    biography VARCHAR(255)
);

CREATE TABLE movie (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(1000),
    release_date DATE,
    poster_url VARCHAR(255)
);

CREATE TABLE series (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(1000),
    release_date DATE,
    end_date DATE
);

CREATE TABLE episode (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    season_number INT NOT NULL,
    episode_number INT NOT NULL,
    release_date DATE,
    series_id BIGINT,
    FOREIGN KEY (series_id) REFERENCES series (id)
);

-- User and Auth Tables
CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

-- User Interaction Tables
CREATE TABLE review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text VARCHAR(5000),
    created_at TIMESTAMP,
    user_id BIGINT,
    movie_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id)
);

-- Join Tables for Many-to-Many Relationships
CREATE TABLE movie_genre (
    movie_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (movie_id, genre_id),
    FOREIGN KEY (movie_id) REFERENCES movie (id),
    FOREIGN KEY (genre_id) REFERENCES genre (id)
);

CREATE TABLE movie_actor (
    movie_id BIGINT NOT NULL,
    actor_id BIGINT NOT NULL,
    PRIMARY KEY (movie_id, actor_id),
    FOREIGN KEY (movie_id) REFERENCES movie (id),
    FOREIGN KEY (actor_id) REFERENCES actor (id)
);

CREATE TABLE movie_director (
    movie_id BIGINT NOT NULL,
    director_id BIGINT NOT NULL,
    PRIMARY KEY (movie_id, director_id),
    FOREIGN KEY (movie_id) REFERENCES movie (id),
    FOREIGN KEY (director_id) REFERENCES director (id)
);

CREATE TABLE series_genre (
    series_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (series_id, genre_id),
    FOREIGN KEY (series_id) REFERENCES series (id),
    FOREIGN KEY (genre_id) REFERENCES genre (id)
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE user_watchlist (
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, movie_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id)
);

-- Seed initial roles
INSERT INTO role (name) VALUES ('ROLE_USER');

INSERT INTO role (name) VALUES ('ROLE_ADMIN');
