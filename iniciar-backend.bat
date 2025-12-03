@echo off
echo ========================================
echo    INICIANDO BACKEND (SPRING BOOT)
echo ========================================
echo.
cd /d "%~dp0"
echo Cambiando a directorio: %CD%
echo.
echo Iniciando backend en http://localhost:8080
echo Espera mientras se inicia el servidor...
echo.
echo NOTA: NO CIERRES ESTA VENTANA mientras uses la aplicacion
echo.
mvn spring-boot:run
pause

