import apiClient from './apiService';

const getWatchlist = () => {
    return apiClient.get('/watchlist');
};

const addMovieToWatchlist = (movieId) => {
    return apiClient.post(`/watchlist/movies/${movieId}`);
};

const removeMovieFromWatchlist = (movieId) => {
    return apiClient.delete(`/watchlist/movies/${movieId}`);
};

const watchlistService = {
    getWatchlist,
    addMovieToWatchlist,
    removeMovieFromWatchlist,
};

export default watchlistService;
