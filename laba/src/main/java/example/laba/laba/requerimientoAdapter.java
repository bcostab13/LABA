package example.laba.laba;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
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
 * Created by Brenda on 31/05/2015.
 */
public class requerimientoAdapter extends ArrayAdapter{
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="RequerimientoAdapter";
    private String URL_JSON="/consultarRequerimiento.php";
    List<Requerimiento> items;
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;

    public requerimientoAdapter(Context context){
        super(context,0);
        //creamos una nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(context);
        //Gestionar peticion del archivo JSON

        jsArrayRequest=new JsonObjectRequest(
                Request.Method.POST,
                URL_BASE+URL_JSON,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "RESPUESTA=" + response);
                        items=parseJson(response);
                        notifyDataSetChanged();
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

    public requerimientoAdapter(Context context,final TextView contador){
        super(context,0);
        //creamos una nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(context);
        //Gestionar peticion del archivo JSON

        jsArrayRequest=new JsonObjectRequest(
                Request.Method.POST,
                URL_BASE+URL_JSON,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "RESPUESTA=" + response);
                        items=parseJson(response);
                        contador.setText("Hay "+getCount()+" requerimientos pendientes.");
                        notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error de Respuesta en JSON: "+error.getMessage());
                        contador.setText("Hay 0 requerimientos pendientes.");
                    }
                }
        );
        requestQueue.add(jsArrayRequest);
    }



    @Override
    public int getCount() {
        return items!=null? items.size():0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int estrellas=0;
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        //guardando la referencia del VIew de la fila
        View listItemView=convertView;

        //comprobando si el View no existe
        if(null==convertView){
            //si no existe, entonces inflarlo
            listItemView=layoutInflater.inflate(
                    R.layout.layout_req,
                    parent,
                    false
            );
            //procesar item
        }else{
            listItemView=convertView;
        }

        //Obtener el item actual
        Requerimiento item=items.get(position);

        //Obtener views
        TextView textoTitulo=(TextView)listItemView.findViewById(R.id.textViewNomReq);
        TextView textoDescripcion=(TextView)listItemView.findViewById(R.id.textViewDescReq);
        final ImageView imagenPost=(ImageView)listItemView.findViewById(R.id.imagenReq);
        RatingBar ratingDificultad=(RatingBar)listItemView.findViewById(R.id.ratingbarDificultadReq);

        //actualizar los views
        textoTitulo.setText(item.getCod_solic());
        textoDescripcion.setText(item.getCategoria());
        if(item.getCategoria().equals("Instalacion de Software")){
            estrellas=5;
        }else if(item.getCategoria().equals("Instalacion de Sist.Operativo")){
            estrellas=4;
        }else if(item.getCategoria().equals("Instalacion de Driver o Dispositivos")){
            estrellas=1;
        }else if(item.getCategoria().equals("Formateado de PC")){
            estrellas=3;
        }else if(item.getCategoria().equals("Limpieza de Disco")){
            estrellas=2;
        }else if(item.getCategoria().equals("Desfragmentacion de Disco")){
            estrellas=2;
        }else{
            estrellas=0;
        }
        ratingDificultad.setRating(estrellas);

        imagenPost.setImageResource(R.drawable.requeri);

        return listItemView;

    }


    public List<Requerimiento> parseJson(JSONObject jsonObject){
        //variables locales
        List<Requerimiento> posts=new ArrayList<Requerimiento>();
        JSONArray jsonArray=null;

        try {
            //obtener el array del objeto
            jsonArray=jsonObject.getJSONArray("requerimiento");
            for (int i=0;i<jsonArray.length();i++){
                try{
                    JSONObject objeto=jsonArray.getJSONObject(i);

                    Requerimiento requerimientoNuevo=new Requerimiento(
                            objeto.getString("cod_solicitud"),
                            objeto.getString("fechaRegistro"),
                            objeto.getString("descripcion"),
                            objeto.getString("imagen"),
                            objeto.getString("categoria"),
                            objeto.getString("cod_usuario"),
                            objeto.getString("cod_ubicacion"),
                            objeto.getString("estado"),
                            objeto.getString("fechaSolicitada")
                    );
                    posts.add(requerimientoNuevo);
                }catch (JSONException e){
                    Log.e(TAG,"Error de parsing: "+e.getMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
