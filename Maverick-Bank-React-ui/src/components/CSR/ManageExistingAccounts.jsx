import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ManageExistingAccounts.css';

const ManageExistingAccounts = () => {
    const [accounts, setAccounts] = useState([]);
    const [filter, setFilter] = useState('');
    const [selectedAccount, setSelectedAccount] = useState(null);
    const [newStatus, setNewStatus] = useState('');
    const [showConfirm, setShowConfirm] = useState(false);

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

    const handleStatusClick = (account) => {
        const statusToSet = account.status === 'ACTIVE' ? 'DEACTIVE' : 'ACTIVE';
        setSelectedAccount(account);
        setNewStatus(statusToSet);
        setShowConfirm(true);
    };

    const confirmStatusChange = async () => {
        try {
            await axios.put(`http://localhost:8081/api/accounts/${selectedAccount.id}/status`, { status: newStatus });
            setShowConfirm(false);
            fetchAccounts();
        } catch (error) {
            console.error('Error updating status:', error);
        }
    };

    const cancelStatusChange = () => {
        setSelectedAccount(null);
        setNewStatus('');
        setShowConfirm(false);
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
                    <div className="header-title">Manage Existing Accounts</div>
                    <div className="profile-dropdown">
                        <img src="https://via.placeholder.com/50" alt="Profile" />
                    </div>
                </div>

                <div className="filter-container">
                    <input
                        type="text"
                        placeholder="Search by Account Number or Customer Name..."
                        className="filter-input"
                        value={filter}
                        onChange={handleFilterChange}
                    />
                </div>

                <div className="table-container">
                    <table className="customer-table">
                        <thead>
                            <tr>
                                <th>Account Number</th>
                                <th>Customer Name</th>
                                <th>Type</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {accounts
                                .filter(account =>
                                    account.accountNumber.toLowerCase().includes(filter.toLowerCase()) ||
                                    account.customer.name.toLowerCase().includes(filter.toLowerCase())
                                )
                                .map(account => (
                                    <tr key={account.id}>
                                        <td>{account.accountNumber}</td>
                                        <td>{account.customer.name}</td>
                                        <td>{account.accountType}</td>
                                        <td>{account.status}</td>
                                        <td>
                                            <button
                                                className={`status-btn ${account.status === 'ACTIVE' ? 'deactivate' : 'activate'}`}
                                                onClick={() => handleStatusClick(account)}
                                            >
                                                {account.status === 'ACTIVE' ? 'Deactivate' : 'Activate'}
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                        </tbody>
                    </table>
                </div>
            </div>

            {/* Confirmation Card */}
            {showConfirm && selectedAccount && (
                <div className="confirmation-overlay">
                    <div className="confirmation-card">
                        <h2>Confirm {newStatus === 'ACTIVE' ? 'Activation' : 'Deactivation'}</h2>
                        <p>
                            Are you sure you want to {newStatus.toLowerCase()} the account <strong>{selectedAccount.accountNumber}</strong>?
                        </p>
                        <div className="confirmation-buttons">
                            <button className="confirm-btn" onClick={confirmStatusChange}>Yes</button>
                            <button className="cancel-btn" onClick={cancelStatusChange}>No</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ManageExistingAccounts;
