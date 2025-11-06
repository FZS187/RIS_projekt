import React, { useState } from 'react';

// Očekujemo da addTodo funkcija sada prima IME i DATUM
const TodoForm = ({ addTodo }) => {
  const [name, setName] = useState('');
  const [dueDate, setDueDate] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!name.trim()) return;

    // Pozivamo addTodo sa oba nova polja
    addTodo(name, dueDate);
    
    // Resetujemo formulare
    setName('');
    setDueDate('');
  };

  return (
    <form onSubmit={handleSubmit} className="todo-form">
      <input
        type="text"
        placeholder="Unesite ime zadatka..."
        value={name}
        onChange={(e) => setName(e.target.value)}
        className="todo-input"
      />
      
      <input
        type="date"
        placeholder="Rok"
        value={dueDate}
        onChange={(e) => setDueDate(e.target.value)}
        className="todo-date-input"
      />
      
      <button type="submit" className="todo-button">
        Dodaj
      </button>
    </form>
  );
};

export default TodoForm;