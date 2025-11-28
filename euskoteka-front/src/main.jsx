import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router'
import { ContextoProvider } from './context/Contexto.jsx'
// import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <ContextoProvider>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </ContextoProvider>
)
