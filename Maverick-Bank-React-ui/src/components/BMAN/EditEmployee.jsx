import React, { useEffect, useState } from "react";
import axios from "axios";
import "./EditEmployee.css";

const EditEmployee = () => {
  const [employees, setEmployees] = useState([]);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [branches, setBranches] = useState([]);
  const [departments, setDepartments] = useState([]);

  // Local file states
  const [addressProofFile, setAddressProofFile] = useState(null);
  const [govIdFile, setGovIdFile] = useState(null);
  const [profilePicFile, setProfilePicFile] = useState(null);

  useEffect(() => {
    fetchEmployees();
    fetchBranches();
    fetchDepartments();
  }, []);

  const fetchEmployees = async () => {
    const res = await axios.get("http://localhost:8081/api/employees/getAll");
    setEmployees(res.data);
  };

  const fetchBranches = async () => {
    const res = await axios.get("http://localhost:8081/api/branches/getAll");
    setBranches(res.data);
  };

  const fetchDepartments = async () => {
    const res = await axios.get("http://localhost:8081/api/departments/getAll");
    setDepartments(res.data);
  };

  const handleSelectChange = (id) => {
    const emp = employees.find((e) => e.id === parseInt(id));
    setSelectedEmployee({ ...emp });
    setAddressProofFile(null);
    setGovIdFile(null);
    setProfilePicFile(null);
  };

  const handleChange = (e) => {
    setSelectedEmployee({
      ...selectedEmployee,
      [e.target.name]: e.target.value,
    });
  };

  const handleUpdate = async () => {
    try {
      const formData = new FormData();

      // Append fields
      formData.append("name", selectedEmployee.name);
      formData.append("email", selectedEmployee.email);
      formData.append("mobileNo", selectedEmployee.mobileNo);
      formData.append("dobStr", selectedEmployee.dob); // Ensure it matches the server's expected format
      formData.append("gender", selectedEmployee.gender);
      formData.append("address", selectedEmployee.address);
      formData.append("branchId", selectedEmployee.branch.id);
      formData.append("departmentId", selectedEmployee.department.id);

      // Append files if selected
      if (addressProofFile) formData.append("addressProof", addressProofFile);
      if (govIdFile) formData.append("governmentIdProof", govIdFile);
      if (profilePicFile) formData.append("profilePic", profilePicFile);

      await axios.put(
        `http://localhost:8081/api/employees/update/${selectedEmployee.id}`,
        formData,
        { headers: { "Content-Type": "multipart/form-data" } }
      );

      alert("Employee updated successfully!");
      fetchEmployees();
    } catch (error) {
      console.error("Update failed", error);
      alert("Failed to update employee.");
    }
  };

  return (
    <div className="edit-emp-container">
      <div className="edit-emp-sidebar">
        <div className="edit-emp-sidebar-brand">Maveric Bank</div>
        <a href="/bman-dashboard" className="edit-emp-sidebar-menu-item">
          Dashboard
        </a>
        <a href="/financial-performance" className="edit-emp-sidebar-menu-item">
          Financial Performance
        </a>
        <a
          href="/employee-management"
          className="edit-emp-sidebar-menu-item active"
        >
          Employee Management
        </a>
        <a href="/login" className="edit-emp-sidebar-menu-item">
          Logout
        </a>
      </div>

      <div className="edit-emp-main-content">
        <h2>Edit Employee</h2>

        <div className="edit-emp-form-section">
          <label>Select Employee:</label>
          <select
            onChange={(e) => handleSelectChange(e.target.value)}
            defaultValue=""
          >
            <option value="" disabled>
              Select one
            </option>
            {employees.map((emp) => (
              <option key={emp.id} value={emp.id}>
                {emp.name} (ID: {emp.id})
              </option>
            ))}
          </select>
        </div>

        {selectedEmployee && (
          <div className="edit-emp-form-section">
            <label>Name:</label>
            <input
              type="text"
              name="name"
              value={selectedEmployee.name || ""}
              onChange={handleChange}
            />

            <label>Email:</label>
            <input
              type="email"
              name="email"
              value={selectedEmployee.email || ""}
              onChange={handleChange}
            />

            <label>Mobile No:</label>
            <input
              type="text"
              name="mobileNo"
              value={selectedEmployee.mobileNo || ""}
              onChange={handleChange}
            />

            <label>Date of Birth:</label>
            <input
              type="date"
              name="dob"
              value={selectedEmployee.dob || ""}
              onChange={handleChange}
            />

            <label>Gender:</label>
            <select
              name="gender"
              value={selectedEmployee.gender}
              onChange={handleChange}
            >
              <option value="">Select</option>
              <option value="MALE">Male</option>
              <option value="FEMALE">Female</option>
            </select>

            <label>Address:</label>
            <input
              type="text"
              name="address"
              value={selectedEmployee.address || ""}
              onChange={handleChange}
            />
            

            <label>Address Proof (Image/PDF):</label>
            <input
              type="file"
              accept=".jpg,.jpeg,.png,.pdf"
              onChange={(e) => setAddressProofFile(e.target.files[0])}
            />

            <label>Government ID Proof (Image/PDF):</label>
            <input
              type="file"
              accept=".jpg,.jpeg,.png,.pdf"
              onChange={(e) => setGovIdFile(e.target.files[0])}
            />

            <label>Profile Picture (Image):</label>
            <input
              type="file"
              accept=".jpg,.jpeg,.png"
              onChange={(e) => setProfilePicFile(e.target.files[0])}
            />

            <button className="edit-emp-update-btn" onClick={handleUpdate}>
              Update Employee
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default EditEmployee;
