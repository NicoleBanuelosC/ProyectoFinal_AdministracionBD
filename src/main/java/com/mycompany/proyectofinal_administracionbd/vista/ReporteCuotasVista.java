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

public class ReporteCuotasVista extends JPanel{
    private JTable tblCuotas;
    private DefaultTableModel modelo;

    public ReporteCuotasVista() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Reporte de Cuotas de Miembros (Año Actual)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(44, 62, 80));
        add(lblTitulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"ID", "Miembro", "Email", "Teléfono", "Estado"}, 0);
        tblCuotas = new JTable(modelo);
        tblCuotas.setRowHeight(28);
        tblCuotas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        add(new JScrollPane(tblCuotas), BorderLayout.CENTER);

        cargarDatos();
    }//ReporteCuotasVista

    private void cargarDatos() {
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM vista_cuotas_miembros")) {
            modelo.setRowCount(0);
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_miembro"),
                    rs.getString("nombre_completo"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("estado")
                });
            }//while
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }//catch
        
    }//cargarDatos
    
}//ReporteCuotasVista
