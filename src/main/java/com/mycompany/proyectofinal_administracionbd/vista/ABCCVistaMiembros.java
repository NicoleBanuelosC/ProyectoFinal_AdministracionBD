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
import java.util.List;


/**
 *
 * @author banue
 */

public class ABCCVistaMiembros extends JPanel {
    
    private final MiembroDAO miembroDAO;
    private final DefaultTableModel modeloTabla;
    private final JTable tblMiembros;
    
    private JTextField txtId, txtNombre, txtApellido, txtEmail, txtTelefono;
    private JTextField txtCalle, txtNumeroExt, txtColonia, txtCiudad, txtEstado, txtCP;
    private JCheckBox chkCuotaPagada;
    private JComboBox<String> cmbAño;
    
    private JTextField txtBusqueda;
    private JRadioButton radioId, radioNombre;
    private ButtonGroup grupoBusqueda;

    public ABCCVistaMiembros() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        miembroDAO = new MiembroDAOImpl();
        
        // encabezados tabla
        String[] columnas = {"ID", "Nombre", "Apellido", "Email", "Teléfono", "Cuota", "Año"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblMiembros = new JTable(modeloTabla);
        
        construirInterfaz();
        configurarTAB();
        cargarDatos();
    }//public

    private void construirInterfaz() {
        JPanel pnlBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlBusqueda.setBackground(new Color(240, 248, 255));
        pnlBusqueda.setBorder(new TitledBorder(
            new LineBorder(new Color(41, 128, 185), 2),
            "🔍 Buscar Miembro",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13), new Color(41, 128, 185)));
        
        JLabel lblBuscar = new JLabel("Buscar por:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        radioId = new JRadioButton("ID");
        radioId.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        radioId.setBackground(new Color(240, 248, 255));
        radioId.setSelected(true);
        
        radioNombre = new JRadioButton("Nombre");
        radioNombre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        radioNombre.setBackground(new Color(240, 248, 255));
        
        grupoBusqueda = new ButtonGroup();
        grupoBusqueda.add(radioId);
        grupoBusqueda.add(radioNombre);
        
        txtBusqueda = new JTextField(20);
        txtBusqueda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBusqueda.setPreferredSize(new Dimension(200, 28));
        
        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBuscar.setBackground(new Color(52, 152, 219));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> buscarMiembro());
        
        JButton btnLimpiarBusqueda = new JButton("🧹 Ver Todos");
        btnLimpiarBusqueda.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLimpiarBusqueda.setBackground(new Color(149, 165, 166));
        btnLimpiarBusqueda.setForeground(Color.WHITE);
        btnLimpiarBusqueda.setFocusPainted(false);
        btnLimpiarBusqueda.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiarBusqueda.addActionListener(e -> {
            txtBusqueda.setText("");
            cargarDatos();
        });
        
        pnlBusqueda.add(lblBuscar);
        pnlBusqueda.add(radioId);
        pnlBusqueda.add(radioNombre);
        pnlBusqueda.add(txtBusqueda);
        pnlBusqueda.add(btnBuscar);
        pnlBusqueda.add(btnLimpiarBusqueda);
        
        add(pnlBusqueda, BorderLayout.NORTH);

