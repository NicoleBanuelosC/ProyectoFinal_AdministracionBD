/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author banue
 */
public class ConexionBD {
    
    // configuración de conexión (usuario de segurirdad el que NO es root)
    private static final String URL = "jdbc:postgresql://localhost:5432/teatro_db";
    private static final String USER = "usuario_teatro";
    private static final String PASS = "teatro2026";
    
    // instancia única de conexión (Singleton)
    private static Connection conexion;
    
    // constructor privado para que se evite que se instancie desde fuera (patrón Singleton)
    private ConexionBD() {}
    
     /**
     * se obtiene la conexión única a la bd
     * si no existe, la crea y se deshabilitan autoccmmit por transacciones
     */
    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // cargar driver de PostgreSQL
                Class.forName("org.postgresql.Driver");
                
                // establecer conexion con el usuario especifico
                conexion = DriverManager.getConnection(URL, USER, PASS);
                
                // deshabilitar autocommit para usar transacciones
                conexion.setAutoCommit(false);
                
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver PostgreSQL no encontrado", e);
            }//Catch
            
        }//if
        
        return conexion;
    
    }//getConexion
    
    
    /**
     * confirma todas las operaciones de la transacción actual
     * @throws SQLException - si llegase a fallar el commit
     */
    public static void commit() throws SQLException {
        if (conexion != null) {
            conexion.commit();
        }//if
        
    }//commit
       
    //cancela todas las operaciones de la transaccion en caso de error (en la actual)
    public static void rollback() {
        try {
            if (conexion != null) {
                conexion.rollback();
            }//if
            
        } catch (SQLException e) {
            System.err.println("Error en rollback: " + e.getMessage());
        }//catch
        
    }//rollback
    
    //cierra la conexion
    public static void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
            }//if
            
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
            
        }//catch
        
    }//Cerrar    
    
}//ConexionBD
