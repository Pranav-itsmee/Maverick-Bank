import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ViewDeactivatedAccounts.css'; // You can style it the same way as 'ViewAccountDetails.css'

const ViewDeactivatedAccounts = () => {
    const [accounts, setAccounts] = useState([]);
    const [filter, setFilter] = useState('');

    useEffect(() => {
        fetchDeactivatedAccounts();
    }, []);

    const fetchDeactivatedAccounts = async () => {
        try {
            const response = await axios.get('http://localhost:8081/api/accounts/getDeactivated');
            setAccounts(response.data);
        } catch (error) {
            console.error('Error fetching deactivated accounts:', error);
        }
    };

    const handleFilterChange = (e) => {
        setFilter(e.target.value);
    };

    const viewAccountDetails = async (accountId) => {
        try {
            const response = await axios.get(`http://localhost:8081/api/accounts/${accountId}`);
            window.location.href = `/account-details/${accountId}`;
        } catch (error) {
            console.error('Error fetching account details:', error);
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
                <a href="/manage-accounts" className="sidebar-menu-item active">Manage Accounts</a>

                <a href="/login" className="sidebar-menu-item">Logout</a>
            </div>

            <div className="main-content">
                <div className="header">
                    <div className="header-title">View Deactivated Accounts</div>
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
                        id="nameFilter"
                        className="filter-input"
                        placeholder="Search by Account Number or Customer Name..."
                        value={filter}
                        onChange={handleFilterChange}
                    />
                </div>

                <div className="table-container">
                    <h5 className="mb-4">Deactivated Accounts</h5>
                    <table className="customer-table">
                        <thead>
                            <tr>
                                <th>Account Number</th>
                                <th>Account Type</th>
                                <th>Balance</th>
                                <th>IFSC Code</th>
                                <th>Status</th>
                                <th>Customer Name</th>
                            </tr>
                        </thead>
                        <tbody>
                            {accounts
                                .filter(account =>
                                    account.accountNumber.toLowerCase().includes(filter.toLowerCase()) ||
                                    account.customer.name.toLowerCase().includes(filter.toLowerCase())
                                )
                                .map(account => (
                                    <tr key={account.id} onClick={() => viewAccountDetails(account.id)}>
                                        <td>{account.accountNumber}</td>
                                        <td>{account.accountType}</td>
                                        <td>{account.balance}</td>
                                        <td>{account.ifscCode}</td>
                                        <td>{account.status}</td>
                                        <td>{account.customer.name}</td>
                                    </tr>
                                ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default ViewDeactivatedAccounts;
