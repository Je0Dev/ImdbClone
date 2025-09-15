import React, { useState, useEffect, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import apiClient from '../services/apiService';
import { useAuth } from '../context/AuthContext';
import ReviewList from '../components/ReviewList';
import ReviewForm from '../components/ReviewForm';

import {
    Box, Heading, Text, Image, Spinner, Alert, AlertIcon,
    Stack, HStack, Tag, Divider
} from '@chakra-ui/react';

const MovieDetailPage = () => {
    const { movieId } = useParams();
    const [movie, setMovie] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { token } = useAuth();

    const fetchMovie = useCallback(async () => {
        try {
            setLoading(true);
            const response = await apiClient.get(`/movies/${movieId}`);
            setMovie(response.data);
        // eslint-disable-next-line no-unused-vars
        } catch (err) {
            setError('Failed to fetch movie details.');
        } finally {
            setLoading(false);
        }
    }, [movieId]);

    useEffect(() => {
        fetchMovie();
    }, [fetchMovie]);

    const handleReviewPosted = () => {
        // Re-fetch the movie data to get the latest reviews
        fetchMovie();
    };

    if (loading) return <Spinner size="xl" />;
    if (error) return <Alert status="error"><AlertIcon />{error}</Alert>;
    if (!movie) return <Alert status="warning"><AlertIcon />Movie not found.</Alert>;

    return (
        <Box p={5} maxW="1200px" mx="auto">
            <Stack direction={{ base: 'column', md: 'row' }} spacing={8}>
                <Image
                    src={movie.posterUrl}
                    alt={movie.title}
                    boxSize={{ base: '100%', md: '400px' }}
                    objectFit="cover"
                    borderRadius="md"
                />
                <Stack spacing={4}>
                    <Heading as="h1" size="2xl">{movie.title}</Heading>
                    <Text fontSize="lg" color="gray.500"><strong>Release Date:</strong> {movie.releaseDate}</Text>
                    <HStack spacing={2}>
                        {movie.genres.map(genre => <Tag key={genre} size="md" colorScheme="teal">{genre}</Tag>)}
                    </HStack>
                    <Text><strong>Starring:</strong> {movie.actors.join(', ')}</Text>
                    <Text fontSize="md" mt={4}>{movie.description}</Text>
                </Stack>
            </Stack>

            <Divider my={10} />

            <Box>
                <Heading as="h2" size="xl" mb={5}>Reviews</Heading>
                <ReviewList reviews={movie.reviews} />
                {token ? (
                    <ReviewForm movieId={movieId} onReviewPosted={handleReviewPosted} />
                ) : (
                    <Text mt={4}>You must be <Link to="/login" style={{color: 'teal'}}>logged in</Link> to write a review.</Text>
                )}
            </Box>
        </Box>
    );
};

export default MovieDetailPage;
