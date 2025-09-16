import React, { useState, useEffect, useCallback } from 'react';
import { useParams, Link } from 'react-router-dom';
import apiClient from '../services/apiService';
import { useAuth } from '../context/AuthContext';
import ReviewList from '../components/ReviewList';
import ReviewForm from '../components/ReviewForm';
import WatchlistButton from '../components/WatchlistButton';

const MovieDetailPage = () => {
    const { movieId } = useParams();
    const [movie, setMovie] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { token } = useAuth();

    const fetchMovie = useCallback(async () => {
        try {
            setLoading(true);
            const response = await apiClient.get(`/movies/${movieId}`);
            setMovie(response.data);
        // eslint-disable-next-line no-unused-vars
        } catch (err) {
            setError('Failed to fetch movie details.');
        } finally {
            setLoading(false);
        }
    }, [movieId]);

    useEffect(() => {
        fetchMovie();
    }, [fetchMovie]);

    const handleReviewPosted = () => {
        fetchMovie(); // Re-fetch data to show the new review
    };

    if (loading) return <div className="text-center p-10">Loading...</div>;
    if (error) return <div className="bg-red-100 text-red-700 p-4 rounded text-center">{error}</div>;
    if (!movie) return <div className="bg-yellow-100 text-yellow-700 p-4 rounded text-center">Movie not found.</div>;

    return (
       <div className="max-w-6xl mx-auto">
        <div className="flex flex-col md:flex-row gap-8">
            <div className="md:w-1/3 relative">
                <img src={movie.posterUrl} alt={movie.title} className="rounded-lg shadow-2xl w-full" />
                <div className="absolute top-4 right-4">
                   <WatchlistButton movieId={movie.id} />
                </div>
            </div>
            <div className="md:w-2/3 text-white">
                <h1 className="text-4xl lg:text-5xl font-bold">{movie.title}</h1>
                <div className="flex items-center gap-4 mt-2">
                    <p className="text-lg text-gray-400">{new Date(movie.releaseDate).getFullYear()}</p>
                    {movie.averageRating > 0 && (
                        <span className="flex items-center gap-1 text-lg font-bold text-brand-yellow">
                            ⭐ {movie.averageRating.toFixed(1)}
                        </span>
                    )}
                </div>
                <div className="flex flex-wrap gap-2 my-4">
                    {movie.genres.map(genre => (
                        <span key={genre} className="bg-gray-700 text-gray-300 text-xs font-semibold px-2.5 py-0.5 rounded-full">
                            {genre}
                        </span>
                    ))}
                </div>
                <p className="text-md text-gray-300 mt-4">
                    <strong>Starring:</strong> {movie.actors.join(', ')}
                </p>
                <p className="mt-4 text-gray-300 leading-relaxed">{movie.description}</p>
            </div>
        </div>

        <hr className="my-8 border-gray-700" />

            <div className="reviews-section">
                <h2 className="text-3xl font-bold text-gray-800 mb-5">Reviews</h2>
                <ReviewList reviews={movie.reviews} />
                {token ? (
                    <ReviewForm movieId={movieId} onReviewPosted={handleReviewPosted} />
                ) : (
                    <p className="mt-4 text-gray-600">
                        You must be <Link to="/login" className="text-blue-500 hover:underline">logged in</Link> to write a review.
                    </p>
                )}
            </div>
        </div>
    );
};

export default MovieDetailPage;
