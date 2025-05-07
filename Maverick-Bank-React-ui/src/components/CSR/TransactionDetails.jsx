import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './TransactionDetails.css'; 

const BmanTransactionDetails = () => {
    const [transaction, setTransaction] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const transactionId = window.location.pathname.split('/').pop()
        if (transactionId) {
            axios.get(`http://localhost:8081/api/transactions/${transactionId}`) 
                .then((response) => {
                    setTransaction(response.data);
                    setLoading(false);
                })
                .catch((err) => {
                    setError("Error fetching transaction details");
                    setLoading(false);
                });
        }
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    if (!transaction) {
        return <div>Transaction not found.</div>;
    }

    const { 
        id: transactionId,
        amount,
        transactionType,
        status,
        transactionMode,
        purpose,
        description,
        transactionDate,
        fromAccount
    } = transaction;

    const { 
        accountNumber: senderAccountNumber,
        customer: { name: senderName },
        branch: senderBranchDetails,
        ifscCode
    } = fromAccount;

    const { branchName } = senderBranchDetails;

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-4">
                    <div className="sidebar">
                        <div className="sidebar-brand">Maveric Bank</div>
                        <a href="/csr-dashboard" className="sidebar-menu-item">Dashboard</a>
                        <a href="/view-accounts" className="sidebar-menu-item">View Account Details</a>
                        <a href="/view-transactions" className="sidebar-menu-item active">View Transaction Details</a>
                        <a href="/manage-service-request" className="sidebar-menu-item">Manage Service Requests</a>
                        <a href="/manage-accounts" className="sidebar-menu-item">Manage Accounts</a>

                        <a href="/login" className="sidebar-menu-item">Logout</a>
                    </div>
                </div>

                <div className="main-content">
                    <div className="header">
                        <div className="header-title">Transaction Details</div>
                    </div>

                    <div className="details-container">
                        <div className="section">
                            <div className="section-title">General Transaction Information</div>
                            <div className="details-row">
                                <span>Transaction ID:</span>
                                <span>{transactionId}</span>
                            </div>
                            <div className="details-row">
                                <span>Transaction Date & Time:</span>
                                <span>{new Date(transactionDate).toLocaleString()}</span>
                            </div>
                            <div className="details-row">
                                <span>Transaction Type:</span>
                                <span>{transactionType}</span>
                            </div>
                            <div className="details-row">
                                <span>Amount:</span>
                                <span>{amount}</span>
                            </div>
                            <div className="details-row">
                                <span>Transaction Status:</span>
                                <span className={`status-badge bg-${status.toLowerCase()}`}>{status}</span>
                            </div>
                            <div className="details-row">
                                <span>Transaction Mode:</span>
                                <span>{transactionMode}</span>
                            </div>
                            <div className="details-row">
                                <span>Purpose:</span>
                                <span>{purpose}</span>
                            </div>
                            <div className="details-row">
                                <span>Description:</span>
                                <span>{description}</span>
                            </div>
                        </div>

                        <div className="section mt-4">
                            <div className="section-title">Sender Details</div>
                            <div className="details-row">
                                <span>Account Number:</span>
                                <span>{senderAccountNumber}</span>
                            </div>
                            <div className="details-row">
                                <span>Name:</span>
                                <span>{senderName}</span>
                            </div>
                            <div className="details-row">
                                <span>Branch Name & IFSC Code:</span>
                                <span>{branchName} - {ifscCode}</span>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
};

export default BmanTransactionDetails;
