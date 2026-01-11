import React from 'react';
import './SyncStatusBadge.css';

const SyncStatusBadge = ({ syncStatus }) => {
  if (!syncStatus) {
    return null;
  }

  const getStatusConfig = (status) => {
    switch (status) {
      case 'IN_PROGRESS':
        return {
          className: 'sync-badge sync-in-progress',
          icon: '🔄',
          text: syncStatus.displayName || 'V teku',
          spinning: true
        };
      case 'COMPLETED':
        return {
          className: 'sync-badge sync-completed',
          icon: '✅',
          text: syncStatus.displayName || 'Zaključeno',
          spinning: false
        };
      case 'FAILED':
        return {
          className: 'sync-badge sync-failed',
          icon: '❌',
          text: syncStatus.displayName || 'Neuspešno',
          spinning: false
        };
      case 'PENDING':
        return {
          className: 'sync-badge sync-pending',
          icon: '⏳',
          text: syncStatus.displayName || 'Čakanje',
          spinning: false
        };
      default:
        return {
          className: 'sync-badge',
          icon: '❓',
          text: 'Nepoznato',
          spinning: false
        };
    }
  };

  const config = getStatusConfig(syncStatus.status);

  return (
    <div className={config.className} title={syncStatus.errorMessage || ''}>
      <span className={config.spinning ? 'sync-icon spinning' : 'sync-icon'}>
        {config.icon}
      </span>
      <span className="sync-text">{config.text}</span>
      {syncStatus.errorMessage && (
        <span className="sync-error-tooltip">
          {syncStatus.errorMessage}
        </span>
      )}
    </div>
  );
};

export default SyncStatusBadge;