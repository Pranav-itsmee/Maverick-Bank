import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './FinancialPerformance.css';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const FinancialPerformance = () => {
    const [deposits, setDeposits] = useState(0);
    const [withdrawals, setWithdrawals] = useState(0);
    const [transfers, setTransfers] = useState(0);
    const [branchId, setBranchId] = useState(null);
    const [transactions, setTransactions] = useState([]);

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

        const fetchTransactions = async (branchId) => {
            try {
                const response = await axios.get(`http://localhost:8081/api/transactions/getAll`);
                const data =  response.data;
                setTransactions(data);
                calculateTransactions(data, branchId);
            } catch (error) {
                console.error("Error fetching transactions:", error);
            }
        };
        fetchTransactions();
        const username = localStorage.getItem("username");
        if (username) {
            fetchEmployeeBranch(username);
        } else {
            console.error("Username not found in localStorage");
        }
        
    }, []);

    const calculateTransactions = (data, branchId) => {
        let totalDeposits = 0;
        let totalWithdrawals = 0;
        let totalTransfers = 0;

        data.forEach(transaction => {
            const fromBranchId = transaction.fromAccount?.branch?.id;
            const transactionStatus = transaction.status;

            if (transactionStatus === "SUCCESS" && fromBranchId === branchId) {
                if (transaction.transactionType === "DEPOSIT") {
                    totalDeposits += transaction.amount;
                } else if (transaction.transactionType === "WITHDRAWAL") {
                    totalWithdrawals += transaction.amount;
                } else if (transaction.transactionType === "TRANSFER") {
                    totalTransfers += transaction.amount;
                }
            }
        });

        setDeposits(totalDeposits);
        setWithdrawals(totalWithdrawals);
        setTransfers(totalTransfers);
    };

    const exportReport = () => {
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;

        if (!startDate || !endDate) {
            alert("Please select both Start Date and End Date.");
            return;
        }

        alert(`Report exported successfully for the period: ${startDate} to ${endDate}`);
    };

    const chartData = {
        labels: ['Deposits', 'Withdrawals', 'Transfers'],
        datasets: [
            {
                label: 'Amount ($)',
                data: [deposits, withdrawals, transfers],
                backgroundColor: ['#0d6efd', '#dc3545', '#ffc107'],
                borderRadius: 10,
            },
        ],
    };

    const chartOptions = {
        responsive: true,
        plugins: {
            legend: { display: false },
            title: {
                display: true,
                text: 'Branch Financial Summary',
                font: { size: 16 },
            },
        },
        animation: {
            duration: 1500,
            easing: 'easeOutBounce',
        },
        scales: {
            y: {
                beginAtZero: true,
                ticks: { stepSize: 1000 },
            },
        },
    };

    return (
        <div className="financial-performance-container">
            {/* Sidebar */}
            <div className="financial-performance-sidebar">
                <div className="financial-performance-sidebar-brand">Maveric Bank</div>
                <a href="/bman-dashboard" className="financial-performance-sidebar-menu-item">Dashboard</a>
                <a href="/financial-performance" className="financial-performance-sidebar-menu-item active">Financial Performance</a>
                <a href="/employee-management" className="financial-performance-sidebar-menu-item">Employee Management</a>
                <a href="/login" className="financial-performance-sidebar-menu-item">Logout</a>
            </div>

            {/* Main Content */}
            <div className="financial-performance-main-content">
                <div className="financial-performance-header">
                    <h1>Financial Performance Reports</h1>
                    <div className="financial-performance-profile-dropdown">
                        <img src="https://via.placeholder.com/50" alt="Profile" />
                        <div className="financial-performance-dropdown-content">
                            <a href="#"><i className="fas fa-user me-2"></i> Profile</a>
                            <a href="#"><i className="fas fa-cog me-2"></i> Settings</a>
                            <a href="#"><i className="fas fa-sign-out-alt me-2"></i> Logout</a>
                        </div>
                    </div>
                </div>

                {/* Insight Cards */}
                <div className="financial-performance-insight-cards">
                    <div className="financial-performance-insight-card clickable" onClick={() => window.location.href = '/deposits'}>
                        <div className="financial-performance-insight-card-title">Total Deposits</div>
                        <div className="financial-performance-insight-card-value">${deposits}</div>
                    </div>
                    <div className="financial-performance-insight-card clickable" onClick={() => window.location.href = '/withdrawals'}>
                        <div className="financial-performance-insight-card-title">Total Withdrawals</div>
                        <div className="financial-performance-insight-card-value">${withdrawals}</div>
                    </div>
                    <div className="financial-performance-insight-card clickable" onClick={() => window.location.href = '/transfers'}>
                        <div className="financial-performance-insight-card-title">Total Transfers</div>
                        <div className="financial-performance-insight-card-value">${transfers}</div>
                    </div>
                </div>

                {/* Bar Chart */}
                <div className="financial-performance-chart-container">
                    <h5>Branch Financial Summary</h5>
                    <Bar data={chartData} options={chartOptions} />
                </div>

                {/* Export Report Section */}
                <div className="financial-performance-export-report-section">
                    <h5>Export Transaction Report</h5>
                    <label htmlFor="startDate">Start Date:</label>
                    <input type="date" id="startDate" />
                    <label htmlFor="endDate">End Date:</label>
                    <input type="date" id="endDate" />
                    <button className="financial-performance-btn-export" onClick={exportReport}>Export Report</button>
                </div>
            </div>
        </div>
    );
};

export default FinancialPerformance;
