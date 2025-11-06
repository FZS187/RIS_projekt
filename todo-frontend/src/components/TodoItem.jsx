import React from 'react'

// Komponenta prima ceo 'todo' objekat, i funkcije za interakciju
const TodoItem = ({ todo, toggleTodo, deleteTodo }) => {
  
  // Stil koji se primenjuje ako je zadatak završen
  const itemStyle = {
    // Precrtaj tekst ako je 'completed' true
    textDecoration: todo.completed ? 'line-through' : 'none', 
    // Opciono: boja teksta ako je završen
    color: todo.completed ? '#888' : '#fff', 
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: '5px 0',
  };

  return (
    <div style={itemStyle}>
      {/* Prikaz teksta zadatka */}
      <p style={{ margin: 0 }}>{todo.text}</p>
      
      <div>
        {/* Dugme za promenu statusa (klikom pozivamo toggleTodo sa ID-jem) */}
        <button 
          onClick={() => toggleTodo(todo.id)}
          // Menjamo tekst dugmeta u zavisnosti od statusa
          style={{ marginRight: '10px', background: todo.completed ? '#4CAF50' : '#f44336' }} 
        >
          {todo.completed ? 'Poništi' : 'Končaj'}
        </button>
        
        {/* Dugme za brisanje (klikom pozivamo deleteTodo sa ID-jem) */}
        <button 
          onClick={() => deleteTodo(todo.id)}
          style={{ background: '#333' }}
        >
          Obriši
        </button>
      </div>
    </div>
  )
}

export default TodoItem