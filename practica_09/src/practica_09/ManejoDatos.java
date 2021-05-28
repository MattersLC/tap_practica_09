package practica_09;

import java.util.List;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * @author Orlando Lucero
 */
public class ManejoDatos {
    
    private Connection conexion; // acceso a conexion
    private Conexion crearConexion; // Crea conexion
    private final int CAMPOS_PERSONA=4;
    private final int CAMPOS_ACTIVIDAD = 3;
    private final int CAMPOS_MEDICIONES = 6;
    private DataBD data = new DataBD();
    
    public ManejoDatos() {
        PedirDatos();
        crearConexion = crearConexion.getConexion("jdbc:derby://localhost:1527/mediciones_personas", data.getUsername(), data.getPassword());
        conexion = crearConexion.getConnection();
    }
    
    private void PedirDatos() {
        String usr = JOptionPane.showInputDialog(null, "Usuario:");
        String pass = JOptionPane.showInputDialog(null, "Password:");
        data.setCredentials(usr, pass);
    }
    
    public boolean actualizaDatos(String sql) {
        // Inserta, actualiza o elimina
        boolean res = false;
        try {
            java.sql.Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            res = true;
            System.out.println("Se insertó/actualizó ");
        } catch (Exception e) {
            System.err.println("Error al Insertar/Actualizar");
        }
        
        return res;
    }
    
    // Consulta de PERSONA
    public List <Object []> conexionConsultaPersona(String sql) {
        // Regresa los registros de las personas en una lista
        List <Object []> datos = new ArrayList<Object[]>();
        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Statement ps = conexion.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            while(rs.next()) {
                String dat[] = new String[CAMPOS_PERSONA];
                // Estructura del registro persona pasado como cadena
                dat[0] = String.valueOf((Integer)rs.getInt(1));
                dat[1] = rs.getString(2);
                dat[2] = fecha.format((Date)rs.getDate(3));
                dat[3] = Character.toString(rs.getString(4).charAt(0));
                datos.add(dat);
            }
        } catch (Exception e) {
            System.err.println("Error al realizar la conexion 'consultar persona'." + e);
        }
        
        return datos;
    }
    
    // Consulta de TIPOACTIVIDAD
    public List <Object []> conexionConsultaActividad(String sql) {
        // Regresa los registros de tipo de actividad
        List <Object []> datos = new ArrayList<Object []>();
        try {
            Statement ps = conexion.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            while(rs.next()) {
                String dat[] = new String[CAMPOS_ACTIVIDAD];
                dat[0] = String.valueOf((Integer)rs.getInt(1));
                dat[1] = rs.getString(2);
                dat[2] = rs.getString(3);
                datos.add(dat);
            }
        } catch (Exception e) {
            System.err.println("Error al realizar la conexion 'consultar actividad'." + e);
        }
        
        return datos;
    }
    
    // Consulta MEDICIONES
    public List <Object []> conexionConsultaMedicion(String sql) {
        // Regresa los registros en la tabla MEDICIONES en forma de lista
        List <Object []> datos = new ArrayList<Object []>();
        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Statement ps = conexion.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            while(rs.next()) {
                String dat[] = new String[CAMPOS_MEDICIONES];
                dat[0] = fecha.format((Date)rs.getDate(2));        // fecha
                dat[1] = String.valueOf((Integer)rs.getInt(3));    // estatura
                dat[2] = String.valueOf((double)rs.getDouble(4));  // peso
                dat[3] = String.valueOf((Integer)rs.getInt(5));    // cintura
                dat[4] = String.valueOf((Integer)rs.getInt(6));    // cadera
                dat[5] = String.valueOf((Integer)rs.getInt(7));    // Tipo Act
                datos.add(dat);
            }
        } catch (Exception e) {
            System.err.println("Error al realizar la conexion 'consultar mediciones'." + e);
        }
        
        return datos;
    }

    /*private void setCredentials(String root, String admin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
