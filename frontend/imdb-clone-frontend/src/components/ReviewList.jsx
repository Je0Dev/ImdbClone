import React from 'react';

const ReviewList = ({ reviews }) => {
    if (!reviews || reviews.length === 0) {
        return <p>No reviews yet.</p>;
    }

    return (
        <div className="review-list">
            <h3>Reviews</h3>
            {reviews.map(review => (
                <div key={review.id} className="review-item">
                    <p><strong>{review.username}</strong> ({new Date(review.createdAt).toLocaleDateString()})</p>
                    <p>{review.text}</p>
                </div>
            ))}
        </div>
    );
};

export default ReviewList;
