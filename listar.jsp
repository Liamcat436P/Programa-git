<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Consulta de Usuarios</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f9f9f9; }
        h2 { color: #333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background-color: #fff; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #0056b3; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        tr:hover { background-color: #e9e9e9; }
        .btn-agregar { background-color: #28a745; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; display: inline-block; font-weight: bold; }
        .btn-editar { color: #007bff; text-decoration: none; font-weight: bold; margin-right: 10px; }
        .btn-eliminar { color: #dc3545; text-decoration: none; font-weight: bold; }
        .enlace-volver { display: inline-block; margin-top: 20px; color: #555; text-decoration: none; }
    </style>
</head>
<body>

    <h2>Lista de Usuarios del Sistema</h2>
    
    <a href="registro.html" class="btn-agregar">+ Agregar Nuevo Usuario</a>

    <table>
        <tr>
            <th>Cédula</th>
            <th>Nombre</th>
            <th>Correo</th>
            <th>Teléfono</th>
            <th>Empresa</th>
            <th>Servicio</th>
            <th>Nacimiento</th>
            <th>Contraseña</th>
            <th>Acciones</th>
        </tr>
        <%
            ArrayList<String[]> lista = (ArrayList<String[]>) request.getAttribute("listaUsuarios");
            
            if (lista != null && !lista.isEmpty()) {
                for (String[] usuario : lista) {
        %>
        <tr>
            <td><%= usuario[0] %></td>
            <td><%= usuario[1] %></td>
            <td><%= usuario[2] %></td>
            <td><%= usuario[3] %></td>
            <td><%= usuario[4] %></td>
            <td><%= usuario[5] %></td>
            <td><%= usuario[6] %></td>
            <td><%= usuario[7] %></td>
            <td>
                <a href="editar?cedula=<%= usuario[0] %>" class="btn-editar">Editar</a>
                <a href="eliminar?cedula=<%= usuario[0] %>" class="btn-eliminar" onclick="return confirm('¿Seguro?')">Eliminar</a>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="8" style="text-align:center;">No hay usuarios registrados.</td>
        </tr>
        <%
            }
        %>
    </table>
    <a href="index.html" class="enlace-volver">← Volver al inicio</a>
</body>
</html>