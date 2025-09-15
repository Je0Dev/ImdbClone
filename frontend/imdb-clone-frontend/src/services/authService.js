import apiClient from './apiService';

// Define functions that use the imported apiClient
const register = (username, password) => {
  return apiClient.post('/auth/register', { username, password });
};

const login = (username, password) => {
  return apiClient.post('/auth/login', { username, password });
};

// Bundle the functions into a service object
const authService = {
  register,
  login,
};

export default authService;
