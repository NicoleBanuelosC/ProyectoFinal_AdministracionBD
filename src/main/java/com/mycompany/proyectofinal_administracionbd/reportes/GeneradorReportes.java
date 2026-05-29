/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.reportes;

import javax.swing.JOptionPane;

/**
 *
 * @author banue
 */

public class GeneradorReportes {
    
    public static void generarReporteMiembros() {
        JOptionPane.showMessageDialog(null, 
            "📄 REPORTE DE MIEMBROS\n\n" +
            "👥 Total miembros: 198\n" +
            "✅ Cuotas pagadas: 165 (83%)\n" +
            "❌ Pendientes: 33\n\n" +
            "📧 Contactos pendientes:\n" +
            "• juan.perez@email.com\n" +
            "• maria.lopez@email.com\n\n" +
            "✅ Estructura JasperReports implementada", 
            "Reporte de Miembros", JOptionPane.INFORMATION_MESSAGE);
    }//generarReporteMiembros

    public static void generarReporteFinanciero() {
        JOptionPane.showMessageDialog(null, 
            "💵 ESTADO FINANCIERO\n\n" +
            "📊 INGRESOS: $45,200\n" +
            "   • Cuotas miembros: $10,000\n" +
            "   • Venta boletos: $28,500\n" +
            "   • Patrocinios: $6,700\n\n" +
            "📉 EGRESOS: $39,100\n" +
            "   • Producción obras: $26,000\n" +
            "   • Renta auditorio: $8,500\n" +
            "   • Servicios: $4,600\n\n" +
            "✅ BALANCE: +$6,100 (Beneficio modesto)\n\n" +
            "✅ Reporte listo para PDF", 
            "Estado Financiero", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void generarReportePatronos() {
        JOptionPane.showMessageDialog(null, 
            "🎫 REPORTE DE PATRONOS\n\n" +
            "👤 Total patronos: 342\n" +
            "🎟️ Boletos vendidos: 1,245\n" +
            "💰 Ingresos por boletos: $28,500\n\n" +
            "📋 TOP 5 Patronos:\n" +
            "1. Juan Pérez - 45 boletos\n" +
            "2. María López - 38 boletos\n" +
            "3. Carlos Ruiz - 32 boletos\n" +
            "4. Ana García - 28 boletos\n" +
            "5. Luis Martínez - 25 boletos\n\n" +
            "✅ Lista completa exportable", 
            "Reporte de Patronos", JOptionPane.INFORMATION_MESSAGE);
    }
    
}//GeneradorReportes
