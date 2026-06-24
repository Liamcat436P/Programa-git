import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GestorUsuarios {

    // 1. MÉTODO PARA REGISTRAR (Original)
    public void registrarUsuario(String nombre, String correo, String telefono, String empresa, String tipo_servicio, String cedula, String fecha_nacimiento) {
        System.out.println("\n--- INICIANDO REGISTRO DE USUARIO ---");
        System.out.println("Intentando registrar a: " + nombre + " (Cédula: " + cedula + ")");
        
        Conexion conexionDB = new Conexion();
        Connection conexion = conexionDB.conectar();
        
        if (conexion == null) {
            System.out.println("ERROR CRÍTICO: No hay conexión a la base de datos.");
            return; // Detiene la ejecución si no hay conexión
        }

        String sql = "INSERT INTO usuarios (nombre, correo, telefono, empresa, tipo_servicio, cedula, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            System.out.println("Conexión exitosa. Preparando la orden SQL...");
            PreparedStatement orden = conexion.prepareStatement(sql);
            orden.setString(1, nombre);
            orden.setString(2, correo);
            orden.setString(3, telefono);
            orden.setString(4, empresa);
            orden.setString(5, tipo_servicio);
            orden.setString(6, cedula);
            orden.setString(7, fecha_nacimiento);
            
            System.out.println("Enviando datos a MySQL...");
            int filasAfectadas = orden.executeUpdate();
            
            System.out.println("¡OPERACIÓN EXITOSA! Filas guardadas: " + filasAfectadas);
            
            orden.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(">>> ERROR SQL AL GUARDAR <<<");
            System.out.println("Motivo exacto del rechazo: " + e.getMessage());
        }
        System.out.println("--- FIN DEL INTENTO DE REGISTRO ---\n");
    }

    // 2. MÉTODO PARA LISTAR (Original)
    public ArrayList<String[]> listarUsuarios() {
        ArrayList<String[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        Conexion conexionDB = new Conexion();
        try (Connection conexion = conexionDB.conectar();
             PreparedStatement orden = conexion.prepareStatement(sql);
             ResultSet resultados = orden.executeQuery()) {
            while (resultados.next()) {
                String[] usuario = new String[7];
                usuario[0] = resultados.getString("cedula");
                usuario[1] = resultados.getString("nombre");
                usuario[2] = resultados.getString("correo");
                usuario[3] = resultados.getString("telefono");
                usuario[4] = resultados.getString("empresa");
                usuario[5] = resultados.getString("tipo_servicio");
                usuario[6] = resultados.getString("fecha_nacimiento");
                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 3. NUEVO: MÉTODO PARA OBTENER UN SOLO USUARIO (Para la pantalla de edición)
    public String[] obtenerUsuarioPorCedula(String cedula) {
        String[] usuario = null;
        String sql = "SELECT * FROM usuarios WHERE cedula = ?";
        Conexion conexionDB = new Conexion();
        try (Connection conexion = conexionDB.conectar();
             PreparedStatement orden = conexion.prepareStatement(sql)) {
            orden.setString(1, cedula);
            ResultSet rs = orden.executeQuery();
            if (rs.next()) {
                usuario = new String[7];
                usuario[0] = rs.getString("cedula");
                usuario[1] = rs.getString("nombre");
                usuario[2] = rs.getString("correo");
                usuario[3] = rs.getString("telefono");
                usuario[4] = rs.getString("empresa");
                usuario[5] = rs.getString("tipo_servicio");
                usuario[6] = rs.getString("fecha_nacimiento");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    // 4. CORREGIDO: MÉTODO PARA ACTUALIZAR (Ahora acepta todos los campos)
    public void actualizarUsuario(String cedula, String nombre, String correo, String telefono, String empresa, String servicio, String nacimiento) {
        String sql = "UPDATE usuarios SET nombre=?, correo=?, telefono=?, empresa=?, tipo_servicio=?, fecha_nacimiento=? WHERE cedula=?";
        Conexion conexionDB = new Conexion();
        try (Connection conexion = conexionDB.conectar();
             PreparedStatement orden = conexion.prepareStatement(sql)) {
            orden.setString(1, nombre);
            orden.setString(2, correo);
            orden.setString(3, telefono);
            orden.setString(4, empresa);
            orden.setString(5, servicio);
            orden.setString(6, nacimiento);
            orden.setString(7, cedula);
            orden.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. MÉTODO ELIMINAR (Original)
    public void eliminarUsuario(String cedula) {
        String sql = "DELETE FROM usuarios WHERE cedula = ?";
        Conexion conexionDB = new Conexion();
        try (Connection conexion = conexionDB.conectar();
             PreparedStatement orden = conexion.prepareStatement(sql)) {
            orden.setString(1, cedula);
            orden.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}