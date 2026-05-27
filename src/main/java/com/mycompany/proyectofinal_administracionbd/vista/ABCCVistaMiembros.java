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
    
    private MiembroDAO miembroDAO;
    private DefaultTableModel modeloTabla;
    private JTable tblMiembros;
    
    private JTextField txtId, txtNombre, txtApellido, txtEmail, txtTelefono;
    private JCheckBox chkCuotaPagada;
    private JComboBox<String> cmbAñoCuota;

    public ABCCVistaMiembros() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        miembroDAO = new MiembroDAOImpl();
        construirInterfaz();
        cargarMiembros();
    }//Public ABCC
    
    
    
    
    
    
    
    
}//ABCCMiembros
