import React, { useState } from 'react';
import reviewService from '../services/reviewService';
import toast from 'react-hot-toast'; // Import toast

const ReviewForm = ({ movieId, onReviewPosted }) => {
    const [text, setText] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);
        try {
            await reviewService.postReview(movieId, text);
            toast.success('Review posted successfully!');
            setText('');
            onReviewPosted(); // Notify parent to refresh reviews
        // eslint-disable-next-line no-unused-vars
        } catch (err) {
            toast.error('Failed to post review.');
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="mt-6">
            <h4 className="text-xl font-semibold mb-2">Write a Review</h4>
            <textarea
                value={text}
                onChange={(e) => setText(e.target.value)}
                placeholder="What did you think?"
                required
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline min-h-[100px]"
            />
            <button type="submit" disabled={isSubmitting} className="mt-2 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline disabled:opacity-50">
                {isSubmitting ? 'Submitting...' : 'Submit Review'}
            </button>
        </form>
    );
};

export default ReviewForm;
