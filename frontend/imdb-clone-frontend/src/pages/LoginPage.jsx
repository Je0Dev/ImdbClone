import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import toast from 'react-hot-toast';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        const loginPromise = login(username, password);

        toast.promise(loginPromise, {
            loading: 'Logging in...',
            success: () => {
                navigate('/');
                return 'Logged in successfully!';
            },
            error: 'Login failed. Please check your credentials.',
        });
    };

    return (
        <div className="max-w-md mx-auto mt-10 p-8 border border-gray-700 bg-brand-light-dark rounded-lg shadow-lg">
            <form onSubmit={handleSubmit}>
                <h2 className="text-3xl font-bold text-center mb-6 text-white">Login</h2>
                <div className="mb-4">
                    <label htmlFor="username" className="block text-gray-300 text-sm font-bold mb-2">Username</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                        className="shadow appearance-none border border-gray-600 rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline focus:border-brand-yellow"
                    />
                </div>
                <div className="mb-6">
                    <label htmlFor="password" className="block text-gray-300 text-sm font-bold mb-2">Password</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        className="shadow appearance-none border border-gray-600 rounded w-full py-2 px-3 bg-gray-700 text-white mb-3 leading-tight focus:outline-none focus:shadow-outline focus:border-brand-yellow"
                    />
                </div>
                <button type="submit" className="bg-brand-yellow hover:bg-yellow-500 text-black font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline w-full">
                    Log In
                </button>
                <p className="text-center text-gray-500 text-xs mt-4">
                    Don't have an account? <Link to="/register" className="text-brand-yellow hover:underline">Register</Link>
                </p>
            </form>
        </div>
    );
};

export default LoginPage;
