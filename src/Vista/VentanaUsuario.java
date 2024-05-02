package Vista;

import Modelo.Usuario;

import javax.swing.*;

public class VentanaUsuario extends JFrame{
    private JLabel usuarioField;
    private JPanel mainPanel;
    private JLabel saldoLabel;

    public VentanaUsuario(Usuario usuario){
        setContentPane(mainPanel);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        usuarioField.setText(usuario.getNombre() + " " + usuario.getApellidos());
        saldoLabel.setText("Saldo: " + usuario.getSaldo());
    }
}
