import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './CustomerSupportDashboard.css';

const CustomerSupportDashboard = () => {
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [employeeName, setEmployeeName] = useState('');
  const [branchName, setBranchName] = useState('');
  const [branchId, setBranchId] = useState('');

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  useEffect(() => {
    const username = localStorage.getItem("username");
    if (username) {
      fetchEmployeeDetails(username);
    } else {
      console.error("Username not found in localStorage");
    }
  }, []);

  const fetchEmployeeDetails = async (username) => {
    try {
      const response = await axios.get(`http://localhost:8081/api/employees/email/${username}`);
      const employee = response.data;
      setEmployeeName(employee.name);
      setBranchName(employee.branch?.branchName || 'Unknown Branch');
      setBranchId(employee.branch?.id || 'Unknown Branch ID');
    } catch (error) {
      console.error("Error fetching employee details:", error);
    }
  };

  return (
    <div className="customerdashboard-container">
      <div className="header">
        <div className="header-title">Customer Support Dashboard</div>
        <div className="profile-dropdown" onClick={toggleDropdown}>
          <img src="https://via.placeholder.com/50" alt="Profile" />
          <div className={`dropdown-content ${dropdownOpen ? 'show' : ''}`}>
            <Link to="#"><i className="fas fa-user me-2"></i> Profile</Link>
            <Link to="#"><i className="fas fa-cog me-2"></i> Settings</Link>
            <Link to="#"><i className="fas fa-sign-out-alt me-2"></i> Logout</Link>
          </div>
        </div>
      </div>

      <div className="main-content">

        {/* Welcome Section */}
        <div className="welcome-section">
          <h2>Welcome, Customer Support Representative!</h2>
          <p>Name: <strong>{employeeName}</strong></p>
          <p>Branch: <strong>{branchName}</strong></p>
          <p>Branch ID: <strong>{branchId}</strong></p>
        </div>

        {/* Dashboard Cards */}
        <div className="card-container">

          <div className="dashboard-card">
            <h5>Manage Account Details</h5>
            <p>Access detailed information about customer accounts.</p>
            <Link to="/view-accounts">
              <button className="btn btn-primary mt-3">View</button>
            </Link>
          </div>

          <div className="dashboard-card">
            <h5>View Transaction Details</h5>
            <p>Track and review all customer transactions.</p>
            <Link to="/view-transactions">
              <button className="btn btn-primary mt-3">View</button>
            </Link>
          </div>

          <div className="dashboard-card">
            <h5>Manage Service Requests</h5>
            <p>Handle and resolve customer service requests.</p>
            <Link to="/manage-service-request">
              <button className="btn btn-primary mt-3">Manage</button>
            </Link>
          </div>

          <div className="dashboard-card">
            <h5>Manage Accounts</h5>
            <p>Add or De-activate Accounts.</p>
            <Link to="/manage-accounts">
              <button className="btn btn-primary mt-3">Manage</button>
            </Link>
          </div>

        </div>
      </div>
    </div>
  );
};

export default CustomerSupportDashboard;
