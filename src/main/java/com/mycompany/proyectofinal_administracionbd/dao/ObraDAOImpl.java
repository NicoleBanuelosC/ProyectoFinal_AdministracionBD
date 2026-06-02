/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.dao;

import com.mycompany.proyectofinal_administracionbd.conexion.ConexionBD;
import com.mycompany.proyectofinal_administracionbd.modelo.Obra;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author banue
 */
public class ObraDAOImpl implements ObraDAO{
    
    public boolean guardar(Obra obra) throws Exception {
        String sql = "INSERT INTO obra (titulo, autor, tipo, numero_actos) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setString(1, obra.getTitulo());
            stmt.setString(2, obra.getAutor());
            stmt.setString(3, obra.getTipo());
            stmt.setInt(4, obra.getNumeroActos());
            int filas = stmt.executeUpdate();
            ConexionBD.commit();
            return filas > 0;
            
        } catch (Exception e) {
            ConexionBD.rollback();
            throw new Exception("Error al guardar obra: " + e.getMessage());
            
        }//Catch
        
    }//guardar

    public List<Obra> listarTodos() throws Exception {
        List<Obra> obras = new ArrayList<>();
        String sql = "SELECT * FROM obra ORDER BY titulo ASC";
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) obras.add(mapearResultSet(rs));
        }//try
        
        return obras;
        
    }//listarTodos

    public boolean actualizar(Obra obra) throws Exception {
        String sql = "UPDATE obra SET titulo=?, autor=?, tipo=?, numero_actos=? WHERE id_obra=?";
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setString(1, obra.getTitulo());
            stmt.setString(2, obra.getAutor());
            stmt.setString(3, obra.getTipo());
            stmt.setInt(4, obra.getNumeroActos());
            stmt.setInt(5, obra.getIdObra());
            int filas = stmt.executeUpdate();
            ConexionBD.commit();
            return filas > 0;
            
        } catch (Exception e) {
            ConexionBD.rollback();
            throw new Exception("Error al actualizar obra: " + e.getMessage());
            
        }//Catch
        
    }//actualizar

    public boolean eliminar(int idObra) throws Exception {
        String sql = "DELETE FROM obra WHERE id_obra=?";
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setInt(1, idObra);
            int filas = stmt.executeUpdate();
            ConexionBD.commit();
            return filas > 0;
            
        } catch (Exception e) {
            ConexionBD.rollback();
            throw new Exception("Error al eliminar obra: " + e.getMessage());
        }//Catch
        
    }//Eliminar

    public Obra obtenerPorId(int idObra) throws Exception {
        String sql = "SELECT * FROM obra WHERE id_obra=?";
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setInt(1, idObra);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }//try
            
        }//try
        return null;
        
    }//obtenerPorID

    @Override
    public List<Obra> buscarPorTitulo(String titulo) throws Exception {
        List<Obra> resultado = new ArrayList<>();
        String sql = "SELECT * FROM obra WHERE titulo ILIKE ?";
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setString(1, "%" + titulo.trim() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) resultado.add(mapearResultSet(rs));
            }//Try
            
        }//try
        
        return resultado;
    
    }//buscarPorTitulo

    //mapeo exacto a las tablas
    private Obra mapearResultSet(ResultSet rs) throws Exception {
        Obra o = new Obra();
        o.setIdObra(rs.getInt("id_obra"));
        o.setTitulo(rs.getString("titulo"));
        o.setAutor(rs.getString("autor"));
        o.setTipo(rs.getString("tipo"));
        o.setNumeroActos(rs.getInt("numero_actos"));
        return o;
    }//mapearResultSet
    
}//ObraDAOImpl
