package Modelo;

public class Historial {
    public Object getId;
    private String accion;
    private String usuario;
    private double cantidad;
    private String fecha;
    private int usuario_id;
    private int id;

    public Historial(String accion, String usuario, double cantidad, String fecha){
        this.accion = accion;
        this.usuario = usuario;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccion(){
        return accion;
    }

    public String getUsuario(){
        return usuario;
    }

    public double getCantidad(){
        return cantidad;
    }

    public String getFecha(){
        return fecha;
    }

    public void setAccion(String accion){
        this.accion = accion;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public void setCantidad(double cantidad){
        this.cantidad = cantidad;
    }

    public void setFecha(String fecha){
        this.fecha = fecha;
    }
}
