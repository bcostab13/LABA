package example.laba.laba;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brenda on 05/05/2015.
 */
public class incidenciaAdapter extends ArrayAdapter{
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="IncidenciaAdapter";
    private String URL_JSON="/consultarIncidencia.php";
    List<incidencia> items;
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;

    public incidenciaAdapter(Context context){
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
                    R.layout.layout_sol,
                    parent,
                    false
            );
            //procesar item
        }else{
            listItemView=convertView;
        }

        //Obtener el item actual
        incidencia item=items.get(position);

        //Obtener views
        TextView textoTitulo=(TextView)listItemView.findViewById(R.id.textViewNomSol);
        TextView textoDescripcion=(TextView)listItemView.findViewById(R.id.textViewDescSol);
        final ImageView imagenPost=(ImageView)listItemView.findViewById(R.id.imagenPost);
        RatingBar ratingDificultad=(RatingBar)listItemView.findViewById(R.id.ratingbarDificultad);

        //actualizar los views
        textoTitulo.setText(item.getCod_solic());
        textoDescripcion.setText(item.getCategoria());
        if(item.getCategoria().equals("Inestabilidad o Reseteo")){
            estrellas=5;
        }else if(item.getCategoria().equals("Incompatibilidad o Lentitud")){
            estrellas=4;
        }else if(item.getCategoria().equals("Ausencia de Software")){
            estrellas=1;
        }else if(item.getCategoria().equals("Falla de Hardware")){
            estrellas=3;
        }else if(item.getCategoria().equals("Ausencia de Hardware")){
            estrellas=2;
        }else if(item.getCategoria().equals("Falla de Red")){
            estrellas=2;
        }else{
            estrellas=0;
        }
        ratingDificultad.setRating(estrellas);

        //Peticion para obtener la imagen
        ImageRequest request=new ImageRequest(
                URL_BASE+item.getImagen(),
                new Response.Listener<Bitmap>(){

                    @Override
                    public void onResponse(Bitmap response) {
                        imagenPost.setImageBitmap(response);
                    }
                },0,0,null,null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imagenPost.setImageResource(R.drawable.error);
                        Log.d(TAG,"Error en respuesta de Bitmap:"+error.getMessage());
                    }
                }
        );

        //añadimos peticion a la cola
        requestQueue.add(request);



        return listItemView;

    }


    public List<incidencia> parseJson(JSONObject jsonObject){
        //variables locales
        List<incidencia> posts=new ArrayList<incidencia>();
        JSONArray jsonArray=null;

        try {
            //obtener el array del objeto
            jsonArray=jsonObject.getJSONArray("incidencia");
            for (int i=0;i<jsonArray.length();i++){
                try{
                    JSONObject objeto=jsonArray.getJSONObject(i);

                    incidencia incidenciaNueva=new incidencia(
                            objeto.getString("cod_solicitud"),
                            objeto.getString("fechaRegistro"),
                            objeto.getString("descripcion"),
                            objeto.getString("imagen"),
                            objeto.getString("categoria"),
                            objeto.getString("cod_usuario"),
                            objeto.getString("cod_ubicacion"),
                            objeto.getString("estado")
                    );
                    posts.add(incidenciaNueva);
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
