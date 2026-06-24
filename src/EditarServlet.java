import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editar")
public class EditarServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Obtener la cédula de la URL
        String cedula = request.getParameter("cedula");
        
        // 2. Buscar al usuario en la base de datos
        GestorUsuarios gestor = new GestorUsuarios();
        String[] usuario = gestor.obtenerUsuarioPorCedula(cedula); 
        
        // 3. Enviar los datos al JSP
        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("editar_usuario.jsp").forward(request, response);
    }
}