import React, { useState } from "react";
import axios from "axios";
import "./AddCustomer.css";

const AddCustomer = () => {
  const [formData, setFormData] = useState({
    name: "",
    username: "",
    mobileNo: "",
    email: "",
    address: "",
    dob: "",
    education: "",
    occupation: "",
    maritalStatus: "",
    panNumber: "",
    aadhaarNumber: "",
    branchId: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const customerPayload = {
      name: formData.name,
      username: formData.username,
      mobileNo: formData.mobileNo,
      email: formData.email,
      address: formData.address,
      dob: formData.dob,
      education: formData.education,
      occupation: formData.occupation,
      maritalStatus: formData.maritalStatus,
      panNumber: formData.panNumber,
      aadhaarNumber: formData.aadhaarNumber,
      branch: { id: formData.branchId },
    };

    try {
      await axios.post("http://localhost:8081/api/customers/add", customerPayload);
      alert("Customer added successfully!");
    } catch (error) {
      console.error("Error adding customer:", error);
      alert("Failed to add customer.");
    }
  };

  return (
    <div className="view-accounts-container">
      <div className="sidebar">
        <div className="sidebar-brand">Maveric Bank</div>
        <a href="/csr-dashboard" className="sidebar-menu-item">Dashboard</a>
        <a href="/view-accounts" className="sidebar-menu-item">View Account Details</a>
        <a href="/view-transactions" className="sidebar-menu-item">View Transaction Details</a>
        <a href="/manage-service-request" className="sidebar-menu-item">Manage Service Requests</a>
        <a href="/manage-accounts" className="sidebar-menu-item active">Manage Accounts / Customer</a>
        <a href="/login" className="sidebar-menu-item">Logout</a>
      </div>

      <div className="main-content">
        <div className="header">
          <div className="header-title">Add Customer</div>
          <div className="profile-dropdown">
            <img src="https://via.placeholder.com/50" alt="Profile" />
          </div>
        </div>

        <form onSubmit={handleSubmit} className="customer-form">
          <div className="form-group">
            <label>Name</label>
            <input
              type="text"
              name="name"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Username</label>
            <input
              type="text"
              name="username"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Mobile Number</label>
            <input
              type="text"
              name="mobileNo"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              name="email"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Address</label>
            <input
              type="text"
              name="address"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Date of Birth</label>
            <input
              type="date"
              name="dob"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Education</label>
            <input
              type="text"
              name="education"
              className="filter-input"
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Occupation</label>
            <input
              type="text"
              name="occupation"
              className="filter-input"
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Marital Status</label>
            <input
              type="text"
              name="maritalStatus"
              className="filter-input"
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>PAN Number</label>
            <input
              type="text"
              name="panNumber"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Aadhaar Number</label>
            <input
              type="text"
              name="aadhaarNumber"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Branch ID</label>
            <input
              type="text"
              name="branchId"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <button type="submit" className="btn btn-primary">
            Add Customer
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddCustomer;
