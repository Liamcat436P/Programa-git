<%-- Recuerda: el objeto "usuario" viene del EditarServlet --%>
<% String[] u = (String[]) request.getAttribute("usuario"); %>

<form action="ActualizarServlet" method="POST">
    <input type="hidden" name="cedula" value="<%= u[0] %>">
    
    <label>Cédula:</label>
    <input type="text" value="<%= u[0] %>" disabled>
    
    <label>Nombre:</label>
    <input type="text" name="nombre" value="<%= u[1] %>">
    
    <label>Correo:</label>
    <input type="email" name="correo" value="<%= u[2] %>">
    
    <label>Teléfono:</label>
    <input type="text" name="telefono" value="<%= u[3] %>">
    
    <label>Empresa:</label>
    <input type="text" name="empresa" value="<%= u[4] %>">
    
    <label>Servicio:</label>
    <input type="text" name="servicio" value="<%= u[5] %>">
    
    <label>Fecha Nacimiento:</label>
    <input type="date" name="nacimiento" value="<%= u[6] %>">
    
    <button type="submit">Guardar Cambios</button>
</form>