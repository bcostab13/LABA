package example.laba.laba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Brenda on 06/06/2015.
 */
public class sincronizar extends Activity{
    ImageView iconoPrincipal;
    TextView textoBienvenida;
    Button botonIniciar,botonRegistrar;
    EditText edituser,editpassword;
    int codigo=0;
    UsuarioGeneral user;

    //atributos de volley
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="SincronizarUsuario";
    private String URL_JSON="/sincronizarUsuario.php?user=";
    private String URL_JSON_ULT="/consultarNumSol.php?cc=";
    private String direccion="";
    private int ultSol;
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue2;
    JsonObjectRequest jsArrayRequest2;
    JsonObjectRequest jsArrayRequestUlt;
    UsuarioGeneral usaux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //asociaciones
        iconoPrincipal=(ImageView)findViewById(R.id.imageView_inicial);
        textoBienvenida=(TextView)findViewById(R.id.textView_bienvenida);
        botonIniciar=(Button)findViewById(R.id.button);
        botonRegistrar=(Button)findViewById(R.id.buttonRegistrar);
        edituser=(EditText)findViewById(R.id.editText_user);
        editpassword=(EditText)findViewById(R.id.editText_pass);

        //parametros de Volley
        requestQueue2= Volley.newRequestQueue(this);

        //agregar animacion inicial
        Animation mostrar;
        mostrar= AnimationUtils.loadAnimation(this, R.animator.anim_inicio);
        mostrar.reset();
        iconoPrincipal.startAnimation(mostrar);
        textoBienvenida.startAnimation(mostrar);



        //al hacer clic en Ingresar
        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsuarioGeneral.deleteAll(UsuarioGeneral.class);
                //iniciar conexion
                //Gestionar peticion
                jsArrayRequest2=new JsonObjectRequest(
                        Request.Method.GET,
                        URL_BASE+URL_JSON+edituser.getText().toString()+"&pw="+editpassword.getText().toString(),
                        null,
                        new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "RESPUESTA=" + response);
                                usaux=parseJson(response);
                                user=usaux;
                                UsuarioGeneral nuevo=new UsuarioGeneral(usaux.getCodigo(),
                                        usaux.getNombre(),usaux.getTipo());
                                nuevo.save();
                                Toast.makeText(sincronizar.this,"Sincronización exitosa",Toast.LENGTH_LONG).show();

                                //iniciar panel de control
                                Bundle cod=new Bundle();
                                cod.putInt("codigo",codigo);
                                //enviando tipo de usuario
                                String tip=user.getTipo();
                                Bundle tipUs=new Bundle();
                                tipUs.putString("tipo",tip);

                                if(user.getTipo().equals("alumno")||user.getTipo().equals("oficina")){
                                    Log.d("bundle1", "" + codigo);
                                    finish();
                                    Intent iniciarAlumno=new Intent(sincronizar.this,alumno.class).putExtras(cod).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    iniciarAlumno.putExtras(tipUs);
                                    startActivity(iniciarAlumno);
                                }else if(user.getTipo().equals("administrador")){
                                    finish();
                                    Intent iniciarAdmi=new Intent(sincronizar.this,administrador.class).putExtras(cod).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(iniciarAdmi);
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG,"Error de Respuesta en JSON: "+error.getMessage());
                                edituser.setText("");
                                editpassword.setText("");
                                Toast.makeText(sincronizar.this,"Usuario o contraseña incorrecta",Toast.LENGTH_LONG).show();
                            }
                        }
                );
                requestQueue2.add(jsArrayRequest2);

            }
        });




        try{
            List<UsuarioGeneral> listaUser=UsuarioGeneral.listAll(UsuarioGeneral.class);
            for(int i=0;i<listaUser.size();i++){
                Log.d("Usuarios","Usuario ["+i+"]="+listaUser.get(i).getNombre());
            }
            user=listaUser.get(0);
        }catch (Exception e){
            //iniciar conexion
            //UsuarioGeneral prueba=new UsuarioGeneral("US0000001","Brenda Costa","administrador");
            //prueba.save();
        }

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar_registrar=new Intent(sincronizar.this,regusuario.class);
                startActivity(lanzar_registrar);
            }
        });
    }

    private int parseJsonNum(JSONObject jsonObject) {
        JSONArray jsonArray=null;

        try {
            //obtener el array del objeto
            jsonArray=jsonObject.getJSONArray("ultimo");
            Log.d(TAG,"longitud ="+jsonArray.length());
            for (int i=0;i<jsonArray.length();i++){

                try{
                    Log.d(TAG,"elementos "+jsonArray.length());
                    JSONObject objeto=jsonArray.getJSONObject(i);
                    ultSol=Integer.parseInt(objeto.getString("nult"));

                }catch (JSONException e){
                    Log.e(TAG,"Error de parsing: "+e.getMessage());
                    ultSol=0;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            ultSol=0;
        }
        return ultSol;
    }

    private UsuarioGeneral parseJson(JSONObject jsonObject) {
        UsuarioGeneral usuarioNuevo=new UsuarioGeneral();
        JSONArray jsonArray=null;

        try {
            //obtener el array del objeto
            jsonArray=jsonObject.getJSONArray("usuariogeneral");
            Log.d(TAG,"longitud ="+jsonArray.length());
            for (int i=0;i<jsonArray.length();i++){

                try{
                    Log.d(TAG,"elementos "+jsonArray.length());
                    JSONObject objeto=jsonArray.getJSONObject(i);
                    usuarioNuevo.setCodigo(objeto.getString("cod_usuario"));
                    usuarioNuevo.setNombre(objeto.getString("nombre") + " " + objeto.getString("apellido"));
                    usuarioNuevo.setTipo(objeto.getString("tipo"));

                }catch (JSONException e){
                    Log.e(TAG,"Error de parsing: "+e.getMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuarioNuevo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
