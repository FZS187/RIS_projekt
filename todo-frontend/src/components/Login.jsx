import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Auth.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    // TODO: Tukaj bo API klic na backend
    // Za zdaj samo simuliramo uspešno prijavo
    if (username && password) {
      localStorage.setItem('user', JSON.stringify({ username }));
      navigate('/todos');
    } else {
      setError('Prosim, vnesite uporabniško ime in geslo');
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>Prijava</h2>
        {error && <div className="error-message">{error}</div>}
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Uporabniško ime</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Vnesite uporabniško ime"
              required
            />
          </div>

          <div className="form-group">
            <label>Geslo</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Vnesite geslo"
              required
            />
          </div>

          <button type="submit" className="auth-button">
            Prijavi se
          </button>
        </form>

        <p className="auth-switch">
          Nimate računa? <span onClick={() => navigate('/register')}>Registrirajte se</span>
        </p>
      </div>
    </div>
  );
};

export default Login;