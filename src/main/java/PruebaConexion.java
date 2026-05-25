/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author banue
 */

import com.mycompany.proyectofinal_administracionbd.conexion.ConexionBD;
import java.sql.Connection;

public class PruebaConexion {
    public static void main(String[] args) {
        try {
            System.out.println("Conectando a PostgreSQL...");
            Connection conn = ConexionBD.getConexion();
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("CONEXIÓN EXITOSA");
                System.out.println("URL" + conn.getMetaData().getURL());
                System.out.println("AutoCommit: " + conn.getAutoCommit() + " (debe ser false)");
                ConexionBD.cerrar();
            }//if
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }//catch
        
    }//void main
    
}//pruebaConexion