import React from 'react';
import { Link } from 'react-router-dom'; // Import Link from react-router-dom
import './BranchManagerDashboard.css';

const BranchManagerDashboard = () => {
  return (
    <div className="branch-manager-dashboard">
      {/* Header */}
      <div className="header">
        <div className="header-title">Branch Manager Dashboard</div>
        <div className="profile-dropdown">
          <img src="https://via.placeholder.com/50" alt="Profile Picture" />
          <div className="dropdown-content">
            <Link to="/profile">
              <i className="fas fa-user me-2"></i> Profile
            </Link>
            <Link to="/settings">
              <i className="fas fa-cog me-2"></i> Settings
            </Link>
            <Link to="/logout">
              <i className="fas fa-sign-out-alt me-2"></i> Logout
            </Link>
          </div>
        </div>
      </div>

      {/* Dashboard Cards */}
      <div className="dashboard-cards">
        {/* Financial Performance Reports Card */}
        <div
          className="card"
        >
          <div className="card-icon">
            <i className="fas fa-chart-line"></i>
          </div>
          <div className="card-title">Financial Performance Reports</div>
          <div className="card-description">
            View detailed financial performance reports, including revenue, expenses, and profitability.
          </div>
          <Link to="/financial-performance" className="stretched-link" />
        </div>

        {/* Employee Management Card */}
        <div
          className="card"
        >
          <div className="card-icon">
            <i className="fas fa-user-tie"></i>
          </div>
          <div className="card-title">Employee Management</div>
          <div className="card-description">
            Manage employee profiles, roles, and performance metrics.
          </div>
          <Link to="/employee-management" className="stretched-link" />
        </div>
      </div>
    </div>
  );
};

export default BranchManagerDashboard;
