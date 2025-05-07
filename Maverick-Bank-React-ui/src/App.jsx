import { Route, Routes } from "react-router"
import Login from "./components/auth/Login"
import '@fortawesome/fontawesome-free/css/all.min.css';
import CustomerSupportDashboard from "./components/CSR/CustomerSupportDashboard"
import ViewAccountDetails from "./components/CSR/ViewAccountDetails"
import ViewTransactionDetails from "./components/CSR/ViewTransactionDetails"
import ManageServiceRequests from "./components/CSR/ManageServiceRequests"
import AccountDetails from "./components/CSR/AccountDetails"
import TransactionDetails from "./components/CSR/TransactionDetails"
import RequestDetails from "./components/CSR/RequestDetails"
import BranchManagerDashboard from "./components/BMAN/BranchManagerDashboard"
import FinancialPerformance from "./components/BMAN/FinancialPerformance"
import DepositsPage from "./components/BMAN/DepositsPage"
import WithdrawalsPage from "./components/BMAN/WithdrawalsPage"
import TransfersPage from "./components/BMAN/TransfersPage"
import EmployeeManagement from "./components/BMAN/EmployeeManagement"
import BmanTransactionDetails from "./components/BMAN/BmanTransactionDetails"
import ManageAccounts from "./components/CSR/ManageAccounts"
import AddAccount from "./components/CSR/AddAccount"
import AddCustomer from "./components/CSR/AddCustomer"
import ViewDeactivatedAccounts from "./components/CSR/ViewDeactivatedAccounts"
import ManageExistingAccounts from "./components/CSR/ManageExistingAccounts";
import AddEmployee from "./components/BMAN/AddEmployee"
import DeleteEmployee from "./components/BMAN/DeleteEmployee"
import EditEmployee from "./components/BMAN/EditEmployee"

function App() { //app is a component and a component must have a return 

  return (
    <Routes>
      <Route index path="/login" element={<Login />} />
      <Route path ="/csr-dashboard" element={<CustomerSupportDashboard />} />
      <Route path="/manage-accounts" element={<ManageAccounts/>} />
      <Route path="/view-accounts" element={<ViewAccountDetails/>} />
      <Route path="/view-transactions" element={<ViewTransactionDetails />} />
      <Route path="/manage-service-request" element={<ManageServiceRequests />} />
      <Route path="/account-details/:accountId" element={<AccountDetails />} />
      <Route path="/transaction-details/:transactionId" element={<TransactionDetails />} />
      <Route path="/service-request-details/:requestId" element={<RequestDetails />} />
      <Route path="/add-account" element={<AddAccount />} />
      <Route path="/add-customer" element={<AddCustomer />} />
      <Route path="/deactivated-accounts" element={<ViewDeactivatedAccounts />} />
      <Route path="/manage-existing-accounts" element={<ManageExistingAccounts />} />
     

      <Route path="/bman-dashboard" element={<BranchManagerDashboard />} />
      <Route path="/financial-performance" element={<FinancialPerformance />} />
      <Route path="/deposits" element={<DepositsPage />} />
      <Route path="/withdrawals" element={<WithdrawalsPage />} />
      <Route path="/transfers" element={<TransfersPage />} />
      <Route path="transfers/transaction-details/:transactionId" element={<BmanTransactionDetails />} />
      <Route path="/withdrawals/transaction-details/:transactionId" element={<BmanTransactionDetails />} />
      <Route path="/employee-management" element={<EmployeeManagement />} />
      <Route path="/add-employee" element={<AddEmployee />} />
      <Route path="delete-employee" element={<DeleteEmployee />} />
      <Route path="/edit-employee" element={<EditEmployee />} />
    </Routes>
  )
}

export default App
