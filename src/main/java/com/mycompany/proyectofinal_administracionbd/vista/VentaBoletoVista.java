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
public class VentaBoletoVista extends JPanel{
    
    private JComboBox<String> cmbProduccion, cmbRepresentacion;
    private JTextField txtFila, txtNumero, txtPatrono;
    private JLabel lblEstado, lblPrecio;
    private JButton btnVerificar, btnReservar, btnLimpiar;
    private int idRepresentacionSeleccionada = -1;
    
   public VentaBoletoVista() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JLabel lblTitulo = new JLabel("VENTA INTERACTIVA DE BOLETOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(44, 62, 80));
        add(lblTitulo, BorderLayout.NORTH);
        
        // Panel central
        JPanel pnlForm = new JPanel(new GridLayout(7, 2, 15, 15));
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        pnlForm.add(new JLabel("Producción:"));
        cmbProduccion = new JComboBox<>();
        pnlForm.add(cmbProduccion);
        
        pnlForm.add(new JLabel("Fecha/Hora:"));
        cmbRepresentacion = new JComboBox<>();
        pnlForm.add(cmbRepresentacion);
        
        pnlForm.add(new JLabel("Nombre del Patrono:"));
        txtPatrono = new JTextField();
        pnlForm.add(txtPatrono);
        
        pnlForm.add(new JLabel("Fila (A-Z):"));
        txtFila = new JTextField();
        pnlForm.add(txtFila);
        
        pnlForm.add(new JLabel("Número de Asiento (1-40):"));
        txtNumero = new JTextField();
        pnlForm.add(txtNumero);
        
        pnlForm.add(new JLabel("Precio:"));
        lblPrecio = new JLabel("$25.00");
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblPrecio.setForeground(new Color(46, 204, 113));
        pnlForm.add(lblPrecio);
        
        lblEstado = new JLabel("Seleccione producción y fecha");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlForm.add(lblEstado);
        
        add(pnlForm, BorderLayout.CENTER);
        
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlBotones.setBackground(Color.WHITE);
        
        btnVerificar = new JButton("🔍 Verificar Disponibilidad");
        btnVerificar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVerificar.setBackground(new Color(52, 152, 219));
        btnVerificar.setForeground(Color.WHITE);
        btnVerificar.setFocusPainted(false);
        btnVerificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVerificar.setPreferredSize(new Dimension(200, 40));
        btnVerificar.addActionListener(e -> verificarDisponibilidad());
        
        btnReservar = new JButton("✅ Reservar e Imprimir Boleto");
        btnReservar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnReservar.setBackground(new Color(46, 204, 113));
        btnReservar.setForeground(Color.WHITE);
        btnReservar.setFocusPainted(false);
        btnReservar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReservar.setPreferredSize(new Dimension(220, 40));
        btnReservar.addActionListener(e -> reservarBoleto());
        
        btnLimpiar = new JButton("+ Nueva Venta");
        btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLimpiar.setBackground(new Color(149, 165, 166));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.setPreferredSize(new Dimension(150, 40));
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        pnlBotones.add(btnVerificar);
        pnlBotones.add(btnReservar);
        pnlBotones.add(btnLimpiar);
        add(pnlBotones, BorderLayout.SOUTH);
        
        cargarProducciones();
        cmbProduccion.addActionListener(e -> cargarRepresentaciones());
    }//VentaBoletos
    
