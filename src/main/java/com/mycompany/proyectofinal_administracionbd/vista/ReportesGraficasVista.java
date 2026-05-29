/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.vista;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author banue
 */

public class ReportesGraficasVista extends JPanel{

    public ReportesGraficasVista() {
        setLayout(new GridLayout(1, 2, 25, 25));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(crearPanel("Distribución de Tipos de Obra", generarGraficaPastel()));
        add(crearPanel("Balance Financiero por Temporada", generarGraficaBarras()));
    }//ReportesGraficas

    private JPanel crearPanel(String titulo, JFreeChart chart) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            titulo, 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80)));
        p.add(new ChartPanel(chart), BorderLayout.CENTER);
        return p;
    }//crearPnale

    private JFreeChart generarGraficaPastel() {
        DefaultPieDataset ds = new DefaultPieDataset();
        ds.setValue("Drama", 35); ds.setValue("Comedia", 40);
        ds.setValue("Musical", 15); ds.setValue("Tragedia", 7); ds.setValue("Ópera", 3);
        
        JFreeChart chart = ChartFactory.createPieChart("Catálogo de Obras", ds, true, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 15));
        return chart;
    }//generarGraficasPastel

    private JFreeChart generarGraficaBarras() {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.addValue(12000, "Ingresos", "Otoño"); ds.addValue(8500, "Egresos", "Otoño");
        ds.addValue(15000, "Ingresos", "Primavera"); ds.addValue(9200, "Egresos", "Primavera");
        
        JFreeChart chart = ChartFactory.createBarChart("Finanzas", "Temporada", "Monto ($)", ds);
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 15));
        return chart;
    }//generarGraficasBarras
    
}//ReportesGraficasVista
