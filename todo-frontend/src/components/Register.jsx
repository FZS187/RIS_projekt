import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Auth.css';

const Register = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    // Validacija
    if (password !== confirmPassword) {
      setError('Gesli se ne ujemata');
      return;
    }

    if (password.length < 6) {
      setError('Geslo mora imeti vsaj 6 znakov');
      return;
    }

    // TODO: Tukaj bo API klic na backend
    if (username && email && password) {
      alert('Registracija uspešna! Lahko se prijavite.');
      navigate('/login');
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>Registracija</h2>
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
            <label>E-pošta</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Vnesite e-pošto"
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

          <div className="form-group">
            <label>Potrditev gesla</label>
            <input
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              placeholder="Potrdite geslo"
              required
            />
          </div>

          <button type="submit" className="auth-button">
            Registriraj se
          </button>
        </form>

        <p className="auth-switch">
          Že imate račun? <span onClick={() => navigate('/login')}>Prijavite se</span>
        </p>
      </div>
    </div>
  );
};

export default Register;