import { useState, useEffect } from 'react';
import { clienteService } from '../services/clienteService';

const Clientes = () => {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    cargarClientes();
  }, []);

  const cargarClientes = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await clienteService.getAll();
      
      if (response.data.success) {
        setClientes(response.data.data);
      } else {
        setError(response.data.message || 'Error al cargar clientes');
      }
    } catch (err) {
      console.error('Error:', err);
      setError('Error al conectar con el servidor');
    } finally {
      setLoading(false);
    }
  };

  const handleEliminar = async (id) => {
    if (!window.confirm('¿Estás seguro de eliminar este cliente?')) {
      return;
    }

    try {
      const response = await clienteService.delete(id);
      if (response.data.success) {
        alert('Cliente eliminado exitosamente');
        cargarClientes();
      }
    } catch (err) {
      console.error('Error:', err);
      alert('Error al eliminar cliente');
    }
  };

  if (loading) {
    return (
      <div style={{ padding: '20px', textAlign: 'center' }}>
        <p>Cargando clientes...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div style={{ padding: '20px', color: 'red' }}>
        <p>Error: {error}</p>
        <button onClick={cargarClientes}>Reintentar</button>
      </div>
    );
  }

  return (
    <div style={{ padding: '20px' }}>
      <h1>Lista de Clientes</h1>
      
      {clientes.length === 0 ? (
        <p>No hay clientes registrados</p>
      ) : (
        <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
          <thead>
            <tr style={{ backgroundColor: '#f0f0f0' }}>
              <th style={{ padding: '10px', border: '1px solid #ddd', textAlign: 'left' }}>ID</th>
              <th style={{ padding: '10px', border: '1px solid #ddd', textAlign: 'left' }}>Nombre</th>
              <th style={{ padding: '10px', border: '1px solid #ddd', textAlign: 'left' }}>Email</th>
              <th style={{ padding: '10px', border: '1px solid #ddd', textAlign: 'left' }}>Teléfono</th>
              <th style={{ padding: '10px', border: '1px solid #ddd', textAlign: 'left' }}>Ciudad</th>
              <th style={{ padding: '10px', border: '1px solid #ddd', textAlign: 'left' }}>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {clientes.map((cliente) => (
              <tr key={cliente.id}>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{cliente.id}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{cliente.nombre}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{cliente.email}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{cliente.telefono}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>{cliente.ciudad}</td>
                <td style={{ padding: '10px', border: '1px solid #ddd' }}>
                  <button 
                    onClick={() => handleEliminar(cliente.id)}
                    style={{ 
                      padding: '5px 10px', 
                      backgroundColor: '#dc3545', 
                      color: 'white', 
                      border: 'none', 
                      borderRadius: '4px',
                      cursor: 'pointer'
                    }}
                  >
                    Eliminar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default Clientes;



