import React from 'react';
import './SyncStatusBadge.css';

/**
 * ✅ TASK 4: Komponenta koja jasno prikazuje status sinhronizacije
 * ✅ TASK 5: Prikazuje razumljivo obvestilo pri grešci
 */
const SyncStatusBadge = ({ syncStatus }) => {
  if (!syncStatus) {
    return (
      <div className="sync-badge sync-pending">
        <span className="sync-icon">⏸️</span>
        <span className="sync-text">Čeka</span>
      </div>
    );
  }

  // ✅ Proveri koji status je aktivan
  const status = syncStatus.status || 'PENDING';
  const isSuccess = syncStatus.isSuccess || status === 'COMPLETED';
  const isFailed = syncStatus.isFailed || status === 'FAILED';
  const isInProgress = syncStatus.isInProgress || status === 'IN_PROGRESS';

  const getStatusConfig = () => {
    if (isSuccess) {
      return {
        className: 'sync-badge sync-completed',
        icon: '✅',
        text: syncStatus.displayName || 'Uspešno',
        spinning: false
      };
    }
    
    if (isFailed) {
      return {
        className: 'sync-badge sync-failed',
        icon: '❌',
        text: syncStatus.displayName || 'Greška',
        spinning: false
      };
    }
    
    if (isInProgress) {
      return {
        className: 'sync-badge sync-in-progress',
        icon: '🔄',
        text: syncStatus.displayName || 'U toku',
        spinning: true
      };
    }
    
    // PENDING
    return {
      className: 'sync-badge sync-pending',
      icon: '⏸️',
      text: syncStatus.displayName || 'Čeka',
      spinning: false
    };
  };

  const config = getStatusConfig();

  return (
    <div 
      className={config.className} 
      title={syncStatus.errorMessage || syncStatus.userMessage || ''}
    >
      <span className={config.spinning ? 'sync-icon spinning' : 'sync-icon'}>
        {config.icon}
      </span>
      <span className="sync-text">{config.text}</span>
      
      {/* ✅ TASK 5: Tooltip sa greškom ako postoji */}
      {syncStatus.errorMessage && isFailed && (
        <span className="sync-error-tooltip">
          {syncStatus.errorMessage}
        </span>
      )}
    </div>
  );
};

export default SyncStatusBadge;