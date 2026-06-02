/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.dao;

import com.mycompany.proyectofinal_administracionbd.modelo.Obra;
import java.util.List;

/**
 *
 * @author banue
 */

public interface ObraDAO {
    boolean guardar(Obra obra) throws Exception;
    List<Obra> listarTodos() throws Exception;
    boolean actualizar(Obra obra) throws Exception;
    boolean eliminar(int idObra) throws Exception;
    Obra obtenerPorId(int idObra) throws Exception;
    List<Obra> buscarPorTitulo(String titulo) throws Exception;
}//ObraDAO
