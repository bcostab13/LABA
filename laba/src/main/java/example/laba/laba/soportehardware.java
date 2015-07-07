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
public class soportehardware extends Activity {
    Spinner spDispos, spEnciende, spFuncMal;
    EditText txDetalle,txNomProblema;
    Button btRegistrar;
    String disp, enc, funmal, det,nompro;

    //atributos de volley
    //atributos
    private String URL_BASE = "http://helpdeskfisi20.esy.es";
    private static final String TAG = "PostAdapter";
    private String URL_JSON_INC = "/registrarFHw.php";
    private String direccion;
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    StringRequest jsArrayRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soportehardware);

        //asociamos elementos
        spDispos = (Spinner) findViewById(R.id.spinnerDispos);
        spEnciende = (Spinner) findViewById(R.id.spinnerEnciende);
        spFuncMal = (Spinner) findViewById(R.id.spinnerFunciona);
        txDetalle = (EditText) findViewById(R.id.detalle);
        btRegistrar = (Button) findViewById(R.id.buttonRegistrar);
        txNomProblema=(EditText) findViewById(R.id.EditTextNomPro);

        //iniciar Volley
        //creamos una nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(this);

        //////////////////SETEO DE SPINNER/////////////////////////////////
        //spinner dispositivo
        final String[] dispositivos = getResources().getStringArray(R.array.dispositivos);

        // Creamos el ArrayAdapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dispositivos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spDispos.setAdapter(adapter);
        disp = dispositivos[0];

        //Obtenemos el valor seleccionado
        spDispos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp = dispositivos[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //spinner enciende
        final String[] enciende = getResources().getStringArray(R.array.enciende);

        // Creamos el ArrayAdapter
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.enciende, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spEnciende.setAdapter(adapter2);
        enc = enciende[0];

        //Obtenemos el valor seleccionado
        spEnciende.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                enc = enciende[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //spinner funciona mal
        final String[] funcionamal = getResources().getStringArray(R.array.funcmal);

        // Creamos el ArrayAdapter
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.funcmal, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spFuncMal.setAdapter(adapter3);
        funmal = funcionamal[0];

        //Obtenemos el valor seleccionado
        spEnciende.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                funmal = funcionamal[i];
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
                //enviar petición con volley
                if (conexionInternet()) {

                        direccion = URL_BASE + URL_JSON_INC + "?np="+nompro+"&disp="+disp+
                        "&enc="+enc+"&fm="+funmal+"&det="+det+"&sol=";
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
                                        Toast.makeText(soportehardware.this,"Problema Registrado",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                },

                                new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(soportehardware.this,"No pudo registrarse",Toast.LENGTH_LONG).show();
                                    }
                                }
                        );

                        requestQueue.add(jsArrayRequest);

                } else {

                    Toast.makeText(soportehardware.this,"No tiene conexión a Internet :(",Toast.LENGTH_LONG).show();

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
