package example.laba.laba;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

/**
 * Created by Brenda on 11/05/2015.
 */
public class actestado extends Activity{
    private RadioButton rbAtendiendo;
    private RadioGroup rgEstados;
    private Button buttonModEsta;
    private int estadoN=1;
    private String cod,est;
    private boolean sw=false;
    //atributos de volley
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="DatosIncidencia";
    private String URL_JSON="/actualizarEstado.php?solicitud=";
    private String URL_ID="/consultarId.php?codsol=";
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue2;
    StringRequest jsArrayRequest2,StringRequestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actestado);

        cod=getIntent().getStringExtra("codigo");
        est=getIntent().getStringExtra("estado");
        Log.d("Codigo y estado:",cod+" y "+est);

        rbAtendiendo=(RadioButton)findViewById(R.id.rbatendiendo);
        rgEstados=(RadioGroup)findViewById(R.id.radiogroupestados);
        buttonModEsta=(Button)findViewById(R.id.buttonModEst);

        //usamos volley para obtener todos los datos de la incidencia
        requestQueue2= Volley.newRequestQueue(this);

        if(est.equals("Atendiendo")){
            rbAtendiendo.setEnabled(false);
        }

        //activar estados de radiobuttons
        rgEstados.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbatendiendo:
                        estadoN=1;
                        sw=false;
                        break;
                    case R.id.rbfinalizado:
                        estadoN=2;
                        sw=true;
                        break;
                    case R.id.rbcancelado:
                        estadoN=3;
                        sw=false;
                        break;
                }
                Log.d("Nuevo Estado",""+estadoN);
            }
        });

        //accion del boton
        buttonModEsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                jsArrayRequest2=new StringRequest(
                        Request.Method.GET,
                        URL_BASE+URL_JSON+cod+"&estado="+estadoN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(actestado.this,"Estado Actualizado",Toast.LENGTH_LONG).show();
                                //finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(actestado.this,"Algo paso",Toast.LENGTH_LONG).show();
                            }
                        }
                );

                requestQueue2.add(jsArrayRequest2);

                //envio de notificacion push al usuario
                StringRequestID=new StringRequest(
                        Request.Method.GET,
                        URL_BASE+URL_ID+cod,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(actestado.this,"ID="+response,Toast.LENGTH_LONG).show();
                                // Create our Installation query
                                ParseQuery pushQuery = ParseInstallation.getQuery();
                                pushQuery.whereEqualTo("deviceToken",response);

                                // Send push notification to query
                                ParsePush push = new ParsePush();
                                push.setQuery(pushQuery); // Set our Installation query
                                switch(estadoN){
                                    case 1: push.setMessage("Su solicitud será atendida en los próximos minutos.");
                                        break;
                                    case 2: push.setMessage("Su solicitud fue atendida satisfactoriamente. " +
                                            "Gracias por confiar en nosotros :)");
                                        break;
                                    case 3: push.setMessage("Su solicitud ha sido rechazada.");
                                        break;
                                }

                                push.sendInBackground();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(actestado.this,"Algo paso",Toast.LENGTH_LONG).show();
                            }
                        }
                );

                requestQueue2.add(StringRequestID);

                if (estadoN==2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(actestado.this);
                    builder.setMessage("¿Desea registrar este problema como recurrente?")
                            .setTitle("Registrar Problema")
                            .setCancelable(false)
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    })
                            .setPositiveButton("Si",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //finish();
                                            Intent lanzar_regproblema=new Intent(actestado.this,regproblema.class);
                                            startActivity(lanzar_regproblema);
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    alert.show();
                }

            }
        });

    }
}
