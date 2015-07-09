package example.laba.laba;

/**
 * Created by Brenda on 08/07/2015.
 */
public class PHardware extends Problema{
    private String dispositivo;
    private String enciende;
    private String funcionamal;

    public PHardware() {
    }

    public PHardware(String dispositivo, String enciende, String funcionamal) {
        this.dispositivo = dispositivo;
        this.enciende = enciende;
        this.funcionamal = funcionamal;
    }

    public PHardware(String nombre, String detalle, String solucion, String dispositivo, String enciende, String funcionamal) {
        super(nombre, detalle, solucion);
        this.dispositivo = dispositivo;
        this.enciende = enciende;
        this.funcionamal = funcionamal;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getEnciende() {
        return enciende;
    }

    public void setEnciende(String enciende) {
        this.enciende = enciende;
    }

    public String getFuncionamal() {
        return funcionamal;
    }

    public void setFuncionamal(String funcionamal) {
        this.funcionamal = funcionamal;
    }

    public String getSintomas(){
        String sintomas="";
        sintomas+="Dispositivo Afectado"+dispositivo+"\n";
        sintomas+="Dispositivo Enciende: "+enciende+"\n";
        sintomas+="Dispositivo Funciona Mal: "+funcionamal;
        return sintomas;
    }
}
