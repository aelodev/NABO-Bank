package Controlador;
import Modelo.Usuario;
import java.sql.*;


public class ControladorUsuario {
    private ConexionMySQL conexion;
    Connection connection = null;

    public ControladorUsuario(ConexionMySQL conexion){
        this.conexion = conexion;
        connection = conexion.connection;
    }

    public boolean comprobarUsuario(String usuario, String password) throws SQLException{
        String consulta = "SELECT * FROM banco WHERE usuario = '" + usuario + "' AND password = '" + password + "'";
        Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery(consulta);
        return rset.next();
    }

    public Usuario obtenerUsuario(String usuario) throws SQLException{
        String consulta = "SELECT * FROM banco WHERE usuario = '" + usuario + "'";
        Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery(consulta);
        if(rset.next()){
            return new Usuario(rset.getString("nombre"), rset.getString("apellidos"), rset.getString("usuario"), rset.getString("password"), rset.getFloat("saldo"));
        }
        return null;
    }

    public void cerrarConexion() throws SQLException{
        conexion.desconectar();
    }

}
