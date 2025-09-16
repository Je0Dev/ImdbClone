import React from 'react';

const ReviewList = ({ reviews }) => {
    return (
        <div className="space-y-4">
            {reviews && reviews.length > 0 ? (
                reviews.map(review => (
                    <div key={review.id} className="bg-brand-light-dark p-4 rounded-lg">
                        <p className="font-bold text-brand-yellow">{review.username}</p>
                        <p className="text-sm text-gray-400 mb-2">
                            {new Date(review.createdAt).toLocaleDateString()}
                        </p>
                        <p className="text-gray-300">{review.text}</p>
                    </div>
                ))
            ) : (
                <p className="text-gray-500">No reviews yet. Be the first to write one!</p>
            )}
        </div>
    );
};

export default ReviewList;
