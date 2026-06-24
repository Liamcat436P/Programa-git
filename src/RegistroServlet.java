import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registrar") // Esta es la ruta que pondrás en tu HTML
public class RegistroServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Recibir los datos del formulario
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String empresa = request.getParameter("empresa");
        String servicio = request.getParameter("tipo_servicio");
        String cedula = request.getParameter("cedula");
        String fecha = request.getParameter("fecha_nacimiento");

        // 2. Llamar a tu lógica que ya creaste en GestorUsuarios
        GestorUsuarios gestor = new GestorUsuarios();
        gestor.registrarUsuario(nombre, correo, telefono, empresa, servicio, cedula, fecha);

        // 3. Responder al usuario
        response.getWriter().println("<h1>¡Registro exitoso para " + nombre + "!</h1>");
    }
}