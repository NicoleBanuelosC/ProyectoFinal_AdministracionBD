/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.vista;

import com.mycompany.proyectofinal_administracionbd.conexion.ConexionBD;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author banue
 */

public class GraficaVista extends JPanel {
    
    public GraficaVista() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel lblTitulo = new JLabel("ESTADÍSTICAS DEL SISTEMA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(44, 62, 80));
        add(lblTitulo, BorderLayout.NORTH);
        
        JPanel pnlGraficas = new JPanel(new GridLayout(2, 2, 20, 20));
        pnlGraficas.setBackground(Color.WHITE);
        
        pnlGraficas.add(crearGraficaBarras());
        pnlGraficas.add(crearGraficaCircular());
        pnlGraficas.add(crearGraficaLineal());
        pnlGraficas.add(crearPanelResumen());
        
        add(pnlGraficas, BorderLayout.CENTER);
        
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnActualizar = new JButton("🔄 Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnActualizar.setBackground(new Color(52, 152, 219));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> {
            pnlGraficas.removeAll();
            pnlGraficas.add(crearGraficaBarras());
            pnlGraficas.add(crearGraficaCircular());
            pnlGraficas.add(crearGraficaLineal());
            pnlGraficas.add(crearPanelResumen());
            pnlGraficas.revalidate();
            pnlGraficas.repaint();
        });
        pnlBotones.add(btnActualizar);
        add(pnlBotones, BorderLayout.SOUTH);
    }//public GraficaVista
    
    // graficas de barra
    private JPanel crearGraficaBarras() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try {
            String sql = "SELECT " +
                        "COALESCE(SUM(CASE WHEN tipo='Ingreso' THEN monto ELSE 0 END),0) as ingresos, " +
                        "COALESCE(SUM(CASE WHEN tipo='Egreso' THEN monto ELSE 0 END),0) as egresos " +
                        "FROM transaccion_financiera";
            try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dataset.addValue(rs.getDouble("ingresos"), "Monto", "Ingresos");
                    dataset.addValue(rs.getDouble("egresos"), "Monto", "Egresos");
                }//if
            }//try
            
        } catch (Exception e) {
            dataset.addValue(45550, "Monto", "Ingresos");
            dataset.addValue(40000, "Monto", "Egresos");
        }//Catch
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Ingresos vs Egresos", "Categoría", "Monto ($)", dataset,
            org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false
        );
        chart.setBackgroundPaint(Color.WHITE);
        
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setBorder(new TitledBorder(
            new LineBorder(new Color(241, 196, 15), 2),
            "Balance Financiero",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(241, 196, 15)
        ));
        return panel;
    }//crearGraficasBarra
    
    //grafica cirucloar
    private JPanel crearGraficaCircular() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        try {
            String sql = "SELECT " +
                        "SUM(CASE WHEN cuota_pagada=true THEN 1 ELSE 0 END) as activos, " +
                        "SUM(CASE WHEN cuota_pagada=false THEN 1 ELSE 0 END) as inactivos " +
                        "FROM miembro";
            try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dataset.setValue("Activos", rs.getInt("activos"));
                    dataset.setValue("Inactivos", rs.getInt("inactivos"));
                }//if
                
            }//try
            
        } catch (Exception e) {
            dataset.setValue("Activos", 15);
            dataset.setValue("Inactivos", 5);
        }//cath
        
        JFreeChart chart = ChartFactory.createPieChart(
            "Miembros por Estado", dataset, true, true, false
        );
        chart.setBackgroundPaint(Color.WHITE);
        
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setBorder(new TitledBorder(
            new LineBorder(new Color(46, 204, 113), 2),
            "Estado de Membresía",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(46, 204, 113)
        ));
        return panel;
    }//crearGraficoCircular
    
    // grafica lineal
    private JPanel crearGraficaLineal() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try {
            String sql = "SELECT o.titulo, COUNT(b.id_boleto) as total " +
                        "FROM produccion p " +
                        "JOIN obra o ON p.id_obra = o.id_obra " +
                        "LEFT JOIN boleto b ON p.id_produccion = b.id_produccion " +
                        "GROUP BY o.titulo ORDER BY total DESC LIMIT 5";
            try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dataset.addValue(rs.getInt("total"), "Boletos", rs.getString("titulo"));
                }//while
            }//try
            
        } catch (Exception e) {
            dataset.addValue(45, "Boletos", "Hamlet");
            dataset.addValue(30, "Boletos", "Romeo");
            dataset.addValue(20, "Boletos", "Bodas");
        }//Catch
        
        JFreeChart chart = ChartFactory.createLineChart(
            "Boletos por Obra", "Obra", "Cantidad", dataset,
            org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false
        );
        chart.setBackgroundPaint(Color.WHITE);
        
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setBorder(new TitledBorder(
            new LineBorder(new Color(52, 152, 219), 2),
            "Venta de Boletos",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(52, 152, 219)
        ));
        return panel;
    }//crearGraficaLineal
    
    // panel de reusmen
    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(
            new LineBorder(new Color(155, 89, 182), 2),
            "Resumen",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(155, 89, 182)
        ));
        
        try {
            String sql = "SELECT " +
                        "(SELECT COUNT(*) FROM miembro) as miembros, " +
                        "(SELECT COUNT(*) FROM obra) as obras, " +
                        "(SELECT COUNT(*) FROM produccion) as producciones, " +
                        "(SELECT COUNT(*) FROM boleto) as boletos";
            try (PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    panel.add(crearLabelResumen("Miembros: " + rs.getInt("miembros")));
                    panel.add(crearLabelResumen("Obras: " + rs.getInt("obras")));
                    panel.add(crearLabelResumen("Producciones: " + rs.getInt("producciones")));
                    panel.add(crearLabelResumen("Boletos: " + rs.getInt("boletos")));
                    return panel;
                }//if
                
            }//try
            
        } catch (Exception e) {}
        
        // Valores por defecto si hay error
        panel.add(crearLabelResumen("👥 Miembros: 20"));
        panel.add(crearLabelResumen("🎭 Obras: 15"));
        panel.add(crearLabelResumen("🎬 Producciones: 7"));
        panel.add(crearLabelResumen("🎫 Boletos: 95"));
        return panel;
    }//crearPanelResumen
    
    private JLabel crearLabelResumen(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setBorder(new EmptyBorder(5, 10, 5, 10));
        return lbl;
    }//CrearLabelResumen
    
}//GraficaVista
