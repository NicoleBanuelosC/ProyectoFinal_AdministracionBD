/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.dao;

import com.mycompany.proyectofinal_administracionbd.conexion.ConexionBD;
import com.mycompany.proyectofinal_administracionbd.modelo.Miembro;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author banue
 */
public class MiembroDAOImpl implements MiembroDAO{
    
    public boolean guardar(Miembro m) throws Exception {
        String sql = "{CALL sp_registrar_miembro_completo(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        
        try (CallableStatement stmt = ConexionBD.getConexion().prepareCall(sql)) {
            stmt.setString(1, m.getNombre());
            stmt.setString(2, m.getPrimerApellido());
            stmt.setString(3, m.getSegundoApellido());
            stmt.setString(4, m.getEmail());
            stmt.setString(5, m.getTelefono());
            stmt.setString(6, "Calle Principal");
            stmt.setString(7, "123");
            stmt.setString(8, "Centro");
            stmt.setString(9, "Ciudad");
            stmt.setString(10, "Estado");
            stmt.setString(11, "12345");
            
            stmt.registerOutParameter(12, Types.INTEGER);
            stmt.registerOutParameter(13, Types.VARCHAR);
            
            stmt.execute();
            
            int idGenerado = stmt.getInt(12);
            if (idGenerado > 0) {
                ConexionBD.commit(); 
                return true;
                
            } else {
                ConexionBD.rollback();
                throw new SQLException(stmt.getString(13));
            }//else
            
        } catch (SQLException e) {
            ConexionBD.rollback();
            throw new Exception("Error al guardar miembro: " + e.getMessage());
        }//catch
        
    }//guardar
    
    
    public List<Miembro> listarTodos() throws Exception {
        List<Miembro> miembros = new ArrayList<>();
        String sql = "SELECT * FROM vista_miembros_completos ORDER BY id_miembro DESC";
        
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                miembros.add(mapearResultSet(rs));
            }//While
            
        }//try
        
        return miembros;
        
    }//listarTodos
    
      public boolean actualizar(Miembro m) throws Exception {
        String sql = "UPDATE miembro SET nombre=?, primer_apellido=?, segundo_apellido=?, " +
                     "email=?, telefono=?, cuota_pagada=?, ano_cuota=? WHERE id_miembro=?";
        
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setString(1, m.getNombre());
            stmt.setString(2, m.getPrimerApellido());
            stmt.setString(3, m.getSegundoApellido());
            stmt.setString(4, m.getEmail());
            stmt.setString(5, m.getTelefono());
            stmt.setBoolean(6, m.isCuotaPagada());
            stmt.setInt(7, m.getAnoCuota());
            stmt.setInt(8, m.getIdMiembro());
            
            int filasAfectadas = stmt.executeUpdate();
            ConexionBD.commit(); 
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.rollback();
            throw new Exception("Error al actualizar: " + e.getMessage());
       
        }//catch
        
    }//Actualizar 
    
      public boolean eliminar(int idMiembro) throws Exception {
        String sql = "DELETE FROM miembro WHERE id_miembro=?";
        
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setInt(1, idMiembro);
            int filas = stmt.executeUpdate();
            ConexionBD.commit();
            return filas > 0;
        
        } catch (SQLException e) {
            ConexionBD.rollback();
            throw new Exception("Error al eliminar: " + e.getMessage());
        
        }//Catch
        
    }//eliminar

    public Miembro obtenerPorId(int idMiembro) throws Exception {
        String sql = "SELECT * FROM miembro WHERE id_miembro=?";
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setInt(1, idMiembro);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }//try
        
        }//try
        return null;
        
    }//ObtenerPorId    
      
    public List<Miembro> buscarPorNombre(String nombre) throws Exception {
        List<Miembro> resultado = new ArrayList<>();
        String sql = "SELECT * FROM miembro WHERE nombre ILIKE ? OR primer_apellido ILIKE ?";
        
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            String patron = "%" + nombre.trim() + "%";
            stmt.setString(1, patron);
            stmt.setString(2, patron);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) resultado.add(mapearResultSet(rs));
            }//try
            
        }//try
        
        return resultado;
        
    }//buscarPorNombre

        // Clean Code - mtodo privado para ue no se repita el código 
    private Miembro mapearResultSet(ResultSet rs) throws SQLException {
        Miembro m = new Miembro();
        m.setIdMiembro(rs.getInt("id_miembro"));
        m.setNombre(rs.getString("nombre"));
        m.setPrimerApellido(rs.getString("primer_apellido"));
        m.setSegundoApellido(rs.getString("segundo_apellido"));
        m.setEmail(rs.getString("email"));
        m.setTelefono(rs.getString("telefono"));
        m.setFechaIngreso(rs.getDate("fecha_ingreso"));
        m.setCuotaPagada(rs.getBoolean("cuota_pagada"));
        m.setAnoCuota(rs.getInt("ano_cuota"));
        return m;
    }//mapearResultSet
      
}//MiembroDAOImpl
