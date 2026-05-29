/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal_administracionbd.vista;

import com.mycompany.proyectofinal_administracionbd.dao.MiembroDAO;
import com.mycompany.proyectofinal_administracionbd.dao.MiembroDAOImpl;
import com.mycompany.proyectofinal_administracionbd.modelo.Miembro;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author banue
 */

public class ABCCVistaMiembros extends JPanel{
    
    private final MiembroDAO miembroDAO;
    private final DefaultTableModel modeloTabla;
    private final JTable tblMiembros;
    
    private JTextField txtId, txtNombre, txtApellido, txtEmail, txtTelefono;
    private JTextField txtCalle, txtNumeroExt, txtColonia, txtCiudad, txtEstado, txtCP;
    private JCheckBox chkCuotaPagada;
    private JComboBox<String> cmbAño;   
    
    public ABCCVistaMiembros() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        miembroDAO = new MiembroDAOImpl();
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Apellido", "Email", "Teléfono", "Cuota", "Año"}, 0);
        tblMiembros = new JTable(modeloTabla);
        
        construirInterfaz();
        configurarTAB(); 
        cargarDatos();
    }//abccVistaM
    
      private void construirInterfaz() {
        JPanel pnlTabla = new JPanel(new BorderLayout());
        pnlTabla.setBackground(Color.WHITE);
        pnlTabla.setBorder(new TitledBorder(
            new LineBorder(new Color(41, 128, 185), 2), 
            "Lista de Miembros", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 13), new Color(41, 128, 185)));

        tblMiembros.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblMiembros.setRowHeight(28);
        tblMiembros.setSelectionBackground(new Color(41, 128, 185));
        tblMiembros.setSelectionForeground(Color.WHITE);
        tblMiembros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblMiembros.getTableHeader().setBackground(new Color(52, 73, 94));
        tblMiembros.getTableHeader().setForeground(Color.WHITE);
        
        pnlTabla.add(new JScrollPane(tblMiembros), BorderLayout.CENTER);

        // === altas, bajas, cambios ===
        JPanel pnlForm = new JPanel();
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setPreferredSize(new Dimension(320, 600));
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBorder(new TitledBorder(
            new LineBorder(new Color(46, 204, 113), 2), 
            "Gestión de Miembro", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 13), new Color(46, 204, 113)));

        // crear campos
        txtId = crearCampo("ID (Auto)", false);
        txtNombre = crearCampo("Nombre *", true);
        txtApellido = crearCampo("Apellido *", true);
        txtEmail = crearCampo("Email *", true);
        txtTelefono = crearCampo("Teléfono (10 dígitos)", true);
        txtCalle = crearCampo("Calle *", true);
        txtNumeroExt = crearCampo("Número Ext. *", true);
        txtColonia = crearCampo("Colonia *", true);
        txtCiudad = crearCampo("Ciudad *", true);
        txtEstado = crearCampo("Estado *", true);
        txtCP = crearCampo("C.P. *", true);

        // validación
        txtTelefono.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) e.consume();
            }//public void
        });
        txtCP.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) e.consume();
            }//public void
        });

        chkCuotaPagada = new JCheckBox("Cuota pagada ($50/año)");
        chkCuotaPagada.setFont(new Font("Segoe UI", Font.BOLD, 12));
        chkCuotaPagada.setBackground(Color.WHITE);

        cmbAño = new JComboBox<>(new String[]{"2024", "2025", "2026"});
        cmbAño.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbAño.setMaximumSize(new Dimension(280, 30));

        JPanel pnlBtn = new JPanel(new GridLayout(4, 1, 8, 8));
        pnlBtn.setBackground(Color.WHITE);
        pnlBtn.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        JButton btnGuardar = crearBoton("💾 Guardar / Actualizar", new Color(46, 204, 113));
        JButton btnEliminar = crearBoton("🗑️ Dar de Baja", new Color(231, 76, 60));
        JButton btnLimpiar = crearBoton("🧹 Limpiar Formulario", new Color(149, 165, 166));
        JButton btnVolver = crearBoton("⬅️ Volver al Menú", new Color(52, 152, 219));

        pnlBtn.add(btnGuardar); pnlBtn.add(btnEliminar); 
        pnlBtn.add(btnLimpiar); pnlBtn.add(btnVolver);

        pnlForm.add(Box.createVerticalStrut(5));
        pnlForm.add(txtId); pnlForm.add(txtNombre); pnlForm.add(txtApellido);
        pnlForm.add(txtEmail); pnlForm.add(txtTelefono);
        pnlForm.add(Box.createVerticalStrut(10));
        pnlForm.add(new JLabel("📍 Dirección Completa"));
        pnlForm.add(txtCalle); pnlForm.add(txtNumeroExt); 
        pnlForm.add(txtColonia); pnlForm.add(txtCiudad); 
        pnlForm.add(txtEstado); pnlForm.add(txtCP);
        pnlForm.add(Box.createVerticalStrut(10));
        pnlForm.add(chkCuotaPagada); pnlForm.add(cmbAño);
        pnlForm.add(Box.createVerticalStrut(15));
        pnlForm.add(pnlBtn); pnlForm.add(Box.createVerticalGlue());

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlTabla, pnlForm);
        split.setDividerLocation(650);
        split.setResizeWeight(0.7);
        split.setContinuousLayout(true);
        add(split, BorderLayout.CENTER);

        //eventos de botones
        btnGuardar.addActionListener(e -> guardarOActualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVolver.addActionListener(e -> {
            if (getParent() instanceof JFrame f) { 
                f.dispose(); 
                new MenuPrincipal().setVisible(true); 
            }//if
        });
        
        // doble click en tabla para carga datos
        tblMiembros.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
                if (e.getClickCount() == 2) cargarFilaSeleccionada(); 
            }//public void
        });
    }//construirInterfaz

    // método extra para creae campo
    private JTextField crearCampo(String placeholder, boolean editable) {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1), 
            new EmptyBorder(6, 8, 6, 8)));
        txt.setMaximumSize(new Dimension(280, 32));
        txt.setEditable(editable);
        txt.putClientProperty("JTextField.placeholderText", placeholder);
        return txt;
    }//crearCampo

    // método extra para crear botones 
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(color); 
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(280, 35));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(color.brighter()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(color); }
        });
        return btn;
    }//crearBron

    private void configurarTAB() {
        // El orden de add() al contenedor define el orden de TAB
        // Ya está configurado en construirInterfaz()
    }//configuracionTab

    // cargar datos del DAO a la tabla
    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);
            for (Miembro m : miembroDAO.listarTodos()) {
                modeloTabla.addRow(new Object[]{
                    m.getIdMiembro(), m.getNombre(), m.getPrimerApellido(),
                    m.getEmail(), m.getTelefono(),
                    m.isCuotaPagada() ? "✅" : "❌", m.getAnoCuota()
                });
            }//Fir
            
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(this, "Error al cargar: " + ex.getMessage()); 
        }//Catch
    }//CargarDatos

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty() || 
            txtEmail.getText().trim().isEmpty() || txtCalle.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Los campos marcados con * son obligatorios.", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }//if
        
        if (!txtEmail.getText().trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Formato de email inválido.", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }//if
        
        if (txtTelefono.getText().trim().length() != 10) {
            JOptionPane.showMessageDialog(this, 
                "El teléfono debe tener 10 dígitos.", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }//if
        
        return true;
    }//ValidarCampos

    //guardar o actualizar
    private void guardarOActualizar() {
        if (!validarCampos()) return;
        try {
            Miembro m = new Miembro();
            if (!txtId.getText().trim().isEmpty()) 
                m.setIdMiembro(Integer.parseInt(txtId.getText().trim()));
            
            m.setNombre(txtNombre.getText().trim()); 
            m.setPrimerApellido(txtApellido.getText().trim());
            m.setEmail(txtEmail.getText().trim()); 
            m.setTelefono(txtTelefono.getText().trim());
            m.setCalle(txtCalle.getText().trim()); 
            m.setNumeroExt(txtNumeroExt.getText().trim());
            m.setColonia(txtColonia.getText().trim()); 
            m.setCiudad(txtCiudad.getText().trim());
            m.setEstado(txtEstado.getText().trim()); 
            m.setCodigoPostal(txtCP.getText().trim());
            m.setPais("México"); // Fijo según indicación
            m.setCuotaPagada(chkCuotaPagada.isSelected());
            m.setAnoCuota(Integer.parseInt(cmbAño.getSelectedItem().toString()));

            boolean exito = (m.getIdMiembro() > 0) ? 
                miembroDAO.actualizar(m) : miembroDAO.guardar(m);
            
            if (exito) { 
                JOptionPane.showMessageDialog(this, "Operación realizada correctamente."); 
                limpiarFormulario(); 
                cargarDatos(); 
            }//if
            
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(this, "❌ " + ex.getMessage()); 
        }//Catch
        
    }//guardarActualizar

    //eliminar
    private void eliminar() {
        if (txtId.getText().trim().isEmpty()) { 
            JOptionPane.showMessageDialog(this, "Selecciona un miembro de la tabla."); 
            return; 
        }//if
        
        if (JOptionPane.showConfirmDialog(this, 
                "¿Eliminar miembro ID " + txtId.getText() + "?", 
                "Confirmar Baja", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (miembroDAO.eliminar(Integer.parseInt(txtId.getText().trim()))) {
                    JOptionPane.showMessageDialog(this, "✅ Baja registrada."); 
                    limpiarFormulario(); 
                    cargarDatos();
                }//if
                
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "❌ " + ex.getMessage()); 
            }//Catch
            
        }//if
        
    }//eliminar

    // cargar los datos de la fila seleccionada
    private void cargarFilaSeleccionada() {
        int row = tblMiembros.getSelectedRow();
        if (row >= 0) {
            txtId.setText(tblMiembros.getValueAt(row, 0).toString());
            txtNombre.setText(tblMiembros.getValueAt(row, 1).toString());
            txtApellido.setText(tblMiembros.getValueAt(row, 2).toString());
            txtEmail.setText(tblMiembros.getValueAt(row, 3).toString());
            txtTelefono.setText(tblMiembros.getValueAt(row, 4).toString());
        }//if
    }//CargarFilaSelec

    //limpiar
    private void limpiarFormulario() {
        txtId.setText(""); txtNombre.setText(""); txtApellido.setText(""); 
        txtEmail.setText(""); txtTelefono.setText("");
        txtCalle.setText(""); txtNumeroExt.setText(""); txtColonia.setText(""); 
        txtCiudad.setText(""); txtEstado.setText(""); txtCP.setText("");
        chkCuotaPagada.setSelected(false); cmbAño.setSelectedIndex(0);
        txtNombre.requestFocus();
    }//limpiarForm
 
}//ABCCMiembros
