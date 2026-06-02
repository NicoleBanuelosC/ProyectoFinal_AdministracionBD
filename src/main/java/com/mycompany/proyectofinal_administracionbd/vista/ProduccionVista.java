/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.vista;

import com.mycompany.proyectofinal_administracionbd.dao.ProduccionDAO;
import com.mycompany.proyectofinal_administracionbd.dao.ProduccionDAOImpl;
import com.mycompany.proyectofinal_administracionbd.modelo.Produccion;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author banue
 */

public class ProduccionVista extends JPanel{

    private final ProduccionDAO dao = new ProduccionDAOImpl();
    private final DefaultTableModel modelo;
    private final JTable tabla;

    public ProduccionVista() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // columnas de la tabla que coincida con la vista
        String[] columnas = {"ID", "Obra", "Autor", "Tipo", "Temporada", "Año", "Productor"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        construirInterfaz();
        cargarDatos();
    }//produccionVista

    private void construirInterfaz() {
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlTop.setBackground(new Color(240, 248, 255));
        pnlTop.setBorder(new TitledBorder(
            new LineBorder(new Color(41, 128, 185), 2),
            " Listado de Producciones (Vista Multitabla)",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13), new Color(41, 128, 185)));
        
        JLabel lblInfo = new JLabel("Datos unidos desde: produccion + obra + patrono");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInfo.setForeground(new Color(100, 100, 100));
        pnlTop.add(lblInfo);
        add(pnlTop, BorderLayout.NORTH);

        JPanel pnlTabla = new JPanel(new BorderLayout());
        pnlTabla.setBackground(Color.WHITE);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(41, 128, 185));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(44, 62, 80));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        pnlTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(pnlTabla, BorderLayout.CENTER);

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtn.setBackground(Color.WHITE);
        JButton btnRefrescar = new JButton("🔄 Actualizar Listado");
        btnRefrescar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRefrescar.setBackground(new Color(52, 152, 219));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.addActionListener(e -> cargarDatos());
        pnlBtn.add(btnRefrescar);
        add(pnlBtn, BorderLayout.SOUTH);
        
    }//construirInterfaz

    private void cargarDatos() {
        try {
            modelo.setRowCount(0);
            for (Produccion p : dao.listarVistaCompleta()) {
                modelo.addRow(new Object[]{
                    p.getIdProduccion(),
                    p.getTituloObra(),
                    p.getAutorObra(),
                    p.getTipoObra(),
                    p.getTemporada(),
                    p.getAño(),
                    p.getNombreProductor() != null ? p.getNombreProductor() : "Sin asignar"
                });
            }//for
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
        }//Catch
        
    }//cargarDatos
    
}//ProduccionVista
