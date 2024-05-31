package Controlador;
import Modelo.Historial;
import Modelo.Usuario;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class ControladorUsuario {
    private ConexionMySQL conexion;
    Connection connection = null;

    public ControladorUsuario(ConexionMySQL conexion) {
        this.conexion = conexion;
        connection = conexion.connection;
    }

    public boolean comprobarUsuario(String usuario, String password) throws SQLException {
        String consulta = "SELECT * FROM users WHERE usuario = '" + usuario + "' AND password = '" + password + "'";
        Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery(consulta);
        return rset.next();
    }

    public Usuario obtenerUsuario(String usuario) throws SQLException {
        String consulta = "SELECT * FROM users WHERE usuario = '" + usuario + "'";
        Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery(consulta);
        if (rset.next()) {
            return new Usuario(rset.getString("nombre"), rset.getString("apellidos"), rset.getString("usuario"), rset.getString("password"), rset.getFloat("saldo"), rset.getInt("id"));
        }
        return null;
    }

    public void cerrarConexion() throws SQLException {
        conexion.desconectar();
    }

    public ArrayList<Usuario> obtenerTodosUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        String consulta = "SELECT * FROM users";
        try {
            ResultSet rset = conexion.ejectuarSelect(consulta);
            while (rset.next()) {
                String nombre = rset.getString("nombre");
                String usuario = rset.getString("usuario");
                String apellido = rset.getString("apellidos");
                String password = rset.getString("password");
                float saldo = rset.getFloat("saldo");
                int id = rset.getInt("id");
                Usuario usuario1 = new Usuario(usuario, nombre, apellido, password, saldo, id);
                lista.add(usuario1);
            }
            rset.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public void pagar(String usuarioEnviador, String usuarioReceptor, int num) {
        try {
            Usuario u1 = obtenerUsuario(usuarioEnviador);
            Usuario u2 = obtenerUsuario(usuarioReceptor);
            if (u1 == null || u2 == null) {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        if (u1.getId() == 0 || u2.getId() == 0) {
            throw new RuntimeException("Usuario id no encontrado");
        }

            float saldo1 = u1.getSaldo();
            float saldo2 = u2.getSaldo();
            if (saldo1 >= num) {
                saldo1 -= num;
                saldo2 += num;
                String consulta1 = "UPDATE users SET saldo = " + saldo1 + " WHERE usuario = '" + usuarioEnviador + "'";
                String consulta2 = "UPDATE users SET saldo = " + saldo2 + " WHERE usuario = '" + usuarioReceptor + "'";
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(consulta1);
                stmt.executeUpdate(consulta2);
            }
            // Insertar registro en la tabla historial para el usuario que realiza el pago
            String sqlPaga = "INSERT INTO historial (accion, usuario, cantidad, usuario_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmtPaga = connection.prepareStatement(sqlPaga);
            pstmtPaga.setString(1, "Payment to " + usuarioReceptor);
            pstmtPaga.setString(2, usuarioEnviador);
            pstmtPaga.setDouble(3, -num);
            pstmtPaga.setInt(4, u1.getId()); // Asume que Usuario tiene un método getId() que devuelve el id del usuario
            pstmtPaga.executeUpdate();

            // Insertar registro en la tabla historial para el usuario que recibe el pago
            String sqlRecibe = "INSERT INTO historial (accion, usuario, cantidad, usuario_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmtRecibe = connection.prepareStatement(sqlRecibe);
            pstmtRecibe.setString(1, "Recibo de " + usuarioEnviador);
            pstmtRecibe.setString(2, usuarioReceptor);
            pstmtRecibe.setDouble(3, num);
            pstmtRecibe.setInt(4, u2.getId()); // Asume que Usuario tiene un método getId() que devuelve el id del usuario
            pstmtRecibe.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public float obtenerSaldo(String usuario) {
        try {
            Usuario u = obtenerUsuario(usuario);
            return u.getSaldo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Historial> obtenerHistorial(String usuario) {
        List<Historial> lista = new ArrayList<Historial>();
        String consulta = "SELECT h.* FROM historial h JOIN users b ON h.usuario_id = b.id WHERE b.usuario = '" + usuario + "'";
        try {
            ResultSet rset = conexion.ejectuarSelect(consulta);
            while (rset.next()) {
                String accion = rset.getString("accion");
                String usuario1 = rset.getString("usuario");
                double cantidad = rset.getDouble("cantidad");
                String fecha = rset.getString("fecha");
                Historial historial = new Historial(accion, usuario1, cantidad, fecha);
                lista.add(historial);
            }
            rset.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public void updateHistory(String usuario, String accion, double cantidad) {
        try {
            Usuario u = obtenerUsuario(usuario);
            String sql = "INSERT INTO historial (accion, usuario, cantidad, usuario_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, accion);
            pstmt.setString(2, usuario);
            pstmt.setDouble(3, cantidad);
            pstmt.setInt(4, u.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportarHistorial(Usuario usuario, String absolutePath) {

        List<Historial> historial = obtenerHistorial(usuario.getUsuario());
        var user = usuario.getUsuario();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(absolutePath + "/historial_" + user + ".txt"))) {
            writer.write("Current balance: " + usuario.getSaldo());
            writer.write("\n");
            for (Historial h : historial) {
                writer.write(h.getAccion() + ", " + h.getUsuario() + ", " + h.getCantidad() + ", " + h.getFecha());
                writer.newLine(); // to write each record on a new line
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSaldo(String usuario, int amount) {
        try {
            float saldo = obtenerSaldo(usuario);
            saldo += amount;
            String consulta = "UPDATE users SET saldo = " + saldo + " WHERE usuario = '" + usuario + "'";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(consulta);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

