package example.laba.laba;

/**
 * Created by Brenda on 29/04/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Time;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class regincidencia extends Activity {
    //El drawerLayout esn el que se desplega y
    //contiene dentro el menú, normalmente un listview
    private DrawerLayout mDrawerLayout;
    //Declaremos el ListView
    private ListView mDrawerList;
    //ActionBarDrawerToggle es donde aparecerá el boton
    //para desplegar el menú
    private ActionBarDrawerToggle mDrawerToggle;
    private Spinner cmbCategoria;
    String cat;

    //atributos de volley
    //atributos
    private String URL_BASE="http://helpdeskfisi20.esy.es";
    private static final String TAG="PostAdapter";
    private String URL_JSON="/registrarSolicitud.php";
    //agregamos el Administrador de Colas de Peticiones de Volley
    private RequestQueue requestQueue;
    StringRequest jsArrayRequest;

    //atributos de la interfaz
    Button bEnviar;
    Spinner spinnerLug;
    String lugar;
    EditText textFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regincidencia);

        //iniciar componentes
        bEnviar = (Button) findViewById(R.id.buttonRegistrar);
        spinnerLug = (Spinner) findViewById(R.id.spinnerLugar);
        textFecha=(EditText)findViewById(R.id.editFecha);

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
        String mesS=hora<10?"0"+mes:""+mes;
        String diaS=dia<10?"0"+dia:""+dia;
        textFecha.setText(diaS+"/"+mesS+"/"+anio+" "+today.format("%k:%M:%S"));
        //textFecha.setText(today.format("%k:%M:%S"));


        //creamos una instancia de la fecha del sistema
        /*Calendar calendar=Calendar.getInstance();
        int anio=calendar.get(Calendar.YEAR);
        int mes=calendar.get(Calendar.MONTH);
        int dia=calendar.get(Calendar.DAY_OF_MONTH);
        int hora=calendar.get(Calendar.HOUR_OF_DAY);
        int minutos=calendar.get(Calendar.MINUTE);
        int ampm=calendar.get(Calendar.AM_PM);
        //seteamos el valor en el campo fecha
        String horario=ampm==0?"AM":"PM";
        hora=hora==0?12:hora;
        String horaS=hora<10?"0"+hora:""+hora;
        String mesS=hora<10?"0"+mes:""+mes;
        String diaS=hora<10?"0"+dia:""+dia;
        textFecha.setText(mesS+"/"+diaS+"/"+anio+" "+horaS+":"+minutos+" "+horario);
        textFecha.setEnabled(false);*/
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
        final String[] opciones = {"Panel de Control", "Nueva Incidencia", "Control de Solicitudes","Control de Laboratorios","Salir"};
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
                Toast.makeText(regincidencia.this, "id: " + opciones[arg2],
                        Toast.LENGTH_SHORT).show();
                //Se cierra el menú
                mDrawerLayout.closeDrawers();
                if(opciones[arg2].equals("Control de Solicitudes")){
                    Intent lanzar_control=new Intent(regincidencia.this,contsolic.class);
                    finish();
                    startActivity(lanzar_control);
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
        Spinner spinnerCat = (Spinner) findViewById(R.id.spinnerCategoria);
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
                //Gestionar peticion
                jsArrayRequest=new StringRequest(
                        Request.Method.POST,
                        URL_BASE+URL_JSON,
                        new Response.Listener<String>(){

                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(regincidencia.this,"Llego hasta aqui",Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("codsol","SOL000001");
                        params.put("fecreg","09/06/2015 2PM");
                        params.put("desc","La CPU hace un sonido extraño");
                        params.put("im","/img/admi.png");
                        params.put("codub","LAB001");
                        params.put("codus","US0000001");
                        return params;
                    }
                };
                requestQueue.add(jsArrayRequest);
            }
        });



        ///////////////////////////////////////////////////////////////////


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