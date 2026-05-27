/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.vista;

import com.mycompany.proyectofinal_administracionbd.controlador.LoginControlador;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author banue
 */
public class LoginVista extends JFrame {
    
    private JPanel pnlFondo;
    private JPanel pnlFormulario;
    private JPanel pnlHeader;
    private JLabel lblIcono;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnSalir;
    private JPanel pnlSeparador1;
    private JPanel pnlSeparador2;

    public LoginVista() {
        configurarVentana();
        construirInterfaz();
        agregarEventos();
    }//LoginVista

    private void configurarVentana() {
        setTitle("Sistema de Teatro - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(900, 600);
        setLocationRelativeTo(null); // Centrar en pantalla
    }//ConfigurarVentana

    private void construirInterfaz() {
        // Fondo con degradado
        pnlFondo = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = new Color(52, 152, 219);
                Color c2 = new Color(41, 128, 185);
                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }//painComponent
        };

        pnlFormulario = new JPanel();
        pnlFormulario.setBackground(Color.WHITE);
        pnlFormulario.setPreferredSize(new Dimension(450, 580));
        pnlFormulario.setLayout(new GridBagLayout());
        pnlFormulario.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(41, 128, 185));
        pnlHeader.setPreferredSize(new Dimension(390, 140));
        pnlHeader.setLayout(new FlowLayout(FlowLayout.CENTER));

        lblIcono = new JLabel("");
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.BOLD, 80));
        lblIcono.setForeground(Color.WHITE);
        pnlHeader.add(lblIcono);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        pnlFormulario.add(pnlHeader, gbc);

        lblTitulo = new JLabel("SISTEMA DE TEATRO", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(44, 62, 80));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        pnlFormulario.add(lblTitulo, gbc);

        lblSubtitulo = new JLabel("Administración de Base de Datos", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(127, 140, 141));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        pnlFormulario.add(lblSubtitulo, gbc);

        lblUsuario = new JLabel(" USUARIO");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsuario.setForeground(new Color(52, 73, 94));
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        pnlFormulario.add(lblUsuario, gbc);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUsuario.setPreferredSize(new Dimension(350, 40));
        txtUsuario.setForeground(new Color(52, 73, 94));
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlFormulario.add(txtUsuario, gbc);

        pnlSeparador1 = new JPanel();
        pnlSeparador1.setBackground(new Color(189, 195, 199));
        pnlSeparador1.setPreferredSize(new Dimension(350, 2));
        gbc.gridy = 5;
        pnlFormulario.add(pnlSeparador1, gbc);

        lblPassword = new JLabel("CONTRASEÑA");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassword.setForeground(new Color(52, 73, 94));
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(15, 0, 5, 0);
        pnlFormulario.add(lblPassword, gbc);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword.setPreferredSize(new Dimension(350, 40));
        txtPassword.setForeground(new Color(52, 73, 94));
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlFormulario.add(txtPassword, gbc);

        pnlSeparador2 = new JPanel();
        pnlSeparador2.setBackground(new Color(189, 195, 199));
        pnlSeparador2.setPreferredSize(new Dimension(350, 2));
        gbc.gridy = 8;
        pnlFormulario.add(pnlSeparador2, gbc);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlBotones.setOpaque(false);

        btnIngresar = new JButton("INGRESAR");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnIngresar.setBackground(new Color(46, 204, 113));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setPreferredSize(new Dimension(160, 45));

        btnSalir = new JButton("SALIR");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSalir.setBackground(new Color(231, 76, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setPreferredSize(new Dimension(160, 45));

        pnlBotones.add(btnIngresar);
        pnlBotones.add(btnSalir);

        gbc.gridy = 9;
        gbc.insets = new Insets(30, 0, 0, 0);
        pnlFormulario.add(pnlBotones, gbc);

        pnlFondo.add(pnlFormulario, gbc);
        add(pnlFondo);
    }//Interfaz

    private void agregarEventos() {
        txtUsuario.addActionListener(e -> txtPassword.requestFocus());

        btnIngresar.addActionListener(e -> validarLogin());

        btnSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) System.exit(0);
        });
    }//agregarEventos

    private void validarLogin() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El usuario es obligatorio", "Validación", JOptionPane.WARNING_MESSAGE);
            txtUsuario.requestFocus();
            return;
        }//if
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La contraseña es obligatoria", "Validación", JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocus();
            return;
        }//if

        try {
            btnIngresar.setEnabled(false);
            btnIngresar.setText("Validando...");

            LoginControlador controlador = new LoginControlador();
            if (controlador.autenticar(usuario, password)) {
                JOptionPane.showMessageDialog(this, "¡Bienvenido al sistema!", "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new MenuPrincipal().setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
                txtPassword.requestFocus();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        
        } finally {
            btnIngresar.setEnabled(true);
            btnIngresar.setText("INGRESAR");
        }//finally
        
    }//validarLogin

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginVista().setVisible(true));
    }//void main
        
}//LoginVista
