# Frontend React - Sistema de Seguridad y Entrenamiento

Frontend desarrollado con **React** y **Vite** para el Sistema de Seguridad y Entrenamiento.

---

## 🚀 Inicio Rápido

### Instalación

```bash
npm install
```

### Desarrollo

```bash
npm run dev
```

La aplicación estará disponible en `http://localhost:5173`

### Build para Producción

```bash
npm run build
```

---

## 📁 Estructura del Proyecto

```
src/
├── components/          # Componentes reutilizables
│   ├── Layout.jsx      # Layout principal (Header, Sidebar, Footer)
│   └── Layout.css      # Estilos del layout
│
├── pages/              # Páginas principales
│   ├── Dashboard.jsx   # Dashboard con estadísticas
│   ├── Clientes.jsx    # Listado de clientes
│   ├── ClienteForm.jsx # Formulario crear/editar cliente
│   ├── ClienteDetalle.jsx # Detalle de cliente
│   ├── Servicios.jsx   # Listado de servicios
│   ├── ServicioForm.jsx # Formulario crear/editar servicio
│   ├── ServicioDetalle.jsx # Detalle de servicio
│   ├── Programas.jsx   # Listado de programas
│   ├── ProgramaForm.jsx # Formulario crear/editar programa
│   ├── ProgramaDetalle.jsx # Detalle de programa
│   ├── Contrataciones.jsx # Listado de contrataciones
│   ├── ContratacionForm.jsx # Formulario crear/editar contratación
│   ├── ContratacionDetalle.jsx # Detalle de contratación
│   ├── Reservas.jsx    # Listado de reservas
│   ├── ReservaForm.jsx # Formulario crear/editar reserva
│   ├── ReservaDetalle.jsx # Detalle de reserva
│   ├── Pagos.jsx       # Listado de pagos
│   ├── PagoForm.jsx    # Formulario crear/editar pago
│   ├── PagoDetalle.jsx # Detalle de pago
│   └── Informes.jsx    # Informes y estadísticas
│
├── services/           # Servicios API
│   ├── api.js          # Configuración de Axios
│   ├── clienteService.js
│   ├── servicioService.js
│   ├── programaService.js
│   ├── contratacionService.js
│   ├── reservaService.js
│   ├── pagoService.js
│   ├── dashboardService.js
│   └── informeService.js
│
├── App.jsx             # Componente principal con rutas
├── main.jsx            # Punto de entrada
└── index.css           # Estilos globales
```

---

## 🔧 Configuración

### Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto:

```env
VITE_API_URL=http://localhost:8080/api
```

---

## 🎨 Características

- ✅ **React Router**: Navegación entre páginas
- ✅ **Axios**: Cliente HTTP para comunicación con API
- ✅ **Diseño Responsive**: Compatible con móviles y tablets
- ✅ **Búsqueda y Filtros**: En todas las páginas de listado
- ✅ **Formularios Completos**: Crear y editar todas las entidades
- ✅ **Páginas de Detalle**: Información completa de cada entidad
- ✅ **Manejo de Errores**: Mensajes claros al usuario
- ✅ **Estados de Carga**: Indicadores mientras se cargan datos

---

## 📱 Páginas Disponibles

### Públicas
- `/` - Dashboard

### Gestión
- `/clientes` - Listado de clientes
- `/clientes/nuevo` - Crear cliente
- `/clientes/editar/:id` - Editar cliente
- `/clientes/detalle/:id` - Ver detalle de cliente

- `/servicios` - Listado de servicios
- `/servicios/nuevo` - Crear servicio
- `/servicios/editar/:id` - Editar servicio
- `/servicios/detalle/:id` - Ver detalle de servicio

- `/programas` - Listado de programas
- `/programas/nuevo` - Crear programa
- `/programas/editar/:id` - Editar programa
- `/programas/detalle/:id` - Ver detalle de programa

- `/contrataciones` - Listado de contrataciones
- `/contrataciones/nuevo` - Crear contratación
- `/contrataciones/editar/:id` - Editar contratación
- `/contrataciones/detalle/:id` - Ver detalle de contratación

- `/reservas` - Listado de reservas
- `/reservas/nuevo` - Crear reserva
- `/reservas/editar/:id` - Editar reserva
- `/reservas/detalle/:id` - Ver detalle de reserva

- `/pagos` - Listado de pagos
- `/pagos/nuevo` - Crear pago
- `/pagos/editar/:id` - Editar pago
- `/pagos/detalle/:id` - Ver detalle de pago

- `/informes` - Informes y estadísticas

---

## 🔌 Conexión con Backend

El frontend se conecta al backend mediante la API REST configurada en `src/services/api.js`.

**Base URL:** `http://localhost:8080/api`

---

## 🎯 Funcionalidades Principales

### Búsqueda y Filtros
Todas las páginas de listado incluyen:
- Búsqueda por texto
- Filtros específicos por campo
- Limpiar filtros

### Formularios
- Validación de campos requeridos
- Manejo de errores
- Confirmación antes de eliminar
- Mensajes de éxito/error

### Envío Automático de Correos
- Los correos se envían automáticamente desde el backend
- Se activa al crear/editar contrataciones o reservas con estado "Confirmada" o "Activa"
- No requiere acción adicional en el frontend

---

## 🛠️ Tecnologías

- **React 19**: Biblioteca de JavaScript
- **React Router DOM 6**: Enrutamiento
- **Axios**: Cliente HTTP
- **Vite**: Build tool
- **Font Awesome**: Iconos

---

## 📝 Scripts Disponibles

- `npm run dev` - Inicia servidor de desarrollo
- `npm run build` - Construye para producción
- `npm run preview` - Previsualiza build de producción
- `npm run lint` - Ejecuta el linter

---

## 🔗 Integración con Backend

El frontend consume la API REST del backend Spring Boot. Todas las comunicaciones se realizan mediante:

- **GET**: Obtener datos
- **POST**: Crear nuevos registros
- **PUT**: Actualizar registros existentes
- **DELETE**: Eliminar registros

---

## 📦 Dependencias Principales

```json
{
  "react": "^19.2.0",
  "react-dom": "^19.2.0",
  "react-router-dom": "^6.28.0",
  "axios": "^1.7.9",
  "react-icons": "^5.4.0"
}
```

---

## 🚀 Despliegue

### Vercel
1. Conecta tu repositorio a Vercel
2. Configura `VITE_API_URL` en las variables de entorno
3. Despliega

### Netlify
1. Conecta tu repositorio a Netlify
2. Build command: `npm run build`
3. Publish directory: `dist`
4. Configura variables de entorno

---

## 📄 Notas

- El backend debe estar ejecutándose para que el frontend funcione correctamente
- La URL del backend se configura en `src/services/api.js` o mediante variable de entorno `VITE_API_URL`
- Todas las funcionalidades del backend están disponibles mediante la API REST
