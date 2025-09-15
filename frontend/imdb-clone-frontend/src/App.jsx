import React from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import HomePage from './pages/HomePage';
import MovieDetailPage from './pages/MovieDetailPage';
import LoginPage from './pages/LoginPage';
// import RegisterPage from './pages/RegisterPage'; // Assuming you created this
import { useAuth } from './context/AuthContext';
import './App.css';

function App() {
  const { token, logout } = useAuth();

  return (
    <div className="App">
      <header className="app-header">
        <Link to="/" className="logo"><h2>IMDb Clone</h2></Link>
        <nav>
          {token ? (
            <button onClick={logout} className="nav-link">Logout</button>
          ) : (
            <>
              <Link to="/login" className="nav-link">Login</Link>
              {/* <Link to="/register" className="nav-link">Register</Link> */}
            </>
          )}
        </nav>
      </header>
      <main>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/movies/:movieId" element={<MovieDetailPage />} />
          <Route path="/login" element={<LoginPage />} />
          {/* <Route path="/register" element={<RegisterPage />} /> */}
        </Routes>
      </main>
    </div>
  );
}

export default App;
