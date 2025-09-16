import React, { createContext, useState, useContext, useEffect, useCallback } from 'react';
import authService from '../services/authService';
import watchlistService from '../services/watchlistService';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [watchlist, setWatchlist] = useState(new Set());

    const fetchWatchlist = useCallback(async () => {
        // Only try to fetch if a token exists
        if (localStorage.getItem('token')) {
            try { // <-- ADDED ERROR HANDLING
                const response = await watchlistService.getWatchlist();
                const watchlistIds = new Set(response.data.map(movie => movie.id));
                setWatchlist(watchlistIds);
            } catch (error) {
                console.error("Failed to fetch watchlist:", error);
                // Don't crash the app; just clear the local watchlist
                setWatchlist(new Set());
            }
        }
    }, []);

    useEffect(() => {
        fetchWatchlist();
    }, [fetchWatchlist]);

    const login = async (username, password) => {
        const response = await authService.login(username, password);
        localStorage.setItem('token', response.data.token);
        setToken(response.data.token);
        await fetchWatchlist(); // Fetch watchlist after login
    };

    const logout = () => {
        localStorage.removeItem('token');
        setToken(null);
        setWatchlist(new Set());
    };

    const addMovieToWatchlist = async (movieId) => {
        await watchlistService.addMovieToWatchlist(movieId);
        setWatchlist(prev => new Set(prev).add(movieId));
    };

    const removeMovieFromWatchlist = async (movieId) => {
        await watchlistService.removeMovieFromWatchlist(movieId);
        setWatchlist(prev => {
            const newSet = new Set(prev);
            newSet.delete(movieId);
            return newSet;
        });
    };

    const value = { token, login, logout, watchlist, addMovieToWatchlist, removeMovieFromWatchlist };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

// eslint-disable-next-line react-refresh/only-export-components
export const useAuth = () => {
    return useContext(AuthContext);
};
