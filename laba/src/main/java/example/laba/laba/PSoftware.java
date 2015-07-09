package example.laba.laba;

/**
 * Created by Brenda on 08/07/2015.
 */
public class PSoftware extends Problema{
    private String bajoDesempeño;
    private String incompatibilidad;
    private String memoriaLlena;
    private String Reseteo;

    public PSoftware() {
    }

    public PSoftware(String nombre, String detalle, String solucion,
                     String reseteo, String memoriaLlena, String incompatibilidad,
                     String bajoDesempeño) {
        super(nombre, detalle, solucion);
        Reseteo = reseteo;
        this.memoriaLlena = memoriaLlena;
        this.incompatibilidad = incompatibilidad;
        this.bajoDesempeño = bajoDesempeño;
    }

    public String getBajoDesempeño() {
        return bajoDesempeño;
    }

    public void setBajoDesempeño(String bajoDesempeño) {
        this.bajoDesempeño = bajoDesempeño;
    }

    public String getIncompatibilidad() {
        return incompatibilidad;
    }

    public void setIncompatibilidad(String incompatibilidad) {
        this.incompatibilidad = incompatibilidad;
    }

    public String getMemoriaLlena() {
        return memoriaLlena;
    }

    public void setMemoriaLlena(String memoriaLlena) {
        this.memoriaLlena = memoriaLlena;
    }

    public String getReseteo() {
        return Reseteo;
    }

    public void setReseteo(String reseteo) {
        Reseteo = reseteo;
    }

    public String getSintomas(){
        String sintomas="";
        sintomas+="Bajo Desempeño: "+bajoDesempeño+"\n";
        sintomas+="Incompatibilidad: "+incompatibilidad+"\n";
        sintomas+="Memoria Llena: "+memoriaLlena+"\n";
        sintomas+="Reseteo: "+Reseteo;
        return sintomas;
    }
}
