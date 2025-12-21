import React, { useState } from "react";

const TodoList = ({ todos, onToggle, onDelete, onEdit }) => {
  return (
    <div className="todo-list-container">
      {todos.map((todo) => (
        <TodoItem
          key={todo.id}
          todo={todo}
          onToggle={onToggle}
          onDelete={onDelete}
          onEdit={onEdit}
        />
      ))}
    </div>
  );
};

const TodoItem = ({ todo, onToggle, onDelete, onEdit }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [newName, setNewName] = useState(todo.name);
  const [newDueDate, setNewDueDate] = useState(todo.dueDate);
  const [newReminderAt, setNewReminderAt] = useState(todo.reminderAt || "");

  const handleEdit = () => {
    if (isEditing) {
      onEdit(todo.id, newName, newDueDate, newReminderAt);
    }
    setIsEditing(!isEditing);
  };

  const handleExtendDueDate = () => {
    const baseDate = newDueDate ? new Date(newDueDate) : new Date();
    baseDate.setDate(baseDate.getDate() + 1);
    const extended = baseDate.toISOString().slice(0, 10);
    setNewDueDate(extended);
    onEdit(todo.id, newName, extended, newReminderAt);
  };

  const formattedDate = todo.dueDate
    ? new Date(todo.dueDate).toLocaleDateString("sr-RS")
    : "Nije definisan";
  const isOverdue =
    !todo.completed &&
    todo.dueDate &&
    new Date(todo.dueDate).setHours(0, 0, 0, 0) <
      new Date().setHours(0, 0, 0, 0);

  // ✅ FIX: Proveri da reminderAt nije prazan string
  const formattedReminder =
    todo.reminderAt && todo.reminderAt.trim() !== ""
      ? (() => {
          const dt = new Date(todo.reminderAt);
          // Proveri da li je datum validan
          if (isNaN(dt.getTime())) return null;

          const dateStr = dt.toLocaleDateString("sr-RS");
          const timeStr = dt.toLocaleTimeString("sr-RS", {
            hour: "2-digit",
            minute: "2-digit",
          });
          return `Opomnik: ${dateStr} u ${timeStr}`;
        })()
      : null;

  return (
    <div
      className={`todo-item ${todo.completed ? "completed" : ""}`}
      style={{
        color: "#000",
        margin: "12px",
        paddingBottom: "8px",
        borderBottom: "1px solid #e2e8f0",
      }}
    >
      <div className="todo-content">
        {isEditing ? (
          <>
            <input
              type="text"
              value={newName}
              onChange={(e) => setNewName(e.target.value)}
              className="edit-input"
            />
            <input
              type="date"
              value={newDueDate}
              onChange={(e) => setNewDueDate(e.target.value)}
              className="edit-date-input"
            />
            <input
              type="datetime-local"
              value={newReminderAt}
              onChange={(e) => setNewReminderAt(e.target.value)}
              className="edit-date-input"
            />
          </>
        ) : (
          <>
            <span
              className="todo-name"
              style={{
                color: "#000",
                fontWeight: 600,
                fontSize: "16px",
                lineHeight: "1.4",
              }}
            >
              {todo.name}, Rok: {formattedDate}
              {isOverdue && (
                <span
                  style={{
                    marginLeft: "8px",
                    color: "#e53e3e",
                    fontWeight: 700,
                    fontSize: "12px",
                  }}
                >
                  Kasni
                </span>
              )}
              {todo.completed && (
                <span
                  style={{
                    marginLeft: "8px",
                    color: "#38a169",
                    fontWeight: 700,
                    fontSize: "12px",
                  }}
                >
                  Zavrseno
                </span>
              )}
            </span>
            {formattedReminder && (
              <span
                style={{
                  color: "#e53e3e",
                  fontSize: "13px",
                  fontWeight: 700,
                  display: "block",
                  marginTop: "4px",
                }}
              >
                {formattedReminder}
              </span>
            )}
          </>
        )}
      </div>

      <div className="todo-actions">
        <button
          onClick={() => onToggle(todo.id)}
          className="action-button edit-button"
        >
          {todo.completed ? "Vrati" : "Zavrseno"}
        </button>
        <button onClick={handleEdit} className="action-button edit-button">
          {isEditing ? "Sacuvaj" : "Uredi"}
        </button>
        <button
          onClick={handleExtendDueDate}
          className="action-button edit-button"
        >
          Produzi rok +1 dan
        </button>
        <button
          onClick={() => onDelete(todo.id)}
          className="action-button delete-button"
        >
          Obrisi
        </button>
      </div>
    </div>
  );
};

export default TodoList;
