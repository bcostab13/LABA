package example.laba.laba;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
 * Created by Brenda on 01/05/2015.
 */
public class auxpostadapter extends ArrayAdapter {

    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="PostAdapter";
    private String URL_JSON="/consultarSolicitud.php";
    List<Post> items;
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;

    public auxpostadapter(Context context){
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
                        Log.d(TAG,"RESPUESTA="+response);
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
        Post item=items.get(position);

        //Obtener views
        TextView textoTitulo=(TextView)listItemView.findViewById(R.id.textViewNomSol);
        TextView textoDescripcion=(TextView)listItemView.findViewById(R.id.textViewDescSol);
        final ImageView imagenPost=(ImageView)listItemView.findViewById(R.id.imagenPost);

        //actualizar los views
        textoTitulo.setText(item.getTitulo());
        textoDescripcion.setText(item.getDescripcion());

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


    public List<Post> parseJson(JSONObject jsonObject){
        //variables locales
        List<Post> posts=new ArrayList<Post>();
        JSONArray jsonArray=null;

        try {
            //obtener el array del objeto
            jsonArray=jsonObject.getJSONArray("posts");
            for (int i=0;i<jsonArray.length();i++){
                try{
                    JSONObject objeto=jsonArray.getJSONObject(i);

                    Post post=new Post(
                        objeto.getString("titulo"),
                        objeto.getString("descripcion"),
                        objeto.getString("imagen")
                    );
                    posts.add(post);
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
