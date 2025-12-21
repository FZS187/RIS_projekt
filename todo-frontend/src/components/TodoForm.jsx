import React, { useEffect, useState } from "react";

const API_URL = "http://localhost:8080/api/todos";
const FALLBACK_CATEGORIES = ["WORK", "PERSONAL", "SHOPPING", "HEALTH", "EDUCATION", "OTHER"];
const FALLBACK_PRIORITIES = ["HIGH", "MEDIUM", "LOW"];

// Ocekujemo da onAdd prima ime, rok, reminder, kategoriju i prioritet
const TodoForm = ({ onAdd }) => {
  const [name, setName] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [reminderAt, setReminderAt] = useState("");
  const [category, setCategory] = useState("OTHER");
  const [priority, setPriority] = useState("MEDIUM");
  const [categories, setCategories] = useState(FALLBACK_CATEGORIES);
  const [priorities, setPriorities] = useState(FALLBACK_PRIORITIES);

  useEffect(() => {
    const fetchOptions = async () => {
      try {
        const [categoryRes, priorityRes] = await Promise.all([
          fetch(`${API_URL}/categories`),
          fetch(`${API_URL}/priorities`),
        ]);

        if (categoryRes.ok) {
          const data = await categoryRes.json();
          if (Array.isArray(data) && data.length > 0) {
            setCategories(data);
          }
        }

        if (priorityRes.ok) {
          const data = await priorityRes.json();
          if (Array.isArray(data) && data.length > 0) {
            setPriorities(data);
          }
        }
      } catch (err) {
        console.error("Greska pri ucitavanju opcija:", err);
      }
    };

    fetchOptions();
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!name.trim()) return;

    onAdd(name, dueDate, reminderAt, category, priority);
    setName("");
    setDueDate("");
    setReminderAt("");
    setCategory("OTHER");
    setPriority("MEDIUM");
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

      <div className="form-group">
        <label style={{ color: "#000", fontWeight: 600, display: "block" }}>
          Kategorija
        </label>
        <select
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          className="todo-date-input"
        >
          {categories.map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
      </div>

      <div className="form-group">
        <label style={{ color: "#000", fontWeight: 600, display: "block" }}>
          Prioriteta
        </label>
        <select
          value={priority}
          onChange={(e) => setPriority(e.target.value)}
          className="todo-date-input"
        >
          {priorities.map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
      </div>

      <button type="submit" className="todo-button">
        Dodaj
      </button>
    </form>
  );
};

export default TodoForm;
