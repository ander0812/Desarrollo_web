const Dashboard = () => {
  return (
    <div style={{ padding: '20px' }}>
      <h1>Dashboard</h1>
      <p>Bienvenido al Sistema de Seguridad y Entrenamiento</p>
      
      <div style={{ marginTop: '30px' }}>
        <a 
          href="/clientes" 
          style={{
            display: 'inline-block',
            padding: '10px 20px',
            backgroundColor: '#1e3c72',
            color: 'white',
            textDecoration: 'none',
            borderRadius: '5px',
            marginRight: '10px'
          }}
        >
          Ver Clientes
        </a>
      </div>
    </div>
  );
};

export default Dashboard;




