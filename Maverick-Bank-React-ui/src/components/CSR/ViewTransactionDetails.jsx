import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; 
import './ViewTransactionDetails.css';

const ViewTransactionDetails = () => {
    const [transactions, setTransactions] = useState([]);
    const [filter, setFilter] = useState('');
    const navigate = useNavigate(); 

    useEffect(() => {
        fetchTransactions();
    }, []);

    const fetchTransactions = async () => {
        try {
            const response = await axios.get('http://localhost:8081/api/transactions/getAll'); 
            setTransactions(response.data);
        } catch (error) {
            console.error('Error fetching transactions:', error);
        }
    };

    const handleFilterChange = (e) => {
        setFilter(e.target.value);
    };

    const viewTransactionDetails = (transactionId) => {
        navigate(`/transaction-details/${transactionId}`);
    };

    return (
        <div className="view-transactions-container">
            <div className="sidebar">
                <div className="sidebar-brand">Maveric Bank</div>
                <a href="/csr-dashboard" className="sidebar-menu-item">Dashboard</a>
                <a href="/view-accounts" className="sidebar-menu-item">View Account Details</a>
                <a href="/view-transactions" className="sidebar-menu-item active">View Transaction Details</a>
                <a href="/manage-service-request" className="sidebar-menu-item">Manage Service Requests</a>
                <a href="/manage-accounts" className="sidebar-menu-item">Manage Accounts</a>

                <a href="/login" className="sidebar-menu-item">Logout</a>
            </div>

            <div className="main-content">
                <div className="header">
                    <div className="header-title">View Transactions</div>
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
                        id="transactionFilter"
                        className="filter-input"
                        placeholder="Search by Transaction ID or Account ID..."
                        value={filter}
                        onChange={handleFilterChange}
                    />
                </div>

                <div className="table-container">
                    <h5 className="mb-4">All Transactions</h5>
                    <table className="customer-table">
                        <thead>
                            <tr>
                                <th>Transaction ID</th>
                                <th>Account Number</th>
                                <th>Amount</th>
                                <th>Transaction Type</th>
                                <th>Status</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {transactions
                                .filter(transaction =>
                                    transaction.id.toString().includes(filter) ||
                                    transaction.fromAccount.accountNumber.includes(filter)
                                )
                                .map(transaction => (
                                    <tr key={transaction.id} onClick={() => viewTransactionDetails(transaction.id)}>
                                        <td>{transaction.id}</td>
                                        <td>{transaction.fromAccount.accountNumber}</td>
                                        <td>{transaction.amount}</td>
                                        <td>{transaction.transactionType}</td>
                                        <td>{transaction.status}</td>
                                        <td>{new Date(transaction.transactionDate).toLocaleString()}</td>
                                    </tr>
                                ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default ViewTransactionDetails;
