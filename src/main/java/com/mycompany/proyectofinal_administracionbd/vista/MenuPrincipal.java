/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.mycompany.proyectofinal_administracionbd.vista.ABCCVistaMiembros;
import com.mycompany.proyectofinal_administracionbd.vista.ListaObrasVista;
import com.mycompany.proyectofinal_administracionbd.vista.VentaBoletoVista;
import com.mycompany.proyectofinal_administracionbd.vista.EstadosFinancierosVista;
import com.mycompany.proyectofinal_administracionbd.vista.ReporteCuotasVista;
import com.mycompany.proyectofinal_administracionbd.vista.LoginVista;
import com.mycompany.proyectofinal_administracionbd.vista.ProduccionVista;  

/**
 *
 * @author banue
 */

public class MenuPrincipal extends JFrame{
    
    private JPanel pnlSidebar;
    private JPanel pnlHeader;
    private JPanel pnlContenido;
    private JPanel pnlWelcome;
    
    private JLabel lblLogo;
    private JLabel lblTituloApp;
    private JLabel lblUsuario;
    private JLabel lblRol;
    private JLabel lblBienvenida;
    private JLabel lblStats;
    
    private JButton btnMiembros;
    private JButton btnObras;
    private JButton btnProducciones;
    private JButton btnReportes;
    private JButton btnConfigurar;
    private JButton btnSalir;
    
    private CardLayout cardLayout;
    private String usuarioActual = "Administrador"; 
    
    public MenuPrincipal() {
        configurarVentana();
        construirSidebar();
        construirHeader();
        construirContenido();
        construirLayout();
        agregarEventos();
    }//ManuPrincipal

