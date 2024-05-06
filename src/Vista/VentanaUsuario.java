package Vista;

import Controlador.ConexionMySQL;
import Controlador.ControladorUsuario;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class VentanaUsuario extends JFrame{
    private JLabel usuarioField;
    private JPanel mainPanel;
    private JLabel saldoLabel;
    private JButton chargeButton;
    private JButton payButton;
    private JButton logOutButton;
    UIManager UI;

    public VentanaUsuario(Usuario usuario, ConexionMySQL conexion, ControladorUsuario ctrlUser){
        setContentPane(mainPanel);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        UI.put("OptionPane.background", new javax.swing.plaf.ColorUIResource(128, 139, 155));
        UI.put("Panel.background", new javax.swing.plaf.ColorUIResource(128, 139, 155));
        usuarioField.setText(usuario.getNombre() + " " + usuario.getApellidos());
        saldoLabel.setText("" + usuario.getSaldo());
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    VentanaPay ventanaPay = new VentanaPay(usuario, conexion);
                    // Check if the window is closed
                    ventanaPay.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            usuario.setSaldo(ctrlUser.obtenerSaldo(usuario.getUsuario()));
                            saldoLabel.setText("" + usuario.getSaldo());
                        }
                    });
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
