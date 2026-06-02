/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.dao;

import com.mycompany.proyectofinal_administracionbd.conexion.ConexionBD;
import com.mycompany.proyectofinal_administracionbd.modelo.Produccion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author banue
 */

public class ProduccionDAOImpl implements ProduccionDAO {

public List<Produccion> listarVistaCompleta() throws Exception {
    List<Produccion> lista = new ArrayList<>();
    String sql = "SELECT * FROM vista_producciones_completas";
    
    System.out.println("DEBUG: Ejecutando vista: " + sql);
    
    try {
        // Limpiar conexión antes de consultar (por si estaba "abortada")
        if (ConexionBD.getConexion().getAutoCommit() == false) {
            try {
                ConexionBD.getConexion().rollback();
            } catch (SQLException ignore) {}
        }//if
        
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            int contador = 0;
            while (rs.next()) {
                Produccion p = new Produccion();
                p.setIdProduccion(rs.getInt("id_produccion"));
                p.setTituloObra(rs.getString("titulo_obra"));
                p.setAutorObra(rs.getString("autor_obra"));
                p.setTipoObra(rs.getString("tipo_obra"));
                p.setTemporada(rs.getString("temporada"));
                p.setAño(rs.getInt("anio"));  // sin ñ para que coincida
                p.setNombreProductor(rs.getString("nombre_productor"));
                lista.add(p);
                contador++;
            }//while
            
            System.out.println("DEBUG: Se cargaron " + contador + " producciones");
            
        }//try
    } catch (SQLException e) {
        System.err.println("ERROR SQL: " + e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        ConexionBD.rollback();
        throw new Exception("Error al cargar producciones: " + e.getMessage(), e);
    }//Catch
    
    return lista;
}//listarVistaCompleta

    public boolean guardar(Produccion p) throws Exception {
        String sql = "INSERT INTO produccion (id_obra, temporada, año, id_productor) VALUES (?, ?::temporada_enum, ?, ?)";
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setInt(1, p.getIdObra());
            stmt.setString(2, p.getTemporada());
            stmt.setInt(3, p.getAño());
            stmt.setInt(4, p.getIdProductor());
            int filas = stmt.executeUpdate();
            ConexionBD.commit();
            return filas > 0;
            
        } catch (Exception e) {
            ConexionBD.rollback();
            throw new Exception("Error al guardar producción: " + e.getMessage());
        }//Catch
        
    }//guardar

    public boolean actualizar(Produccion p) throws Exception {
        String sql = "UPDATE produccion SET id_obra=?, temporada=?::temporada_enum, año=?, id_productor=? WHERE id_produccion=?";
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setInt(1, p.getIdObra());
            stmt.setString(2, p.getTemporada());
            stmt.setInt(3, p.getAño());
            stmt.setInt(4, p.getIdProductor());
            stmt.setInt(5, p.getIdProduccion());
            int filas = stmt.executeUpdate();
            ConexionBD.commit();
            return filas > 0;
            
        } catch (Exception e) {
            ConexionBD.rollback();
            throw new Exception("Error al actualizar: " + e.getMessage());
        }//Catch
        
    }//Actualizar

    public boolean eliminar(int idProduccion) throws Exception {
        String sql = "DELETE FROM produccion WHERE id_produccion=?";
        try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql)) {
            stmt.setInt(1, idProduccion);
            int filas = stmt.executeUpdate();
            ConexionBD.commit();
            return filas > 0;
            
        } catch (Exception e) {
            ConexionBD.rollback();
            throw new Exception("Error al eliminar: " + e.getMessage());
        }//Cacth
        
    }//eliminar

}//ProduccionDAOImpl
