import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './DeleteEmployee.css';

const DeleteEmployee = () => {
  const [employees, setEmployees] = useState([]);
  const [showConfirm, setShowConfirm] = useState(false);
  const [selectedEmpId, setSelectedEmpId] = useState(null);

  useEffect(() => {
    fetchEmployees();
  }, []);

  const fetchEmployees = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/employees/getAll');
      setEmployees(response.data);
    } catch (error) {
      console.error('Error fetching employees:', error);
    }
  };

  const handleDeleteClick = (id) => {
    setSelectedEmpId(id);
    setShowConfirm(true);
  };

  const confirmDelete = async () => {
    try {
      await axios.delete(`http://localhost:8081/api/employees/${selectedEmpId}`);
      setEmployees(employees.filter(emp => emp.id !== selectedEmpId));
      setShowConfirm(false);
      setSelectedEmpId(null);
    } catch (error) {
      console.error('Error deleting employee:', error);
    }
  };

  const cancelDelete = () => {
    setShowConfirm(false);
    setSelectedEmpId(null);
  };

  return (
    <div className="delete-employee-container">
      {/* Sidebar */}
      <div className="delete-employee-sidebar">
        <div className="delete-employee-sidebar-brand">Maveric Bank</div>
        <a href="/bman-dashboard" className="delete-employee-sidebar-menu-item">Dashboard</a>
        <a href="/financial-performance" className="delete-employee-sidebar-menu-item">Financial Performance</a>
        <a href="/employee-management" className="delete-employee-sidebar-menu-item active">Employee Management</a>
        <a href="/login" className="delete-employee-sidebar-menu-item">Logout</a>
      </div>

      {/* Main Content */}
      <div className="delete-employee-main-content">
        <div className="delete-employee-header">
          <div className="delete-employee-header-title">Delete Employee</div>
        </div>

        <table className="delete-employee-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Branch</th>
              <th>Department</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {employees.map(emp => (
              <tr key={emp.id}>
                <td>{emp.id}</td>
                <td>{emp.name}</td>
                <td>{emp.email}</td>
                <td>{emp.branch?.branchName}</td>
                <td>{emp.department?.role}</td>
                <td>
                  <button className="delete-employee-btn" onClick={() => handleDeleteClick(emp.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Confirmation Dialog */}
        {showConfirm && (
          <div className="delete-employee-confirm-overlay">
            <div className="delete-employee-confirm-card">
              <p>Are you sure you want to delete this employee?</p>
              <div className="delete-employee-confirm-buttons">
                <button className="delete-employee-confirm-btn" onClick={confirmDelete}>Yes, Delete</button>
                <button className="delete-employee-cancel-btn" onClick={cancelDelete}>Cancel</button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default DeleteEmployee;
