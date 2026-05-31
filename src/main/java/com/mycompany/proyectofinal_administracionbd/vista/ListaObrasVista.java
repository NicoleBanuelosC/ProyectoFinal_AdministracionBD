/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.vista;

import com.mycompany.proyectofinal_administracionbd.conexion.ConexionBD;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author banue
 */

public class ListaObrasVista extends JPanel{
    
     public ListaObrasVista() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel lblTitulo = new JLabel("CATÁLOGO DE OBRAS DISPONIBLES");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(44, 62, 80));
        add(lblTitulo, BorderLayout.NORTH);
        
        String[] columnas = {"Título", "Autor", "Tipo", "Número de Actos"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tblObras = new JTable(modelo);
        tblObras.setRowHeight(30);
        tblObras.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblObras.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblObras.getTableHeader().setBackground(new Color(52, 152, 219));
        tblObras.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tblObras);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scroll, BorderLayout.CENTER);
        
        cargarObras(modelo);
    }//ListaObrasVista
     
    private void cargarObras(DefaultTableModel modelo) {
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT titulo, autor, tipo, numero_actos FROM obra ORDER BY titulo")) {
            
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("tipo"),
                    rs.getInt("numero_actos")
                });
            }//while
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar obras: " + e.getMessage());
        }//Catch
        
    }//cargarObras  
     
}//ListaObrasVosta
