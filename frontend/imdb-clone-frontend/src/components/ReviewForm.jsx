import React, { useState } from 'react';
import reviewService from '../services/reviewService';

const ReviewForm = ({ movieId, onReviewPosted }) => {
    const [text, setText] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await reviewService.postReview(movieId, text);
            setText('');
            onReviewPosted(); // Notify parent to refresh reviews
        // eslint-disable-next-line no-unused-vars
        } catch (err) {
            setError('Failed to post review.');
        }
    };

    return (
        <form onSubmit={handleSubmit} className="review-form">
            <h4>Write a Review</h4>
            {error && <p className="error-message">{error}</p>}
            <textarea
                value={text}
                onChange={(e) => setText(e.target.value)}
                placeholder="What did you think?"
                required
            />
            <button type="submit">Submit Review</button>
        </form>
    );
};

export default ReviewForm;
