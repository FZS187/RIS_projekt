import React, { useEffect, useState } from "react";

import StatCard from "./StatCard";
import "./StatsDashboard.css";

const API_URL = "http://localhost:8080/api/todos";
const CATEGORY_ORDER = [
  "WORK",
  "PERSONAL",
  "SHOPPING",
  "HEALTH",
  "EDUCATION",
  "OTHER",
];
const PRIORITY_ORDER = ["HIGH", "MEDIUM", "LOW"];

const toLabel = (value) => {
  const cleaned = String(value || "")
    .toLowerCase()
    .replace(/_/g, " ")
    .trim();
  if (!cleaned) return "Unknown";
  return cleaned.charAt(0).toUpperCase() + cleaned.slice(1);
};

const buildCounts = (items, fallbackOrder) => {
  const map = {};
  items.forEach(([key, count]) => {
    map[key] = count;
  });
  fallbackOrder.forEach((key) => {
    if (map[key] === undefined) {
      map[key] = 0;
    }
  });
  return map;
};

function StatsDashboard({ isActive = true, refreshKey = 0 }) {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!isActive) {
      return;
    }

    const fetchStats = async () => {
      try {
        setLoading(true);
        setError("");
        const response = await fetch(`${API_URL}/statistics`, {
          credentials: "include",
        });
        if (!response.ok) {
          throw new Error(`HTTP ${response.status}`);
        }
        const data = await response.json();
        setStats(data);
      } catch (err) {
        console.error("Error fetching stats:", err);
        setError("Napaka pri nalaganju statistike.");
      } finally {
        setLoading(false);
      }
    };

    fetchStats();
  }, [isActive, refreshKey]);

  if (loading) {
    return (
      <section className="stats-dashboard">
        <div className="stats-header">
          <div>
            <p className="stats-kicker">Productivity</p>
            <h2 className="stats-title">Task Statistics</h2>
            <p className="stats-subtitle">Loading statistics...</p>
          </div>
        </div>
      </section>
    );
  }

  if (error) {
    return (
      <section className="stats-dashboard">
        <div className="stats-header">
          <div>
            <p className="stats-kicker">Productivity</p>
            <h2 className="stats-title">Task Statistics</h2>
            <p className="stats-subtitle" style={{ color: "#ff9b9b" }}>
              {error}
            </p>
          </div>
        </div>
      </section>
    );
  }

  if (!stats) {
    return (
      <section className="stats-dashboard">
        <div className="stats-header">
          <div>
            <p className="stats-kicker">Productivity</p>
            <h2 className="stats-title">Task Statistics</h2>
            <p className="stats-subtitle">No statistics available.</p>
          </div>
        </div>
      </section>
    );
  }

  const {
    totalTasks = 0,
    completedTasks = 0,
    pendingTasks = 0,
    tasksByCategory = {},
    tasksByPriority = {},
  } = stats;

  const categories = buildCounts(
    Object.entries(tasksByCategory),
    CATEGORY_ORDER
  );
  const priorities = buildCounts(
    Object.entries(tasksByPriority),
    PRIORITY_ORDER
  );

  return (
    <section className="stats-dashboard">
      <div className="stats-header">
        <div>
          <p className="stats-kicker">Productivity</p>
          <h2 className="stats-title">Task Statistics</h2>
          <p className="stats-subtitle">Quick view of progress and focus.</p>
        </div>
        <div className="stats-pill">
          {totalTasks} task{totalTasks === 1 ? "" : "s"} tracked
        </div>
      </div>

      <div className="stats-cards">
        <StatCard
          title="Total tasks"
          value={totalTasks}
          subtitle="All items"
          tone="neutral"
        />
        <StatCard
          title="Completed"
          value={completedTasks}
          subtitle="Finished work"
          tone="success"
        />
        <StatCard
          title="Pending"
          value={pendingTasks}
          subtitle="Still open"
          tone="warning"
        />
      </div>

      <div className="stats-grid">
        <div className="stats-panel">
          <h3>By category</h3>
          <ul>
            {Object.keys(categories).map((key) => (
              <li key={key}>
                <span className="stats-label">{toLabel(key)}</span>
                <span className="stats-value">{categories[key]}</span>
              </li>
            ))}
          </ul>
        </div>

        <div className="stats-panel">
          <h3>By priority</h3>
          <ul>
            {Object.keys(priorities).map((key) => (
              <li key={key}>
                <span className="stats-label">{toLabel(key)}</span>
                <span className="stats-value">{priorities[key]}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </section>
  );
}

export default StatsDashboard;
