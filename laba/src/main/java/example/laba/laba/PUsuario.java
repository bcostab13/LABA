package example.laba.laba;

/**
 * Created by Brenda on 08/07/2015.
 */
public class PUsuario extends Problema{
    private String iniciSesSist;
    private String iniciSesAplic;
    private String usoGenSis;
    private String usoApliDisp;
    private String descProc;
    private String descMet;
    private String descLoQueIng;
    private String appdisp;

    public PUsuario() {
    }

    public PUsuario(String nombre, String detalle, String solucion, String iniciSesSist,
                    String iniciSesAplic, String usoGenSis, String usoApliDisp, String descProc,
                    String descMet, String descLoQueIng,String appdisp) {
        super(nombre, detalle, solucion);
        this.iniciSesSist = iniciSesSist;
        this.iniciSesAplic = iniciSesAplic;
        this.usoGenSis = usoGenSis;
        this.usoApliDisp = usoApliDisp;
        this.descProc = descProc;
        this.descMet = descMet;
        this.descLoQueIng = descLoQueIng;
        this.setAppdisp(appdisp);
    }

    public String getIniciSesSist() {
        return iniciSesSist;
    }

    public void setIniciSesSist(String iniciSesSist) {
        this.iniciSesSist = iniciSesSist;
    }

    public String getIniciSesAplic() {
        return iniciSesAplic;
    }

    public void setIniciSesAplic(String iniciSesAplic) {
        this.iniciSesAplic = iniciSesAplic;
    }

    public String getUsoGenSis() {
        return usoGenSis;
    }

    public void setUsoGenSis(String usoGenSis) {
        this.usoGenSis = usoGenSis;
    }

    public String getUsoApliDisp() {
        return usoApliDisp;
    }

    public void setUsoApliDisp(String usoApliDisp) {
        this.usoApliDisp = usoApliDisp;
    }

    public String getDescProc() {
        return descProc;
    }

    public void setDescProc(String descProc) {
        this.descProc = descProc;
    }

    public String getDescMet() {
        return descMet;
    }

    public void setDescMet(String descMet) {
        this.descMet = descMet;
    }

    public String getDescLoQueIng() {
        return descLoQueIng;
    }

    public void setDescLoQueIng(String descLoQueIng) {
        this.descLoQueIng = descLoQueIng;
    }


    public String getAppdisp() {
        return appdisp;
    }

    public void setAppdisp(String appdisp) {
        this.appdisp = appdisp;
    }

    public String getSintomas(){
        String sintomas="";
        sintomas+="Problemas de Inicio de Sesion de Sistema: "+iniciSesSist+"\n";
        sintomas+="Problemas de Inicio de Sesion de Aplicación: "+iniciSesAplic+"\n";
        sintomas+="Problema en el Uso General del Sistema: "+usoGenSis+"\n";
        sintomas+="Problema en el Uso de Aplicación/Dispositivo: "+usoApliDisp+"\n";
        sintomas+="Desconoce Procedimiento: "+descProc+"\n";
        sintomas+="Desconoce Método de Ingreso de Datos: "+descMet+"\n";
        sintomas+="Desconoce Lo que Debe Ingresar: "+descLoQueIng+"\n";
        sintomas+="Aplicación/Dispositivo: "+appdisp;
        return sintomas;
    }
}
