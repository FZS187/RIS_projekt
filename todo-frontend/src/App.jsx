import React, { useState, useEffect, useCallback } from "react";
import "./App.css";

// Auth komponente
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";

// Todo komponente
import TodoForm from "./components/TodoForm";
import TodoList from "./components/TodoList";
import FilterForm from "./components/FilterForm";

const API_URL = "http://localhost:8080/api/todos";

function App() {
  // ============== AUTH STATE ==============
  const [currentPage, setCurrentPage] = useState("login"); // 'login', 'register', 'app'
  const [user, setUser] = useState(null);

  // ============== TODO STATE ==============
  const [todos, setTodos] = useState([]);
  const [filter, setFilter] = useState({ search: "", status: "all" });

  // ============== AUTH HANDLERS ==============
  const handleLogin = (userData) => {
    setUser(userData);
    setCurrentPage("app");
    // TODO: Sačuvati token u localStorage ako backend vraća JWT
    // localStorage.setItem('token', userData.token);
  };

  const handleRegister = (userData) => {
    setUser(userData);
    setCurrentPage("app");
    // TODO: Sačuvati token u localStorage ako backend vraća JWT
    // localStorage.setItem('token', userData.token);
  };

  const handleLogout = () => {
    setUser(null);
    setCurrentPage("login");
    setTodos([]);
    // TODO: Obrisati token iz localStorage
    // localStorage.removeItem('token');
  };

  // ============== TODO FUNKCIJE ==============

  // Dohvatanje zadataka sa filtriranjem
  const fetchTodos = useCallback(() => {
    const queryString = new URLSearchParams({
      search: filter.search,
      status: filter.status,
    }).toString();

    fetch(`${API_URL}?${queryString}`)
      .then((response) => response.json())
      .then((data) => setTodos(data))
      .catch((error) =>
        console.error("Greška pri dohvatanju zadataka:", error)
      );
  }, [filter]);

  useEffect(() => {
    if (currentPage === "app") {
      fetchTodos();
    }
  }, [fetchTodos, currentPage]);

  // Promena filtera
  const handleFilterChange = (search, status) => {
    setFilter({ search, status });
  };

  // Ponovno učitavanje liste
  const refreshTodos = () => {
    fetchTodos();
  };

  // Dodavanje novog zadatka (POST)
  const addTodo = (name, dueDate) => {
    if (!name.trim()) return;

    const newTodo = {
      name: name,
      completed: false,
      dueDate: dueDate || null,
    };

    fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newTodo),
    })
      .then((response) => response.json())
      .then((savedTodo) => {
        refreshTodos();
      })
      .catch((error) => console.error("Greška pri dodavanju:", error));
  };

  // Uređivanje zadatka (PUT)
  const editTodo = (id, newName, newDueDate) => {
    const todoToUpdate = todos.find((t) => t.id === id);
    if (!todoToUpdate) return;

    const updatedTodo = {
      ...todoToUpdate,
      name: newName,
      dueDate: newDueDate,
    };

    fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(updatedTodo),
    })
      .then((response) => {
        if (response.ok) {
          refreshTodos();
        } else {
          console.error("Neuspešno uređivanje na serveru");
        }
      })
      .catch((error) => console.error("Greška pri uređivanju:", error));
  };

  // Toggle completed status (PUT)
  const toggleTodo = (id) => {
    const todoToUpdate = todos.find((t) => t.id === id);
    if (!todoToUpdate) return;

    const updatedTodo = {
      ...todoToUpdate,
      completed: !todoToUpdate.completed,
    };

    fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(updatedTodo),
    })
      .then((response) => {
        if (response.ok) {
          refreshTodos();
        }
      })
      .catch((error) => console.error("Greška pri promeni statusa:", error));
  };

  // Brisanje zadatka (DELETE)
  const deleteTodo = (id) => {
    fetch(`${API_URL}/${id}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (response.ok) {
          refreshTodos();
        }
      })
      .catch((error) => console.error("Greška pri brisanju:", error));
  };

  // ============== RENDER ==============

  // Prikaži Login stranicu
  if (currentPage === "login") {
    return (
      <LoginPage
        onLogin={handleLogin}
        onSwitchToRegister={() => setCurrentPage("register")}
      />
    );
  }

  // Prikaži Registration stranicu
  if (currentPage === "register") {
    return (
      <RegisterPage
        onRegister={handleRegister}
        onSwitchToLogin={() => setCurrentPage("login")}
      />
    );
  }

  // Prikaži Todo App
  return (
    <div
      style={{
        minHeight: "100vh",
        background: "#f7fafc",
        padding: "20px",
      }}
    >
      <div
        style={{
          maxWidth: "800px",
          margin: "0 auto",
          background: "white",
          borderRadius: "12px",
          padding: "30px",
          boxShadow: "0 4px 20px rgba(0,0,0,0.08)",
        }}
      >
        {/* Header sa imenom korisnika i Logout dugmetom */}
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            marginBottom: "30px",
            paddingBottom: "20px",
            borderBottom: "2px solid #e2e8f0",
          }}
        >
          <div>
            <h1
              style={{
                fontSize: "32px",
                background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
                WebkitBackgroundClip: "text",
                WebkitTextFillColor: "transparent",
                marginBottom: "8px",
                fontWeight: "700",
              }}
            >
              Moja To-Do Lista
            </h1>
            <p
              style={{
                color: "#718096",
                fontSize: "14px",
                margin: 0,
              }}
            >
              Dobrodošli, {user?.name || "Korisniče"}!
            </p>
          </div>
          <button
            onClick={handleLogout}
            style={{
              display: "flex",
              alignItems: "center",
              gap: "8px",
              padding: "10px 20px",
              background: "white",
              border: "2px solid #e2e8f0",
              borderRadius: "8px",
              color: "#718096",
              cursor: "pointer",
              fontSize: "14px",
              fontWeight: "600",
              transition: "all 0.2s",
            }}
            onMouseEnter={(e) => {
              e.target.style.borderColor = "#667eea";
              e.target.style.color = "#667eea";
            }}
            onMouseLeave={(e) => {
              e.target.style.borderColor = "#e2e8f0";
              e.target.style.color = "#718096";
            }}
          >
            <span style={{ fontSize: "18px" }}>🚪</span>
            Odjavi se
          </button>
        </div>

        {/* Todo Form - za dodavanje novih zadataka */}
        <TodoForm onAdd={addTodo} />

        {/* Filter Form - za pretragu i filtriranje */}
        <FilterForm onFilterChange={handleFilterChange} />

        {/* Todo List - lista svih zadataka */}
        <TodoList
          todos={todos}
          onToggle={toggleTodo}
          onDelete={deleteTodo}
          onEdit={editTodo}
        />
      </div>
    </div>
  );
}

export default App;
