@echo off
:: Definir rutas relativas (ajustadas para que funcionen dentro de la carpeta 'src')
set LIB=..\WEB-INF\lib\servlet-api.jar
set DESTINO=..\WEB-INF\classes

echo Iniciando compilacion...

:: 1. Compilar y enviar directamente a WEB-INF\classes
javac -cp "%LIB%" *.java -d "%DESTINO%"

echo.
echo ========================================================
echo ¡Exito! Los archivos .class se han generado en:
echo %DESTINO%
echo ========================================================
echo.
pause