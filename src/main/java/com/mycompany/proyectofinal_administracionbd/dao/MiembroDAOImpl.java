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
        String sql = "{? = call fn_registrar_miembro_completo(?,?,?,?,?,?,?,?,?,?,?)}";

        try (CallableStatement stmt = ConexionBD.getConexion().prepareCall(sql)) {

            stmt.registerOutParameter(1, Types.INTEGER);

            stmt.setString(2, m.getNombre());
            stmt.setString(3, m.getPrimerApellido());
            stmt.setString(4, m.getSegundoApellido());
            stmt.setString(5, m.getEmail());
            stmt.setString(6, m.getTelefono());
            stmt.setString(7, m.getCalle());
            stmt.setString(8, m.getNumeroExt());
            stmt.setString(9, m.getColonia());
            stmt.setString(10, m.getCiudad());
            stmt.setString(11, m.getEstado());
            stmt.setString(12, m.getCodigoPostal());

            stmt.execute();

            int idGenerado = stmt.getInt(1);

            if (idGenerado > 0) {
                ConexionBD.commit();
                return true;
                
            } else {
                ConexionBD.rollback();
                return false;
            }//else
            
        } catch (SQLException e) {
            ConexionBD.rollback();
            throw new Exception("Error al guardar: " + e.getMessage());
        }//cath
        
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
        String sql =  "SELECT m.id_miembro, m.nombre, m.primer_apellido, m.segundo_apellido, " +
                 "m.email, m.telefono, m.fecha_ingreso, m.cuota_pagada, m.ano_cuota, " +
                 "d.calle, d.numero_ext, d.numero_int, d.colonia, d.ciudad, d.estado, " +
                 "d.codigo_postal, d.pais " +
                 "FROM miembro m " +
                 "LEFT JOIN direccion d ON m.id_direccion = d.id_direccion " +
                 "WHERE m.id_miembro = ?";
        
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
        
        m.setCalle(rs.getString("calle"));
        m.setNumeroExt(rs.getString("numero_ext"));
        m.setNumeroInt(rs.getString("numero_int"));
        m.setColonia(rs.getString("colonia"));
        m.setCiudad(rs.getString("ciudad"));
        m.setEstado(rs.getString("estado"));
        m.setCodigoPostal(rs.getString("codigo_postal"));
        m.setPais(rs.getString("pais"));

        return m;
    }//mapearResultSet
      
}//MiembroDAOImpl
