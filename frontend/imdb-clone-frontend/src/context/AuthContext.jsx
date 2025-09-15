import React, { createContext, useState, useContext } from 'react';
import authService from '../services/authService';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem('token'));

    const login = async (username, password) => {
        const response = await authService.login(username, password);
        localStorage.setItem('token', response.data.token);
        setToken(response.data.token);
    };

    const logout = () => {
        localStorage.removeItem('token');
        setToken(null);
    };

    const value = { token, login, logout };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

// eslint-disable-next-line react-refresh/only-export-components
export const useAuth = () => {
    return useContext(AuthContext);
};
