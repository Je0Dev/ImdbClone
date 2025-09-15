import React, { useState, useEffect } from 'react';
import apiClient from '../services/apiService';

const HomePage = () => {
  // 1. State for movies, loading status, and errors
  const [movies, setMovies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

 useEffect(() => {
    const fetchMovies = async () => {
        try {
            // The hardcoded token is GONE! The interceptor adds it for us.
            const response = await apiClient.get('/movies?page=0&size=10');
            setMovies(response.data.content);
        } catch (err) {
            setError('Failed to fetch movies.');
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
          <Link to={`/movies/${movie.id}`} key={movie.id}>
    <div className="movie-card">
        <img src={movie.posterUrl} alt={movie.title} />
        <h3>{movie.title}</h3>
    </div>
</Link>
        ))}
      </div>
    </div>
  );
};

export default HomePage;
