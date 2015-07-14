package example.laba.laba;

/**
 * Created by Brenda on 29/04/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parse.ParseInstallation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class regincidencia extends Activity {
    ResolverNombres traductor;
    //El drawerLayout esn el que se desplega y
    //contiene dentro el menú, normalmente un listview
    private DrawerLayout mDrawerLayout;
    //Declaremos el ListView
    private ListView mDrawerList;
    //ActionBarDrawerToggle es donde aparecerá el boton
    //para desplegar el menú
    private ActionBarDrawerToggle mDrawerToggle;
    private Spinner cmbCategoria;


    //atributos de volley
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="PostAdapter";
    private String URL_JSON_INC="/registrarIncidencia.php";
    private String direccion;
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    StringRequest jsArrayRequest,jsArrayRequest2;

    private String URL_JSON_ULT="/consultarNumSol.php?cc=";
    JsonObjectRequest jsArrayRequestUlt;
    String usuarioE;

    //atributos de la interfaz
    Button bEnviar;
    Spinner spinnerLug,spinnerCat;
    String lugar="Lab 1",cat="Indeterminada";
    EditText textFecha,textDesc;
    int cod_op2=0,codigo;
    String cod_op;
    UsuarioGeneral user;
    String fecha,descripcion,coddeuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regincidencia);

        //iniciar componentes
        bEnviar = (Button) findViewById(R.id.buttonRegistrar);
        spinnerLug = (Spinner) findViewById(R.id.spinnerLugar);
        textFecha=(EditText)findViewById(R.id.editFecha);
        spinnerCat = (Spinner) findViewById(R.id.spinnerCategoria);
        textDesc=(EditText)findViewById(R.id.desc);
        List<UsuarioGeneral> listaUser=UsuarioGeneral.listAll(UsuarioGeneral.class);
        user=listaUser.get(0);
        traductor=new ResolverNombres();

        //iniciar Volley
        //creamos una nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(this);

        //////////////////SETEO DE FECHA DEL SISTEMA///////////////////////
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int anio=today.year;
        int mes=today.month;
        int dia=today.monthDay;
        int hora=today.hour;
        int minutos=today.minute;
        mes=mes+1;
        String mesS=mes<10?"0"+mes:""+mes;
        String diaS=dia<10?"0"+dia:""+dia;
        textFecha.setText(diaS+"/"+mesS+"/"+anio+" "+today.format("%k:%M:%S"));
        fecha=anio+"-"+mesS+"-"+diaS+" "+today.format("%k:%M:%S");
        ///////////////////////////////////////////////////////////////////

        /////////////////SETEO DE SPINNER LUGAR////////////////////////////
        //declaramos un array con las opciones

        //declaramos un adapter para usar un array generico de java
        ArrayAdapter<CharSequence> adaptadorLug = ArrayAdapter.createFromResource(this,
                R.array.lugares, android.R.layout.simple_spinner_item);

        //enlazamos el spinner
        adaptadorLug.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLug.setAdapter(adaptadorLug);

        //activamos los eventos
        //OnItemSelected=cuando se hace click
        //onNothingSelected=lo que pasa cuando no seleccionas nada

        final String[] losLugares=getResources().getStringArray(R.array.lugares);
        spinnerLug.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){
                    //cuando hago clic en uno
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v,int position, long id){
                        lugar=losLugares[position];
                    }
                    public void onNothingSelected(AdapterView<?> parent){

                    }
                }
        );

        ///////////////////////////////////////////////////////////////////


        /////////////////CODIGO DE MENU DESPLEGABLE////////////////////////
        //relacionamos con el XML
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        //Configuramos el Boton que desplegará el menú
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                 //la actividad
                mDrawerLayout,         //el drawerLayout que desplegará
                R.drawable.ic_drawer, //el icono que mostraremos
                R.string.prueba,  //descripción al abrir
                R.string.app_name  //descripción al cerrar
        ) {     };

        //Creamos nuestro menú
        final String[] opciones = {"Panel de Control", "Nueva Incidencia", "Control de Solicitudes",
                "Control de Laboratorios","Gestión de Problemas"};
        //rellenamos la List view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, opciones));

        //Añadimos la acción que haga en cada fila del
        //list view. en este caso solo mostraremos un Toast con un mensaje
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                //Se cierra el menú
                mDrawerLayout.closeDrawers();
                if (opciones[arg2].equals("Panel de Control")) {
                    Intent lanzar_panel = new Intent(regincidencia.this, administrador.class);
                    finish();
                    startActivity(lanzar_panel);
                }
                if (opciones[arg2].equals("Nueva Incidencia")) {
                    //Intent lanzar_nuevaIncidencia=new Intent(regincidencia.this,regincidencia.class);
                    //finish();
                    //startActivity(lanzar_nuevaIncidencia);
                    Toast.makeText(regincidencia.this, "Ya se encuentra en esta sección", Toast.LENGTH_LONG).show();
                }
                if (opciones[arg2].equals("Control de Solicitudes")) {
                    Intent lanzar_control = new Intent(regincidencia.this, control.class);
                    finish();
                    startActivity(lanzar_control);
                }
                if (opciones[arg2].equals("Control de Laboratorios")) {
                    final CharSequence[] items = {"Lab 1", "Lab 2", "Lab 3", "Lab 4","Lab 5","Lab 6","Lab 7",
                            "Lab 8"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(regincidencia.this);
                    builder.setTitle("Seleccionar Laboratorio");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            String ubic = "";
                            switch (item) {
                                case 0:
                                    ubic = "LAB001";
                                    break;
                                case 1:
                                    ubic = "LAB002";
                                    break;
                                case 2:
                                    ubic = "LAB003";
                                    break;
                                case 3:
                                    ubic = "LAB004";
                                    break;
                                case 4:
                                    ubic = "LAB005";
                                    break;
                                case 5:
                                    ubic = "LAB006";
                                    break;
                                case 6:
                                    ubic = "LAB007";
                                    break;
                                case 7:
                                    ubic = "LAB008";
                                    break;
                            }
                            Bundle ubicacion = new Bundle();
                            ubicacion.putString("ub", ubic);
                            Intent lanzar_contlab = new Intent(regincidencia.this, contlab.class);
                            lanzar_contlab.putExtras(ubicacion);
                            lanzar_contlab.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(lanzar_contlab);
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                if (opciones[arg2].equals("Gestión de Problemas")) {
                    Intent lanzar_gestproblemas = new Intent(regincidencia.this, gestionproblemas.class);
                    finish();
                    startActivity(lanzar_gestproblemas);
                }

            }
        });

        //Mostramos el botón en la barra de la aplicación
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //Activamso el click en el icono de la aplicación
        getActionBar().setHomeButtonEnabled(true);

        ////////////////////////////////////////////////////////////////////

        //////////////////SETEO DE SPINNER/////////////////////////////////

        final String[] categorias=getResources().getStringArray(R.array.fallos);

        // Creamos el ArrayAdapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fallos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCat.setAdapter(adapter);

        //Obtenemos el valor seleccionado
        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat=categorias[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ///////////////////////////////////////////////////////////////////

        ////////////////////////ENVIAR SOLICITUD///////////////////////////

        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (conexionInternet()) {

                    //mostrar progreso
                    LayoutInflater inflater = getLayoutInflater();

                    View dialoglayout = inflater.inflate(R.layout.dialog_cargando, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(regincidencia.this);
                    builder.setView(dialoglayout);
                    //builder.show();
                    final AlertDialog dialog=builder.create();
                    dialog.show();

                    try {

                        List<Marca> lista = Marca.listAll(Marca.class);
                        //Tag.deleteAll(Tag.class);

                        codigo = lista.get(0).getCode();
                        //obtenemos el numero de la ultima solicitud del usuario
                        direccion = URL_BASE + URL_JSON_ULT + user.getCodigo();
                        direccion = direccion.replace(" ", "%20");
                        direccion = direccion.replace("í", "i");
                        direccion = direccion.replace("á", "a");
                        direccion = direccion.replace("é", "e");
                        direccion = direccion.replace("ó", "o");
                        direccion = direccion.replace("ú", "u");
                        Log.d("Direccion", direccion);

                        jsArrayRequestUlt = new JsonObjectRequest(
                                Request.Method.GET,
                                direccion,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("Codigo mas reciente", "RESPUESTA=" + response);
                                        codigo = parseJsonNum(response);
                                        //actualizamos el indice
                                        Marca cont = Marca.findById(Marca.class, (long) 1);
                                        cont.setCode(codigo);
                                        cont.save();

                                        //iniciamos ahora el envió de la solicitud

                                        //obtenemos codigo
                                        Log.d("Codigo", "elemento=" + codigo);
                                        cod_op=""+codigo;
                                        for (int i=0;i<4-String.valueOf(codigo).length();i++){
                                            cod_op="0"+cod_op;
                                        }
                                        Log.d("Codigo","cod_op="+cod_op);

                                        ResolverNombres rn = new ResolverNombres();
                                        lugar = rn.getTrad(lugar);
                                        descripcion = textDesc.getText().toString();
                                        usuarioE = user.getCodigo();
                                        final String codInc = "I" + usuarioE.substring(5) + cod_op;

                                        direccion=URL_BASE + URL_JSON_INC+"?codsol="+codInc+"&fecreg="+fecha
                                                +"&desc="+descripcion+"&im="+"/img/aus_software.jpg"+"&codub="
                                                +lugar+"&codus="+usuarioE+"&categ="+cat+"&id="+
                                                ParseInstallation.getCurrentInstallation().get("deviceToken");
                                        direccion=direccion.replace(" ","%20");
                                        direccion=direccion.replace("í","i");
                                        direccion=direccion.replace("á","a");
                                        direccion=direccion.replace("é","e");
                                        direccion=direccion.replace("ó","o");
                                        direccion=direccion.replace("ú","u");
                                        Log.d("Direccion",direccion);

                                        //ingresamos la solicitud
                                        jsArrayRequest = new StringRequest(
                                                Request.Method.GET,
                                                direccion,
                                                new Response.Listener<String>() {

                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(regincidencia.this,"Incidencia Registrada",Toast.LENGTH_LONG).show();
                                                        dialog.cancel();
                                                        textDesc.setText("");
                                                        spinnerLug.setSelection(0);
                                                        spinnerCat.setSelection(0);
                                                        lugar="Lab 1";
                                                        cat="Indeterminado";
                                                        //actualizamos el indice
                                                        Marca cont = Marca.findById(Marca.class, (long) 1);
                                                        //actualizar cuenta en la base de datos
                                                        cont.setCode(cont.getCode() + 1);
                                                        cont.save();
                                                    }
                                                },
                                                new Response.ErrorListener() {

                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        dialog.cancel();
                                                    }
                                                }
                                        ) ;
                                        jsArrayRequest.setTag("solicitud");
                                        requestQueue.add(jsArrayRequest);
                                    }
                                },
                                new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dialog.cancel();
                                    }
                                }
                        );

                        requestQueue.add(jsArrayRequestUlt);

                    }catch (Exception e){
                        Marca marca=new Marca(0);
                        marca.save();
                        List<Marca> lista = Marca.listAll(Marca.class);
                        Log.d("ID","id="+lista.get(0).getId());
                        codigo = lista.get(0).getCode();
                    }

                }

                else{

                    //iniciar envio de solicitud por mensaje de texto
                    String tel="943434934";
                    String sms="REGISTRO INCIDENCIA  Codigo:"+cod_op+"  Fecha:"+fecha+
                            "  Descripcion:"+descripcion+ "  Im:"+"/img/aus_software.jpg"+
                            "  Ubicacion:"+lugar+"  Usuario:"+usuarioE+"  Categoria:"+cat;
                    Uri uri = Uri.parse("smsto:"+tel);
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", sms);
                    startActivity(it);
                    textDesc.setText("");
                    spinnerLug.setSelection(0);
                    spinnerCat.setSelection(0);
                    lugar="Lab 1";
                    cat="Indeterminado";

                }
            }
        });



        ///////////////////////////////////////////////////////////////////


    }

    private int parseJsonNum(JSONObject jsonObject) {
        int ultSol=0;
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







    //Que el botón de desplegar siempre este sincronizado
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    //Igual con la configuración
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    //Activamos el click paradesplegar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}