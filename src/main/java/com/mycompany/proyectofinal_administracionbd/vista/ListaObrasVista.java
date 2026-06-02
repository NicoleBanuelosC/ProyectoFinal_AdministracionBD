/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.vista;

import com.mycompany.proyectofinal_administracionbd.dao.ObraDAO;
import com.mycompany.proyectofinal_administracionbd.dao.ObraDAOImpl;
import com.mycompany.proyectofinal_administracionbd.modelo.Obra;
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

public class ListaObrasVista extends JPanel {

    private final ObraDAO obraDAO;
    private final DefaultTableModel modeloTabla;
    private final JTable tblObras;
    
    private JTextField txtId, txtTitulo, txtAutor, txtActos;
    private JComboBox<String> cmbTipo;
    private JTextField txtBusqueda;

    public ListaObrasVista() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.BLUE);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        obraDAO = new ObraDAOImpl();
        
        String[] columnas = {"ID", "Título", "Autor", "Tipo", "Actos"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblObras = new JTable(modeloTabla);
        
        construirInterfaz();
        cargarDatos();
    }//ListaObrasVista

    private void construirInterfaz() {
        JPanel pnlBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlBusqueda.setBackground(new Color(240, 248, 255));
        pnlBusqueda.setBorder(new TitledBorder(
            new LineBorder(new Color(41, 128, 185), 2),
            "🔍 Buscar Obra",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13), new Color(41, 128, 185)));
        
        txtBusqueda = new JTextField(20);
        txtBusqueda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBuscar.setBackground(new Color(52, 152, 219));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> buscarObra());
        
        JButton btnVerTodos = new JButton("Ver Todas");
        btnVerTodos.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVerTodos.setBackground(new Color(149, 165, 166));
        btnVerTodos.setForeground(Color.WHITE);
        btnVerTodos.setFocusPainted(false);
        btnVerTodos.addActionListener(e -> { txtBusqueda.setText(""); cargarDatos(); });
        
        pnlBusqueda.add(new JLabel("Título:"));
        pnlBusqueda.add(txtBusqueda);
        pnlBusqueda.add(btnBuscar);
        pnlBusqueda.add(btnVerTodos);
        add(pnlBusqueda, BorderLayout.NORTH);

        JPanel pnlTabla = new JPanel(new BorderLayout());
        pnlTabla.setBackground(Color.WHITE);
        pnlTabla.setBorder(new TitledBorder(
            new LineBorder(new Color(41, 128, 185), 2), 
            "📋 Obras Registradas", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 13), new Color(41, 128, 185)));

        tblObras.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblObras.setRowHeight(28);
        tblObras.setSelectionBackground(new Color(41, 128, 185));
        tblObras.setSelectionForeground(Color.WHITE);
        tblObras.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblObras.getTableHeader().setBackground(new Color(44, 62, 80));
        tblObras.getTableHeader().setForeground(Color.WHITE);
        
        pnlTabla.add(new JScrollPane(tblObras), BorderLayout.CENTER);
        add(pnlTabla, BorderLayout.CENTER);

        JPanel pnlForm = new JPanel();
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setPreferredSize(new Dimension(320, 500));
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBorder(new TitledBorder(
            new LineBorder(new Color(46, 204, 113), 2), 
            "✏️ Gestión de Obra", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 13), new Color(46, 204, 113)));

        pnlForm.add(crearLabel("ID (Auto)"));
        txtId = crearCampo(false); pnlForm.add(txtId);

        pnlForm.add(crearLabel("Título *"));
        txtTitulo = crearCampo(true); pnlForm.add(txtTitulo);

        pnlForm.add(crearLabel("Autor *"));
        txtAutor = crearCampo(true); pnlForm.add(txtAutor);

        pnlForm.add(crearLabel("Tipo de Obra *"));
        cmbTipo = new JComboBox<>(new String[]{"Drama", "Comedia", "Musical", "Tragedia", "Opera"});
        cmbTipo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbTipo.setMaximumSize(new Dimension(280, 32));
        pnlForm.add(cmbTipo);

        pnlForm.add(crearLabel("Número de Actos *"));
        txtActos = crearCampo(true);
        txtActos.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }//if
            }//public void
        });
        pnlForm.add(txtActos);

        JPanel pnlBtn = new JPanel(new GridLayout(4, 1, 10, 10));
        pnlBtn.setBackground(Color.WHITE);
        pnlBtn.setBorder(new EmptyBorder(20, 0, 0, 0));

        Color verde = new Color(168, 230, 207);
        Color rojo = new Color(255, 190, 195);
        Color gris = new Color(235, 235, 240);
        Color azul = new Color(195, 225, 255);
        Color texto = new Color(45, 55, 65);

        JButton btnGuardar = crearBoton("Guardar / Actualizar", verde, texto);
        JButton btnEliminar = crearBoton("Dar de Baja", rojo, texto);
        JButton btnLimpiar  = crearBoton("Limpiar", gris, texto);
        JButton btnVolver   = crearBoton("Volver al Menú", azul, texto);

        pnlBtn.add(btnGuardar); pnlBtn.add(btnEliminar);
        pnlBtn.add(btnLimpiar); pnlBtn.add(btnVolver);

        pnlForm.add(Box.createVerticalGlue());
        pnlForm.add(pnlBtn);
        add(pnlForm, BorderLayout.EAST);

        btnGuardar.addActionListener(e -> guardarOActualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnVolver.addActionListener(e -> {
            if (getParent() instanceof JFrame f) {
                f.dispose();
                new MenuPrincipal().setVisible(true);
            }//if
        });

        tblObras.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) cargarSeleccionada();
            }//public void
        });
    }//construirInterfaz

    // Métodos extra - auxiliares
    private JLabel crearLabel(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(52, 73, 94));
        l.setMaximumSize(new Dimension(280, 20));
        return l;
    }//CrearLabel
    
    private JTextField crearCampo(boolean editable) {
        JTextField t = new JTextField();
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1), new EmptyBorder(6, 8, 6, 8)));
        t.setMaximumSize(new Dimension(280, 32));
        t.setEditable(editable);
        return t;
    }//CrearCampo

    private JButton crearBoton(String t, Color cBase, Color cTexto) {
        JButton b = new JButton(t);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBackground(cBase); b.setForeground(cTexto);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(280, 38));
        b.setOpaque(true);
        return b;
    }//crearBoton

    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);
            for (Obra o : obraDAO.listarTodos()) {
                modeloTabla.addRow(new Object[]{
                    o.getIdObra(), o.getTitulo(), o.getAutor(), o.getTipo(), o.getNumeroActos()
                });
            }//for
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }//Catch
        
    }//CargarDatos

    private void buscarObra() {
        String titulo = txtBusqueda.getText().trim();
        if (titulo.isEmpty()) { cargarDatos(); return; }
        try {
            modeloTabla.setRowCount(0);
            for (Obra o : obraDAO.buscarPorTitulo(titulo)) {
                modeloTabla.addRow(new Object[]{
                    o.getIdObra(), o.getTitulo(), o.getAutor(), o.getTipo(), o.getNumeroActos()
                });
            }//Fir
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + ex.getMessage());
        }//Cacth
        
    }//busarObra

    private void cargarSeleccionada() {
        int row = tblObras.getSelectedRow();
        if (row >= 0) {
            int id = (int) modeloTabla.getValueAt(row, 0);
            try {
                Obra o = obraDAO.obtenerPorId(id);
                if (o != null) {
                    txtId.setText(String.valueOf(o.getIdObra()));
                    txtTitulo.setText(o.getTitulo());
                    txtAutor.setText(o.getAutor());
                    cmbTipo.setSelectedItem(o.getTipo());
                    txtActos.setText(String.valueOf(o.getNumeroActos()));
                }//if
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }//catch
            
        }//if
        
    }//cargarSeleccionada

    private void guardarOActualizar() {
        if (txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título y Autor son obligatorios"); return;
        }
        try {
            Obra o = new Obra();
            if (!txtId.getText().isEmpty()) o.setIdObra(Integer.parseInt(txtId.getText()));
            o.setTitulo(txtTitulo.getText().trim());
            o.setAutor(txtAutor.getText().trim());
            o.setTipo((String) cmbTipo.getSelectedItem());
            o.setNumeroActos(Integer.parseInt(txtActos.getText().trim()));

            boolean exito = (o.getIdObra() > 0) ? obraDAO.actualizar(o) : obraDAO.guardar(o);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación exitosa");
                limpiar(); cargarDatos();
            }//if
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }//Cacth
        
    }//guardarOActualizada

    private void eliminar() {
        if (txtId.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Selecciona una obra"); return; }
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar esta obra?") == 0) {
            try {
                if (obraDAO.eliminar(Integer.parseInt(txtId.getText()))) {
                    JOptionPane.showMessageDialog(this, "Eliminada");
                    limpiar(); cargarDatos();
                }//if
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }//Cacth
            
        }//if
        
    }//eliminar

    private void limpiar() {
        txtId.setText(""); txtTitulo.setText(""); txtAutor.setText("");
        txtActos.setText(""); cmbTipo.setSelectedIndex(0);
    }//limpiar    
    
}//ListaObrasVosta
