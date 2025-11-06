import React, { useState } from 'react';

// Ova komponenta će slati filtere nazad App.jsx-u
const FilterForm = ({ onFilterChange }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [status, setStatus] = useState('all'); // all, active, completed

  const handleSearchChange = (e) => {
    const newSearchTerm = e.target.value;
    setSearchTerm(newSearchTerm);
    // Pozivamo roditeljsku funkciju sa novim filterima
    onFilterChange(newSearchTerm, status);
  };

  const handleStatusChange = (e) => {
    const newStatus = e.target.value;
    setStatus(newStatus);
    // Pozivamo roditeljsku funkciju sa novim filterima
    onFilterChange(searchTerm, newStatus);
  };

  return (
    <div className="filter-form">
      <input
        type="text"
        placeholder="Pretraga po imenu zadatka..."
        value={searchTerm}
        onChange={handleSearchChange}
        className="filter-input"
      />
      
      
    </div>
  );
};

export default FilterForm;