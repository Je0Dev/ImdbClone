import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router-dom';
import apiClient from '../services/apiService';
import MovieCardSkeleton from '../components/MovieCardSkeleton';
import WatchlistButton from '../components/WatchlistButton';
import MovieCard from '../components/MovieCard';

const HomePage = () => {
    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [debouncedSearchTerm, setDebouncedSearchTerm] = useState('');
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    // Debouncing effect for search
    useEffect(() => {
        const timerId = setTimeout(() => {
            setDebouncedSearchTerm(searchTerm);
            setCurrentPage(0);
        }, 500);
        return () => clearTimeout(timerId);
    }, [searchTerm]);

    const fetchMovies = useCallback(async (page, query) => {
        // Reset state before fetching
        setLoading(true);
        setError(null);

        try {
            const endpoint = query ? `/movies/search?title=${query}` : '/movies';
            const response = await apiClient.get(`${endpoint}?page=${page}&size=10`);
            setMovies(response.data.content);
            setTotalPages(response.data.totalPages);
        // eslint-disable-next-line no-unused-vars
        } catch (err) {
            setError('Failed to fetch movies. The server might be down.');
        } finally {
            setLoading(false);
        }
    }, []);

    // Effect to decide whether to fetch movies
    useEffect(() => {
        if (localStorage.getItem('token')) {
            fetchMovies(currentPage, debouncedSearchTerm);
        } else {
            // If there's no token, stop loading and show an explicit message.
            setLoading(false);
            setError('Please log in to discover movies.');
            setMovies([]); // Ensure movies array is empty
        }
    }, [currentPage, debouncedSearchTerm, fetchMovies]);

    // --- RENDER LOGIC ---

    const renderContent = () => {
        if (loading) {
            return (
                <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-6">
                {movies.map((movie) => (
                    <MovieCard movie={movie} key={movie.id} />
                ))}
            </div>
            );
        }

        if (error) {
            return <div className="text-center text-yellow-400 p-4 bg-yellow-900/50 rounded">{error}</div>;
        }

        if (movies.length === 0) {
            return <div className="text-center text-gray-400 p-4 bg-brand-light-dark rounded">No movies found.</div>;
        }

        return (
            <>
                <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-6">
                    {movies.map((movie) => (
                        <Link to={`/movies/${movie.id}`} key={movie.id} className="group">
                            <div className="relative bg-brand-light-dark rounded-lg shadow-lg overflow-hidden transform hover:-translate-y-2 transition-transform duration-300">
                                <WatchlistButton movieId={movie.id} />
                                <img src={movie.posterUrl} alt={movie.title} className="w-full h-96 object-cover" />
                                <div className="p-4">
                                    <h3 className="text-lg font-semibold truncate text-white group-hover:text-brand-yellow">{movie.title}</h3>
                                    <p className="text-sm text-gray-400">{movie.releaseDate}</p>
                                </div>
                            </div>
                        </Link>
                    ))}
                </div>
                {/* Pagination Controls */}
                <div className="flex justify-center items-center gap-4 mt-8">
                    <button
                        onClick={() => setCurrentPage(prev => Math.max(0, prev - 1))}
                        disabled={currentPage === 0 || totalPages === 0}
                        className="bg-gray-700 hover:bg-gray-600 text-white font-semibold py-2 px-4 border border-gray-600 rounded shadow disabled:opacity-50"
                    >
                        Previous
                    </button>
                    <span className="font-semibold text-gray-300">Page {totalPages > 0 ? currentPage + 1 : 0} of {totalPages}</span>
                    <button
                        onClick={() => setCurrentPage(prev => prev + 1)}
                        disabled={currentPage + 1 >= totalPages}
                        className="bg-gray-700 hover:bg-gray-600 text-white font-semibold py-2 px-4 border border-gray-600 rounded shadow disabled:opacity-50"
                    >
                        Next
                    </button>
                </div>
            </>
        );
    };

    return (
        <div>
            {/* Hero Section with Search Bar */}
            <div className="bg-brand-light-dark text-white text-center p-10 md:p-16 rounded-lg mb-8 shadow-xl">
                <h1 className="text-4xl md:text-5xl font-bold">Welcome to IMDb Clone</h1>
                <p className="text-lg md:text-xl mt-4 mb-6">Discover and review your favorite movies.</p>
                <div className="max-w-xl mx-auto">
                    <input
                        type="text"
                        placeholder="Search by title (e.g., Inception)..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        className="shadow appearance-none border rounded w-full py-3 px-4 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
                    />
                </div>
            </div>

            {/* Movie Grid Section */}
            <div>
                <h2 className="text-3xl font-bold mb-6 text-white">
                    {debouncedSearchTerm ? `Results for "${debouncedSearchTerm}"` : 'Top Movies'}
                </h2>
                {renderContent()}
            </div>
        </div>
    );
};

export default HomePage;
