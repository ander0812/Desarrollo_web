import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Dashboard from './pages/Dashboard';
import Clientes from './pages/Clientes';
import ClienteForm from './pages/ClienteForm';
import ClienteDetalle from './pages/ClienteDetalle';
import Servicios from './pages/Servicios';
import ServicioForm from './pages/ServicioForm';
import ServicioDetalle from './pages/ServicioDetalle';
import Programas from './pages/Programas';
import ProgramaForm from './pages/ProgramaForm';
import ProgramaDetalle from './pages/ProgramaDetalle';
import Contrataciones from './pages/Contrataciones';
import ContratacionForm from './pages/ContratacionForm';
import ContratacionDetalle from './pages/ContratacionDetalle';
import Reservas from './pages/Reservas';
import ReservaForm from './pages/ReservaForm';
import ReservaDetalle from './pages/ReservaDetalle';
import Pagos from './pages/Pagos';
import PagoForm from './pages/PagoForm';
import PagoDetalle from './pages/PagoDetalle';
import Informes from './pages/Informes';
import './index.css';

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/dashboard" element={<Dashboard />} />
          
          {/* Clientes */}
          <Route path="/clientes" element={<Clientes />} />
          <Route path="/clientes/nuevo" element={<ClienteForm />} />
          <Route path="/clientes/editar/:id" element={<ClienteForm />} />
          <Route path="/clientes/detalle/:id" element={<ClienteDetalle />} />
          
          {/* Servicios */}
          <Route path="/servicios" element={<Servicios />} />
          <Route path="/servicios/nuevo" element={<ServicioForm />} />
          <Route path="/servicios/editar/:id" element={<ServicioForm />} />
          <Route path="/servicios/detalle/:id" element={<ServicioDetalle />} />
          
          {/* Programas */}
          <Route path="/programas" element={<Programas />} />
          <Route path="/programas/nuevo" element={<ProgramaForm />} />
          <Route path="/programas/editar/:id" element={<ProgramaForm />} />
          <Route path="/programas/detalle/:id" element={<ProgramaDetalle />} />
          
          {/* Contrataciones */}
          <Route path="/contrataciones" element={<Contrataciones />} />
          <Route path="/contrataciones/nuevo" element={<ContratacionForm />} />
          <Route path="/contrataciones/editar/:id" element={<ContratacionForm />} />
          <Route path="/contrataciones/detalle/:id" element={<ContratacionDetalle />} />
          
          {/* Reservas */}
          <Route path="/reservas" element={<Reservas />} />
          <Route path="/reservas/nuevo" element={<ReservaForm />} />
          <Route path="/reservas/editar/:id" element={<ReservaForm />} />
          <Route path="/reservas/detalle/:id" element={<ReservaDetalle />} />
          
          {/* Pagos */}
          <Route path="/pagos" element={<Pagos />} />
          <Route path="/pagos/nuevo" element={<PagoForm />} />
          <Route path="/pagos/editar/:id" element={<PagoForm />} />
          <Route path="/pagos/detalle/:id" element={<PagoDetalle />} />
          
          {/* Informes */}
          <Route path="/informes" element={<Informes />} />
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;
