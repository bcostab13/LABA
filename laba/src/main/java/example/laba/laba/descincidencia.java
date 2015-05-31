package example.laba.laba;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Brenda on 05/05/2015.
 */
public class descincidencia extends Activity {

    //atributos de volley
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="DatosIncidencia";
    private String URL_JSON="/datosIncidencia.php?ci=";
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue2;
    JsonObjectRequest jsArrayRequest2;
    incidencia incaux;

    //ATRIBUTOS DE NUESTROS COMPONENTES
    TextView est,cod,fec,cat,lug,us,desc;
    Button actEst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descincidencia);

        ///////////////ACTIVAR BOTON RETROCESO//////////////////////////////////
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //////////////////////////////////////////////////////////////////////////

        ///////////////////SOLICITUD//////////////////////////////////////////////
        //inicializamos la cola

        //asociamos los elementos

        est=(TextView)findViewById(R.id.textViewEstado);
        cod=(TextView)findViewById(R.id.textViewValCodIncid);
        fec=(TextView)findViewById(R.id.textViewValTiempo);
        cat=(TextView)findViewById(R.id.textViewValCategoria);
        lug=(TextView)findViewById(R.id.textViewValLugar);
        us=(TextView)findViewById(R.id.textViewValUsuario);
        desc=(TextView)findViewById(R.id.textViewValDescrip);
        actEst=(Button)findViewById(R.id.buttonModEst);

        //desempacamos el bundle
        final String codigo=getIntent().getStringExtra("codigo");
        Log.d(TAG,"codigo bundle="+codigo);

        //usamos volley para obtener todos los datos de la incidencia
        requestQueue2= Volley.newRequestQueue(this);

        //Gestionar peticion
        jsArrayRequest2=new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE+URL_JSON+codigo,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "RESPUESTA=" + response);
                        incaux=parseJson(response);
                        int estado=Integer.parseInt(incaux.getEstado());
                        String estaux="";
                        switch (estado){
                            case 0: estaux="Nuevo";
                                    actEst.setEnabled(true);
                                break;
                            case 1: estaux="Atendiendo";
                                    actEst.setEnabled(true);
                                break;
                            case 2: estaux="Terminado";
                                break;
                            case 3: estaux="Cancelado";
                                break;
                        }
                        est.setText(estaux);
                        cod.setText(incaux.getCod_solic());
                        fec.setText(incaux.getFechaRegistro());
                        cat.setText(incaux.getCategoria());
                        lug.setText(incaux.getCod_ubicacion());
                        us.setText(incaux.getCod_usuario());
                        desc.setText(incaux.getDescripcion());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error de Respuesta en JSON: "+error.getMessage());
                    }
                }
        );
        requestQueue2.add(jsArrayRequest2);

        //////////////////////////////////////////////////////////////////////////////
        actEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(descincidencia.this);
                builder.setMessage("El cambio es irreversible y se notificará al usuario del mismo. ¿Desea continuar?")
                        .setTitle("Advertencia")
                        .setCancelable(false)
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("Continuar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent lanzar_actestado=new Intent(descincidencia.this,actestado.class);
                                        startActivity(lanzar_actestado);

                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    public incidencia parseJson(JSONObject jsonObject) {
        incidencia incidenciaNueva=new incidencia();
        JSONArray jsonArray=null;

        try {
            //obtener el array del objeto
            jsonArray=jsonObject.getJSONArray("incidencia");
            Log.d(TAG,"longitud ="+jsonArray.length());
            for (int i=0;i<jsonArray.length();i++){

                try{
                    Log.d(TAG,"elementos "+jsonArray.length());
                    JSONObject objeto=jsonArray.getJSONObject(i);
                    incidenciaNueva.setEstado(objeto.getString("estado"));
                    incidenciaNueva.setCategoria(objeto.getString("categoria"));
                    incidenciaNueva.setCod_solic(objeto.getString("cod_solicitud"));
                    incidenciaNueva.setCod_ubicacion(objeto.getString("ubicacion"));
                    incidenciaNueva.setCod_usuario(objeto.getString("nombre") + " " + objeto.getString("apellido"));
                    incidenciaNueva.setDescripcion(objeto.getString("descripcion"));
                    incidenciaNueva.setFechaRegistro(objeto.getString("fechaRegistro"));
                    Log.d(TAG,"Sale "+incidenciaNueva.getCod_solic());

                }catch (JSONException e){
                    Log.e(TAG,"Error de parsing: "+e.getMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"Sale "+incidenciaNueva.getCod_solic());
        return incidenciaNueva;
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
