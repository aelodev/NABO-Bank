package Vista;

import Controlador.ConexionMySQL;
import Controlador.ControladorUsuario;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCharge extends JFrame {
    private JPanel panel1;
    private JButton exitButton;
    private JTextField numberField;
    private JButton addButton;

    ImageIcon icon = new ImageIcon(getClass().getResource("../media/logo.png"));

    public VentanaCharge(Usuario usuario, ConexionMySQL conexion, ControladorUsuario ctrlUser){
        setContentPane(panel1);
        setTitle("Charge - NABO Bank");
        setIconImage(icon.getImage());
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = numberField.getText();
                if (number.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a number", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int amount = Integer.parseInt(number);
                        if (amount == 0){
                            JOptionPane.showMessageDialog(null, "Please enter a number different from 0", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (amount < 0){
                            JOptionPane.showMessageDialog(null, "Please enter a positive number", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "The amount has been added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            ctrlUser.addSaldo(usuario.getUsuario(), amount);
                            usuario.setSaldo(usuario.getSaldo() + amount);
                            ctrlUser.updateHistory(usuario.getUsuario(), "Charge", amount);
                            numberField.setText("0");
                        }
                    } catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
