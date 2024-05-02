package Vista;

import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import Controlador.*;

public class VentanaPrincipal extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JButton entrarButton;
    private JPasswordField password;
    ConexionMySQL conexion;
    ControladorUsuario ctrlUser;

    public VentanaPrincipal(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        UIManager UI=new UIManager();
        UI.put("OptionPane.background", new javax.swing.plaf.ColorUIResource(128, 139, 155));
        UI.put("Panel.background", new javax.swing.plaf.ColorUIResource(128, 139, 155));

        conexion = new ConexionMySQL("root", "", "banco");
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conexion.conectar();
                    ctrlUser = new ControladorUsuario(conexion);
                    if(ctrlUser.comprobarUsuario(textField1.getText(), password.getText())){
                        JOptionPane.showMessageDialog(null, "Usuario correcto");

                        VentanaUsuario ventanaUsuario = new VentanaUsuario(ctrlUser.obtenerUsuario(textField1.getText()));
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "Usuario incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        getRootPane().setDefaultButton(entrarButton);
    }

    public static void main(String[] args) {
        new VentanaPrincipal();
    }
}
