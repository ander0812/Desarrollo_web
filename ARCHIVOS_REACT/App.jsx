import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Clientes from './pages/Clientes';
import Dashboard from './pages/Dashboard';

function App() {
  return (
    <Router>
      <div style={{ minHeight: '100vh', backgroundColor: '#f5f5f5' }}>
        <nav style={{ 
          backgroundColor: '#1e3c72', 
          color: 'white', 
          padding: '1rem 2rem',
          marginBottom: '20px'
        }}>
          <h1 style={{ margin: 0 }}>Sistema de Seguridad y Entrenamiento</h1>
        </nav>
        
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/clientes" element={<Clientes />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

