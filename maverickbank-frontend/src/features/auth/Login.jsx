import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import './Login.css';

function Login() {
    const [empID, setEmpID] = useState("");
    const [password, setPassword] = useState("");
    const [msgEmpID, setMsgEmpID] = useState(null);
    const [msgPassword, setMsgPassword] = useState(null);
    const navigate = useNavigate();

    const handleLogin = () => {
        if (!empID) {
            setMsgEmpID("Employee ID cannot be blank");
            return;
        } else {
            setMsgEmpID(null);
        }

        if (!password) {
            setMsgPassword("Password cannot be blank");
            return;
        } else {
            setMsgPassword(null);
        }

        const body = {
            username: empID,
            password: password
        };

        axios.post("http://localhost:8081/api/auth/token/generate", body)
            .then(response => {
                const token = response.data.token;
                localStorage.setItem("token", token);
                localStorage.setItem("username", response.data.username);

                axios.get("http://localhost:8081/api/auth/user/details", {
                    headers: {
                        "Authorization": `Bearer ${token}`
                    }
                }).then(resp => {
                    switch (resp.data.role) {
                        case 'CSR':
                            navigate("/csr-dashboard");
                            break;
                        case 'DLM':
                            navigate("/dlm");
                            break;
                        case 'BRANCH_MANAGER':
                            navigate("/bman-dashboard");
                            break;
                        case 'REGIONAL_MANAGER':
                            navigate("/regional-manager");
                            break;
                        default:
                            break;
                    }
                }).catch(err => {
                    setMsgEmpID("Invalid Credentials");
                    console.error(err);
                });
            }).catch(err => {
                setMsgEmpID("Invalid Credentials");
                console.error(err);
            });
    };

    return (
        <div className="login-page">
            <div className="login-container">
                <div className="brand-title">Maveric Bank</div>
                <div className="brand-subtitle">Banking Beyond Boundaries</div>

                <div className="form-group">
                    <label className="form-label">Employee ID</label>
                    <input
                        type="text"
                        className="form-control"
                        value={empID}
                        onChange={(e) => {
                            setEmpID(e.target.value);
                            setMsgEmpID(null);
                        }}
                        placeholder="Enter Employee ID"
                    />
                    {msgEmpID && <div className="error-msg">{msgEmpID}</div>}
                </div>

                <div className="form-group">
                    <label className="form-label">Password</label>
                    <input
                        type="password"
                        className="form-control"
                        value={password}
                        onChange={(e) => {
                            setPassword(e.target.value);
                            setMsgPassword(null);
                        }}
                        placeholder="Enter Password"
                    />
                    {msgPassword && <div className="error-msg">{msgPassword}</div>}
                </div>

                <button className="btn btn-primary w-100" onClick={handleLogin}>Login</button>

                <p className="footer-text">© 2025 Maveric Bank. All rights reserved.</p>
            </div>
        </div>
    );
}

export default Login;
