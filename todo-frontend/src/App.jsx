import React, { useState, useEffect, useCallback } from 'react'; 
import './App.css';
import TodoForm from './components/TodoForm';
import TodoList from './components/TodoList';
import FilterForm from './components/FilterForm'; // NOVO: Komponenta za filtriranje

const API_URL = 'http://localhost:8080/api/todos';

function App() {
  const [todos, setTodos] = useState([]);
  // NOVO: Stanje za čuvanje trenutnih filtera (inicijalno: prazna pretraga, svi zadaci)
  const [filter, setFilter] = useState({ search: '', status: 'all' }); 

  // NOVO: Asinhrona funkcija za dohvatanje, koristi trenutne filtere
  const fetchTodos = useCallback(() => {
    // 1. Kreiranje Query Stringa: npr. ?search=posao&status=active
    const queryString = new URLSearchParams({
        search: filter.search,
        status: filter.status
    }).toString();
    
    // 2. Učitavanje sa backend-a sa Query Parametrima
    fetch(`${API_URL}?${queryString}`)
        .then(response => response.json())
        .then(data => setTodos(data))
        .catch(error => console.error('Greška pri dohvatanju zadataka:', error));
  }, [filter]); // Funkcija se menja samo kada se filter stanje promeni

  // UČITAVANJE (GET) - Pokreće se pri mountovanju i kad god se filter promeni
  useEffect(() => {
    fetchTodos();
  }, [fetchTodos]); 

  // NOVO: Funkcija za promenu filtera (poziva se iz FilterForm komponente)
  const handleFilterChange = (search, status) => {
      // Postavljamo novo filter stanje, što automatski pokreće fetchTodos preko useEffect-a
      setFilter({ search, status });
  };

  // Pomoćna funkcija za ponovno učitavanje liste nakon POST, PUT, DELETE
  const refreshTodos = () => {
    // Nakon svake promene na serveru, ponovo učitaj celu filtriranu listu
    fetchTodos(); 
  };
    
  // 1. DODAVANJE (POST) - Poziva refreshTodos nakon uspeha
  const addTodo = (name, dueDate) => {
    if (!name.trim()) return; 
    const newTodo = { name: name, completed: false, dueDate: dueDate || null };

    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTodo),
    })
    .then(response => response.json())
    .then(savedTodo => {
        refreshTodos(); 
    })
    .catch(error => console.error('Greška pri dodavanju:', error));
  };

  // 2. UREĐIVANJE (PUT) - Poziva refreshTodos nakon uspeha
  const editTodo = (id, newName, newDueDate) => {
    const todoToUpdate = todos.find(t => t.id === id);
    if (!todoToUpdate) return;
    const updatedTodo = { ...todoToUpdate, name: newName, dueDate: newDueDate };

    fetch(`${API_URL}/${id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(updatedTodo), })
    .then(response => {
        if (response.ok) {
            refreshTodos(); 
        } else {
             console.error('Neuspešno uređivanje na serveru');
        }
    })
    .catch(error => console.error('Greška pri uređivanju:', error));
  };
    
  // 3. TOGGLE (PUT) - Poziva refreshTodos nakon uspeha
  const toggleTodo = (id) => {
    const todoToUpdate = todos.find(t => t.id === id);
    if (!todoToUpdate) return;
    const updatedTodo = { ...todoToUpdate, completed: !todoToUpdate.completed };

    fetch(`${API_URL}/${id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(updatedTodo), })
    .then(response => {
        if (response.ok) {
            refreshTodos(); 
        }
    })
    .catch(error => console.error('Greška pri promeni statusa:', error));
  };

  // 4. BRISANJE (DELETE) - Poziva refreshTodos nakon uspeha
  const deleteTodo = (id) => {
    fetch(`${API_URL}/${id}`, { method: 'DELETE' })
    .then(response => {
        if (response.ok) {
            refreshTodos(); 
        }
    })
    .catch(error => console.error('Greška pri brisanju:', error));
  };


  return (
    <div className="App">
      <h1>Moja To-Do Lista</h1>
      
      <TodoForm addTodo={addTodo} />
      
      {/* NOVO: Dodajemo Filter formu i prosleđujemo funkciju za promenu filtera */}
      <FilterForm onFilterChange={handleFilterChange} /> 
      
      <hr />

      <TodoList 
        todos={todos} 
        toggleTodo={toggleTodo} 
        deleteTodo={deleteTodo} 
        editTodo={editTodo} 
      /> 
    </div>
  );
}

export default App;