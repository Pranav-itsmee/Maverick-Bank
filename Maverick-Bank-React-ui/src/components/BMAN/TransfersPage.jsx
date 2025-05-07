import React, { useEffect, useState } from 'react';
import './TransfersPage.css';
import axios from 'axios';

const TransfersPage = () => {
    const [transactions, setTransactions] = useState([]);
    const [branchId, setBranchId] = useState(null);

    useEffect(() => {
        const fetchEmployeeBranch = async () => {
            try {
                const empResponse = await axios.get(`http://localhost:8081/api/employees/email/${username}`); // step 2: fetch by email
                const employee = empResponse.data;
                const branchId = employee.branch?.id;
                console.log("Transactions fetched:", branchId); // Debugging line
                setBranchId(branchId); // step 3: set branchId
                fetchTransactions(branchId); // now fetch transactions with the real branch ID
            } catch (error) {
                console.error("Error fetching employee details:", error);
            }
        };
        const fetchTransactions = async () => {
            try {
                const response = await axios.get('http://localhost:8081/api/transactions/getAll');
                const data =  response.data;
                const transfers = data.filter(transaction => 
                    transaction.transactionType === 'TRANSFER' &&
                    transaction.status === 'SUCCESS' &&
                    transaction.fromAccount?.branch?.id === branchId
                );
                setTransactions(transfers);
            } catch (error) {
                console.error('Error fetching transactions:', error);
            }
        };

        fetchTransactions();
        const username = localStorage.getItem("username");
        if (username) {
            fetchEmployeeBranch(username);
        } else {
            console.error("Username not found in localStorage");
        }
    }, [branchId]);

    const exportCSV = () => {
        const headers = ['Transaction ID', 'Sender Name', 'Sender Account', 'Receiver Name', 'Receiver Account', 'Amount', 'Date', 'Status'];
        const rows = transactions.map(txn => [
            txn.id,
            txn.fromAccount?.customer?.name || 'N/A',
            txn.fromAccount?.accountNumber || 'N/A',
            txn.toAccount?.customer?.name || 'N/A',
            txn.toAccount?.accountNumber || 'N/A',
            `$${txn.amount}`,
            txn.transactionDate || 'N/A',
            txn.status
        ]);

        let csvContent = 'data:text/csv;charset=utf-8,';
        csvContent += headers.join(',') + '\n';
        rows.forEach(row => {
            csvContent += row.join(',') + '\n';
        });

        const encodedUri = encodeURI(csvContent);
        const link = document.createElement('a');
        link.setAttribute('href', encodedUri);
        link.setAttribute('download', 'transfer_transactions.csv');
        document.body.appendChild(link);
        link.click();
    };

    const viewDetails = (transactionId) => {
        window.location.href = `transfers/transaction-details/${transactionId}`;
    };

    return (
        <div className="transfers-page">
            {/* Sidebar */}
            <div className="sidebar">
                <div className="sidebar-brand">Maveric Bank</div>
                <a href="/bman-dashboard" className="sidebar-menu-item">Dashboard</a>
                <a href="/financial-performance" className="sidebar-menu-item active">Financial Performance</a>
                <a href="/employee-management" className="sidebar-menu-item">Add Employee</a>
                <a href="/login" className="sidebar-menu-item">Logout</a>
            </div>

            {/* Main Content */}
            <div className="main-content">
                {/* Header */}
                <div className="header">
                    <div className="header-title">Total Transfers</div>
                    <div className="profile-dropdown">
                        <img src="https://via.placeholder.com/50" alt="Profile" />
                    </div>
                </div>

                {/* Filter Section */}
                <div className="filter-container">
                    <h5>Transfers</h5>
                    <button className="btn-export" onClick={exportCSV}>Export CSV</button>
                </div>

                {/* Transfers Table */}
                <div>
                    <table className="transfer-table">
                        <thead>
                            <tr>
                                <th>Transaction ID</th>
                                <th>Sender Name</th>
                                <th>Sender Account</th>
                                {/* <th>Receiver Name</th>
                                <th>Receiver Account</th> */}
                                <th>Amount</th>
                                <th>Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            {transactions.map((txn) => (
                                <tr key={txn.id} onClick={() => viewDetails(txn.id)}>
                                    <td>{txn.id}</td>
                                    <td>{txn.fromAccount?.customer?.name || 'N/A'}</td>
                                    <td>{txn.fromAccount?.accountNumber || 'N/A'}</td>
                                    {/* <td>{txn.toAccount?.customer?.name || 'N/A'}</td>
                                    <td>{txn.toAccount?.accountNumber || 'N/A'}</td> */}
                                    <td>${txn.amount}</td>
                                    <td>{txn.transactionDate || 'N/A'}</td>
                                    <td>
                                        <span className={`status-badge ${txn.status === 'SUCCESS' ? 'bg-success' : 'bg-danger'}`}>
                                            {txn.status}
                                        </span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default TransfersPage;
