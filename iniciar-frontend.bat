@echo off
echo ========================================
echo    INICIANDO FRONTEND (REACT)
echo ========================================
echo.
cd /d "%~dp0\seguridad-frontend"
echo Cambiando a directorio: %CD%
echo.
echo Verificando dependencias...
if not exist "node_modules" (
    echo Instalando dependencias por primera vez...
    echo Esto puede tardar unos minutos...
    call npm install
)
echo.
echo Iniciando frontend en http://localhost:5173
echo El navegador se abrira automaticamente
echo.
echo NOTA: NO CIERRES ESTA VENTANA mientras uses la aplicacion
echo.
call npm run dev
pause

