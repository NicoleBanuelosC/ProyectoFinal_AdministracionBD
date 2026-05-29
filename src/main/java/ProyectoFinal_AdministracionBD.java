/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.mycompany.proyectofinal_administracionbd.vista.LoginVista;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author banue
 */
public class ProyectoFinal_AdministracionBD {
    
    public static void main(String[] args) {
        // hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
            } catch (Exception e) {
                System.err.println("Warning: No se aplicó look and feel: " + e.getMessage());
            }//Catch
   
            // Abrir login
            new LoginVista().setVisible(true);
        });
        
    }//voidMain
    
}//PFAdminBD
