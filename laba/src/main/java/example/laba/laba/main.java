package example.laba.laba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class main extends ActionBarActivity {
    ImageView iconoPrincipal;
    TextView textoBienvenida;
    Button botonIniciar;
    EditText edituser,editpassword;
    int codigo=0;
    UsuarioGeneral user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ///////////////////////////VERIFICAR SINCRONIZACION///////////////////////////////

        //sincronizado
        try{
            List<UsuarioGeneral> listaUser=UsuarioGeneral.listAll(UsuarioGeneral.class);
            for(int i=0;i<listaUser.size();i++){
                Log.d("Usuarios","Usuario ["+i+"]="+listaUser.get(i).getNombre());
            }
            user=listaUser.get(0);
            finish();
            Intent lanzar_bienvenido=new Intent(main.this,bienvenido.class);
            startActivity(lanzar_bienvenido);

        //no sincronizado
        }catch (Exception e){
            //iniciar conexion
            finish();
            Intent lanzar_sincronizar=new Intent(main.this,sincronizar.class);
            startActivity(lanzar_sincronizar);

        }
        ////////////////////////////////////////////////////////////////////////////
    }

}
