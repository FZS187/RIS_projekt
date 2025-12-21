import React, { useState, useEffect, useCallback, useRef } from "react";
import "./App.css";

import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import AdminDashboard from "./components/AdminDashboard";
import TodoForm from "./components/TodoForm";
import TodoList from "./components/TodoList";
import FilterForm from "./components/FilterForm";
import StatsDashboard from "./components/StatsDashboard";

const API_URL = "http://localhost:8080/api/todos";

function App() {
  const [currentPage, setCurrentPage] = useState("login"); // 'login', 'register', 'app', 'admin'
  const [user, setUser] = useState(null);
  const [todos, setTodos] = useState([]);
  const [filter, setFilter] = useState({ search: "", status: "all" });
  const [appView, setAppView] = useState("todos"); // 'todos' or 'stats'
  const [statsRefresh, setStatsRefresh] = useState(0);
  const reminderTimers = useRef([]);

  const handleLogin = (userData) => {
    // Proveri da li je admin (npr. email === admin@admin.com)
    const isAdmin =
      userData.email === "admin@admin.com" || userData.name === "admin";

    setUser({ ...userData, isAdmin });
    setCurrentPage(isAdmin ? "admin" : "app");
    setAppView("todos");
    localStorage.setItem("loggedIn", "true");
    localStorage.setItem("userData", JSON.stringify({ ...userData, isAdmin }));
  };

  const handleRegister = (userData) => {
    setUser({ ...userData, isAdmin: false });
    setCurrentPage("app");
    setAppView("todos");
    localStorage.setItem("loggedIn", "true");
    localStorage.setItem(
      "userData",
      JSON.stringify({ ...userData, isAdmin: false })
    );
  };

  const handleLogout = () => {
    setUser(null);
    setCurrentPage("login");
    setTodos([]);
    localStorage.removeItem("loggedIn");
    localStorage.removeItem("userData");
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
        console.log("ðŸ“‹ Fetched todos:", data);
        setTodos(data);
      })
      .catch((error) => {
        console.error("GreÅ¡ka pri dohvatanju zadataka:", error);
        if (error.message.includes("401") || error.message.includes("403")) {
          setCurrentPage("login");
          localStorage.removeItem("loggedIn");
          localStorage.removeItem("userData");
        }
      });
  }, [filter]);

  useEffect(() => {
    const isLogged = localStorage.getItem("loggedIn") === "true";
    const storedUserData = localStorage.getItem("userData");

    if (isLogged && storedUserData) {
      const userData = JSON.parse(storedUserData);
      setUser(userData);
      setCurrentPage(userData.isAdmin ? "admin" : "app");
      if (!userData.isAdmin) {
        fetchTodos();
      }
    }
  }, []);

  useEffect(() => {
    if (currentPage === "app") {
      fetchTodos();
    }
  }, [fetchTodos, currentPage]);

  const handleFilterChange = (search, status) => {
    setFilter({ search, status });
  };

  const addTodo = (name, dueDate, reminderAt, category, priority) => {
    if (!name.trim()) return;

    const newTodo = {
      name: name,
      completed: false,
      dueDate: dueDate || null,
      reminderAt: reminderAt || null,
      category: category || "OTHER",
      priority: priority || "MEDIUM",
    };

    console.log("âž• Adding todo:", newTodo);

    fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(newTodo),
    })
      .then((response) => response.json())
      .then((createdTodo) => {
        console.log("âœ… Todo created:", createdTodo);
        fetchTodos();
        setStatsRefresh((prev) => prev + 1);
      })
      .catch((error) => console.error("GreÅ¡ka pri dodavanju:", error));
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
          setStatsRefresh((prev) => prev + 1);
        } else {
          console.error("NeuspeÅ¡no ureÄ‘ivanje na serveru");
        }
      })
      .catch((error) => console.error("GreÅ¡ka pri ureÄ‘ivanju:", error));
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
          setStatsRefresh((prev) => prev + 1);
        }
      })
      .catch((error) => console.error("GreÅ¡ka pri promeni statusa:", error));
  };

  const deleteTodo = (id) => {
    fetch(`${API_URL}/${id}`, {
      method: "DELETE",
      credentials: "include",
    })
      .then((response) => {
        if (response.ok) {
          fetchTodos();
          setStatsRefresh((prev) => prev + 1);
        }
      })
      .catch((error) => console.error("GreÅ¡ka pri brisanju:", error));
  };

  // Lokalne notifikacije (in-app alert) za reminderAt
  useEffect(() => {
    reminderTimers.current.forEach((t) => clearTimeout(t));
    reminderTimers.current = [];

    todos.forEach((todo) => {
      if (todo.reminderAt && todo.reminderAt.trim() !== "") {
        const target = new Date(todo.reminderAt).getTime();
        const now = Date.now();
        const delay = target - now;

        console.log(
          `â° Reminder for "${todo.name}": ${delay}ms (${
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

  // Login strana
  if (currentPage === "login") {
    return (
      <LoginPage
        onLogin={handleLogin}
        onSwitchToRegister={() => setCurrentPage("register")}
      />
    );
  }

  // Register strana
  if (currentPage === "register") {
    return (
      <RegisterPage
        onRegister={handleRegister}
        onSwitchToLogin={() => setCurrentPage("login")}
      />
    );
  }

  // Admin Dashboard
  if (currentPage === "admin") {
    return <AdminDashboard onLogout={handleLogout} user={user} />;
  }

  // ObiÄna korisniÄka strana
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
            flexWrap: "wrap",
            gap: "16px",
            marginBottom: "30px",
            paddingBottom: "20px",
            borderBottom: "2px solid #e2e8f0",
          }}
        >
          <div style={{ flex: "1 1 260px", minWidth: "240px" }}>
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
              Dobrodosli, {user?.name || "KorisniŽ?e"}!
            </p>
          </div>
          <div
            style={{
              display: "flex",
              gap: "10px",
              alignItems: "center",
              flexWrap: "wrap",
            }}
          >
            <button
              onClick={() =>
                setAppView(appView === "todos" ? "stats" : "todos")
              }
              style={{
                display: "flex",
                alignItems: "center",
                gap: "8px",
                padding: "10px 16px",
                background: "white",
                border: "2px solid #e2e8f0",
                borderRadius: "8px",
                color: "#4a5568",
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
                e.target.style.color = "#4a5568";
              }}
            >
              {appView === "todos" ? "View stats" : "Back to tasks"}
            </button>
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
              <span style={{ fontSize: "18px" }}>dYs¦</span>
              Odjavi se
            </button>
          </div>
        </div>

        {appView === "stats" ? (
          <div style={{ marginTop: "10px" }}>
            <StatsDashboard
              isActive={appView === "stats"}
              refreshKey={statsRefresh}
            />
          </div>
        ) : (
          <>
            <TodoForm onAdd={addTodo} />
            <FilterForm onFilterChange={handleFilterChange} />
            <TodoList
              todos={todos}
              onToggle={toggleTodo}
              onDelete={deleteTodo}
              onEdit={editTodo}
            />
          </>
        )}
      </div>
    </div>
  );
}

export default App;
