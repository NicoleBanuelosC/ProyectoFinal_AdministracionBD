/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.dao;

import com.mycompany.proyectofinal_administracionbd.modelo.Produccion;
import java.util.List;

/**
 *
 * @author banue
 */

public interface ProduccionDAO {
    List<Produccion> listarVistaCompleta() throws Exception;
    boolean guardar(Produccion p) throws Exception;
    boolean actualizar(Produccion p) throws Exception;
    boolean eliminar(int idProduccion) throws Exception;
}//ProduccionDAO
