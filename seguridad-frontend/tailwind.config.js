/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  // Evitar conflictos con Bootstrap
  corePlugins: {
    preflight: false, // Desactivar reset de Tailwind para no interferir con Bootstrap
  },
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#1e3c72',
          light: '#2a5298',
        },
        secondary: {
          DEFAULT: '#f39c12',
          dark: '#e67e22',
        },
      },
    },
  },
  plugins: [],
}

