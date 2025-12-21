import React from "react";

// Komponenta prima ceo 'todo' objekat, i funkcije za interakciju
const TodoItem = ({ todo, toggleTodo, deleteTodo }) => {
  // Stil koji se primenjuje ako je zadatak zavrsen
  const itemStyle = {
    textDecoration: todo.completed ? "line-through" : "none",
    color: todo.completed ? "#333" : "#fff",
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
    padding: "5px 0",
  };

  return (
    <div style={itemStyle}>
      <label style={{ margin: 0, display: "flex", alignItems: "center", gap: "8px" }}>
        <input
          type="checkbox"
          checked={!!todo.completed}
          onChange={() => toggleTodo(todo.id)}
        />
        <span>{todo.name}</span>
      </label>

      <button
        onClick={() => deleteTodo(todo.id)}
        style={{ background: "#333" }}
      >
        Obrisi
      </button>
    </div>
  );
};

export default TodoItem;
