package Vista;

import Controlador.ConexionMySQL;
import Controlador.ControladorUsuario;
import Modelo.Usuario;
import java.io.*;

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
    private JButton historyButton;
    private JButton exportButton;
    UIManager UI;
    ImageIcon icon = new ImageIcon(getClass().getResource("../media/logo.png"));

    public VentanaUsuario(Usuario usuario, ConexionMySQL conexion, ControladorUsuario ctrlUser){
        setContentPane(mainPanel);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("General - NABO Bank");
        setIconImage(icon.getImage());          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        UI.put("OptionPane.background", new javax.swing.plaf.ColorUIResource(128, 139, 155));
        UI.put("Panel.background", new javax.swing.plaf.ColorUIResource(128, 139, 155));
        usuarioField.setText(usuario.getNombre() + " " + usuario.getApellidos());
        saldoLabel.setText("" + usuario.getSaldo());
        int id = usuario.getId();
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
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaHistorial ventanaHistorial = new VentanaHistorial(usuario, ctrlUser, conexion);
            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showSaveDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    ctrlUser.exportarHistorial(usuario, file.getAbsolutePath());
                    JOptionPane.showMessageDialog(null, "History exported succesfully", "Export history", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error exporting history", "Export history", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        chargeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaCharge ventanaCharge = new VentanaCharge(usuario, conexion, ctrlUser);
                // Check if the window is closed
                ventanaCharge.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        usuario.setSaldo(ctrlUser.obtenerSaldo(usuario.getUsuario()));
                        saldoLabel.setText("" + usuario.getSaldo());
                    }
                });
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
            }
        });
    }
}
