package Vista;

import Controlador.ConexionMySQL;
import Controlador.ControladorUsuario;
import Modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.sql.*;

public class VentanaPay extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton payButton;
    private JScrollPane scroll1;
    private JButton exitButton;
    ResultSet rset;
    ControladorUsuario ctrlUser;
    ImageIcon icon = new ImageIcon(getClass().getResource("../media/logo.png"));

    public VentanaPay(Usuario usuario, ConexionMySQL conexion) throws SQLException {
        setContentPane(panel1);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("Pay - NABO Bank");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
        scroll1.setBackground(new Color(26,31,44, 3));
        scroll1.getViewport().setOpaque(false);
        scroll1.setForeground(new Color(26,31,44, 3));
        scroll1.setBorder(BorderFactory.createEmptyBorder());

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
        String [] cabecera = {"User", "Name", "Last Name"};
        modelo.setColumnIdentifiers(cabecera);
        table1.setModel(modelo);

        conexion.conectar();
        List<Usuario> usuarios = ctrlUser.obtenerTodosUsuarios();
        for (Usuario u : usuarios) {
            Object[] fila = {u.getNombre(), u.getApellidos(), u.getUsuario()};
            modelo.addRow(fila);
        }

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = JOptionPane.showInputDialog(null, "Introduce the user to pay", "Payment", JOptionPane.QUESTION_MESSAGE);
                if (usuario == null) {
                    JOptionPane.showMessageDialog(null, "User not found", "Payment", JOptionPane.ERROR_MESSAGE);
                } else if (user.equals(usuario.getUsuario())) {
                    JOptionPane.showMessageDialog(null, "You can't pay yourself", "Payment", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    try {
                        if (ctrlUser.obtenerUsuario(user) == null) {
                            JOptionPane.showMessageDialog(null, "User not found", "Payment", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (usuario.getSaldo() <= 0 ) {
                            JOptionPane.showMessageDialog(null, "You have no current balance", "Payment", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            String number = JOptionPane.showInputDialog(null, "Introduce the amount to pay", "Payment", JOptionPane.QUESTION_MESSAGE);
                            int num = Integer.parseInt(number);
                            ctrlUser.pagar(usuario.getUsuario(), user, num);
                            JOptionPane.showMessageDialog(null, "Payment made succesfully", "Payment", JOptionPane.INFORMATION_MESSAGE);
                            modelo.setRowCount(0);
                            List<Usuario> usuarios = ctrlUser.obtenerTodosUsuarios();
                            for (Usuario u : usuarios) {
                                Object[] fila = {u.getNombre(), u.getApellidos(), u.getUsuario()};
                                modelo.addRow(fila);
                            }

                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
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
