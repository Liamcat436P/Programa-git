import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    // 1. Definimos las credenciales de tu base de datos XAMPP
   private static final String URL = "jdbc:mysql://localhost:3307/trueline_db";
private static final String USER = "root";
private static final String PASSWORD = "1234";;

    // 2. Método para establecer la conexión
    public Connection conectar() {
        Connection conexion = null;
        try {
            // Cargar el driver (el archivo .jar)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Intentar conectarse
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa a la base de datos trueline_db!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver. ¿Agregaste el .jar a Referenced Libraries?");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: Problema de credenciales o XAMPP está apagado.");
            e.printStackTrace();
        }
        return conexion;
    }

    // 3. Método principal solo para probar si funciona
    public static void main(String[] args) {
        Conexion prueba = new Conexion();
        prueba.conectar();
    }
}