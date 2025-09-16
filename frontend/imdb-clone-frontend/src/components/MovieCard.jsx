import React from 'react';
import { Link } from 'react-router-dom';
import WatchlistButton from './WatchlistButton';

const MovieCard = ({ movie }) => {
    return (
        <Link to={`/movies/${movie.id}`} className="group block rounded-lg overflow-hidden relative">
            <div className="relative">
                <img src={movie.posterUrl} alt={movie.title} className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105" />
                <WatchlistButton movieId={movie.id} />

                {/* Gradient overlay for text */}
                <div className="absolute bottom-0 left-0 right-0 h-2/5 bg-gradient-to-t from-black to-transparent" />

                <div className="absolute bottom-0 left-0 p-4">
                    <h3 className="text-lg font-bold text-white truncate">{movie.title}</h3>
                    <p className="text-sm text-gray-300">{new Date(movie.releaseDate).getFullYear()}</p>
                </div>

                {/* Average Rating Badge */}
                {movie.averageRating > 0 && (
                    <div className="absolute top-2 left-2 bg-brand-yellow text-black text-sm font-bold px-2 py-1 rounded">
                        ⭐ {movie.averageRating.toFixed(1)}
                    </div>
                )}
            </div>
        </Link>
    );
};

export default MovieCard;
