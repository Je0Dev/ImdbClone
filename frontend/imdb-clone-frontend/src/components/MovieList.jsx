import React, { useState, useEffect } from 'react';
import apiClient from '../services/apiService';
import './MovieList.css'; // We'll create this file for styling

const MovieList = () => {
  // 1. State for movies, loading status, and errors
  const [movies, setMovies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // 2. useEffect to fetch data when the component mounts
  useEffect(() => {
    const fetchMovies = async () => {
      try {
        // We need a valid token to fetch movies
        // For now, let's hardcode one. We'll fix this in the auth step.
        // Go to Postman, log in as a user, and get a token.
        const token = 'PASTE_YOUR_JWT_TOKEN_HERE';

        const response = await apiClient.get('/movies?page=0&size=10', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setMovies(response.data.content); // The data is in the 'content' property of the Page object
      } catch (err) {
        setError('Failed to fetch movies. Are you logged in and is the backend running?');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchMovies();
  }, []); // The empty array [] means this effect runs only once

  // 3. Conditional rendering based on state
  if (loading) {
    return <div>Loading movies...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  // 4. Render the list of movies
  return (
    <div className="movie-list-container">
      <h1>Top Movies</h1>
      <div className="movie-grid">
        {movies.map((movie) => (
          <div key={movie.id} className="movie-card">
            <img src={movie.posterUrl} alt={movie.title} />
            <h3>{movie.title}</h3>
            <p>Released: {movie.releaseDate}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MovieList;
