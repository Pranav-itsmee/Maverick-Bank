import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AccountDetails.css';

const AccountDetails = () => {
  const [accountData, setAccountData] = useState(null);
  const [transactions, setTransactions] = useState([]); 
  const [loading, setLoading] = useState(true);
  const [transactionLoading, setTransactionLoading] = useState(true); 

  useEffect(() => {
    const accountId = window.location.pathname.split('/').pop();

    axios.get(`http://localhost:8081/api/accounts/${accountId}`)
      .then(response => {
        setAccountData(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching account data', error);
        setLoading(false);
      });

    axios.get(`http://localhost:8081/api/transactions/account/${accountId}`)
      .then(response => {
        setTransactions(response.data);
        setTransactionLoading(false);
      })
      .catch(error => {
        console.error('Error fetching transactions', error);
        setTransactionLoading(false);
      });
  }, []);

  if (loading) {
    return <div>Loading account details...</div>;
  }

  if (!accountData) {
    return <div>No account data available</div>;
  }

  return (
    <div className="account-details-container">
      <div className="sidebar">
        <div className="sidebar-brand">Maveric Bank</div>
        <a href="/csr-dashboard" className="sidebar-menu-item">Dashboard</a>
        <a href="/view-accounts" className="sidebar-menu-item active">View Account Details</a>
        <a href="/view-transactions" className="sidebar-menu-item">View Transaction Details</a>
        <a href="/manage-service-request" className="sidebar-menu-item">Manage Service Requests</a>
        <a href="/manage-accounts" className="sidebar-menu-item">Manage Accounts</a>

        <a href="/login" className="sidebar-menu-item">Logout</a>
      </div>

      <div className="main-content">
        <div className="header">
          <div className="header-title">Account Details</div>
          <div className="profile-dropdown">
            <img src={accountData.customer?.profileImage || 'https://via.placeholder.com/50'} alt="Profile" />
            <div className="dropdown-content">
              <a href="#"><i className="fas fa-user me-2"></i> Profile</a>
              <a href="#"><i className="fas fa-cog me-2"></i> Settings</a>
              <a href="#"><i className="fas fa-sign-out-alt me-2"></i> Logout</a>
            </div>
          </div>
        </div>

        <div className="account-details-main">
          <div className="section">
            <div className="section-title">1. Basic Account Information</div>
            <div className="details-row">
              <span>Customer Name:</span>
              <span>{accountData.customer?.name}</span>
            </div>
            <div className="details-row">
              <span>Customer ID:</span>
              <span>{accountData.customer?.id}</span>
            </div>
            <div className="details-row">
              <span>Account Number:</span>
              <span>{accountData.accountNumber}</span>
            </div>
            <div className="details-row">
              <span>Account Type:</span>
              <span>{accountData.accountType}</span>
            </div>
            <div className="details-row">
              <span>Branch Name & IFSC Code:</span>
              <span>{accountData.branch?.branchName} - {accountData.ifscCode}</span>
            </div>
            <div className="details-row">
              <span>Account Status:</span>
              <span className={`status-badge ${accountData.status === 'ACTIVE' ? 'bg-success' : 'bg-danger'}`}>{accountData.status}</span>
            </div>
          </div>

          <div className="section mt-4">
            <div className="section-title">2. Customer Contact & KYC Details</div>
            <div className="details-row">
              <span>Registered Phone Number:</span>
              <span>{accountData.customer?.mobileNo}</span>
            </div>
            <div className="details-row">
              <span>Email:</span>
              <span>{accountData.customer?.email}</span>
            </div>
            <div className="details-row">
              <span>Date of Birth & Age:</span>
              <span>{accountData.customer?.dob} ({accountData.customer?.age} years)</span>
            </div>
            <div className="details-row">
              <span>Permanent Address:</span>
              <span>{accountData.customer?.address}</span>
            </div>
          </div>

          <div className="section mt-4">
            <div className="section-title">3. Financial Overview</div>
            <div className="details-row">
              <span>Available Balance:</span>
              <span>{accountData.balance}</span>
            </div>
          </div>

          <div className="section mt-4">
            <div className="section-title">4. Transaction History & Account Activity</div>
            <h6>Recent Transactions</h6>
            
            {transactionLoading ? (
              <div>Loading transactions...</div>
            ) : (
              <table className="customer-table">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {transactions.length > 0 ? (
                    transactions.map((transaction) => (
                      <tr key={transaction.id}>
                        <td>{new Date(transaction.transactionDate).toLocaleString()}</td>
                        <td>{transaction.transactionType}</td>
                        <td>{transaction.amount}</td>
                        <td>
                          <span className={`status-badge ${transaction.status === 'SUCCESS' ? 'bg-success' : 'bg-danger'}`}>
                            {transaction.status}
                          </span>
                        </td>
                      </tr>
                    ))
                  ) : (
                    <tr>
                      <td colSpan="4">No transactions available</td>
                    </tr>
                  )}
                </tbody>
              </table>
            )}
          </div>

        </div>
      </div>
    </div>
  );
};

export default AccountDetails;
