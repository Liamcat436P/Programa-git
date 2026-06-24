import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/eliminar")
public class EliminarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Obtener la cédula de la URL (ej. ?cedula=21478)
        String cedula = request.getParameter("cedula");

        // 2. Llamar al gestor para que elimine el registro de la base de datos
        if (cedula != null && !cedula.isEmpty()) {
            GestorUsuarios gestor = new GestorUsuarios();
            gestor.eliminarUsuario(cedula);
        }

        // 3. Redirigir de vuelta a la tabla para ver la lista actualizada
        response.sendRedirect("listar.jsp");
    }
}