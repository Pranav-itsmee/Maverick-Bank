import React, { useState, useEffect } from "react";
import axios from "axios";
import "./ManageServiceRequests.css";

const ManageServiceRequests = () => {
  const [serviceRequests, setServiceRequests] = useState([]);
  const [filter, setFilter] = useState("");

  useEffect(() => {
    fetchServiceRequests();
  }, []);


  const fetchServiceRequests = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8081/api/service-requests/getAll" 
      );
      console.log("Service Requests Response:", response.data); 
      setServiceRequests(response.data);
    } catch (error) {
      console.error("Error fetching service requests:", error);
    }
  };

  const handleFilterChange = (e) => {
    setFilter(e.target.value);
  };

  const viewServiceRequestDetails = (requestId) => {
    window.location.href = `/service-request-details/${requestId}`; 
  };

  const getCategoryName = (categoryCode) => {
    switch (categoryCode) {
      case "ACCOUNT":
        return "Account";
      case "LOAN":
        return "Loan";
      case "TECHNICAL":
        return "Technical";
      default:
        return "Unknown";
    }
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString();
  };

  return (
    <div className="manage-service-requests-container">
      <div className="sidebar">
        <div className="sidebar-brand">Maveric Bank</div>
        <a href="/csr-dashboard" className="sidebar-menu-item">
          Dashboard
        </a>
        <a href="/view-accounts" className="sidebar-menu-item">
          View Account Details
        </a>
        <a href="/view-transactions" className="sidebar-menu-item">
          View Transaction Details
        </a>
        <a href="/manage-service-request" className="sidebar-menu-item active">
          Manage Service Requests
        </a>
        <a href="/manage-accounts" className="sidebar-menu-item">Manage Accounts</a>

        <a href="/login" className="sidebar-menu-item">
          Logout
        </a>
      </div>

      <div className="main-content">
        <div className="header">
          <div className="header-title">Manage Service Requests</div>
          <div className="profile-dropdown">
            <img src="https://via.placeholder.com/50" alt="Profile" />
            <div className="dropdown-content">
              <a href="#">Profile</a>
              <a href="#">Settings</a>
              <a href="#">Logout</a>
            </div>
          </div>
        </div>

        <div className="filter-container">
          <input
            type="text"
            id="requestFilter"
            className="filter-input"
            placeholder="Search by Request ID or Customer Name..."
            value={filter}
            onChange={handleFilterChange}
          />
        </div>

        <div className="table-container">
          <h5 className="mb-4">All Service Requests</h5>
          <table className="service-requests-table">
            <thead>
              <tr>
                <th>Request ID</th>
                <th>Customer Name</th>
                <th>Category</th>
                <th>Status</th>
                <th>Date Submitted</th>
              </tr>
            </thead>
            <tbody>
              {serviceRequests
                .filter(
                  (request) =>
                    request.id
                      .toString()
                      .toLowerCase()
                      .includes(filter.toLowerCase()) ||
                    request.customer.name
                      .toLowerCase()
                      .includes(filter.toLowerCase())
                )
                .map((request) => (
                  <tr
                    key={request.id}
                    onClick={() => viewServiceRequestDetails(request.id)}
                  >
                    <td>{request.id}</td>
                    <td>{request.customer.name}</td>
                    <td>
                      <span
                        className={`status-badge ${request.category.toLowerCase()}`}
                      >
                        {getCategoryName(request.category)}
                      </span>
                    </td>
                    <td>
                      <span
                        className={`status-badge ${request.status.toLowerCase()}`}
                      >
                        {request.status}
                      </span>
                    </td>
                    <td>{formatDate(request.createdDate)}</td>
                  </tr>
                ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default ManageServiceRequests;
