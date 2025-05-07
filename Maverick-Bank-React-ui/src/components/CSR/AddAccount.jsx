import React, { useState } from "react";
import "./ManageAccounts.css";
import axios from "axios";

const AddAccount = () => {
  const [formData, setFormData] = useState({
    accountNumber: "",
    ifscCode: "",
    accountType: "",
    balance: "",
    status: "Active",
    branchId: "",
    customerId: "",
    dailyLimit: "",
    monthlyLimit: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const accountPayload = {
      accountNumber: formData.accountNumber,
      ifscCode: formData.ifscCode,
      accountType: formData.accountType,
      balance: parseFloat(formData.balance),
      status: formData.status,
      branch: { id: formData.branchId },
      customer: { id: formData.customerId },
      transactionLimit: {
        dailyLimit: parseFloat(formData.dailyLimit),
        monthlyLimit: parseFloat(formData.monthlyLimit),
      },
    };

    try {
      await axios.post("http://localhost:8081/api/accounts/add", accountPayload);
      alert("Account created successfully!");
    } catch (error) {
      console.error("Error creating account:", error);
      alert("Failed to create account.");
    }
  };

  return (
    <div className="view-accounts-container">
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
        <a href="/manage-service-request" className="sidebar-menu-item">
          Manage Service Requests
        </a>
        <a href="/manage-accounts" className="sidebar-menu-item active">
          Manage Accounts
        </a>
        <a href="/login" className="sidebar-menu-item">
          Logout
        </a>
      </div>

      <div className="main-content">
        <div className="header">
          <div className="header-title">Add Account</div>
          <div className="profile-dropdown">
            <img src="https://via.placeholder.com/50" alt="Profile" />
          </div>
        </div>

        <form
          onSubmit={handleSubmit}
          style={{ maxWidth: "700px", margin: "0 auto", textAlign: "left" }}
        >
          <div className="form-group">
            <label>Account Number</label>
            <input
              type="text"
              name="accountNumber"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>IFSC Code</label>
            <input
              type="text"
              name="ifscCode"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Account Type</label>
            <input
              type="text"
              name="accountType"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Balance</label>
            <input
              type="number"
              name="balance"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Status</label>
            <input
              type="text"
              name="status"
              className="filter-input"
              value="active"
              disabled
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
          <div className="form-group">
            <label>Customer ID</label>
            <input
              type="text"
              name="customerId"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Daily Transaction Limit</label>
            <input
              type="number"
              name="dailyLimit"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Monthly Transaction Limit</label>
            <input
              type="number"
              name="monthlyLimit"
              className="filter-input"
              required
              onChange={handleChange}
            />
          </div>
          <button type="submit" className="btn btn-primary mt-3">
            Create Account
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddAccount;
