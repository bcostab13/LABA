package example.laba.laba;

import com.orm.SugarRecord;

/**
 * Created by Brenda on 22/05/2015.
 */
public class UsuarioGeneral extends SugarRecord<UsuarioGeneral>{
    private String codigo;
    private String nombre;
    private String tipo;

    public UsuarioGeneral(){

    }

    public UsuarioGeneral(String codigo,String nombre,String tipo){
        this.setCodigo(codigo);
        this.setNombre(nombre);
        this.setTipo(tipo);
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
