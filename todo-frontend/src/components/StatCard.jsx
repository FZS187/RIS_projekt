import React from "react";

import "./StatsDashboard.css";

function StatCard({ title, value, subtitle, tone = "neutral" }) {
  return (
    <div className={`stat-card stat-card--${tone}`}>
      <div className="stat-card__value">{value}</div>
      <div className="stat-card__title">{title}</div>
      {subtitle ? <div className="stat-card__subtitle">{subtitle}</div> : null}
    </div>
  );
}

export default StatCard;
