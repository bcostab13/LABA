package example.laba.laba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;

import java.util.List;


public class main extends ActionBarActivity {
    int codigo=0;
    UsuarioGeneral user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ////////////////////////PARSE NOTIFICACIONES PUSH//////////////////////
        Parse.initialize(this, "jnzgcYO5pmbt2iLsqPLU39MuUtweFDPf0L5kp7gn", "OLFZlwSisplR0hGMMX80gpouwvy2JzANxsy0ZzCU");
        ParseInstallation instalacion=ParseInstallation.getCurrentInstallation();
        instalacion.put("usuario",instalacion.get("deviceToken").toString());
        instalacion.saveInBackground();

        ///////////////////////////////////////////////////////////////////////

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
