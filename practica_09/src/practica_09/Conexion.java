package practica_09;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
/**
 * @author Orlando Lucero
 */
public class Conexion {
    
    private static Connection coneccion; // Contenida en el paquete sql
    private static Conexion conexion; // Instancia a utilizar
    private static int numConexiones=0; // Controla el número de veces que se accedió
    
    private Conexion(String url, String usuario, String password) {
        try {
            // Clase usada para una conexión con derby
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            // Para MySQL: "com.mysql.jdbc.Driver"
            // Para PostgreSQL: "org.postgresql.Driver"
            try {
                coneccion = (Connection) DriverManager.getConnection(url, usuario, password);
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    /*
    Incrementa el número de veces que se accedió a la BD
    si a la conexion no se le ha asignado la url, el usuario y la contraseña, este método se la asigna y devuelve la variable conexion
    caso contrario y a conexion ya se le asignaron los valores indicados, entonces sólo devuelve conexion, sin modificar nada.
    */
    public static Conexion getConexion(String url, String usuario, String password) {
        numConexiones++;
        if (conexion == null) {
            conexion = new Conexion(url, usuario, password);
        }
        
        return conexion;
    }
    
    // Método que regresa la conexión creada
    public static Connection getConnection() {
        return coneccion;
    }
    
    // Método para cerrar la conexión
    public boolean cerrarConexion() {
        try {
            if (coneccion!=null) {
                if (numConexiones==1) {
                    coneccion.close();
                    return true;
                } else {
                    numConexiones--;
                }
                return false;
            }
        } catch(SQLException e) {
            System.err.println("Error al tratar de cerrar la conexion"+e);
        }
    
        return false;
    }
}