    private void cargarProducciones() {
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT p.id_produccion, o.titulo, p.temporada, p.año " +
                 "FROM produccion p JOIN obra o ON p.id_obra = o.id_obra " +
                 "ORDER BY p.año DESC, o.titulo")) {
            
            cmbProduccion.removeAllItems();
            
            while (rs.next()) {
                String item = rs.getInt("id_produccion") + " - " + 
                             rs.getString("titulo") + " (" + 
                             rs.getString("temporada") + " " + 
                             rs.getInt("año") + ")";
                cmbProduccion.addItem(item);
            }//while
            
        } catch (Exception e) {
            lblEstado.setText("Error cargando producciones");
        }//Catch
        
    }//CargarProducciones
    
    private void cargarRepresentaciones() {
        if (cmbProduccion.getSelectedItem() == null) return;
        
        String prodId = cmbProduccion.getSelectedItem().toString().split(" - ")[0];
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT id_representacion, fecha, hora FROM representacion " +
                 "WHERE id_produccion = ? ORDER BY fecha, hora")) {
            
            ps.setInt(1, Integer.parseInt(prodId));
            ResultSet rs = ps.executeQuery();
            
            cmbRepresentacion.removeAllItems();
            
            while (rs.next()) {
                String item = rs.getInt("id_representacion") + " | " + 
                             rs.getDate("fecha") + " " + rs.getTime("hora");
                cmbRepresentacion.addItem(item);
                idRepresentacionSeleccionada = rs.getInt("id_representacion");
            }//while
            
            lblEstado.setText("Seleccione asiento y verifique disponibilidad");
            
        } catch (Exception e) {
            lblEstado.setText("Error cargando fechas");
        }//Catch
        
    }//cargarRepresentaciones
    
    private void verificarDisponibilidad() {
        if (cmbRepresentacion.getSelectedItem() == null || 
            txtFila.getText().trim().isEmpty() || 
            txtNumero.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Complete todos los campos primero", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        
        String fila = txtFila.getText().trim().toUpperCase();
        int numero;
        
        try {
            numero = Integer.parseInt(txtNumero.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El número de asiento debe ser un número", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }//try
        
        if (fila.length() != 1 || fila.charAt(0) < 'A' || fila.charAt(0) > 'Z') {
            JOptionPane.showMessageDialog(this, 
                "La fila debe ser una letra de A a Z", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        
        if (numero < 1 || numero > 40) {
            JOptionPane.showMessageDialog(this, 
                "El número de asiento debe ser entre 1 y 40", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        
        // verificar en base de datos
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT b.id_boleto FROM boleto b " +
                 "JOIN representacion r ON b.id_representacion = r.id_representacion " +
                 "WHERE r.id_produccion = (SELECT id_produccion FROM representacion WHERE id_representacion = ?) " +
                 "AND b.fila = ? AND b.numero = ?")) {
            
            ps.setInt(1, idRepresentacionSeleccionada);
            ps.setString(2, fila);
            ps.setInt(3, numero);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                lblEstado.setText("ASIENTO NO DISPONIBLE - Ya está vendido");
                lblEstado.setForeground(new Color(231, 76, 60));
                btnReservar.setEnabled(false);
                
            } else {
                lblEstado.setText("ASIENTO DISPONIBLE - Puede reservar");
                lblEstado.setForeground(new Color(46, 204, 113));
                btnReservar.setEnabled(true);
            }//else
            
        } catch (Exception e) {
            lblEstado.setText("Error en verificación: " + e.getMessage());
            btnReservar.setEnabled(false);
        }//Catch
        
    }//verificarDisponibilidad
    
    private void reservarBoleto() {
        if (!btnReservar.isEnabled()) {
            JOptionPane.showMessageDialog(this, 
                "Verifique disponibilidad primero", 
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        
        if (txtPatrono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Ingrese el nombre del patrono", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Confirmar reserva del asiento?\n\n" +
            "Producción: " + cmbProduccion.getSelectedItem() + "\n" +
            "Asiento: Fila " + txtFila.getText().toUpperCase() + 
            " Número " + txtNumero.getText() + "\n" +
            "Precio: $25.00",
            "Confirmar Reserva",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        try (Connection conn = ConexionBD.getConexion()) {
            conn.setAutoCommit(false);
            
            // Obtener o crear ID de patrono (por ahora 1)
            int idPatrono = 1;
            
            // insertar boleto
            try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO boleto (id_representacion, id_patrono, fila, numero, precio, fecha_compra, tipo_boleto) " +
                "VALUES (?, ?, ?, ?, 25.00, NOW(), 'General')", 
                Statement.RETURN_GENERATED_KEYS)) {
                
                ps.setInt(1, idRepresentacionSeleccionada);
                ps.setInt(2, idPatrono);
                ps.setString(3, txtFila.getText().toUpperCase());
                ps.setInt(4, Integer.parseInt(txtNumero.getText()));
                
                int filasAfectadas = ps.executeUpdate();
                
                if (filasAfectadas > 0) {
                    conn.commit();
                    
                  // Mostrar "boleto impreso"
                    String obraSeleccionada = cmbProduccion.getSelectedItem().toString();
                    if (obraSeleccionada.length() > 10) {
                        obraSeleccionada = obraSeleccionada.substring(10); 
                    }//if

                    String fechaActual = new java.sql.Date(System.currentTimeMillis()).toString();
                    String filaAsiento = txtFila.getText().toUpperCase();
                    String nombrePatrono = txtPatrono.getText();

                    String boleto = "╔══════════════════════════════════════╗\n" +
                                   "║         BOLETO DE ADMISIÓN           ║\n" +
                                   "╠══════════════════════════════════════╣\n" +
                                   String.format("║ OBRA: %-28s ║\n", obraSeleccionada.length() > 28 ? obraSeleccionada.substring(0, 28) : obraSeleccionada) +
                                   String.format("║ FECHA: %-27s ║\n", fechaActual) +
                                   String.format("║ ASIENTO: Fila %-20s ║\n", filaAsiento) +
                                   "║ PRECIO: $45.00                          ║\n" +
                                   String.format("║ PATRONO: %-25s ║\n", nombrePatrono.length() > 25 ? nombrePatrono.substring(0, 25) : nombrePatrono) +
                                   "╚══════════════════════════════════════╝";                    
                    JOptionPane.showMessageDialog(this, 
                        "¡BOLETO RESERVADO EXITOSAMENTE!\n\n" + boleto,
                        "Reserva Confirmada",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    limpiarFormulario();
                }//if
            }//try
            
        } catch (Exception e) {
            try { ConexionBD.rollback(); } catch (Exception ex) {}
            JOptionPane.showMessageDialog(this, 
                "Error al reservar: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }//catch
        
    }//reservarBoleto
    
    private void limpiarFormulario() {
        txtFila.setText("");
        txtNumero.setText("");
        txtPatrono.setText("");
        lblEstado.setText("Seleccione producción y fecha");
        lblEstado.setForeground(new Color(52, 152, 219));
        btnReservar.setEnabled(false);
        txtFila.requestFocus();
    }//limpiarFormulario
 
}//VentaBoletoVista
