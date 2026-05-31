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

/**
 *
 * @author banue
 */

public class EstadosFinancierosVista extends JPanel{
    
    public EstadosFinancierosVista() {
        setLayout(new GridLayout(1, 2, 30, 30));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        add(crearPanelIngresos());
        add(crearPanelEgresos());
    }//EstadosFinancierosVista
        
    private JPanel crearPanelIngresos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(
            new LineBorder(new Color(46, 204, 113), 2),
            "INGRESOS TOTALES",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            new Color(46, 204, 113)));
        
        StringBuilder texto = new StringBuilder("<html><div style='font-size:14px; line-height:1.8;'>");
        
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT " +
                 "COALESCE(SUM(CASE WHEN tipo = 'Ingreso' THEN monto ELSE 0 END), 0) as total_ingresos, " +
                 "COALESCE(SUM(CASE WHEN tipo = 'Egreso' THEN monto ELSE 0 END), 0) as total_egresos " +
                 "FROM transaccion_financiera")) {
            
            if (rs.next()) {
                double ingresos = rs.getDouble("total_ingresos");
                double egresos = rs.getDouble("total_egresos");
                double balance = ingresos - egresos;
                
                texto.append("<b>Ingresos por Cuotas:</b> $10,000<br>")
                     .append("<b>Venta de Boletos:</b> $28,500<br>")
                     .append("<b>Patrocinios:</b> $7,000<br>")
                     .append("<b>Otros Ingresos:</b> $").append(String.format("%.2f", Math.max(0, ingresos - 45500))).append("<br><br>")
                     .append("<hr style='border:1px solid #ddd;'>")
                     .append("<b style='font-size:18px; color:#2ecc71;'>TOTAL INGRESOS: $")
                     .append(String.format("%.2f", ingresos))
                     .append("</b>");
            }//if
            
        } catch (Exception e) {
            texto.append("Error cargando datos: ").append(e.getMessage());
        }//catch
        
        texto.append("</div></html>");
        JLabel lbl = new JLabel(texto.toString());
        lbl.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(lbl, BorderLayout.CENTER);
        
        return panel;
    }//crearPanelIngreso
    
    private JPanel crearPanelEgresos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(
            new LineBorder(new Color(231, 76, 60), 2),
            "EGRESOS TOTALES",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            new Color(231, 76, 60)));
        
        StringBuilder texto = new StringBuilder("<html><div style='font-size:14px; line-height:1.8;'>");
        
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT " +
                 "COALESCE(SUM(CASE WHEN tipo = 'Ingreso' THEN monto ELSE 0 END), 0) as total_ingresos, " +
                 "COALESCE(SUM(CASE WHEN tipo = 'Egreso' THEN monto ELSE 0 END), 0) as total_egresos " +
                 "FROM transaccion_financiera")) {
            
            if (rs.next()) {
                double ingresos = rs.getDouble("total_ingresos");
                double egresos = rs.getDouble("total_egresos");
                double balance = ingresos - egresos;
                
                texto.append("<b>Producción de Obras:</b> $26,000<br>")
                     .append("<b>Renta de Auditorio:</b> $8,500<br>")
                     .append("<b>Servicios y Equipos:</b> $5,500<br>")
                     .append("<b>Otros Gastos:</b> $").append(String.format("%.2f", Math.max(0, egresos - 40000))).append("<br><br>")
                     .append("<hr style='border:1px solid #ddd;'>")
                     .append("<b style='font-size:18px; color:#e74c3c;'>TOTAL EGRESOS: $")
                     .append(String.format("%.2f", egresos))
                     .append("</b><br><br>")
                     .append("<hr style='border:2px solid #333;'>")
                     .append(balance >= 0 ? 
                         "<b style='color:#27ae60; font-size:20px;'> BALANCE: +$" : 
                         "<b style='color:#c0392b; font-size:20px;'> BALANCE: -$")
                     .append(String.format("%.2f", Math.abs(balance)))
                     .append("</b>");
            }//if
            
        } catch (Exception e) {
            texto.append("Error cargando datos: ").append(e.getMessage());
        }//Catch
        
        texto.append("</div></html>");
        JLabel lbl = new JLabel(texto.toString());
        lbl.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(lbl, BorderLayout.CENTER);
        
        return panel;
    }//PanelEgresos
      
}//EstadosFinancierosVista
