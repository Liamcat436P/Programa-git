import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GestorUsuarios {

    // --- NUEVO: MÉTODO PRIVADO PARA ENCRIPTAR CONTRASENAS (SHA-256) ---
    private String encriptarSHA256(String textoOriginal) {
        try {
            java.security.MessageDigest motor = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = motor.digest(textoOriginal.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder resultadoHex = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    resultadoHex.append('0');
                }
                resultadoHex.append(hex);
            }
            return resultadoHex.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Error crítico: No se encontró el algoritmo de encriptación.");
            e.printStackTrace();
            return textoOriginal;
        }
    }

    // 1. MÉTODO PARA REGISTRAR (Actualizado con contraseña encriptada)
    public void registrarUsuario(String nombre, String correo, String telefono, String empresa, String tipo_servicio, String cedula, String fecha_nacimiento, String contrasena) {
        
        // Encriptamos la contraseña entrante antes de procesarla en la base de datos
        String contrasenaSegura = encriptarSHA256(contrasena);
        
        System.out.println("\n--- INICIANDO REGISTRO DE USUARIO ---");
        System.out.println("Intentando registrar a: " + nombre + " (Cédula: " + cedula + ")");
        
        Conexion conexionDB = new Conexion();
        Connection conexion = conexionDB.conectar();
        
        if (conexion == null) {
            System.out.println("ERROR CRÍTICO: No hay conexión a la base de datos.");
            return;
        }

        String sql = "INSERT INTO usuarios (nombre, correo, telefono, empresa, tipo_servicio, cedula, fecha_nacimiento, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
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
            orden.setString(8, contrasenaSegura); // Guardamos el hash seguro de 64 caracteres
            
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

    // 2. MÉTODO PARA LISTAR (Actualizado a 8 columnas)
    public ArrayList<String[]> listarUsuarios() {
        ArrayList<String[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        Conexion conexionDB = new Conexion();
        try (Connection conexion = conexionDB.conectar();
             PreparedStatement orden = conexion.prepareStatement(sql);
             ResultSet resultados = orden.executeQuery()) {
            while (resultados.next()) {
                String[] usuario = new String[8]; 
                usuario[0] = resultados.getString("cedula");
                usuario[1] = resultados.getString("nombre");
                usuario[2] = resultados.getString("correo");
                usuario[3] = resultados.getString("telefono");
                usuario[4] = resultados.getString("empresa");
                usuario[5] = resultados.getString("tipo_servicio");
                usuario[6] = resultados.getString("fecha_nacimiento");
                usuario[7] = resultados.getString("contrasena"); 
                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 3. MÉTODO PARA OBTENER UN SOLO USUARIO (Actualizado a 8 columnas)
    public String[] obtenerUsuarioPorCedula(String cedula) {
        String[] usuario = null;
        String sql = "SELECT * FROM usuarios WHERE cedula = ?";
        Conexion conexionDB = new Conexion();
        try (Connection conexion = conexionDB.conectar();
             PreparedStatement orden = conexion.prepareStatement(sql)) {
            orden.setString(1, cedula);
            ResultSet rs = orden.executeQuery();
            if (rs.next()) {
                usuario = new String[8]; 
                usuario[0] = rs.getString("cedula");
                usuario[1] = rs.getString("nombre");
                usuario[2] = rs.getString("correo");
                usuario[3] = rs.getString("telefono");
                usuario[4] = rs.getString("empresa");
                usuario[5] = rs.getString("tipo_servicio");
                usuario[6] = rs.getString("fecha_nacimiento");
                usuario[7] = rs.getString("contrasena"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    // 4. MÉTODO PARA ACTUALIZAR (Actualizado con contraseña encriptada)
    public void actualizarUsuario(String cedula, String nombre, String correo, String telefono, String empresa, String servicio, String nacimiento, String contrasena) {
        
        // También encriptamos si la contraseña se actualiza
        String contrasenaSegura = encriptarSHA256(contrasena);
        
        String sql = "UPDATE usuarios SET nombre=?, correo=?, telefono=?, empresa=?, tipo_servicio=?, fecha_nacimiento=?, contrasena=? WHERE cedula=?";
        Conexion conexionDB = new Conexion();
        try (Connection conexion = conexionDB.conectar();
             PreparedStatement orden = conexion.prepareStatement(sql)) {
            orden.setString(1, nombre);
            orden.setString(2, correo);
            orden.setString(3, telefono);
            orden.setString(4, empresa);
            orden.setString(5, servicio);
            orden.setString(6, nacimiento);
            orden.setString(7, contrasenaSegura); // Mandamos el hash de actualización
            orden.setString(8, cedula);     
            orden.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. MÉTODO ELIMINAR (Sin cambios, funciona mediante la cédula)
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

    // --- NUEVO: 6. MÉTODO PARA VALIDAR EL INICIO DE SESIÓN ---
    public String validarLogin(String correo, String contrasenaPlana) {
        String sql = "SELECT nombre FROM usuarios WHERE correo = ? AND contrasena = ?";
        Conexion conexionDB = new Conexion();
        
        // Reutilizamos tu método privado para encriptar lo que el usuario escribió en el login
        String contrasenaEncriptada = encriptarSHA256(contrasenaPlana);
        
        try (Connection conexion = conexionDB.conectar();
             PreparedStatement orden = conexion.prepareStatement(sql)) {
            
            orden.setString(1, correo);
            orden.setString(2, contrasenaEncriptada);
            
            ResultSet rs = orden.executeQuery();
            
            if (rs.next()) {
                return rs.getString("nombre"); // Si coincide, devuelve el nombre del usuario logueado
            }
            
        } catch (SQLException e) {
            System.out.println("Error en la consulta de inicio de sesión: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null; // Si no hay coincidencias, retorna null
    }
}