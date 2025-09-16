import React from 'react';
import { useAuth } from '../context/AuthContext';
import toast from 'react-hot-toast'; // 1. Import toast

const WatchlistButton = ({ movieId }) => {
    const { watchlist, addMovieToWatchlist, removeMovieFromWatchlist, token } = useAuth();

    if (!token) return null;

    const isInWatchlist = watchlist.has(movieId);

    const handleClick = (e) => {
        e.preventDefault();
        e.stopPropagation();

        // 2. Use toast.promise for automatic loading/success/error messages
        toast.promise(
            isInWatchlist ? removeMovieFromWatchlist(movieId) : addMovieToWatchlist(movieId),
            {
                loading: 'Updating watchlist...',
                success: `Movie ${isInWatchlist ? 'removed from' : 'added to'} watchlist!`,
                error: 'Could not update watchlist.',
            }
        );
    };

    return (
        <button
            onClick={handleClick}
            className={`absolute top-2 right-2 text-white p-2 rounded-full z-10 text-xs font-bold transition-transform hover:scale-110 ${
                isInWatchlist ? 'bg-red-500 hover:bg-red-600' : 'bg-green-500 hover:bg-green-600'
            }`}
            title={isInWatchlist ? 'Remove from Watchlist' : 'Add to Watchlist'}
        >
           {isInWatchlist ? '✓' : '＋'}
        </button>
    );
};

export default WatchlistButton;
