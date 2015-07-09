package example.laba.laba;

import android.content.Context;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brenda on 08/07/2015.
 */
public class problemaAdapter extends ArrayAdapter{
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="IncidenciaAdapter";
    private String URL_JSONHW="/consultarFHw.php";
    private String URL_JSONSW="/consultarFSw.php";
    private String URL_JSONRD="/consultarFRd.php";
    private String URL_JSONUS="/consultarFUs.php";
    List<Problema> items;
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;

    public problemaAdapter(Context context, final int problema) {
        super(context, 0);

        //creamos una nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(context);
        //Gestionar peticion del archivo JSON

        switch (problema) {
            case 1:
            jsArrayRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    URL_BASE + URL_JSONHW,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "RESPUESTA=" + response);
                            items = parseJson(response,problema);
                            //numero.setText("Hay " + getCount() + " incidencias pendientes.");
                            notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "Error de Respuesta en JSON: " + error.getMessage());
                            //numero.setText("Hay 0 incidencias pendientes.");
                        }
                    }
            );
            requestQueue.add(jsArrayRequest);
                break;

            case 2:
                jsArrayRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL_BASE + URL_JSONRD,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "RESPUESTA=" + response);
                                items = parseJson(response,problema);
                                //numero.setText("Hay " + getCount() + " incidencias pendientes.");
                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error de Respuesta en JSON: " + error.getMessage());
                                //numero.setText("Hay 0 incidencias pendientes.");
                            }
                        }
                );
                requestQueue.add(jsArrayRequest);
                break;
            case 3:
                jsArrayRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL_BASE + URL_JSONSW,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "RESPUESTA=" + response);
                                items = parseJson(response,problema);
                                //numero.setText("Hay " + getCount() + " incidencias pendientes.");
                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error de Respuesta en JSON: " + error.getMessage());
                                //numero.setText("Hay 0 incidencias pendientes.");
                            }
                        }
                );
                requestQueue.add(jsArrayRequest);
                break;
            case 4:
                jsArrayRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL_BASE + URL_JSONUS,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "RESPUESTA=" + response);
                                items = parseJson(response,problema);
                                //numero.setText("Hay " + getCount() + " incidencias pendientes.");
                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error de Respuesta en JSON: " + error.getMessage());
                                //numero.setText("Hay 0 incidencias pendientes.");
                            }
                        }
                );
                requestQueue.add(jsArrayRequest);
                break;
        }
    }

    public problemaAdapter(Context context, final int problema, final ArrayList<String> atributos) {
        super(context, 0);

        //creamos una nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(context);
        //Gestionar peticion del archivo JSON

        switch (problema) {
            case 1:
                jsArrayRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL_BASE + URL_JSONHW,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "RESPUESTA=" + response);
                                items = parseJson(response,problema);
                                //numero.setText("Hay " + getCount() + " incidencias pendientes.");
                                List itemsAux=new ArrayList<PHardware>();
                                int meta=atributos.size(),cont=0;
                                Log.d("Busqueda HW","Array="+atributos.get(0)+","+atributos.get(1)+","+atributos.get(2));
                                for (int i=0;i<items.size();i++){
                                    cont=0;
                                    Log.d("Busqueda Hw","item="+((PHardware)items.get(i)).getNombre());
                                    Log.d("Busqueda Hw","dispositivo="+((PHardware)items.get(i)).getDispositivo());
                                    Log.d("Busqueda Hw","enciende="+((PHardware)items.get(i)).getEnciende());
                                    Log.d("Busqueda Hw","funciona mal="+((PHardware)items.get(i)).getFuncionamal());

                                    if(atributos.get(0).equals(((PHardware)items.get(i)).getDispositivo())){
                                        cont++;
                                    }
                                    if(atributos.get(1).equals(((PHardware)items.get(i)).getEnciende())||atributos.get(1).equals("Irrelevante")){
                                        cont++;
                                    }
                                    if(atributos.get(2).equals(((PHardware)items.get(i)).getFuncionamal())||atributos.get(2).equals("Irrelevante")){
                                        cont++;
                                    }
                                    Log.d("Busqueda Hw","conteo="+cont);
                                    if(cont==meta){
                                        itemsAux.add(items.get(i));
                                    }
                                }
                                items=itemsAux;
                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error de Respuesta en JSON: " + error.getMessage());
                                //numero.setText("Hay 0 incidencias pendientes.");
                            }
                        }
                );
                requestQueue.add(jsArrayRequest);
                break;

            case 2:
                jsArrayRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL_BASE + URL_JSONRD,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "RESPUESTA=" + response);
                                items = parseJson(response,problema);
                                //numero.setText("Hay " + getCount() + " incidencias pendientes.");
                                List itemsAux=new ArrayList<PRed>();
                                int meta=atributos.size(),cont=0;
                                Log.d("Busqueda Red","Array="+atributos.get(0)+","+atributos.get(1)+","+atributos.get(2));
                                for (int i=0;i<items.size();i++){
                                    cont=0;
                                    Log.d("Busqueda Red","item="+((PRed)items.get(i)).getNombre());
                                    Log.d("Busqueda Red","cableConectado="+((PRed)items.get(i)).getCableConectado());
                                    Log.d("Busqueda Red","visualizaRedes="+((PRed)items.get(i)).getVisualizaRedes());
                                    Log.d("Busqueda Red","tipoConexion="+((PRed)items.get(i)).getTipoConexion());

                                    if(atributos.get(0).equals(((PRed)items.get(i)).getCableConectado())){
                                        cont++;
                                    }
                                    if(atributos.get(1).equals(((PRed)items.get(i)).getVisualizaRedes())||atributos.get(1).equals("Irrelevante")){
                                        cont++;
                                    }
                                    if(atributos.get(2).equals(((PRed)items.get(i)).getTipoConexion())||atributos.get(2).equals("Irrelevante")){
                                        cont++;
                                    }
                                    Log.d("Busqueda Red","conteo="+cont);
                                    if(cont==meta){
                                        itemsAux.add(items.get(i));
                                    }
                                }
                                items=itemsAux;
                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error de Respuesta en JSON: " + error.getMessage());
                                //numero.setText("Hay 0 incidencias pendientes.");

                            }
                        }
                );
                requestQueue.add(jsArrayRequest);
                break;
            case 3:
                jsArrayRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL_BASE + URL_JSONSW,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "RESPUESTA=" + response);
                                items = parseJson(response,problema);
                                //numero.setText("Hay " + getCount() + " incidencias pendientes.");
                                List itemsAux=new ArrayList<PSoftware>();
                                int meta=atributos.size(),cont=0;
                                Log.d("Busqueda Software","Array="+atributos.get(0)+","+atributos.get(1)+","+
                                        atributos.get(2)+","+atributos.get(3));
                                for (int i=0;i<items.size();i++){
                                    cont=0;
                                    Log.d("Busqueda Software","item="+((PSoftware)items.get(i)).getNombre());
                                    Log.d("Busqueda Software","bajoDesempeño="+((PSoftware)items.get(i)).getBajoDesempeño());
                                    Log.d("Busqueda Software","incompatibilidad="+((PSoftware)items.get(i)).getIncompatibilidad());
                                    Log.d("Busqueda Software","reseteo="+((PSoftware)items.get(i)).getReseteo());
                                    Log.d("Busqueda Software","memoria llena="+((PSoftware)items.get(i)).getMemoriaLlena());
                                    if(atributos.get(0).equals(((PSoftware)items.get(i)).getBajoDesempeño())){
                                        cont++;
                                    }
                                    if(atributos.get(1).equals(((PSoftware)items.get(i)).getIncompatibilidad())||atributos.get(1).equals("Irrelevante")){
                                        cont++;
                                    }
                                    if(atributos.get(2).equals(((PSoftware)items.get(i)).getReseteo())||atributos.get(2).equals("Irrelevante")){
                                        cont++;
                                    }
                                    if(atributos.get(3).equals(((PSoftware)items.get(i)).getMemoriaLlena())||atributos.get(3).equals("Irrelevante")){
                                        cont++;
                                    }

                                    Log.d("Busqueda Software","conteo="+cont);
                                    if(cont==meta){
                                        itemsAux.add(items.get(i));
                                    }
                                }
                                items=itemsAux;
                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error de Respuesta en JSON: " + error.getMessage());
                                //numero.setText("Hay 0 incidencias pendientes.");
                            }
                        }
                );
                requestQueue.add(jsArrayRequest);
                break;
            case 4:
                jsArrayRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL_BASE + URL_JSONUS,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "RESPUESTA=" + response);
                                items = parseJson(response,problema);
                                //numero.setText("Hay " + getCount() + " incidencias pendientes.");
                                List itemsAux=new ArrayList<PUsuario>();
                                int meta=atributos.size(),cont=0;
                                Log.d("Busqueda Software","Array="+atributos.get(0)+","+atributos.get(1)+","+
                                        atributos.get(2)+","+atributos.get(3)+","+atributos.get(4)+
                                        ","+atributos.get(5)+","+atributos.get(6));
                                for (int i=0;i<items.size();i++){
                                    cont=0;
                                    Log.d("Busqueda Software","item="+((PUsuario)items.get(i)).getNombre());
                                    Log.d("Busqueda Software","IniciSesSist="+((PUsuario)items.get(i)).getIniciSesSist());
                                    Log.d("Busqueda Software","IniciSesAplic="+((PUsuario)items.get(i)).getIniciSesAplic());
                                    Log.d("Busqueda Software","UsoGenSis="+((PUsuario)items.get(i)).getUsoGenSis());
                                    Log.d("Busqueda Software","UsoApliDisp="+((PUsuario)items.get(i)).getUsoApliDisp());
                                    Log.d("Busqueda Software","DescProc="+((PUsuario)items.get(i)).getDescProc());
                                    Log.d("Busqueda Software","DescMet="+((PUsuario)items.get(i)).getDescMet());
                                    Log.d("Busqueda Software","DescLoQueIng="+((PUsuario)items.get(i)).getDescLoQueIng());
                                    if(atributos.get(0).equals(((PUsuario)items.get(i)).getIniciSesSist())){
                                        cont++;
                                    }
                                    if(atributos.get(1).equals(((PUsuario)items.get(i)).getIniciSesAplic())||atributos.get(1).equals("Irrelevante")){
                                        cont++;
                                    }
                                    if(atributos.get(2).equals(((PUsuario)items.get(i)).getUsoGenSis())||atributos.get(2).equals("Irrelevante")){
                                        cont++;
                                    }
                                    if(atributos.get(3).equals(((PUsuario)items.get(i)).getUsoApliDisp())||atributos.get(3).equals("Irrelevante")){
                                        cont++;
                                    }
                                    if(atributos.get(4).equals(((PUsuario)items.get(i)).getDescProc())||atributos.get(4).equals("Irrelevante")){
                                        cont++;
                                    }
                                    if(atributos.get(5).equals(((PUsuario)items.get(i)).getDescMet())||atributos.get(5).equals("Irrelevante")){
                                        cont++;
                                    }
                                    if(atributos.get(6).equals(((PUsuario)items.get(i)).getDescLoQueIng())||atributos.get(6).equals("Irrelevante")){
                                        cont++;
                                    }

                                    Log.d("Busqueda Usuario","conteo="+cont);
                                    if(cont==meta){
                                        itemsAux.add(items.get(i));
                                    }
                                }
                                items=itemsAux;

                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error de Respuesta en JSON: " + error.getMessage());
                                //numero.setText("Hay 0 incidencias pendientes.");
                            }
                        }
                );
                requestQueue.add(jsArrayRequest);
                break;
        }
    }

    @Override
    public int getCount() {
        return items!=null? items.size():0;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
                    R.layout.layout_problema,
                    parent,
                    false
            );
            //procesar item
        }else{
            listItemView=convertView;
        }

        //Obtener el item actual
        Problema item=items.get(position);

        //Obtener views
        TextView textoTitulo=(TextView)listItemView.findViewById(R.id.textViewNomProblema);
        TextView textoDescripcion=(TextView)listItemView.findViewById(R.id.textViewDescProblema);
        final ImageView imagenPost=(ImageView)listItemView.findViewById(R.id.imagenReq);

        //actualizar los views
        textoTitulo.setText(item.getNombre());
        textoDescripcion.setText(item.getDetalle());
        imagenPost.setImageResource(R.drawable.problema);

        return listItemView;

    }


    public List<Problema> parseJson(JSONObject jsonObject,int problema){
        //variables locales
        List<Problema> posts=new ArrayList<Problema>();
        JSONArray jsonArray=null;
        switch (problema) {
            case 1:
                try {
                    //obtener el array del objeto
                    jsonArray = jsonObject.getJSONArray("fallosHw");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject objeto = jsonArray.getJSONObject(i);

                            PHardware problemaNuevo = new PHardware(
                                    objeto.getString("NombreProblema"),
                                    objeto.getString("Detalle"),
                                    objeto.getString("Solucion"),
                                    objeto.getString("Dispositivo"),
                                    objeto.getString("Enciende"),
                                    objeto.getString("FuncionaMal")
                            );
                            posts.add(problemaNuevo);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error de parsing: " + e.getMessage());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    //obtener el array del objeto
                    jsonArray = jsonObject.getJSONArray("fallosRed");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject objeto = jsonArray.getJSONObject(i);

                            PRed problemaNuevo = new PRed(
                                    objeto.getString("nombre"),
                                    objeto.getString("detalle"),
                                    objeto.getString("solucion"),
                                    objeto.getString("cableConectado"),
                                    objeto.getString("visualizaRedes"),
                                    objeto.getString("tipoConexion")
                            );
                            posts.add(problemaNuevo);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error de parsing: " + e.getMessage());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                try {
                    //obtener el array del objeto
                    jsonArray = jsonObject.getJSONArray("fallosSw");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject objeto = jsonArray.getJSONObject(i);

                            PSoftware problemaNuevo = new PSoftware(
                                    objeto.getString("NombreDelProblema"),
                                    objeto.getString("Detalle"),
                                    objeto.getString("Solucion"),
                                    objeto.getString("BajoDesempeno"),
                                    objeto.getString("Incompatibilidad"),
                                    objeto.getString("MemoriaLlena"),
                                    objeto.getString("Reseteo")
                            );
                            posts.add(problemaNuevo);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error de parsing: " + e.getMessage());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 4:
                try {
                    //obtener el array del objeto
                    jsonArray = jsonObject.getJSONArray("fallosUs");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject objeto = jsonArray.getJSONObject(i);

                            PUsuario problemaNuevo = new PUsuario(
                                    objeto.getString("nombre"),
                                    objeto.getString("detalle"),
                                    objeto.getString("solucion"),
                                    objeto.getString("iniciSesSist"),
                                    objeto.getString("iniciSesAplic"),
                                    objeto.getString("usoGenSis"),
                                    objeto.getString("usoApliDisp"),
                                    objeto.getString("descProc"),
                                    objeto.getString("descMet"),
                                    objeto.getString("descLoQueIng"),
                                    objeto.getString("appOdisp")
                            );
                            posts.add(problemaNuevo);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error de parsing: " + e.getMessage());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        return posts;
    }

    public List<Problema> getResultados(){
        return items;
    }
}
