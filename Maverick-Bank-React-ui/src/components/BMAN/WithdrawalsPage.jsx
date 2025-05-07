import React, { useEffect, useState } from 'react';
import './WithdrawalsPage.css';
import axios from 'axios';

const WithdrawalsPage = () => {
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
                const withdrawals = data.filter(transaction => 
                    transaction.transactionType === 'WITHDRAWAL' &&
                    transaction.status === 'SUCCESS' &&
                    transaction.fromAccount?.branch?.id === branchId
                );
                setTransactions(withdrawals);
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
        const headers = ['Transaction ID', 'Customer Name', 'Account Number', 'Amount', 'Date', 'Status'];
        const rows = transactions.map(txn => [
            txn.id,
            txn.fromAccount?.customer?.name || 'N/A',
            txn.fromAccount?.accountNumber || 'N/A',
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
        link.setAttribute('download', 'withdrawal_transactions.csv');
        document.body.appendChild(link);
        link.click();
    };

    const viewDetails = (transactionId) => {
        window.location.href = `withdrawals/transaction-details/${transactionId}`;
    };

    return (
        <div className="withdrawals-page">
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
                    <div className="header-title">Total Withdrawals</div>
                    <div className="profile-dropdown">
                        <img src="https://via.placeholder.com/50" alt="Profile" />
                    </div>
                </div>

                {/* Filter Section */}
                <div className="filter-container">
                    <h5>Withdrawals</h5>
                    <button className="btn-export" onClick={exportCSV}>Export CSV</button>
                </div>

                {/* Withdrawal Table */}
                <div>
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
                            {transactions.map((txn) => (
                                <tr key={txn.id} onClick={() => viewDetails(txn.id)}>
                                    <td>{txn.id}</td>
                                    <td>{txn.fromAccount?.customer?.name || 'N/A'}</td>
                                    <td>{txn.fromAccount?.accountNumber || 'N/A'}</td>
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

export default WithdrawalsPage;
