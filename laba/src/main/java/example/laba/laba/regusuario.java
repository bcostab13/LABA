package example.laba.laba;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Brenda on 13/07/2015.
 */
public class regusuario extends Activity{
    EditText txNombre,txApellido,txEmail,txUser,txPassword;
    Button btRegistrar;
    String nombreu,apellido,email,user,password;

    //atributos de volley
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="PostAdapter";
    private String URL_JSON_US="/registrarUsuario.php";
    private String direccion;
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    StringRequest jsArrayRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regusuario);

        //asociar elementos
        txNombre=(EditText)findViewById(R.id.textViewValCodIncid);
        txApellido=(EditText)findViewById(R.id.textViewValTiempo);
        txEmail=(EditText)findViewById(R.id.textViewValCategoria);
        txUser=(EditText)findViewById(R.id.textViewValLugar);
        txPassword=(EditText)findViewById(R.id.textViewValUsuario);
        btRegistrar=(Button)findViewById(R.id.buttonModEst);

        //iniciar Volley
        //creamos una nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(this);

        //Mostramos el botón en la barra de la aplicación
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //Activamso el click en el icono de la aplicación
        getActionBar().setHomeButtonEnabled(true);

        //registrar
        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (conexionInternet()) {
                    nombreu=txNombre.getText().toString();
                    apellido=txApellido.getText().toString();
                    email=txEmail.getText().toString();
                    user=txUser.getText().toString();
                    password=txPassword.getText().toString();
                    //mostrar progreso
                    LayoutInflater inflater = getLayoutInflater();

                    View dialoglayout = inflater.inflate(R.layout.dialog_cargando, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(regusuario.this);
                    builder.setView(dialoglayout);
                    //builder.show();
                    final AlertDialog dialog=builder.create();
                    dialog.show();

                    try {
                        if(nombreu.equals("")||apellido.equals("")||email.equals("")||user.equals("")
                                ||password.equals("")){
                            Toast.makeText(regusuario.this,"No ha completado los campos.",Toast.LENGTH_LONG).show();
                        }else {


                            //obtenemos el numero de la ultima solicitud del usuario
                            direccion = URL_BASE + URL_JSON_US +"?nombre="+nombreu+"&apellido="+apellido+
                            "&email="+email+"&user="+user+"&pw="+password+"&tipo="+"alumno"+"&ubicacion="+"Indeterminado";
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
                                            dialog.cancel();
                                            Toast.makeText(regusuario.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    },
                                    new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            dialog.cancel();
                                        }
                                    }
                            );

                            requestQueue.add(jsArrayRequest);
                        }

                    }catch (Exception e){
                        Toast.makeText(regusuario.this,"Error. Intentalo de nuevo.",Toast.LENGTH_LONG).show();
                    }

                }

                else{

                    Toast.makeText(regusuario.this,"No tienes conexión",Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        });

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
}
