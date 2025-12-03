import { Link, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import '../index.css';

const Home = () => {
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    // Verificar si hay usuario logueado
    const user = localStorage.getItem('user');
    setIsLoggedIn(!!user);
  }, []);

  return (
    <div className="home-container">
      {/* Hero Section */}
      <header className="hero-section">
        <div className="hero-content">
          <div className="hero-logo">
            <img src="/logo.svg" alt="Logo" />
          </div>
          <h1 className="hero-title">Sistema de Seguridad y Entrenamiento</h1>
          <p className="hero-subtitle">
            Protección integral y capacitación profesional para tu empresa
          </p>
          <div className="hero-buttons">
            {isLoggedIn ? (
              <>
                <Link to="/dashboard" className="btn btn-hero btn-primary">
                  <i className="fas fa-tachometer-alt"></i> Ir al Dashboard
                </Link>
                <Link to="/login" className="btn btn-hero btn-secondary">
                  <i className="fas fa-sign-in-alt"></i> Cambiar Usuario
                </Link>
              </>
            ) : (
              <>
                <Link to="/login" className="btn btn-hero btn-primary">
                  <i className="fas fa-sign-in-alt"></i> Iniciar Sesión
                </Link>
                <Link to="/register" className="btn btn-hero btn-secondary">
                  <i className="fas fa-user-plus"></i> Registrarse
                </Link>
              </>
            )}
          </div>
        </div>
      </header>

      {/* About Section */}
      <section className="about-section">
        <div className="container">
          <h2 className="section-title">Sobre Nosotros</h2>
          <p className="section-text">
            Somos una empresa líder especializada en servicios de seguridad privada y programas 
            de entrenamiento profesional. Nuestra experiencia y compromiso nos han convertido en 
            la opción preferida de empresas que buscan protección de calidad y desarrollo de talento.
          </p>
        </div>
      </section>

      {/* Mission & Vision Section */}
      <section className="mission-vision-section">
        <div className="container">
          <div className="mission-vision-grid">
            {/* Misión */}
            <div className="mv-card">
              <div className="mv-icon">
                <i className="fas fa-bullseye"></i>
              </div>
              <h3>Misión</h3>
              <p>
                Proporcionar servicios de seguridad de excelencia y programas de entrenamiento 
                profesional que garanticen la protección integral de nuestros clientes y el 
                desarrollo de competencias técnicas en el sector. Nos comprometemos a mantener 
                los más altos estándares de calidad, innovación y responsabilidad social.
              </p>
            </div>

            {/* Visión */}
            <div className="mv-card">
              <div className="mv-icon">
                <i className="fas fa-eye"></i>
              </div>
              <h3>Visión</h3>
              <p>
                Ser la empresa referente a nivel nacional en servicios de seguridad y 
                entrenamiento profesional, reconocida por nuestra innovación tecnológica, 
                excelencia operativa y compromiso con la formación continua. Aspiramos a 
                establecer nuevos estándares en la industria y contribuir al crecimiento 
                sostenible de nuestros clientes.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Values Section */}
      <section className="values-section">
        <div className="container">
          <h2 className="section-title">Nuestros Valores</h2>
          <div className="values-grid">
            <div className="value-card">
              <i className="fas fa-shield-alt"></i>
              <h4>Seguridad</h4>
              <p>Protección confiable y constante</p>
            </div>
            <div className="value-card">
              <i className="fas fa-certificate"></i>
              <h4>Excelencia</h4>
              <p>Calidad en cada servicio</p>
            </div>
            <div className="value-card">
              <i className="fas fa-users"></i>
              <h4>Compromiso</h4>
              <p>Dedición total al cliente</p>
            </div>
            <div className="value-card">
              <i className="fas fa-lightbulb"></i>
              <h4>Innovación</h4>
              <p>Tecnología de vanguardia</p>
            </div>
            <div className="value-card">
              <i className="fas fa-handshake"></i>
              <h4>Integridad</h4>
              <p>Transparencia y honestidad</p>
            </div>
            <div className="value-card">
              <i className="fas fa-graduation-cap"></i>
              <h4>Formación</h4>
              <p>Desarrollo continuo</p>
            </div>
          </div>
        </div>
      </section>

      {/* Services Preview Section */}
      <section className="services-section">
        <div className="container">
          <h2 className="section-title">Nuestros Servicios</h2>
          <div className="services-grid">
            <div className="service-card">
              <i className="fas fa-shield-alt"></i>
              <h3>Servicios de Seguridad</h3>
              <p>Protección personalizada para tu empresa con personal altamente capacitado</p>
            </div>
            <div className="service-card">
              <i className="fas fa-graduation-cap"></i>
              <h3>Programas de Entrenamiento</h3>
              <p>Capacitación profesional especializada en seguridad y desarrollo de habilidades</p>
            </div>
            <div className="service-card">
              <i className="fas fa-file-contract"></i>
              <h3>Contrataciones</h3>
              <p>Soluciones flexibles adaptadas a tus necesidades específicas</p>
            </div>
            <div className="service-card">
              <i className="fas fa-chart-bar"></i>
              <h3>Gestión Integral</h3>
              <p>Sistema completo para administrar todos tus recursos y operaciones</p>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="cta-section">
        <div className="container">
          <h2>¿Listo para comenzar?</h2>
          <p>Únete a nuestro sistema y experimenta la mejor gestión de seguridad y entrenamiento</p>
          <div className="cta-buttons">
            <Link to="/register" className="btn btn-cta btn-primary">
              <i className="fas fa-rocket"></i> Crear Cuenta
            </Link>
            <Link to="/login" className="btn btn-cta btn-secondary">
              <i className="fas fa-sign-in-alt"></i> Iniciar Sesión
            </Link>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="home-footer">
        <div className="container">
          <p>&copy; 2024 Sistema de Seguridad y Entrenamiento. Todos los derechos reservados.</p>
        </div>
      </footer>
    </div>
  );
};

export default Home;

