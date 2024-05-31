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
    ImageIcon icon = new ImageIcon(getClass().getResource("../media/logo.png"));

    public VentanaPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setSize(500, 400);
        setTitle("Login - NABO Bank");
        setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        setVisible(true);
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new javax.swing.plaf.ColorUIResource(128, 139, 155));
        UI.put("Panel.background", new javax.swing.plaf.ColorUIResource(128, 139, 155));

        conexion = new ConexionMySQL("root", "", "banco");
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conexion.comprobarConexion();
                    conexion.conectar();
                    ctrlUser = new ControladorUsuario(conexion);
                    if (ctrlUser.comprobarUsuario(textField1.getText(), new String(password.getPassword()))) {
                        JOptionPane.showMessageDialog(null, "Succesful login", "Success", JOptionPane.INFORMATION_MESSAGE);
                        VentanaUsuario ventanaUsuario;
                        ventanaUsuario = new VentanaUsuario(ctrlUser.obtenerUsuario(textField1.getText()), conexion, ctrlUser);
                        dispose();
                    } else if (textField1.getText().equals("") || new String(password.getPassword()).equals("")) {
                        JOptionPane.showMessageDialog(null, "Empty fields", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect user", "Error", JOptionPane.ERROR_MESSAGE);
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
