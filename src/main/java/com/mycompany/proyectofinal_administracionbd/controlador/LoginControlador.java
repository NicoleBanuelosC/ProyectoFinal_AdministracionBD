/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.controlador;

import com.mycompany.proyectofinal_administracionbd.conexion.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author banue
 */
public class LoginControlador {
    
    /**
     * se autentica usuario con PreparedStatement para evitar el SQL Injection
     * @param usuario - nomnbre usuario
     * @param password - contraseña 
     * @return - true si es válidp
     */
    public boolean autenticar(String usuario, String password) throws Exception {
        // importante el usar PreparedStatement en lugar de concatenar varios strings
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ? AND password = ? AND activo = true";
        
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            // SQL Injection (se usan paramentros en lugar de concatenación)
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }//if
            }//try
            
        } catch (SQLException e) {
            throw new Exception("Error al autenticar: " + e.getMessage());
        }//catch
        
        return false;
    }//autenticar
    
}//LoginControlador
