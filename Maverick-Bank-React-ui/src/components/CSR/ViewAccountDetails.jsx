import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ViewAccountDetails.css';

const ViewAccountDetails = () => {
    const [accounts, setAccounts] = useState([]);
    const [filter, setFilter] = useState('');

    useEffect(() => {
        fetchAccounts();
    }, []);

    const fetchAccounts = async () => {
        try {
            const response = await axios.get('http://localhost:8081/api/accounts/getAll');
            setAccounts(response.data);
        } catch (error) {
            console.error('Error fetching accounts:', error);
        }
    };

    const handleFilterChange = (e) => {
        setFilter(e.target.value);
    };

    const viewAccountDetails = (accountId) => {
        window.location.href = `/account-details/${accountId}`;
    };

    return (
        <div className="view-accounts-container">
            {/* Sidebar */}
            <div className="sidebar">
                <div className="sidebar-brand">Maveric Bank</div>
                <a href="/csr-dashboard" className="sidebar-menu-item">Dashboard</a>
                <a href="/view-accounts" className="sidebar-menu-item active">View Account Details</a>
                <a href="/view-transactions" className="sidebar-menu-item">View Transaction Details</a>
                <a href="/manage-service-request" className="sidebar-menu-item">Manage Service Requests</a>
                <a href="/manage-accounts" className="sidebar-menu-item">Manage Accounts</a>
                <a href="/login" className="sidebar-menu-item">Logout</a>
            </div>

            {/* Main Content */}
            <div className="main-content">
                <div className="header">
                    <div className="header-title">View Accounts</div>
                    <div className="profile-dropdown">
                        <img src="https://via.placeholder.com/50" alt="Profile" />
                        <div className="dropdown-content">
                            <a href="#">Profile</a>
                            <a href="#">Settings</a>
                            <a href="#">Logout</a>
                        </div>
                    </div>
                </div>

                {/* Filter */}
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

                {/* Table */}
                <div className="table-container">
                    <h5>All Accounts</h5>
                    <table className="customer-table">
                        <thead>
                            <tr>
                                <th>Account Number</th>
                                <th>Customer Name</th>
                                <th>Email</th>
                                <th>Phone</th>
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
                                        <td>{account.customer.name}</td>
                                        <td>{account.customer.email}</td>
                                        <td>{account.customer.mobileNo}</td>
                                    </tr>
                                ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default ViewAccountDetails;
