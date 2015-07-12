package example.laba.laba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brenda on 23/05/2015.
 */
public class contlab extends Activity {
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="Control de Laboratorios";
    private String URL_JSON="/consultarHorario.php";
    private String dia="lunes";
    private String ubicacion="LAB001";
    private TextView[][] posiciones;

    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contlab);

        ubicacion=getIntent().getStringExtra("ub");
        //Toast.makeText(this,"ub=" + ubicacion, Toast.LENGTH_LONG).show();


        //asociamos los elementos
        posiciones=new TextView[14][2];
        posiciones[0][0]=(TextView)findViewById(R.id.curso0809a);
        posiciones[0][1]=(TextView)findViewById(R.id.profesor0809a);
        posiciones[1][0]=(TextView)findViewById(R.id.curso0910a);
        posiciones[1][1]=(TextView)findViewById(R.id.profesor0910a);
        posiciones[2][0]=(TextView)findViewById(R.id.curso1011a);
        posiciones[2][1]=(TextView)findViewById(R.id.profesor1011a);
        posiciones[3][0]=(TextView)findViewById(R.id.curso1112a);
        posiciones[3][1]=(TextView)findViewById(R.id.profesor1112a);
        posiciones[4][0]=(TextView)findViewById(R.id.curso1213a);
        posiciones[4][1]=(TextView)findViewById(R.id.profesor1213a);
        posiciones[5][0]=(TextView)findViewById(R.id.curso1314a);
        posiciones[5][1]=(TextView)findViewById(R.id.profesor1314a);
        posiciones[6][0]=(TextView)findViewById(R.id.curso1415a);
        posiciones[6][1]=(TextView)findViewById(R.id.profesor1415a);
        posiciones[7][0]=(TextView)findViewById(R.id.curso1516a);
        posiciones[7][1]=(TextView)findViewById(R.id.profesor1516a);
        posiciones[8][0]=(TextView)findViewById(R.id.curso1617a);
        posiciones[8][1]=(TextView)findViewById(R.id.profesor1617a);
        posiciones[9][0]=(TextView)findViewById(R.id.curso1718a);
        posiciones[9][1]=(TextView)findViewById(R.id.profesor1718a);
        posiciones[10][0]=(TextView)findViewById(R.id.curso1819a);
        posiciones[10][1]=(TextView)findViewById(R.id.profesor1819a);
        posiciones[11][0]=(TextView)findViewById(R.id.curso1920a);
        posiciones[11][1]=(TextView)findViewById(R.id.profesor1920a);
        posiciones[12][0]=(TextView)findViewById(R.id.curso2021a);
        posiciones[12][1]=(TextView)findViewById(R.id.profesor2021a);
        posiciones[13][0]=(TextView)findViewById(R.id.curso2122a);
        posiciones[13][1]=(TextView)findViewById(R.id.profesor2122a);

        //creamos una nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(contlab.this);
        //Gestionar peticion del archivo JSON

        Log.d("Mensaje",URL_BASE+URL_JSON+"?dia="+dia+"&ub="+ubicacion);
        jsArrayRequest=new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE+URL_JSON+"?dia="+dia+"&ub="+ubicacion,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "RESPUESTA=" + response);
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error de Respuesta en JSON: "+error.getMessage());
                    }
                }
        );
        requestQueue.add(jsArrayRequest);
    }

    private void parseJson(JSONObject response) {
        //variables locales
        List<incidencia> posts=new ArrayList<incidencia>();
        JSONArray jsonArray=null;

        try {
            //obtener el array del objeto
            jsonArray=response.getJSONArray("horarios");
            for (int i=0;i<jsonArray.length();i++){
                try{
                    JSONObject objeto=jsonArray.getJSONObject(i);
                    int inicio=Integer.parseInt(objeto.getString("hora_inicio"));
                    int fin=Integer.parseInt(objeto.getString("hora_fin"));
                    String curso=objeto.getString("curso");
                    String profesor=objeto.getString("prof");
                    int j=inicio;
                    while(j<fin){
                        posiciones[j-8][0].setText(curso);
                        posiciones[j-8][1].setText(profesor);
                        j++;
                    }
                }catch (JSONException e){
                    Log.e(TAG,"Error de parsing: "+e.getMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
