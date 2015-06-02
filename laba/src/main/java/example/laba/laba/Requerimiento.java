package example.laba.laba;

/**
 * Created by Brenda on 31/05/2015.
 */
public class Requerimiento {
    private String cod_solic;
    private String fechaRegistro;
    private String descripcion;
    private String imagen;
    private String categoria;
    private String cod_usuario;
    private String cod_ubicacion;
    private String estado;
    private String fechaLimite;

    public Requerimiento(){

    }

    public Requerimiento(String cod_solic,String fechaRegistro,String descripcion,String imagen,
                      String categoria,String cod_usuario,String cod_ubicacion,String estado,String fechaLi){
        this.setCod_solic(cod_solic);
        this.setFechaRegistro(fechaRegistro);
        this.setDescripcion(descripcion);
        this.setImagen(imagen);
        this.setCategoria(categoria);
        this.setCod_usuario(cod_usuario);
        this.setCod_ubicacion(cod_ubicacion);
        this.setEstado(estado);
        this.setFechaLimite(fechaLi);
    }


    public String getCod_solic() {
        return cod_solic;
    }

    public void setCod_solic(String cod_solic) {
        this.cod_solic = cod_solic;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getCod_ubicacion() {
        return cod_ubicacion;
    }

    public void setCod_ubicacion(String cod_ubicacion) {
        this.cod_ubicacion = cod_ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}
