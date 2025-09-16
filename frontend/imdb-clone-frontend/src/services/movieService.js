import apiClient from './apiService';

const importMovie = (title) => {
    return apiClient.post(`/movies/import?title=${encodeURIComponent(title)}`);
};

const movieService = {
    importMovie,
};

export default movieService;
