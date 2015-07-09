package example.laba.laba;

/**
 * Created by Brenda on 08/07/2015.
 */
public class Problema {
    private String nombre;
    private String detalle;
    private String solucion;

    public Problema(){

    }

    public Problema(String nombre, String detalle, String solucion) {
        this.nombre = nombre;
        this.detalle = detalle;
        this.setSolucion(solucion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }


    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }
}
