package Vista;

import Controlador.ConexionMySQL;
import Controlador.ControladorUsuario;
import Modelo.Historial;
import Modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaHistorial extends JFrame{
    private JPanel panel1;
    private JTable table1;
    private JButton exitButton;
    ImageIcon icon = new ImageIcon(getClass().getResource("../media/logo.png"));

    public VentanaHistorial(Usuario usuario, ControladorUsuario ctrlUser, ConexionMySQL conexion){
        setContentPane(panel1);
        setTitle("History - NABO Bank");
        setIconImage(icon.getImage());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are uneditable
                return false;
            }
        };

        ctrlUser = new ControladorUsuario(conexion);
        table1.setModel(modelo);
        modelo.setRowCount(0);
        String [] cabecera = {"Last Action", "By", "Amount", "Date"};
        modelo.setColumnIdentifiers(cabecera);
        table1.setModel(modelo);

        List<Historial> historial = ctrlUser.obtenerHistorial(usuario.getUsuario());
        for (Historial h : historial) {
            Object[] fila = {h.getAccion(), h.getUsuario(), h.getCantidad(), h.getFecha()};
            modelo.addRow(fila);
        }


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
