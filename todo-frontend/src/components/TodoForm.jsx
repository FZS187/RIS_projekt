import React, { useState } from "react";

// Očekujemo da onAdd prima ime, rok i reminder
const TodoForm = ({ onAdd }) => {
  const [name, setName] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [reminderAt, setReminderAt] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!name.trim()) return;

    onAdd(name, dueDate, reminderAt);
    setName("");
    setDueDate("");
    setReminderAt("");
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

      <div className="form-group">
        <label style={{ color: "#000", fontWeight: 600, display: "block" }}>
          Postavi rok
        </label>
        <input
          type="date"
          value={dueDate}
          onChange={(e) => setDueDate(e.target.value)}
          className="todo-date-input"
        />
      </div>

      <div className="form-group">
        <label style={{ color: "#000", fontWeight: 600, display: "block" }}>
          Opomnik (datum i vreme)
        </label>
        <input
          type="datetime-local"
          value={reminderAt}
          onChange={(e) => setReminderAt(e.target.value)}
          className="todo-date-input"
        />
      </div>

      <button type="submit" className="todo-button">
        Dodaj
      </button>
    </form>
  );
};

export default TodoForm;
