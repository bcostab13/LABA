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
 * Created by Brenda on 31/05/2015.
 */
public class descrequerimiento extends Activity{

    //atributos de volley
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="DatosIncidencia";
    private String URL_JSON="/datosRequerimiento.php?ci=";
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue2;
    JsonObjectRequest jsArrayRequest2;
    Requerimiento reqaux;

    //ATRIBUTOS DE NUESTROS COMPONENTES
    TextView est,cod,fec,cat,lug,us,desc,lim;
    Button actEst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descrequerimiento);

        ///////////////ACTIVAR BOTON RETROCESO//////////////////////////////////
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //////////////////////////////////////////////////////////////////////////

        ///////////////////SOLICITUD//////////////////////////////////////////////
        //inicializamos la cola

        //asociamos los elementos

        est=(TextView)findViewById(R.id.textViewEstadoRequer);
        cod=(TextView)findViewById(R.id.textViewValCodRequer);
        fec=(TextView)findViewById(R.id.textViewValTiempoRequer);
        cat=(TextView)findViewById(R.id.textViewValCategoriaRequer);
        lug=(TextView)findViewById(R.id.textViewValLugarRequer);
        us=(TextView)findViewById(R.id.textViewValUsuarioRequer);
        desc=(TextView)findViewById(R.id.textViewValDescripRequer);
        lim=(TextView)findViewById(R.id.textViewValFecLiRequer);
        actEst=(Button)findViewById(R.id.buttonModEstRequer);

        //desempacamos el bundle
        final String codigo=getIntent().getStringExtra("codigo");
        Log.d(TAG, "codigo bundle=" + codigo);

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
                        reqaux=parseJson(response);
                        int estado=Integer.parseInt(reqaux.getEstado());
                        String estaux="";
                        switch (estado){
                            case 0: estaux="Nuevo";
                                actEst.setEnabled(true);
                                break;
                            case 1: estaux="Atendiendo";
                                actEst.setEnabled(true);
                                break;
                            case 2: estaux="Finalizado";
                                break;
                            case 3: estaux="Cancelado";
                                break;
                        }
                        est.setText(estaux);
                        cod.setText(reqaux.getCod_solic());
                        fec.setText(reqaux.getFechaRegistro());
                        cat.setText(reqaux.getCategoria());
                        lug.setText(reqaux.getCod_ubicacion());
                        us.setText(reqaux.getCod_usuario());
                        desc.setText(reqaux.getDescripcion());
                        lim.setText(reqaux.getFechaLimite());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(descrequerimiento.this);
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
                                        Bundle codigo=new Bundle();
                                        codigo.putString("codigo",cod.getText().toString());
                                        Bundle estado=new Bundle();
                                        codigo.putString("estado",est.getText().toString());
                                        Intent lanzar_actestado=new Intent(descrequerimiento.this,actestado.class).putExtras(codigo);
                                        lanzar_actestado.putExtras(estado);
                                        lanzar_actestado.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(lanzar_actestado);
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    public Requerimiento parseJson(JSONObject jsonObject) {
        Requerimiento requerimientoNuevo=new Requerimiento();
        JSONArray jsonArray=null;

        try {
            //obtener el array del objeto
            jsonArray=jsonObject.getJSONArray("requerimiento");
            Log.d(TAG,"longitud ="+jsonArray.length());
            for (int i=0;i<jsonArray.length();i++){

                try{
                    Log.d(TAG,"elementos "+jsonArray.length());
                    JSONObject objeto=jsonArray.getJSONObject(i);
                    requerimientoNuevo.setEstado(objeto.getString("estado"));
                    requerimientoNuevo.setCategoria(objeto.getString("categoria"));
                    requerimientoNuevo.setCod_solic(objeto.getString("cod_solicitud"));
                    requerimientoNuevo.setCod_ubicacion(objeto.getString("ubicacion"));
                    requerimientoNuevo.setCod_usuario(objeto.getString("nombre") + " " + objeto.getString("apellido"));
                    requerimientoNuevo.setDescripcion(objeto.getString("descripcion"));
                    requerimientoNuevo.setFechaRegistro(objeto.getString("fechaRegistro"));
                    requerimientoNuevo.setFechaLimite(objeto.getString("fechaSolicitada"));
                    Log.d(TAG,"Sale "+requerimientoNuevo.getCod_solic());

                }catch (JSONException e){
                    Log.e(TAG,"Error de parsing: "+e.getMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"Sale "+requerimientoNuevo.getCod_solic());
        return requerimientoNuevo;
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
