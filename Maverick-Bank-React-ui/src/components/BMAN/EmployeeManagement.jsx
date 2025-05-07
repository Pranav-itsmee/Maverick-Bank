import React from "react";
import "./EmployeeManagement.css";
import { useNavigate } from "react-router-dom";

const EmployeeManagement = () => {
  const navigate = useNavigate();

  return (
    <div className="container-fluid p-0">
      {/* Sidebar */}
      <div className="sidebar">
        <div className="sidebar-brand">Maverick Bank</div>
        <div
          className="sidebar-menu-item"
          onClick={() => navigate("/bman-dashboard")}
        >
          Dashboard
        </div>
        <div
          className="sidebar-menu-item"
          onClick={() => navigate("/financial-performance")}
        >
          Financial Performance
        </div>
        <div
          className="sidebar-menu-item active"
          onClick={() => navigate("/employee-management")}
        >
          Employee Management
        </div>
        <div className="sidebar-menu-item" onClick={() => navigate("/login")}>
          Logout
        </div>
      </div>

      {/* Main Content */}
      <div className="main-content">
        <div className="header">
          <div className="header-title">Employee Management</div>
          <div className="profile-dropdown">
            <img src="https://via.placeholder.com/50" alt="Profile" />
            <div className="dropdown-content">
              <div>
                <i className="fas fa-user me-2"></i> Profile
              </div>
              <div>
                <i className="fas fa-cog me-2"></i> Settings
              </div>
              <div>
                <i className="fas fa-sign-out-alt me-2"></i> Logout
              </div>
            </div>
          </div>
        </div>

        {/* Employee Management Cards */}
        <div className="employee-management-cards">
          <div className="card" onClick={() => navigate("/add-employee")}>
            <div className="card-icon">
              <i className="fas fa-user-plus"></i>
            </div>
            <div className="card-title">Add Employee</div>
            <div className="card-description">
              Add a new employee profile to the system.
            </div>
            <button className="btn-card mt-3">Manage</button>
          </div>

          <div className="card" onClick={() => navigate("/edit-employee")}>
            <div className="card-icon">
              <i className="fas fa-user-edit"></i>
            </div>
            <div className="card-title">Edit Employee</div>
            <div className="card-description">
              Update or modify details of an existing employee profile.
            </div>
            <button className="btn-card mt-3">Edit Employee</button>
          </div>

          <div className="card" onClick={() => navigate("/delete-employee")}>
            <div className="card-icon">
              <i className="fas fa-user-minus"></i>
            </div>
            <div className="card-title">Delete Employee</div>
            <div className="card-description">
              Delete an existing employee profile from the system.
            </div>
            <button className="btn-card mt-3">Manage</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EmployeeManagement;
