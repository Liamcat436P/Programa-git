import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 10L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena"); // Contraseña en texto plano desde el formulario

        GestorUsuarios gestor = new GestorUsuarios();
        String nombreUsuario = gestor.validarLogin(correo, contrasena);

        if (nombreUsuario != null) {
            // LOGIN CORRECTO: Se crea la sesión HTTP (el estado de logueado)
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuarioLogueado", nombreUsuario);
            sesion.setAttribute("correoUsuario", correo);

            // Redirige al panel principal o lista de usuarios
            response.sendRedirect("consultar");
        } else {
            // LOGIN INCORRECTO: Regresa al login y envía un parámetro de error
            response.sendRedirect("login.jsp?error=true");
        }
    }
}