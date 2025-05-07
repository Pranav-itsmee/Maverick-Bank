import React, { useState } from "react";
import axios from "axios";
import "./AddEmployee.css";

const AddEmployee = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    mobileNo: "",
    dobStr: "",
    gender: "MALE", // Use proper enum values
    profilePic: null,
    address: "",
    addressProof: null,
    governmentIdProof: null,
    branchId: "",
    role: "",
    password: "",
  });

  const roleToDepartmentId = {
    CSR: 1,
    DLM: 2,
    BRANCH_MANAGER: 3,
    REGIONAL_MANAGER: 4,
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e, fieldName) => {
    const file = e.target.files[0];
    if (file) {
      setFormData((prev) => ({ ...prev, [fieldName]: file }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const dobFormatted = new Date(formData.dobStr).toISOString().split('T')[0];
    const departmentId = roleToDepartmentId[formData.role];

    const data = new FormData();
    data.append("name", formData.name);
    data.append("email", formData.email);
    data.append("mobileNo", formData.mobileNo);
    data.append("dobStr", dobFormatted);
    data.append("gender", formData.gender); // must be "MALE" or "FEMALE"
    data.append("profilePic", formData.profilePic);
    data.append("address", formData.address);
    data.append("addressProof", formData.addressProof);
    data.append("governmentIdProof", formData.governmentIdProof);
    data.append("branchId", formData.branchId);
    data.append("role", formData.role);
    data.append("departmentId", departmentId);
    data.append("password", formData.password);

    console.log("Role:", formData.role);
    console.log("Department ID:", departmentId);

    try {
      const response = await axios.post("http://localhost:8081/api/employees/add", data, {
        headers: { "Content-Type": "multipart/form-data" }
      });
      alert("Employee added successfully!");
    } catch (error) {
      console.error("Error adding employee:", error);
      if (error.response) {
        console.error("Backend Error Response:", error.response.data);
        alert(`Failed to add employee: ${error.response.data.message}`);
      } else {
        alert("Failed to add employee: Unknown error");
      }
    }
  };

  return (
    <div className="add-employee-container">
      <div className="add-employee-sidebar">
        <div className="add-employee-sidebar-brand">Maveric Bank</div>
        <a href="/bman-dashboard" className="add-employee-sidebar-menu-item">Dashboard</a>
        <a href="/financial-performance" className="add-employee-sidebar-menu-item">Financial Performance</a>
        <a href="/employee-management" className="add-employee-sidebar-menu-item active">Employee Management</a>
        <a href="/login" className="add-employee-sidebar-menu-item">Logout</a>
      </div>

      <div className="add-employee-main-content">
        <div className="add-employee-header">
          <div className="add-employee-header-title">Add Employee</div>
          <div className="add-employee-profile-dropdown">
            <img src="https://via.placeholder.com/50" alt="Profile" />
          </div>
        </div>

        <form className="add-employee-form" onSubmit={handleSubmit}>
          {[
            { label: "Name", name: "name", type: "text" },
            { label: "Email", name: "email", type: "email" },
            { label: "Password", name: "password", type: "password" },
            { label: "Contact Number", name: "mobileNo", type: "text" },
            { label: "Date of Birth", name: "dobStr", type: "date" },
            {
              label: "Gender",
              name: "gender",
              type: "select",
              options: [
                { label: "Male", value: "MALE" },
                { label: "Female", value: "FEMALE" },
              ],
            },
            { label: "Profile Picture", name: "profilePic", type: "file" },
            { label: "Address", name: "address", type: "text" },
            { label: "Address Proof", name: "addressProof", type: "file" },
            { label: "ID Proof", name: "governmentIdProof", type: "file" },
            { label: "Branch ID", name: "branchId", type: "text" },
            {
              label: "Role",
              name: "role",
              type: "select",
              options: [
                { label: "CSR (Customer Service Rep)", value: "CSR" },
                { label: "DLM (Department Loan Manager)", value: "DLM" },
              ],
            },
          ].map(({ label, name, type, options }) => (
            <div className="add-employee-form-group" key={name}>
              <label>{label}</label>
              {type === "select" ? (
                <select
                  name={name}
                  className="add-employee-form-select"
                  value={formData[name]}
                  onChange={handleChange}
                  required
                >
                  <option value="">Select</option>
                  {options.map((opt) => (
                    <option key={opt.value} value={opt.value}>
                      {opt.label}
                    </option>
                  ))}
                </select>
              ) : type === "file" ? (
                <input
                  type="file"
                  name={name}
                  accept=".jpg,.jpeg,.png,.pdf"
                  className="add-employee-form-file-input"
                  required
                  onChange={(e) => handleFileChange(e, name)}
                />
              ) : (
                <input
                  type={type}
                  name={name}
                  className="add-employee-form-input"
                  value={formData[name]}
                  required
                  onChange={handleChange}
                />
              )}
            </div>
          ))}
          <button type="submit" className="add-employee-form-submit-button">
            Add Employee
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddEmployee;
