import React from 'react';

const MovieCardSkeleton = () => {
    return (
        <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <div className="w-full h-96 bg-gray-300 animate-pulse"></div>
            <div className="p-4">
                <div className="h-6 bg-gray-300 rounded w-3/4 mb-2 animate-pulse"></div>
                <div className="h-4 bg-gray-300 rounded w-1/2 animate-pulse"></div>
            </div>
        </div>
    );
};

export default MovieCardSkeleton;
