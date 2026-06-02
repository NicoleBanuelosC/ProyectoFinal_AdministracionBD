/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.modelo;

/**
 *
 * @author banue
 */

public class Produccion {
    private int idProduccion;
    private int idObra;
    private int idProductor;
    private String tituloObra;
    private String autorObra;
    private String tipoObra;
    private String temporada;
    private int año;
    private String nombreProductor;

    public Produccion() {}

    public int getIdProduccion() { return idProduccion; }
    public void setIdProduccion(int idProduccion) { this.idProduccion = idProduccion; }
    
    public int getIdObra() { return idObra; }
    public void setIdObra(int idObra) { this.idObra = idObra; }
    
    public int getIdProductor() { return idProductor; }
    public void setIdProductor(int idProductor) { this.idProductor = idProductor; }
    
    public String getTituloObra() { return tituloObra; }
    public void setTituloObra(String tituloObra) { this.tituloObra = tituloObra; }
    
    public String getAutorObra() { return autorObra; }
    public void setAutorObra(String autorObra) { this.autorObra = autorObra; }
    
    public String getTipoObra() { return tipoObra; }
    public void setTipoObra(String tipoObra) { this.tipoObra = tipoObra; }
    
    public String getTemporada() { return temporada; }
    public void setTemporada(String temporada) { this.temporada = temporada; }
    
    public int getAño() { return año; }
    public void setAño(int año) { this.año = año; }
    
    public String getNombreProductor() { return nombreProductor; }
    public void setNombreProductor(String nombreProductor) { this.nombreProductor = nombreProductor; }

}//Produccion
