import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import watchlistService from '../services/watchlistService';
import MovieCardSkeleton from '../components/MovieCardSkeleton';

const WatchlistPage = () => {
    const [watchlist, setWatchlist] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchWatchlist = async () => {
            try {
                const response = await watchlistService.getWatchlist();
                setWatchlist(response.data);
            // eslint-disable-next-line no-unused-vars
            } catch (err) {
                setError('Failed to fetch watchlist.');
            } finally {
                setLoading(false);
            }
        };
        fetchWatchlist();
    }, []);

    if (loading) return (
        <div>
            <h1 className="text-4xl font-bold mb-8 text-gray-800">My Watchlist</h1>
            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-6">
                {[...Array(5)].map((_, i) => <MovieCardSkeleton key={i} />)}
            </div>
        </div>
    );
    if (error) return <div className="text-center text-red-500">{error}</div>;

    return (
        <div>
            <h1 className="text-4xl font-bold mb-8 text-gray-800">My Watchlist</h1>
            {watchlist.length > 0 ? (
                <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-6">
                    {watchlist.map((movie) => (
                        <Link to={`/movies/${movie.id}`} key={movie.id} className="group">
                             <div className="bg-white rounded-lg shadow-md overflow-hidden transform hover:-translate-y-1 transition-transform duration-300">
                                <img src={movie.posterUrl} alt={movie.title} className="w-full h-96 object-cover" />
                                <div className="p-4">
                                    <h3 className="text-lg font-semibold truncate group-hover:text-blue-600">{movie.title}</h3>
                                </div>
                            </div>
                        </Link>
                    ))}
                </div>
            ) : (
                <p className="text-gray-500">Your watchlist is empty. Add movies from the homepage!</p>
            )}
        </div>
    );
};

export default WatchlistPage;
