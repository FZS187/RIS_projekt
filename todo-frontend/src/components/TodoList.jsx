import React, { useState, useEffect } from 'react';
import axios from 'axios';
import SyncStatusBadge from './SyncStatusBadge';
import './TodoList.css';

const API_URL = 'http://localhost:8080/api/todos';

const TodoList = ({ todos, onToggle, onDelete, onEdit }) => {
  const [localTodos, setLocalTodos] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [editingId, setEditingId] = useState(null);
  const [editForm, setEditForm] = useState({ name: '', dueDate: '', reminderAt: '' });

  // ✅ Refresh todos svake 3 sekunde da vidimo promenu sync statusa
  useEffect(() => {
    const fetchTodosWithSync = async () => {
      try {
        const response = await axios.get(API_URL, { withCredentials: true });
        setLocalTodos(response.data);
      } catch (err) {
        console.error('Greška pri učitavanju:', err);
      }
    };

    fetchTodosWithSync();

    const interval = setInterval(() => {
      fetchTodosWithSync();
    }, 3000); // Svake 3 sekunde

    return () => clearInterval(interval);
  }, []);

  // Sync sa parent todos prop-om
  useEffect(() => {
    if (todos && todos.length > 0) {
      setLocalTodos(todos);
    }
  }, [todos]);

  // ✅ Ručno pokreni sinhronizaciju
  const handleManualSync = async (todoId) => {
    try {
      setLoading(true);
      await axios.post(`${API_URL}/${todoId}/sync`, {}, { withCredentials: true });
      
      // Refresh nakon 2 sekunde da vidimo rezultat
      setTimeout(async () => {
        const response = await axios.get(API_URL, { withCredentials: true });
        setLocalTodos(response.data);
        setLoading(false);
      }, 2000);
    } catch (err) {
      setError('Greška pri pokretanju sinhronizacije');
      console.error(err);
      setLoading(false);
    }
  };

  // Započni uređivanje
  const startEdit = (todo) => {
    setEditingId(todo.id);
    setEditForm({
      name: todo.title || todo.name || '',
      dueDate: todo.dueDate || '',
      reminderAt: todo.reminderAt || ''
    });
  };

  // Sačuvaj izmene
  const saveEdit = (todoId) => {
    if (onEdit) {
      onEdit(todoId, editForm.name, editForm.dueDate, editForm.reminderAt);
    }
    setEditingId(null);
    setEditForm({ name: '', dueDate: '', reminderAt: '' });
  };

  // Otkaži uređivanje
  const cancelEdit = () => {
    setEditingId(null);
    setEditForm({ name: '', dueDate: '', reminderAt: '' });
  };

  // ✅ TASK 5: Prikaži user-friendly poruku
  const getUserMessage = (syncStatus) => {
    if (!syncStatus) return null;
    
    if (syncStatus.userMessage) {
      return syncStatus.userMessage;
    }
    
    // Fallback ako userMessage ne postoji
    if (syncStatus.isSuccess) return '✅ Sinhronizacija uspešna';
    if (syncStatus.isFailed) return `❌ Greška: ${syncStatus.errorMessage || 'Nepoznata greška'}`;
    if (syncStatus.isInProgress) return '⏳ Sinhronizacija u toku...';
    return '⏸️ Čeka na sinhronizaciju';
  };

  if (localTodos.length === 0) {
    return (
      <div className="no-todos">
        <p>📝 Nema zadataka. Dodajte novu nalog!</p>
      </div>
    );
  }

  return (
    <div className="todo-list">
      {error && <div className="error-message">{error}</div>}
      
      {localTodos.map((todo) => (
        <div 
          key={todo.id} 
          className={`todo-item ${todo.completed ? 'completed' : ''}`}
        >
          {/* Checkbox i Naziv */}
          <div className="todo-content">
            <input
              type="checkbox"
              checked={todo.completed || false}
              onChange={() => onToggle && onToggle(todo.id)}
              className="todo-checkbox"
              disabled={loading}
            />
            
            <div className="todo-details">
              {editingId === todo.id ? (
                // Edit mode
                <div>
                  <input
                    type="text"
                    value={editForm.name}
                    onChange={(e) => setEditForm({ ...editForm, name: e.target.value })}
                    className="todo-input"
                    style={{ marginBottom: '8px' }}
                  />
                  <input
                    type="date"
                    value={editForm.dueDate}
                    onChange={(e) => setEditForm({ ...editForm, dueDate: e.target.value })}
                    className="todo-input"
                    style={{ marginBottom: '8px' }}
                  />
                  <input
                    type="datetime-local"
                    value={editForm.reminderAt}
                    onChange={(e) => setEditForm({ ...editForm, reminderAt: e.target.value })}
                    className="todo-input"
                  />
                </div>
              ) : (
                // View mode
                <>
                  <h3 className="todo-title">
                    {todo.title || todo.name}
                  </h3>
                  {todo.description && (
                    <p className="todo-description">{todo.description}</p>
                  )}
                  {todo.dueDate && (
                    <span className="todo-date">📅 {todo.dueDate}</span>
                  )}
                  {todo.category && (
                    <span className="todo-category" style={{
                      marginLeft: '8px',
                      padding: '2px 8px',
                      background: '#f0f0f0',
                      borderRadius: '4px',
                      fontSize: '12px'
                    }}>
                      {todo.category}
                    </span>
                  )}
                  {todo.priority && (
                    <span className="todo-priority" style={{
                      marginLeft: '8px',
                      padding: '2px 8px',
                      background: todo.priority === 'HIGH' ? '#ffe0e0' : '#f0f0f0',
                      borderRadius: '4px',
                      fontSize: '12px'
                    }}>
                      {todo.priority}
                    </span>
                  )}
                </>
              )}
            </div>
          </div>
          
          {/* ✅ TASK 4: Sync Status Badge - Jasno prikazuje status */}
          <div className="todo-actions">
            {todo.syncStatus && (
              <div style={{ display: 'flex', flexDirection: 'column', gap: '4px', alignItems: 'flex-end' }}>
                <SyncStatusBadge syncStatus={todo.syncStatus} />
                
                {/* ✅ TASK 5: Razumljivo obvestilo za korisnika */}
                <span style={{
                  fontSize: '11px',
                  color: '#666',
                  fontStyle: 'italic',
                  maxWidth: '200px',
                  textAlign: 'right'
                }}>
                  {getUserMessage(todo.syncStatus)}
                </span>
              </div>
            )}
            
            {/* Edit/Save/Cancel dugmad */}
            {editingId === todo.id ? (
              <>
                <button 
                  onClick={() => saveEdit(todo.id)}
                  className="btn-sync"
                  style={{ background: '#28a745' }}
                >
                  💾 Sačuvaj
                </button>
                <button 
                  onClick={cancelEdit}
                  className="btn-delete"
                  style={{ background: '#6c757d' }}
                >
                  ❌ Otkaži
                </button>
              </>
            ) : (
              <>
                {/* Dugme za ručnu sinhronizaciju */}
                {todo.syncStatus?.status !== 'IN_PROGRESS' && (
                  <button 
                    onClick={() => handleManualSync(todo.id)}
                    className="btn-sync"
                    title="Ručna sinhronizacija"
                    disabled={loading}
                  >
                    🔄 Sync
                  </button>
                )}
                
                {/* Edit dugme */}
                <button 
                  onClick={() => startEdit(todo)}
                  className="btn-sync"
                  style={{ background: '#ffc107', color: '#000' }}
                  title="Uredi nalog"
                >
                  ✏️
                </button>
                
                {/* Delete dugme */}
                <button 
                  onClick={() => onDelete && onDelete(todo.id)}
                  className="btn-delete"
                  title="Izbriši nalog"
                >
                  🗑️
                </button>
              </>
            )}
          </div>
        </div>
      ))}
      
      {/* ✅ Informacija o auto-refresh */}
      <div className="info-box" style={{ marginTop: '20px' }}>
        ℹ️ Status sinhronizacije se automatski ažurira svake 3 sekunde
      </div>
    </div>
  );
};

export default TodoList;