import React, { useState, useEffect } from "react";
import axios from "axios";
import "./RequestDetails.css";

const RequestDetails = () => {
    const [serviceRequest, setServiceRequest] = useState(null);
    const [remarks, setRemarks] = useState("");
    const [status, setStatus] = useState("Pending");
    const [isLoading, setIsLoading] = useState(true);

    const requestId = window.location.pathname.split('/').pop();

    useEffect(() => {
        const fetchServiceRequest = async () => {
            try {
                setIsLoading(true);
                const response = await axios.get(`http://localhost:8081/api/service-requests/${requestId}`);
                setServiceRequest(response.data);
            } catch (error) {
                console.error("Error fetching service request:", error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchServiceRequest();
    }, [requestId]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const updateResponse = await axios.put(`http://localhost:8081/api/service-requests/${serviceRequest.id}`, {
                status
            });

            if (updateResponse.status === 200) {
                const serviceQueryData = {
                    requestId: serviceRequest.id,
                    remarks,
                    status,
                    queryDate: new Date().toISOString(),
                };

                const createQueryResponse = await axios.post(`http://localhost:8081/api/service-queries/add`, serviceQueryData);

                if (createQueryResponse.status === 200) {
                    alert("Request updated and query added successfully.");
                } else {
                    alert("Error adding service query.");
                }
            }
        } catch (error) {
            console.error("Error updating service request and adding service query:", error);
            alert("Error updating service request or adding service query.");
        }
    };

    if (isLoading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container">
            <div className="sidebar">
                <div className="sidebar-brand">Maveric Bank</div>
                <a href="/csr-dashboard" className="sidebar-menu-item">Dashboard</a>
                <a href="/view-accounts" className="sidebar-menu-item">View Account Details</a>
                <a href="/view-transactions" className="sidebar-menu-item">View Transaction Details</a>
                <a href="/manage-service-request" className="sidebar-menu-item active">Manage Service Requests</a>
                <a href="/manage-accounts" className="sidebar-menu-item">Manage Accounts</a>

                <a href="/login" className="sidebar-menu-item">Logout</a>
            </div>

            <div className="main-content">
                <div className="header">
                    <div className="header-title">Service Request Details</div>
                </div>

                <div className="details-container">
                    <div className="section">
                        <div className="section-title">Account & Customer Details</div>
                        <div className="details-row">
                            <span>Request ID:</span>
                            <span>{serviceRequest.id}</span>
                        </div>
                        <div className="details-row">
                            <span>Customer Name:</span>
                            <span>{serviceRequest.customer.name}</span>
                        </div>
                        <div className="details-row">
                            <span>Email:</span>
                            <span>{serviceRequest.customer.email}</span>
                        </div>
                        <div className="details-row">
                            <span>Phone:</span>
                            <span>{serviceRequest.customer.mobileNo}</span>
                        </div>
                        <div className="details-row">
                            <span>Account ID:</span>
                            <span>{serviceRequest.customer.panNumber}</span>
                        </div>
                        <div className="details-row">
                            <span>Branch:</span>
                            <span>{serviceRequest.customer.branch.branchName}</span>
                        </div>
                        <div className="details-row">
                            <span>Created Date:</span>
                            <span>{new Date(serviceRequest.createdDate).toLocaleDateString()}</span>
                        </div>
                    </div>

                    <div className="section mt-4">
                        <div className="section-title">Service Request Information</div>
                        <div className="details-row">
                            <span>Category:</span>
                            <span>{serviceRequest.category}</span>
                        </div>
                        <div className="details-row">
                            <span>Subject:</span>
                            <span>{serviceRequest.subject}</span>
                        </div>
                        <div className="details-row">
                            <span>Message:</span>
                            <span>{serviceRequest.message}</span>
                        </div>
                        <div className="details-row">
                            <span>Proof:</span>
                            <img src={serviceRequest.attachmentUrl} alt="Proof Document" className="proof-image" />
                        </div>
                    </div>

                    <div className="section mt-4">
                        <div className="section-title">Add Remarks</div>
                        <textarea
                            className="form-control"
                            rows="4"
                            placeholder="Add remarks"
                            value={remarks}
                            onChange={(e) => setRemarks(e.target.value)}
                        ></textarea>
                    </div>

                    <div className="section mt-4">
                        <div className="section-title">Update Status</div>
                        <select
                            className="form-control"
                            value={status}
                            onChange={(e) => setStatus(e.target.value)}
                        >
                            <option value="PENDING">PENDING</option>
                            <option value="RESOLVED">RESOLVED</option>
                            <option value="IN-PROGRESS">IN-PROGRESS</option>
                        </select>
                    </div>

                    <div className="section mt-4 text-center">
                        <button className="resolve-btn" onClick={handleSubmit}>
                            Update Request
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RequestDetails;
