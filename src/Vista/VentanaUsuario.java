package Vista;

import Modelo.Usuario;

import javax.swing.*;

public class VentanaUsuario extends JFrame{
    private JLabel usuarioField;
    private JPanel mainPanel;
    private JLabel saldoLabel;
    private JButton chargeButton;
    private JButton payButton;
    UIManager UI;

    public VentanaUsuario(Usuario usuario){
        setContentPane(mainPanel);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        UI.put("OptionPane.background", new javax.swing.plaf.ColorUIResource(128, 139, 155));
        UI.put("Panel.background", new javax.swing.plaf.ColorUIResource(128, 139, 155));
        usuarioField.setText(usuario.getNombre() + " " + usuario.getApellidos());
        saldoLabel.setText("" + usuario.getSaldo());
    }
}
