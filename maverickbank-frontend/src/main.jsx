import { createRoot } from 'react-dom/client'

import './api/axiosClient.js'
import App from './App.jsx'
import { BrowserRouter } from 'react-router'

createRoot(document.getElementById('root')).render(
    

    <BrowserRouter >
        <App />
    </BrowserRouter>
)
