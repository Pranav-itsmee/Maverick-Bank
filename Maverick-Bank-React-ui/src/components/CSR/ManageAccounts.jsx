import React from 'react';
import { Link } from 'react-router-dom';
import './ManageAccounts.css';

const ManageAccounts = () => {
  return (
    <div className="view-accounts-container">
      <div className="sidebar">
        <div className="sidebar-brand">Maveric Bank</div>
        <a href="/csr-dashboard" className="sidebar-menu-item">Dashboard</a>
        <a href="/view-accounts" className="sidebar-menu-item">View Account Details</a>
        <a href="/view-transactions" className="sidebar-menu-item">View Transaction Details</a>
        <a href="/manage-service-request" className="sidebar-menu-item">Manage Service Requests</a>
        <a href="/manage-accounts" className="sidebar-menu-item active">Manage Accounts/Customer </a>

        <a href="/login" className="sidebar-menu-item">Logout</a>
      </div>

      <div className="main-content">
        <div className="header">
          <div className="header-title">Manage Accounts</div>
          <div className="profile-dropdown">
            <img src="https://via.placeholder.com/50" alt="Profile" />
            <div className="dropdown-content">
              <a href="#">Profile</a>
              <a href="#">Settings</a>
              <a href="#">Logout</a>
            </div>
          </div>
        </div>

        <div className="card-container1">
          <div className="dashboard-card">
            <h5>Add Account</h5>
            <p>Create a new customer account in the system.</p>
            <Link to="/add-account">
              <button className="btn btn-primary mt-3">Add</button>
            </Link>
          </div>

          <div className="dashboard-card">
            <h5>Add Customer</h5>
            <p>Create a new customer  in the system.</p>
            <Link to="/add-customer">
              <button className="btn btn-primary mt-3">Add</button>
            </Link>
          </div>

          <div className="dashboard-card">
            <h5>Deactivated Accounts</h5>
            <p>Review all deactivated or closed accounts.</p>
            <Link to="/deactivated-accounts">
              <button className="btn btn-primary mt-3">View</button>
            </Link>
          </div>

          <div className="dashboard-card">
            <h5>Manage Existing Accounts</h5>
            <p>Update, activate, or delete customer accounts.</p>
            <Link to="/manage-existing-accounts">
              <button className="btn btn-primary mt-3">Manage</button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ManageAccounts;
