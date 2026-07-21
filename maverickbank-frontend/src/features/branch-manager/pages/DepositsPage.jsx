import React, { useEffect, useState } from 'react';
import './DepositsPage.css'; // CSS extracted separately
import axios from 'axios';

const DepositsPage = () => {
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    const fetchTransactions = async (branchId) => {
      try {
        const response = await axios.get(`http://localhost:8081/api/transactions/getAll`);
        const data = response.data;
        const filteredDeposits = data.filter(
          (transaction) =>
            transaction.transactionType === "DEPOSIT" &&
            transaction.status === "SUCCESS" &&
            transaction.fromAccount?.branch?.id === branchId
        );
        setTransactions(filteredDeposits);
      } catch (error) {
        console.error("Error fetching transactions:", error);
      }
    };

    const fetchEmployeeBranch = async (username) => {
      try {
          const empResponse = await axios.get(`http://localhost:8081/api/employees/email/${username}`);
          const employee = empResponse.data;
          const branchId = employee.branch?.id;
          fetchTransactions(branchId);
      } catch (error) {
          console.error("Error fetching employee details:", error);
      }
    };

    const username = localStorage.getItem("username");
    if (username) {
        fetchEmployeeBranch(username);
    } else {
        console.error("Username not found in localStorage");
    }
  }, []);

  const exportCSV = () => {
    const csvRows = [
      ["Transaction ID", "Customer Name", "Account Number", "Amount", "Date", "Status"],
      ...transactions.map((t) => [
        t.id,
        t.fromAccount?.customer?.name || "-",
        t.fromAccount?.accountNumber || "-",
        `$${t.amount}`,
        t.transactionDate,
        t.status,
      ]),
    ];

    const csvContent = "data:text/csv;charset=utf-8," + csvRows.map((e) => e.join(",")).join("\n");
    const encodedUri = encodeURI(csvContent);
    const link = document.createElement("a");
    link.setAttribute("href", encodedUri);
    link.setAttribute("download", "deposit_transactions.csv");
    document.body.appendChild(link);
    link.click();
  };

  const viewDetails = (transactionId) => {
    window.location.href = `/deposits/transaction-details/${transactionId}`;
  };

  return (
    <div className="deposits-page">
      {/* Sidebar */}
      <div className="sidebar">
        <div className="sidebar-brand">Maveric Bank</div>
        <a href="/bman-dashboard" className="sidebar-menu-item">Dashboard</a>
        <a href="/financial-performance" className="sidebar-menu-item active">Financial Performance</a>
        <a href="/employee-management" className="sidebar-menu-item">Add Employee</a>
        <a href="/login" className="sidebar-menu-item" onClick={() => localStorage.clear()}>Logout</a>
      </div>

      {/* Main Content */}
      <div className="main-content">
        <div className="header">
          <div className="header-title">Total Deposits</div>
          <div className="profile-dropdown">
            <img src="https://via.placeholder.com/50" alt="Profile" />
          </div>
        </div>

        <div className="filter-container">
          <h5>Deposits</h5>
          <button className="btn-export" onClick={exportCSV}>Export CSV</button>
        </div>

        {/* Deposits Table */}
        <div>
          <h5>Deposit Transactions</h5>
          <table className="withdrawal-table">
            <thead>
              <tr>
                <th>Transaction ID</th>
                <th>Customer Name</th>
                <th>Account Number</th>
                <th>Amount</th>
                <th>Date</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {transactions.length > 0 ? (
                transactions.map((t) => (
                  <tr key={t.id} onClick={() => viewDetails(t.id)}>
                    <td>{t.id}</td>
                    <td>{t.fromAccount?.customer?.name || "-"}</td>
                    <td>{t.fromAccount?.accountNumber || "-"}</td>
                    <td>${t.amount}</td>
                    <td>{t.transactionDate}</td>
                    <td>
                      <span className={`status-badge ${t.status === "SUCCESS" ? "bg-success" : "bg-danger"}`}>
                        {t.status}
                      </span>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: "center" }}>No Deposit Transactions Found</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default DepositsPage;
