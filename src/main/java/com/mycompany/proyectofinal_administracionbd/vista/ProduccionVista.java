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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

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
        
        // Botón generar boleto
        JButton btnGenerarBoleto = new JButton("Generar Boleto");
        btnGenerarBoleto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGenerarBoleto.setBackground(new Color(241, 196, 15));
        btnGenerarBoleto.setForeground(new Color(45, 55, 65));
        btnGenerarBoleto.setFocusPainted(false);
        btnGenerarBoleto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerarBoleto.addActionListener(e -> generarBoleto());
        pnlBtn.add(btnGenerarBoleto);
        
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
    
    private void generarBoleto() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, 
                "Selecciona una producción de la tabla", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }//if

        try {
            // obtener datos de la fila seleccionada
            int idProduccion = (int) modelo.getValueAt(filaSeleccionada, 0);
            String tituloObra = modelo.getValueAt(filaSeleccionada, 1).toString();
            String temporada = modelo.getValueAt(filaSeleccionada, 4).toString();
            int anio = (int) modelo.getValueAt(filaSeleccionada, 5);

            // generar número de boleto único
            String numeroBoleto = "BOL-" + System.currentTimeMillis();
            LocalDateTime fechaEmision = LocalDateTime.now();

            // Mostrar boleto
            String mensajeBoleto = String.format(
                "╔══════════════════════════════════════╗\n" +
                "║     🎭 TEATRO PLEASANTVILLE         ║\n" +
                "╠══════════════════════════════════════╣\n" +
                "║  BOLETO: %-26s  ║\n" +
                "║                                      ║\n" +
                "║  Obra: %-28s  ║\n" +
                "║  Temporada: %-23s  ║\n" +
                "║  Año: %-29d  ║\n" +
                "║                                      ║\n" +
                "║  Fecha de Emisión: %-16s  ║\n" +
                "║                                      ║\n" +
                "║  Precio: $250.00                     ║\n" +
                "║                                      ║\n" +
                "║  ¡Disfruta la función! 🎭           ║\n" +
                "╚══════════════════════════════════════╝",
                numeroBoleto,
                tituloObra,
                temporada,
                anio,
                fechaEmision.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );

            JOptionPane.showMessageDialog(this, mensajeBoleto, "Boleto Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar boleto: " + e.getMessage());
        }//Catch
        
    }//nererarBoleto
    
}//ProduccionVista
