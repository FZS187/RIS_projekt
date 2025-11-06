import React, { useState } from 'react';

// Ova komponenta prima i editTodo funkciju
const TodoList = ({ todos, toggleTodo, deleteTodo, editTodo }) => {
  return (
    <div className="todo-list-container">
      {todos.map((todo) => (
        // Umesto TodoItem, sve rešavamo direktno ovde
        <TodoItem 
          key={todo.id} 
          todo={todo} 
          toggleTodo={toggleTodo} 
          deleteTodo={deleteTodo} 
          editTodo={editTodo} 
        />
      ))}
    </div>
  );
};

// Nova pomoćna komponenta za prikaz i uređivanje pojedinačnog zadatka
const TodoItem = ({ todo, toggleTodo, deleteTodo, editTodo }) => {
    // Stanje koje prati da li je zadatak u modu za uređivanje
    const [isEditing, setIsEditing] = useState(false);
    const [newName, setNewName] = useState(todo.name);
    const [newDueDate, setNewDueDate] = useState(todo.dueDate); 

    const handleEdit = () => {
        if (isEditing) {
            // Ako smo već u modu za uređivanje, čuvamo promene
            editTodo(todo.id, newName, newDueDate);
        }
        // Prebacujemo stanje
        setIsEditing(!isEditing); 
    };
    
    // Formatiranje datuma za prikaz
    const formattedDate = todo.dueDate ? new Date(todo.dueDate).toLocaleDateString('sr-RS') : 'Nije definisan';

    return (
        <div className={`todo-item ${todo.completed ? 'completed' : ''}`}>
            
            <div className="todo-content">
                {isEditing ? (
                    // MOD ZA UREĐIVANJE
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
                    </>
                ) : (
                    // MOD ZA PRIKAZ
                    <>
                        <span className="todo-name" onClick={() => toggleTodo(todo.id)}>
                            {todo.name}
                        </span>
                        <span className="todo-date">
                            Rok: {formattedDate}
                        </span>
                    </>
                )}
            </div>

            <div className="todo-actions">
                <button onClick={handleEdit} className="action-button edit-button">
                    {isEditing ? 'Sačuvaj' : 'Uredi'}
                </button>
                <button onClick={() => deleteTodo(todo.id)} className="action-button delete-button">
                    Obriši
                </button>
            </div>
        </div>
    );
};

export default TodoList;