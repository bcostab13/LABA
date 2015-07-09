package example.laba.laba;

/**
 * Created by Brenda on 08/07/2015.
 */
public class PRed extends Problema{
    private String cableConectado;
    private String visualizaRedes;
    private String tipoConexion;

    public PRed() {
    }

    public PRed(String nombre, String detalle, String solucion, String cableConectado, String visualizaRedes, String tipoConexion) {
        super(nombre, detalle, solucion);
        this.cableConectado = cableConectado;
        this.visualizaRedes = visualizaRedes;
        this.tipoConexion = tipoConexion;
    }

    public String getCableConectado() {
        return cableConectado;
    }

    public void setCableConectado(String cableConectado) {
        this.cableConectado = cableConectado;
    }

    public String getVisualizaRedes() {
        return visualizaRedes;
    }

    public void setVisualizaRedes(String visualizaRedes) {
        this.visualizaRedes = visualizaRedes;
    }

    public String getTipoConexion() {
        return tipoConexion;
    }

    public void setTipoConexion(String tipoConexion) {
        this.tipoConexion = tipoConexion;
    }

    public String getSintomas(){
        String sintomas="";
        sintomas+="Cables Conectados: "+cableConectado+"\n";
        sintomas+="Visualiza Redes: "+visualizaRedes+"\n";
        sintomas+="Tipo de Conexión: "+tipoConexion;
        return sintomas;
    }
}
