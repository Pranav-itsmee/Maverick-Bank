import { Route, Routes } from "react-router"
import Login from "./features/auth/Login"
import '@fortawesome/fontawesome-free/css/all.min.css';
import CustomerSupportDashboard from "./features/csr/pages/CustomerSupportDashboard"
import ViewAccountDetails from "./features/csr/pages/ViewAccountDetails"
import ViewTransactionDetails from "./features/csr/pages/ViewTransactionDetails"
import ManageServiceRequests from "./features/csr/pages/ManageServiceRequests"
import AccountDetails from "./features/csr/pages/AccountDetails"
import TransactionDetails from "./features/csr/pages/TransactionDetails"
import RequestDetails from "./features/csr/pages/RequestDetails"
import BranchManagerDashboard from "./features/branch-manager/pages/BranchManagerDashboard"
import FinancialPerformance from "./features/branch-manager/pages/FinancialPerformance"
import DepositsPage from "./features/branch-manager/pages/DepositsPage"
import WithdrawalsPage from "./features/branch-manager/pages/WithdrawalsPage"
import TransfersPage from "./features/branch-manager/pages/TransfersPage"
import EmployeeManagement from "./features/branch-manager/pages/EmployeeManagement"
import BmanTransactionDetails from "./features/branch-manager/pages/BmanTransactionDetails"
import ManageAccounts from "./features/csr/pages/ManageAccounts"
import AddAccount from "./features/csr/pages/AddAccount"
import AddCustomer from "./features/csr/pages/AddCustomer"
import ViewDeactivatedAccounts from "./features/csr/pages/ViewDeactivatedAccounts"
import ManageExistingAccounts from "./features/csr/pages/ManageExistingAccounts";
import AddEmployee from "./features/branch-manager/pages/AddEmployee"
import DeleteEmployee from "./features/branch-manager/pages/DeleteEmployee"
import EditEmployee from "./features/branch-manager/pages/EditEmployee"

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
      <Route path="/deposits/transaction-details/:transactionId" element={<BmanTransactionDetails />} />
      <Route path="/transfers/transaction-details/:transactionId" element={<BmanTransactionDetails />} />
      <Route path="/withdrawals/transaction-details/:transactionId" element={<BmanTransactionDetails />} />
      <Route path="/employee-management" element={<EmployeeManagement />} />
      <Route path="/add-employee" element={<AddEmployee />} />
      <Route path="/delete-employee" element={<DeleteEmployee />} />
      <Route path="/edit-employee" element={<EditEmployee />} />
    </Routes>
  )
}

export default App
