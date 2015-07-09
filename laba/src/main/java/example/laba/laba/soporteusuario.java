package example.laba.laba;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Brenda on 22/06/2015.
 */
public class soporteusuario extends Activity{
    Spinner spAsociadoa, spProblema;
    EditText txDetalle,txNomProblema,txApp;
    Button btRegistrar;
    String asociadoa, problema,iniciSesSist,iniciSesAplic,usoGenSis,usoApliDisp,descProc,descMet,descLoQueIng,appOdisp, det,nompro;

    //atributos de volley
    //atributos
    private String URL_BASE = "http://helpdeskfisi20.esy.es";
    private static final String TAG = "PostAdapter";
    private String URL_JSON_INC = "/registrarFUs.php";
    private String direccion;
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    StringRequest jsArrayRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soporteusuario);

        //asociamos elementos
        spAsociadoa = (Spinner) findViewById(R.id.spinnerAsociadoa);
        spProblema = (Spinner) findViewById(R.id.spinnerProblema);
        txDetalle = (EditText) findViewById(R.id.detalle);
        btRegistrar = (Button) findViewById(R.id.buttonRegistrar);
        txNomProblema=(EditText) findViewById(R.id.EditTextNomPro);
        txApp=(EditText) findViewById(R.id.editAplicacion);

        //iniciar Volley
        //creamos una nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(this);

        //////////////////SETEO DE SPINNER/////////////////////////////////
        //spinner asociado a
        final String[] asociadoaA = getResources().getStringArray(R.array.asociadoa);

        // Creamos el ArrayAdapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.asociadoa, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spAsociadoa.setAdapter(adapter);
        asociadoa = asociadoaA[0];

        //Obtenemos el valor seleccionado
        spAsociadoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                asociadoa = asociadoaA[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //spinner problema
        final String[] problemaA = getResources().getStringArray(R.array.problema);

        // Creamos el ArrayAdapter
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.problema, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spProblema.setAdapter(adapter2);
        problema = problemaA[0];

        //Obtenemos el valor seleccionado
        spProblema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                problema = problemaA[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ///////////////////////////////////////////////////////////////////

        ////////////////////REGISTRAR PROBLEMA/////////////////////////////
        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                det = txDetalle.getText().toString();
                nompro=txNomProblema.getText().toString();
                appOdisp=txApp.getText().toString();

                //enviar petición con volley
                if(asociadoa.equals("Inicio de Sesión del Sistema")){
                    iniciSesSist="Si";iniciSesAplic="No";usoGenSis="No";usoApliDisp="No";
                }else if(asociadoa.equals("Inicio de Sesión de Aplicación")){
                    iniciSesSist="No";iniciSesAplic="Si";usoGenSis="No";usoApliDisp="No";
                }else if(asociadoa.equals("Uso General del Sistema")){
                    iniciSesSist="No";iniciSesAplic="No";usoGenSis="Si";usoApliDisp="No";
                }else if(asociadoa.equals("Uso de Aplicación o Dispositivo")){
                    iniciSesSist="No";iniciSesAplic="No";usoGenSis="No";usoApliDisp="Si";
                }

                if (problema.equals("Desconocer Procedimiento")){
                    descProc="Si";descMet="No";descLoQueIng="No";
                }else if(problema.equals("Desconocer Método de Ingreso")){
                    descProc="No";descMet="Si";descLoQueIng="No";
                }else if(problema.equals("Desconocer Lo que Debe Ingresar")){
                    descProc="No";descMet="No";descLoQueIng="Si";
                }

                if (conexionInternet()) {

                    direccion = URL_BASE + URL_JSON_INC + "?np="+nompro+"&iss="+iniciSesSist+
                            "&isa="+iniciSesAplic+"&ugs="+usoGenSis+"&uad="+usoApliDisp+"&dp="+descProc+
                            "&dm="+descMet+"&dlq="+descLoQueIng+"&aod="+appOdisp+
                            "&det="+det+"&sol=";

                    direccion = direccion.replace(" ", "%20");
                    direccion = direccion.replace("í", "i");
                    direccion = direccion.replace("á", "a");
                    direccion = direccion.replace("é", "e");
                    direccion = direccion.replace("ó", "o");
                    direccion = direccion.replace("ú", "u");
                    Log.d("Direccion", direccion);

                    jsArrayRequest = new StringRequest(
                            Request.Method.GET,
                            direccion,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(soporteusuario.this, "Problema Registrado", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            },

                            new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(soporteusuario.this,"No pudo registrarse",Toast.LENGTH_LONG).show();
                                }
                            }
                    );

                    requestQueue.add(jsArrayRequest);

                } else {

                    Toast.makeText(soporteusuario.this,"No tiene conexión a Internet :(",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    ///////////////////////////////////////////////////////////////////

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
}