    private void configurarVentana() {
        setTitle("Sistema de Administración de Teatro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
    }//configurarVentana
    
       private void construirSidebar() {
        pnlSidebar = new JPanel();
        pnlSidebar.setPreferredSize(new Dimension(260, 700));
        pnlSidebar.setBackground(new Color(44, 62, 80));
        pnlSidebar.setLayout(new BorderLayout());

        JPanel pnlTop = new JPanel();
        pnlTop.setBackground(new Color(44, 62, 80));
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));
        pnlTop.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        lblLogo = new JLabel("🎭",SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 60));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTituloApp = new JLabel("TEATRO DB", SwingConstants.CENTER);
        lblTituloApp.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTituloApp.setForeground(new Color(52, 152, 219));
        lblTituloApp.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pnlUsuario = new JPanel();
        pnlUsuario.setBackground(new Color(52, 73, 94));
        pnlUsuario.setLayout(new GridLayout(2, 1, 0, 5));
        pnlUsuario.setBorder(new EmptyBorder(20, 15, 20, 15));
        pnlUsuario.setMaximumSize(new Dimension(220, 80));

        lblUsuario = new JLabel("👤 " + usuarioActual);
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsuario.setForeground(Color.WHITE);

        lblRol = new JLabel(" Administrador");
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblRol.setForeground(new Color(189, 195, 199));

        pnlUsuario.add(lblUsuario);
        pnlUsuario.add(lblRol);
        pnlUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlTop.add(lblLogo);
        pnlTop.add(Box.createVerticalStrut(10));
        pnlTop.add(lblTituloApp);
        pnlTop.add(Box.createVerticalStrut(30));
        pnlTop.add(pnlUsuario);

        // Panel de botones del sidebar
        JPanel pnlBotones = new JPanel();
        pnlBotones.setBackground(new Color(44, 62, 80));
        pnlBotones.setLayout(new BoxLayout(pnlBotones, BoxLayout.Y_AXIS));
        pnlBotones.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        btnMiembros = crearBotonSidebar(" Gestión de Miembros", new Color(52, 152, 219));
        btnObras = crearBotonSidebar(" Catálogo de Obras", new Color(155, 89, 182));
        btnProducciones = crearBotonSidebar("🎬 Producciones", new Color(46, 204, 113));
        btnReportes = crearBotonSidebar(" Reportes y Gráficas", new Color(241, 196, 15));
        btnConfigurar = crearBotonSidebar("⚙️ Configuración", new Color(149, 165, 166));
        btnSalir = crearBotonSidebar("🚪 Cerrar Sesión", new Color(231, 76, 60));

        pnlBotones.add(btnMiembros);
        pnlBotones.add(Box.createVerticalStrut(10));
        pnlBotones.add(btnObras);
        pnlBotones.add(Box.createVerticalStrut(10));
        pnlBotones.add(btnProducciones);
        pnlBotones.add(Box.createVerticalStrut(10));
        pnlBotones.add(btnReportes);
        pnlBotones.add(Box.createVerticalStrut(10));
        pnlBotones.add(btnConfigurar);
        pnlBotones.add(Box.createVerticalGlue());
        pnlBotones.add(btnSalir);

        pnlSidebar.add(pnlTop, BorderLayout.NORTH);
        pnlSidebar.add(pnlBotones, BorderLayout.CENTER);
    }//construirsidebar

    private JButton crearBotonSidebar(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(230, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new CompoundBorder(
            new MatteBorder(0, 5, 0, 0, new Color(255, 255, 255, 100)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.brighter());
            }//mouseEntered
            
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }//mouseExited
            
        });
        
        return btn;
    }//BotonSideBar
   
    private void construirHeader() {
        pnlHeader = new JPanel();
        pnlHeader.setPreferredSize(new Dimension(940, 80));
        pnlHeader.setBackground(new Color(52, 152, 219));
        pnlHeader.setLayout(new BorderLayout());
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel lblTitulo = new JLabel(" SISTEMA DE ADMINISTRACIÓN DE TEATRO - PLEASANTVILLE");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        lblBienvenida = new JLabel("👋 Bienvenido, " + usuarioActual + " | " + 
            new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
        lblBienvenida.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblBienvenida.setForeground(new Color(236, 240, 241));

        pnlHeader.add(lblTitulo, BorderLayout.NORTH);
        pnlHeader.add(lblBienvenida, BorderLayout.SOUTH);
    }//Header
    
      private void construirContenido() {
        pnlContenido = new JPanel();
        pnlContenido.setLayout(new BorderLayout());
        
        pnlWelcome = new JPanel(new GridBagLayout());
        pnlWelcome.setBackground(Color.WHITE);
        pnlWelcome.setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel lblIconoBienvenida = new JLabel("🎭");
        lblIconoBienvenida.setFont(new Font("Segoe UI Emoji", Font.BOLD, 100));
        pnlWelcome.add(lblIconoBienvenida, gbc);

        gbc.gridy = 1;
        JLabel lblMsgBienvenida = new JLabel("Sistema de Gestión de Teatro");
        lblMsgBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblMsgBienvenida.setForeground(new Color(44, 62, 80));
        pnlWelcome.add(lblMsgBienvenida, gbc);

        gbc.gridy = 2;
        JLabel lblSubMsg = new JLabel("Selecciona una opción del menú lateral para comenzar");
        lblSubMsg.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubMsg.setForeground(new Color(127, 140, 141));
        pnlWelcome.add(lblSubMsg, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(30, 0, 10, 0);
        JPanel pnlStats = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pnlStats.setBackground(Color.WHITE);

        lblStats = new JLabel("👥 Miembros: ~200 | 🎭 Obras: 2/año | 🎬 Producciones: Activas");
        lblStats.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblStats.setForeground(new Color(52, 152, 219));
        pnlStats.add(lblStats);

        pnlWelcome.add(pnlStats, gbc);

        pnlContenido.add(pnlWelcome, BorderLayout.CENTER);
    }//Contenido 

    private void construirLayout() {
        JPanel pnlPrincipal = new JPanel(new BorderLayout());
        pnlPrincipal.add(pnlSidebar, BorderLayout.WEST);

        JPanel pnlCentro = new JPanel(new BorderLayout());
        pnlCentro.add(pnlHeader, BorderLayout.NORTH);
        pnlCentro.add(pnlContenido, BorderLayout.CENTER);

        pnlPrincipal.add(pnlCentro, BorderLayout.CENTER);
        add(pnlPrincipal);
    }//layput

    private void agregarEventos() {
        btnMiembros.addActionListener(e -> {
            pnlContenido.removeAll();
            pnlContenido.add(new ABCCVistaMiembros(), BorderLayout.CENTER);
            pnlContenido.revalidate();
            pnlContenido.repaint();
            actualizarHeader("Gestión de Miembros");
        });

        btnObras.addActionListener(e -> {
            pnlContenido.removeAll();
            pnlContenido.add(new ListaObrasVista(), BorderLayout.CENTER);
            pnlContenido.revalidate();
            pnlContenido.repaint();
            actualizarHeader("Catálogo de Obras");
        });

        btnProducciones.addActionListener(e -> {
            pnlContenido.removeAll();
            pnlContenido.add(new ProduccionVista(), BorderLayout.CENTER); 
            pnlContenido.revalidate();
            pnlContenido.repaint();
            actualizarHeader("Producciones (Vista Multitabla)");  
        });

        btnReportes.addActionListener(e -> {
            pnlContenido.removeAll();
            pnlContenido.add(new EstadosFinancierosVista(), BorderLayout.CENTER);
            pnlContenido.revalidate();
            pnlContenido.repaint();
            actualizarHeader("Estados Financieros (Tesorero)");
        });

        btnConfigurar.addActionListener(e -> {
            pnlContenido.removeAll();
            pnlContenido.add(new ReporteCuotasVista(), BorderLayout.CENTER);
            pnlContenido.revalidate();
            pnlContenido.repaint();
            actualizarHeader("Reporte de Cuotas Pagadas");
        });

        btnSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Cerrar sesión y salir del sistema?", 
                "Confirmar Salida", JOptionPane.YES_NO_OPTION);
            
            if (opcion == JOptionPane.YES_OPTION) {
                dispose();
                new LoginVista().setVisible(true);
            }//if
        });
        
    }//agregarEventos

   private void actualizarHeader(String modulo) {
        lblBienvenida.setText("📂 Módulo: " + modulo + " | " + 
            new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
    }//ActualizarHeadre

    static class MensajePlaceholder extends JPanel {
        public MensajePlaceholder(String titulo, String mensaje) {
            setLayout(new GridBagLayout());
            setBackground(Color.WHITE);
            setBorder(new EmptyBorder(50, 50, 50, 50));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(10, 0, 10, 0);

            JLabel lblIcono = new JLabel(titulo.substring(0, 2));
            lblIcono.setFont(new Font("Segoe UI Emoji", Font.BOLD, 80));
            add(lblIcono, gbc);

            gbc.gridy = 1;
            JLabel lblTitulo = new JLabel(titulo);
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
            lblTitulo.setForeground(new Color(44, 62, 80));
            add(lblTitulo, gbc);

            gbc.gridy = 2;
            JLabel lblMsg = new JLabel("<html><center>" + mensaje + "<br><br>Módulo en desarrollo</center></html>");
            lblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lblMsg.setForeground(new Color(127, 140, 141));
            add(lblMsg, gbc);
        }//placeHolder
        
    }//public class

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {}
            new MenuPrincipal().setVisible(true);
        });
        
    }//void main    
    
}//MenuPrincipal
