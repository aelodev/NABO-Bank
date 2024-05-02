package Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        setLocationRelativeTo(null);
        setSize(400, 300);
        this.setVisible(true);
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
                    }else{
                        JOptionPane.showMessageDialog(null, "Usuario incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new VentanaPrincipal();
    }
}