        JPanel pnlTabla = new JPanel(new BorderLayout());
        pnlTabla.setBackground(Color.WHITE);
        pnlTabla.setBorder(new TitledBorder(
            new LineBorder(new Color(41, 128, 185), 2), 
            "📋 Lista de Miembros", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 13), new Color(41, 128, 185)));

        tblMiembros.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblMiembros.setRowHeight(28);
        tblMiembros.setSelectionBackground(new Color(41, 128, 185));
        tblMiembros.setSelectionForeground(Color.WHITE);
        
        tblMiembros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblMiembros.getTableHeader().setBackground(new Color(44, 62, 80));
        tblMiembros.getTableHeader().setForeground(Color.WHITE);
        tblMiembros.getTableHeader().setOpaque(true);
        
        pnlTabla.add(new JScrollPane(tblMiembros), BorderLayout.CENTER);
        
        add(pnlTabla, BorderLayout.CENTER);

        JPanel pnlForm = new JPanel();
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setPreferredSize(new Dimension(340, 650));
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBorder(new TitledBorder(
            new LineBorder(new Color(46, 204, 113), 2), 
            "✏️ Gestión de Miembro", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 13), new Color(46, 204, 113)));

        // ID
        pnlForm.add(crearLabel("ID (Auto-generado)"));
        txtId = crearCampoTexto(false);
        pnlForm.add(txtId);

        // nombre - solo letras
        pnlForm.add(crearLabel("Nombre *"));
        txtNombre = crearCampoTexto(true);
        txtNombre.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c) && 
                    "áéíóúÁÉÍÓÚñÑ".indexOf(c) == -1 && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }//if
            }//void
        });
        pnlForm.add(txtNombre);

        // epellido - solo letras
        pnlForm.add(crearLabel("Apellido *"));
        txtApellido = crearCampoTexto(true);
        txtApellido.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c) && 
                    "áéíóúÁÉÍÓÚñÑ".indexOf(c) == -1 && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }//if
            }//void
        });
        pnlForm.add(txtApellido);

        // email  (con @email.com por predeterminado)
        pnlForm.add(crearLabel("Email *"));
        txtEmail = crearCampoTexto(true);
        txtEmail.setText("@email.com");
        txtEmail.select(0, 0);
        txtEmail.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtEmail.getText().equals("@email.com")) {
                    txtEmail.select(0, 0);
                }//if
            }//void
            
            public void focusLost(FocusEvent e) {
                if (txtEmail.getText().trim().isEmpty()) {
                    txtEmail.setText("@email.com");
                }//if
            }//void
        });
        pnlForm.add(txtEmail);

        pnlForm.add(crearLabel("Teléfono (10 dígitos)"));
        txtTelefono = crearCampoTexto(true);
        txtTelefono.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) e.consume();
            }//void
        });
        pnlForm.add(txtTelefono);

        pnlForm.add(Box.createVerticalStrut(10));
        JLabel lblDir = new JLabel("📍 Dirección Completa");
        lblDir.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDir.setForeground(new Color(52, 73, 94));
        pnlForm.add(lblDir);
        pnlForm.add(Box.createVerticalStrut(5));

        pnlForm.add(crearLabel("Calle *"));
        txtCalle = crearCampoTexto(true);
        pnlForm.add(txtCalle);

        pnlForm.add(crearLabel("Número Exterior *"));
        txtNumeroExt = crearCampoTexto(true);
        txtNumeroExt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) e.consume();
            }//void
        });
        pnlForm.add(txtNumeroExt);

        pnlForm.add(crearLabel("Colonia *"));
        txtColonia = crearCampoTexto(true);
        pnlForm.add(txtColonia);

        pnlForm.add(crearLabel("Ciudad *"));
        txtCiudad = crearCampoTexto(true);
        txtCiudad.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c) && 
                    c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }//if
            }//void
        });
        pnlForm.add(txtCiudad);

        pnlForm.add(crearLabel("Estado *"));
        txtEstado = crearCampoTexto(true);
        txtEstado.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c) && 
                    c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }//if
            }//void
        });
        pnlForm.add(txtEstado);

        pnlForm.add(crearLabel("C.P. * (5 dígitos)"));
        txtCP = crearCampoTexto(true);
        txtCP.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) e.consume();
            }//void
        });
        txtCP.setDocument(new javax.swing.text.PlainDocument() {
            public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) 
                    throws javax.swing.text.BadLocationException {
                if (getLength() + str.length() <= 5) {
                    super.insertString(offset, str, attr);
                }//if
            }//void
        });
        pnlForm.add(txtCP);

        pnlForm.add(Box.createVerticalStrut(10));
        chkCuotaPagada = new JCheckBox("✅ Cuota pagada ($50/año)");
        chkCuotaPagada.setFont(new Font("Segoe UI", Font.BOLD, 12));
        chkCuotaPagada.setBackground(Color.WHITE);
        pnlForm.add(chkCuotaPagada);

        pnlForm.add(crearLabel("Año de cuota:"));
        cmbAño = new JComboBox<>(new String[]{"2024", "2025", "2026"});
        cmbAño.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbAño.setMaximumSize(new Dimension(280, 30));
        pnlForm.add(cmbAño);

        JPanel pnlBtn = new JPanel(new GridLayout(4, 1, 10, 10));
        pnlBtn.setBackground(Color.WHITE);
        pnlBtn.setBorder(new EmptyBorder(20, 0, 0, 0));

        Color pastelVerde = new Color(168, 230, 207);
        Color pastelRojo  = new Color(255, 190, 195);
        Color pastelAzul  = new Color(195, 225, 255);
        Color pastelGris  = new Color(235, 235, 240);
        Color textoOscuro = new Color(45, 55, 65);

        JButton btnGuardar = crearBotonPastel("💾 Guardar / Actualizar", pastelVerde, textoOscuro);
        JButton btnEliminar = crearBotonPastel("🗑️ Dar de Baja", pastelRojo, textoOscuro);
        JButton btnLimpiar  = crearBotonPastel("🧹 Limpiar Formulario", pastelGris, textoOscuro);
        JButton btnVolver   = crearBotonPastel("⬅️ Volver al Menú", pastelAzul, textoOscuro);

        pnlBtn.add(btnGuardar); pnlBtn.add(btnEliminar);
        pnlBtn.add(btnLimpiar); pnlBtn.add(btnVolver);

        pnlForm.add(Box.createVerticalStrut(15));
        pnlForm.add(pnlBtn);
        pnlForm.add(Box.createVerticalGlue());

        add(pnlForm, BorderLayout.EAST);

        // eventos de botones
        btnGuardar.addActionListener(e -> guardarOActualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVolver.addActionListener(e -> {
            if (getParent() instanceof JFrame f) { 
                f.dispose(); 
                new MenuPrincipal().setVisible(true); 
            }//if
        });
        
        // doble click en tabla para cargar datos
        tblMiembros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { 
                cargarFilaSeleccionada();
            }//If
        });
    }//construirInterfaz

    //Métodos extra - auxiliar
    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(52, 73, 94));
        lbl.setMaximumSize(new Dimension(280, 20));
        return lbl;
    }//crearLabel

    private JTextField crearCampoTexto(boolean editable) {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1), 
            new EmptyBorder(6, 8, 6, 8)));
        txt.setMaximumSize(new Dimension(280, 32));
        txt.setEditable(editable);
        return txt;
    }//crear campo de texto

    private JButton crearBotonPastel(String texto, Color colorBase, Color colorTexto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(colorBase);
        btn.setForeground(colorTexto);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(280, 38));
        btn.setOpaque(true);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(colorBase.darker()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(colorBase); }
        });
        return btn;
    }//botones pastel

    private void configurarTAB() {}

    //cargar todos los miembtos
    private void cargarDatos() {
        try {
            List<Miembro> miembros = miembroDAO.listarTodos();
            modeloTabla.setRowCount(0);
            
            for (Miembro m : miembros) {
                modeloTabla.addRow(new Object[]{
                    m.getIdMiembro(), m.getNombre(), m.getPrimerApellido(),
                    m.getEmail(), m.getTelefono(),
                    m.isCuotaPagada() ? "✅ Sí" : "❌ No", m.getAnoCuota()
                });
            }//for
            
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(this, "Error al cargar: " + ex.getMessage()); 
        }//catch
        
    }//cargarDatos
    
    // buscar/consultar
    private void buscarMiembro() {
        String texto = txtBusqueda.getText().trim();
        
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa un valor para buscar", "Búsqueda", JOptionPane.WARNING_MESSAGE);
            return;
        }//if
        
        try {
            modeloTabla.setRowCount(0);
            
            if (radioId.isSelected()) {
                // buscar por ID
                try {
                    int id = Integer.parseInt(texto);
                    Miembro m = miembroDAO.obtenerPorId(id);
                    if (m != null) {
                        modeloTabla.addRow(new Object[]{
                            m.getIdMiembro(), m.getNombre(), m.getPrimerApellido(),
                            m.getEmail(), m.getTelefono(),
                            m.isCuotaPagada() ? "✅ Sí" : "❌ No", m.getAnoCuota()
                        });
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró un miembro con ID: " + id);
                    }//else
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                }//catch
                
            } else {
                // buscar por Nombre
                List<Miembro> resultados = miembroDAO.buscarPorNombre(texto);
                if (resultados.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron miembros con ese nombre");
                } else {
                    for (Miembro m : resultados) {
                        modeloTabla.addRow(new Object[]{
                            m.getIdMiembro(), m.getNombre(), m.getPrimerApellido(),
                            m.getEmail(), m.getTelefono(),
                            m.isCuotaPagada() ? "✅ Sí" : "❌ No", m.getAnoCuota()
                        });
                    }//for
                }//else
                
            }//else
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + ex.getMessage());
        }//catch
    }//buscarMiembro

    // Validaciones de DATOS
    private boolean validarCampos() {
        // 1 - campos obligatorios
        if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty() || 
            txtEmail.getText().trim().isEmpty() || txtCalle.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Los campos con * son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }//if
        
        // 2 - nombre (solo letras)
        if (!txtNombre.getText().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ El nombre solo debe contener letras.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }//if
        
        // 3 - apellido (solo letras)
        if (!txtApellido.getText().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ El apellido solo debe contener letras.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtApellido.requestFocus();
            return false;
        }//if
        
        // 4 - email (con @email.com)
        String email = txtEmail.getText().trim();
        if (!email.matches("^[\\w-\\.]+@email\\.com$")) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ El email debe tener el formato: usuario@email.com", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }//if
        
        // 5 - telefono
        if (txtTelefono.getText().trim().length() != 10) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ El teléfono debe tener exactamente 10 dígitos.", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }//if
        
        // 6 - numero ext
        if (!txtNumeroExt.getText().trim().matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ El número exterior solo debe contener números.", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            txtNumeroExt.requestFocus();
            return false;
        }//if
        
        // 7 - ciudad (letras y puntos)
        if (!txtCiudad.getText().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s\\.]+$")) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ La ciudad solo debe contener letras y puntos.", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            txtCiudad.requestFocus();
            return false;
        }//if
        
        // 8 - estado (letras y puntos)
        if (!txtEstado.getText().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s\\.]+$")) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ El estado solo debe contener letras y puntos.", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            txtEstado.requestFocus();
            return false;
        }
        
        // 9 - código postal (exactamente 5 dígitos)
        if (!txtCP.getText().trim().matches("^\\d{5}$")) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ El código postal debe tener exactamente 5 dígitos.", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            txtCP.requestFocus();
            return false;
        }//if
        
        return true;
    }//validarCampo

    // guardar o actualizar
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
            m.setPais("México");
            m.setCuotaPagada(chkCuotaPagada.isSelected());
            m.setAnoCuota(Integer.parseInt(cmbAño.getSelectedItem().toString()));

            boolean exito = (m.getIdMiembro() > 0) ? 
                miembroDAO.actualizar(m) : miembroDAO.guardar(m);
            
            if (exito) { 
                JOptionPane.showMessageDialog(this, "✅ Operación realizada correctamente."); 
                limpiarFormulario(); 
                cargarDatos(); 
            }//if
            
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(this, "❌ " + ex.getMessage()); 
        }//Catch
        
    }//guardarActualizar

    // eliminar
    private void eliminar() {
        if (txtId.getText().trim().isEmpty()) { 
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un miembro de la tabla (doble click)."); return; 
        }//if
        
        if (JOptionPane.showConfirmDialog(this, 
                "¿Eliminar miembro ID " + txtId.getText() + "?", 
                "Confirmar Baja", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (miembroDAO.eliminar(Integer.parseInt(txtId.getText().trim()))) {
                    JOptionPane.showMessageDialog(this, "✅ Baja registrada correctamente."); 
                    limpiarFormulario(); cargarDatos();
                }//if
                
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "❌ " + ex.getMessage()); 
            }//catch
            
        }//if
        
    }//eliminar

    //cargar fila con el doble clic
    private void cargarFilaSeleccionada() {
        int row = tblMiembros.getSelectedRow();
        if (row < 0) return; // nada seleccionado

        try {
            // obtener ID de la columna 0
            Object idObj = tblMiembros.getValueAt(row, 0);
            if (idObj == null) return;
            int id = Integer.parseInt(idObj.toString());

            // traer registro completo de la BD
            Miembro m = miembroDAO.obtenerPorId(id);
            if (m == null) return;

            // llenar formulario
            txtId.setText(String.valueOf(m.getIdMiembro()));
            txtNombre.setText(m.getNombre() != null ? m.getNombre() : "");
            txtApellido.setText(m.getPrimerApellido() != null ? m.getPrimerApellido() : "");
            txtEmail.setText(m.getEmail() != null ? m.getEmail() : "");
            txtTelefono.setText(m.getTelefono() != null ? m.getTelefono() : "");

            txtCalle.setText(m.getCalle() != null ? m.getCalle() : "");
            txtNumeroExt.setText(m.getNumeroExt() != null ? m.getNumeroExt() : "");
            txtColonia.setText(m.getColonia() != null ? m.getColonia() : "");
            txtCiudad.setText(m.getCiudad() != null ? m.getCiudad() : "");
            txtEstado.setText(m.getEstado() != null ? m.getEstado() : "");
            txtCP.setText(m.getCodigoPostal() != null ? m.getCodigoPostal() : "");

            chkCuotaPagada.setSelected(m.isCuotaPagada());
            cmbAño.setSelectedItem(String.valueOf(m.getAnoCuota()));

        } catch (Exception ex) {
            System.err.println("Error al cargar fila: " + ex.getMessage());
        }//catch
    }//CargarFilaSeleccionada

    // limpiar formulario
    private void limpiarFormulario() {
        txtId.setText(""); txtNombre.setText(""); txtApellido.setText(""); 
        txtEmail.setText("@email.com"); txtTelefono.setText("");
        txtCalle.setText(""); txtNumeroExt.setText(""); txtColonia.setText(""); 
        txtCiudad.setText(""); txtEstado.setText(""); txtCP.setText("");
        chkCuotaPagada.setSelected(false); cmbAño.setSelectedIndex(0);
        txtNombre.requestFocus();
    }//limpiarFormulario
    
}//ABCCVistaMiembro

