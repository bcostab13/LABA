package example.laba.laba;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by Brenda on 08/07/2015.
 */
public class regsolucion extends Activity{
    TextView txNombre,txSintomas,txDetalle,txSolucion;
    Button btRegistrar;
    String tipoP;

    //atributos de volley
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="PostAdapter";
    private String URL_SW="/actualizarSolucionSw.php";
    private String URL_HW="/actualizarSolucionHw.php";
    private String URL_RED="/actualizarSolucionRed.php";
    private String URL_US="/actualizarSolucionUs.php";
    private String direccion;
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    StringRequest jsArrayRequest,jsArrayRequest2;
    UsuarioGeneral usuarioActual;
    String tipoUs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsolucion);

        //obtener tipo de usuario
        List<UsuarioGeneral> listaUser=UsuarioGeneral.listAll(UsuarioGeneral.class);
        usuarioActual=listaUser.get(0);
        tipoUs=usuarioActual.getTipo();

        //asociamos las variables
        txNombre=(TextView)findViewById(R.id.textViewValNombre);
        txDetalle=(TextView)findViewById(R.id.textViewValDetalle);
        txSintomas=(TextView)findViewById(R.id.textViewValSintomas);
        txSolucion=(TextView)findViewById(R.id.textViewValSolucion);
        btRegistrar=(Button)findViewById(R.id.buttonModSolucion);

        if(tipoUs.equals("alumno")){
            btRegistrar.setEnabled(false);
        }


        //iniciar Volley
        //creamos una nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(this);

        ///////////////ACTIVAR BOTON RETROCESO//////////////////////////////////
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //////////////////////////////////////////////////////////////////////////

        ///////////////LLENADO DE DATOS////////////////////////////////////////
        final String auxNombre=getIntent().getStringExtra("nombre");
        String auxSintomas=getIntent().getStringExtra("sintomas");
        String auxDetalle=getIntent().getStringExtra("detalle");
        String auxSolucion=getIntent().getStringExtra("solucion");
        tipoP=getIntent().getStringExtra("tipoP");

        txNombre.setText(auxNombre);
        txSintomas.setText(auxSintomas);
        txDetalle.setText(auxDetalle);
        if(auxSolucion==""){
            txSolucion.setText("Aún no hay solución para este problema.");
        }else{
            txSolucion.setText(auxSolucion);
        }


        if (auxSolucion.equals("")){
            btRegistrar.setEnabled(true);
        }
        ///////////////////////////////////////////////////////////////////////

        /////////////////REGISTRAR SOLUCION///////////////////////////////
        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(regsolucion.this);
                builder.setTitle("Registro de Solución");

                // Set up the input
                final EditText input = new EditText(regsolucion.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(regsolucion.this,input.getText().toString(),Toast.LENGTH_LONG).show();
                        //registro de solucion :)
                                if (conexionInternet()) {
                                    if(tipoP.equals("Red")){
                                        direccion = URL_BASE + URL_RED;
                                    }else if(tipoP.equals("Hardware")){
                                        direccion = URL_BASE + URL_HW;
                                    }else if(tipoP.equals("Software")){
                                        direccion = URL_BASE + URL_SW;
                                    }else if(tipoP.equals("General")){
                                        direccion = URL_BASE + URL_US;
                                    }
                                        direccion+= "?nombre="+auxNombre+"&solucion="+input.getText().toString();
                                        direccion = direccion.replace(" ", "%20");
                                        direccion = direccion.replace("í", "i");
                                        direccion = direccion.replace("á", "a");
                                        direccion = direccion.replace("é", "e");
                                        direccion = direccion.replace("ó", "o");
                                        direccion = direccion.replace("ú", "u");
                                        direccion = direccion.replace("ñ", "ni");
                                        Log.d("Direccion", direccion);

                                        jsArrayRequest = new StringRequest(
                                                Request.Method.GET,
                                                direccion,
                                                new Response.Listener<String>() {

                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(regsolucion.this,"Solución Registrada",Toast.LENGTH_LONG).show();
                                                        finish();
                                                    }
                                                },
                                                new Response.ErrorListener() {

                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {

                                                    }
                                                }
                                        ) ;
                                        requestQueue.add(jsArrayRequest);
                                }

                                else{
                                    //no tienes conexion u.u
                                }
                            }

                        ///////////////////////////////////////////////////////////////////

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        //////////////////////////////////////////////////////////////////

    }

    //////////////////METODOS PARA VERIFICAR CONEXION/////////////////////////////

    private boolean conexionInternet(){

        if(isNetworkAvailable()){
            return true;
        }else{
            return false;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
