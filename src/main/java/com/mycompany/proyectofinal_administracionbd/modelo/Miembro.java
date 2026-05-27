/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.modelo;

import java.sql.Date;

/**
 *
 * @author banue
 */
public class Miembro {
    private int idMiembro;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;
    private String telefono;
    private Date fechaIngreso;
    private boolean cuotaPagada;
    private int anoCuota;
    private String calle;
    private String numeroExt;
    private String numeroInt;
    private String colonia;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;

    public Miembro() {}

    public int getIdMiembro() { return idMiembro; }
    public void setIdMiembro(int idMiembro) { this.idMiembro = idMiembro; }
   
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public boolean isCuotaPagada() { return cuotaPagada; }
    public void setCuotaPagada(boolean cuotaPagada) { this.cuotaPagada = cuotaPagada; }
    
    public int getAnoCuota() { return anoCuota; }
    public void setAnoCuota(int anoCuota) { this.anoCuota = anoCuota; }

        public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    
    public String getNumeroExt() { return numeroExt; }
    public void setNumeroExt(String numeroExt) { this.numeroExt = numeroExt; }
    
    public String getNumeroInt() { return numeroInt; }
    public void setNumeroInt(String numeroInt) { this.numeroInt = numeroInt; }
    
    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
    
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    
}//Miemrbo
