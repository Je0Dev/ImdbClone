import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Layout = ({ children }) => {
    const { token, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/');
    };

    return (
        <div className="bg-brand-dark min-h-screen font-sans text-white">
            <header className="bg-brand-light-dark border-b border-gray-700">
                <nav className="container mx-auto px-6 py-4 flex justify-between items-center">
                    <Link to="/" className="text-2xl font-bold text-brand-yellow">
                        IMDb Clone
                    </Link>
                    <div className="flex items-center space-x-4">
                        {token ? (
                            <>
                                <Link to="/watchlist" className="text-gray-300 hover:text-brand-yellow">My Watchlist</Link>
                                <button onClick={handleLogout} className="bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-4 rounded">
                                    Logout
                                </button>
                            </>
                        ) : (
                            <>
                                <Link to="/login" className="text-gray-300 hover:text-brand-yellow">Login</Link>
                                <Link to="/register" className="bg-brand-yellow hover:bg-yellow-500 text-black font-bold py-2 px-4 rounded">
                                    Register
                                </Link>
                            </>
                        )}
                    </div>
                </nav>
            </header>
            <main className="container mx-auto px-6 py-8">
                {/* This is where your pages will be rendered */}
                {children}
            </main>
        </div>
    );
};

export default Layout;
