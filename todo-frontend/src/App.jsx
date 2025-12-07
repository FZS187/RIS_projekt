import React, { useState, useEffect, useCallback, useRef } from "react";
import "./App.css";

import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import TodoForm from "./components/TodoForm";
import TodoList from "./components/TodoList";
import FilterForm from "./components/FilterForm";

const API_URL = "http://localhost:8080/api/todos";

function App() {
  const [currentPage, setCurrentPage] = useState("login"); // 'login', 'register', 'app'
  const [user, setUser] = useState(null);
  const [todos, setTodos] = useState([]);
  const [filter, setFilter] = useState({ search: "", status: "all" });
  const reminderTimers = useRef([]);

  const handleLogin = (userData) => {
    setUser(userData);
    setCurrentPage("app");
    localStorage.setItem("loggedIn", "true");
  };

  const handleRegister = (userData) => {
    setUser(userData);
    setCurrentPage("app");
    localStorage.setItem("loggedIn", "true");
  };

  const handleLogout = () => {
    setUser(null);
    setCurrentPage("login");
    setTodos([]);
    localStorage.removeItem("loggedIn");
  };

  const fetchTodos = useCallback(() => {
    const queryString = new URLSearchParams({
      search: filter.search,
      status: filter.status,
    }).toString();

    fetch(`${API_URL}?${queryString}`, { credentials: "include" })
      .then(async (response) => {
        if (!response.ok) {
          throw new Error(`HTTP ${response.status}`);
        }
        const data = await response.json();
        console.log("📋 Fetched todos:", data); // Debug log
        setTodos(data);
      })
      .catch((error) => {
        console.error("Greška pri dohvatanju zadataka:", error);
        if (error.message.includes("401") || error.message.includes("403")) {
          setCurrentPage("login");
          localStorage.removeItem("loggedIn");
        }
      });
  }, [filter]);

  useEffect(() => {
    const isLogged = localStorage.getItem("loggedIn") === "true";
    if (isLogged) {
      setCurrentPage("app");
      fetchTodos();
    }
  }, [fetchTodos]);

  useEffect(() => {
    if (currentPage === "app") {
      fetchTodos();
    }
  }, [fetchTodos, currentPage]);

  const handleFilterChange = (search, status) => {
    setFilter({ search, status });
  };

  const addTodo = (name, dueDate, reminderAt) => {
    if (!name.trim()) return;

    const newTodo = {
      name: name,
      completed: false,
      dueDate: dueDate || null,
      reminderAt: reminderAt || null,
    };

    console.log("➕ Adding todo:", newTodo); // Debug log

    fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(newTodo),
    })
      .then((response) => response.json())
      .then((createdTodo) => {
        console.log("✅ Todo created:", createdTodo); // Debug log
        fetchTodos();
      })
      .catch((error) => console.error("Greška pri dodavanju:", error));
  };

  const editTodo = (id, newName, newDueDate, newReminderAt) => {
    const todoToUpdate = todos.find((t) => t.id === id);
    if (!todoToUpdate) return;

    const updatedTodo = {
      ...todoToUpdate,
      name: newName,
      dueDate: newDueDate,
      reminderAt: newReminderAt,
    };

    fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(updatedTodo),
    })
      .then((response) => {
        if (response.ok) {
          fetchTodos();
        } else {
          console.error("Neuspešno uređivanje na serveru");
        }
      })
      .catch((error) => console.error("Greška pri uređivanju:", error));
  };

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
      credentials: "include",
      body: JSON.stringify(updatedTodo),
    })
      .then((response) => {
        if (response.ok) {
          fetchTodos();
        }
      })
      .catch((error) => console.error("Greška pri promeni statusa:", error));
  };

  const deleteTodo = (id) => {
    fetch(`${API_URL}/${id}`, {
      method: "DELETE",
      credentials: "include",
    })
      .then((response) => {
        if (response.ok) {
          fetchTodos();
        }
      })
      .catch((error) => console.error("Greška pri brisanju:", error));
  };

  // Lokalne notifikacije (in-app alert) za reminderAt
  useEffect(() => {
    // očisti stare timere
    reminderTimers.current.forEach((t) => clearTimeout(t));
    reminderTimers.current = [];

    todos.forEach((todo) => {
      if (todo.reminderAt && todo.reminderAt.trim() !== "") {
        const target = new Date(todo.reminderAt).getTime();
        const now = Date.now();
        const delay = target - now;

        console.log(
          `⏰ Reminder for "${todo.name}": ${delay}ms (${
            delay > 0 ? "future" : "past"
          })`
        );

        if (delay > 0) {
          const timerId = setTimeout(() => {
            alert(
              `Opomnik: "${todo.name}" zakazan za ${new Date(
                todo.reminderAt
              ).toLocaleString("sr-RS")}`
            );
          }, delay);
          reminderTimers.current.push(timerId);
        }
      }
    });

    return () => {
      reminderTimers.current.forEach((t) => clearTimeout(t));
      reminderTimers.current = [];
    };
  }, [todos]);

  if (currentPage === "login") {
    return (
      <LoginPage
        onLogin={handleLogin}
        onSwitchToRegister={() => setCurrentPage("register")}
      />
    );
  }

  if (currentPage === "register") {
    return (
      <RegisterPage
        onRegister={handleRegister}
        onSwitchToLogin={() => setCurrentPage("login")}
      />
    );
  }

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

        <TodoForm onAdd={addTodo} />
        <FilterForm onFilterChange={handleFilterChange} />
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
