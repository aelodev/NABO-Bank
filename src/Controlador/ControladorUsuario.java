package Controlador;
import Modelo.Usuario;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;


public class ControladorUsuario {
    private ConexionMySQL conexion;
    Connection connection = null;

    public ControladorUsuario(ConexionMySQL conexion) {
        this.conexion = conexion;
        connection = conexion.connection;
    }

    public boolean comprobarUsuario(String usuario, String password) throws SQLException {
        String consulta = "SELECT * FROM banco WHERE usuario = '" + usuario + "' AND password = '" + password + "'";
        Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery(consulta);
        return rset.next();
    }

    public Usuario obtenerUsuario(String usuario) throws SQLException {
        String consulta = "SELECT * FROM banco WHERE usuario = '" + usuario + "'";
        Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery(consulta);
        if (rset.next()) {
            return new Usuario(rset.getString("nombre"), rset.getString("apellidos"), rset.getString("usuario"), rset.getString("password"), rset.getFloat("saldo"));
        }
        return null;
    }

    public void cerrarConexion() throws SQLException {
        conexion.desconectar();
    }

    public ArrayList<Usuario> obtenerTodosUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        String consulta = "SELECT * FROM banco";
        try{
        ResultSet rset = conexion.ejectuarSelect(consulta);
        while(rset.next()) {
            String nombre = rset.getString("nombre");
            String usuario = rset.getString("usuario");
            String apellido = rset.getString("apellidos");
            String password = rset.getString("password");
            float saldo = rset.getFloat("saldo");
            Usuario usuario1 = new Usuario(usuario, nombre, apellido, password, saldo);
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
            float saldo1 = u1.getSaldo();
            float saldo2 = u2.getSaldo();
            if (saldo1 >= num) {
                saldo1 -= num;
                saldo2 += num;
                String consulta1 = "UPDATE banco SET saldo = " + saldo1 + " WHERE usuario = '" + usuarioEnviador + "'";
                String consulta2 = "UPDATE banco SET saldo = " + saldo2 + " WHERE usuario = '" + usuarioReceptor + "'";
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(consulta1);
                stmt.executeUpdate(consulta2);
            }
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
}
