package example.laba.laba;

/**
 * Created by Brenda on 22/05/2015.
 */
public class ResolverNombres {
    private String nombres[]={"Lab 1","Lab 2","Lab 3","Lab 4","Lab 5","Lab 6","Lab 7","Lab 8","Aula 101",
            "Aula 102","Aula 103","Aula 104","Aula 105","Aula 106","Aula 201","Aula 202","Aula 203",
            "Aula 204","Aula 205","Aula 206","Aula FAM1","Aula FAM2","Exteriores","Dentro de la Facultad",
            "Fuera de la Facultad"};
    private String trad[]={"LAB001","LAB002","LAB003","LAB004","LAB005","LAB006","LAB007","LAB008","AUL101",
            "AUL102","AUL103","AUL104","AUL105","AUL106","AUL201","AUL202","AUL203","AUL204","AUL205",
            "AUL206","FAM001","FAM002","EXTERI","INTERN","EXTERI"};

    public ResolverNombres(){

    }

    public String getTrad(String palabra){
        String traduc="";
        palabra=palabra.trim();
        boolean fin=false;
        for(int i=0;i<nombres.length&&fin==false;i++){
            if(palabra.equals(nombres[i])){
                traduc=trad[i];
            }
        }
        return traduc;
    }
}
