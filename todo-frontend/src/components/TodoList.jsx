import React, { useState, useEffect } from 'react';
import axios from 'axios';
import SyncStatusBadge from './SyncStatusBadge';
import './TodoList.css';

const API_URL = 'http://localhost:8080/api/todos';

const TodoList = () => {
  const [todos, setTodos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newTodo, setNewTodo] = useState({ title: '', description: '', dueDate: '' });
  const [editingId, setEditingId] = useState(null);

  // Dohvati sve naloge sa sync statusima
  const fetchTodos = async () => {
    try {
      setLoading(true);
      const response = await axios.get(API_URL);
      setTodos(response.data);
      setError(null);
    } catch (err) {
      setError('Greška pri učitavanju nalog');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTodos();
    
    // Refresh sync statusa svake 3 sekunde
    const interval = setInterval(() => {
      fetchTodos();
    }, 3000);
    
    return () => clearInterval(interval);
  }, []);

  // Kreiraj novu nalog
  const handleCreateTodo = async (e) => {
    e.preventDefault();
    if (!newTodo.title.trim()) return;

    try {
      const response = await axios.post(API_URL, newTodo);
      setTodos([...todos, response.data]);
      setNewTodo({ title: '', description: '', dueDate: '' });
      
      // Refresh nakon 2 sekunde da vidimo promenu statusa
      setTimeout(fetchTodos, 2000);
    } catch (err) {
      setError('Greška pri kreiranju naloge');
      console.error(err);
    }
  };

  // Označi kao završeno/nezavršeno
  const handleToggleComplete = async (todo) => {
    try {
      const updated = { ...todo, completed: !todo.completed };
      await axios.put(`${API_URL}/${todo.id}`, updated);
      fetchTodos();
    } catch (err) {
      setError('Greška pri ažuriranju naloge');
      console.error(err);
    }
  };

  // Ručno pokreni sinhronizaciju
  const handleManualSync = async (todoId) => {
    try {
      await axios.post(`${API_URL}/${todoId}/sync`);
      fetchTodos();
    } catch (err) {
      setError('Greška pri pokretanju sinhronizacije');
      console.error(err);
    }
  };

  // Izbriši nalog
  const handleDelete = async (id) => {
    if (window.confirm('Da li ste sigurni da želite izbrisati ovu nalog?')) {
      try {
        await axios.delete(`${API_URL}/${id}`);
        setTodos(todos.filter(t => t.id !== id));
      } catch (err) {
        setError('Greška pri brisanju naloge');
        console.error(err);
      }
    }
  };

  if (loading) {
    return <div className="loading">Učitavanje nalog...</div>;
  }

  return (
    <div className="todo-container">
      <h1>📋 Moje Naloge</h1>
      
      {error && <div className="error-message">{error}</div>}
      
      {/* Forma za novu nalog */}
      <form onSubmit={handleCreateTodo} className="todo-form">
        <input
          type="text"
          placeholder="Naziv naloge..."
          value={newTodo.title}
          onChange={(e) => setNewTodo({ ...newTodo, title: e.target.value })}
          className="todo-input"
        />
        <input
          type="text"
          placeholder="Opis (opciono)..."
          value={newTodo.description}
          onChange={(e) => setNewTodo({ ...newTodo, description: e.target.value })}
          className="todo-input"
        />
        <input
          type="date"
          value={newTodo.dueDate}
          onChange={(e) => setNewTodo({ ...newTodo, dueDate: e.target.value })}
          className="todo-input"
        />
        <button type="submit" className="btn-add">Dodaj Nalog</button>
      </form>

      {/* Lista nalog */}
      <div className="todo-list">
        {todos.length === 0 ? (
          <p className="no-todos">Nema nalog. Dodajte novu nalog!</p>
        ) : (
          todos.map(todo => (
            <div key={todo.id} className={`todo-item ${todo.completed ? 'completed' : ''}`}>
              <div className="todo-content">
                <input
                  type="checkbox"
                  checked={todo.completed}
                  onChange={() => handleToggleComplete(todo)}
                  className="todo-checkbox"
                />
                <div className="todo-details">
                  <h3 className="todo-title">{todo.title}</h3>
                  {todo.description && (
                    <p className="todo-description">{todo.description}</p>
                  )}
                  {todo.dueDate && (
                    <span className="todo-date">📅 {todo.dueDate}</span>
                  )}
                </div>
              </div>
              
              {/* Sync Status Badge */}
              <div className="todo-actions">
                <SyncStatusBadge syncStatus={todo.syncStatus} />
                
                {/* Dugme za ručnu sinhronizaciju */}
                {todo.syncStatus?.status !== 'IN_PROGRESS' && (
                  <button 
                    onClick={() => handleManualSync(todo.id)}
                    className="btn-sync"
                    title="Ručna sinhronizacija"
                  >
                    🔄 Sync
                  </button>
                )}
                
                <button 
                  onClick={() => handleDelete(todo.id)}
                  className="btn-delete"
                >
                  🗑️
                </button>
              </div>
            </div>
          ))
        )}
      </div>
      
      {/* Informacija o auto-refresh */}
      <div className="info-box">
        ℹ️ Status sinhronizacije se automatski ažurira svake 3 sekunde
      </div>
    </div>
  );
};

export default TodoList;