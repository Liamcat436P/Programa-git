import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/consultar")
public class ConsultarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GestorUsuarios gestor = new GestorUsuarios();
        ArrayList<String[]> lista = gestor.listarUsuarios();
        
        // Guardamos la lista en la solicitud para que el JSP pueda leerla
        request.setAttribute("listaUsuarios", lista);
        
        // Redirigimos el flujo hacia la página JSP de visualización
        request.getRequestDispatcher("listar.jsp").forward(request, response);
    }
}