/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.dao;

import com.mycompany.proyectofinal_administracionbd.modelo.Miembro;
import java.util.List;

/**
 *
 * @author banue
 */
public interface MiembroDAO {
    boolean guardar(Miembro miembro) throws Exception;
    boolean actualizar(Miembro miembro) throws Exception;
    boolean eliminar(int idMiembro) throws Exception;
    Miembro obtenerPorId(int idMiembro) throws Exception;
    
    List<Miembro> listarTodos() throws Exception;
    List<Miembro> buscarPorNombre(String nombre) throws Exception;
}//MiembroDAO
