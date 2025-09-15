import apiClient from './apiService';

const postReview = (movieId, text) => {
    return apiClient.post(`/movies/${movieId}/reviews`, { text });
};

const reviewService = {
    postReview,
};

export default reviewService;
