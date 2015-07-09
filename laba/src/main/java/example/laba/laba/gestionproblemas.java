package example.laba.laba;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Brenda on 07/07/2015.
 */
public class gestionproblemas extends Activity{
    //El drawerLayout esn el que se desplega y
    //contiene dentro el menú, normalmente un listview
    private DrawerLayout mDrawerLayout;
    //Declaremos el ListView
    private ListView mDrawerList;
    //ActionBarDrawerToggle es donde aparecerá el boton
    //para desplegar el menú
    private ActionBarDrawerToggle mDrawerToggle;

    Button btBuscar,btMostrarTodo;
    ListView listViewR;
    ArrayAdapter adapterR;
    Spinner spTipoP,spAtrib1,spAtrib2,spAtrib3;
    TextView txAtrib1,txAtrib2,txAtrib3;
    LinearLayout frOpcional;
    String tipop,atrib1,atrib2,atrib3;
    int indAtrib1=0,indAtrib2=0,indAtrib3=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionproblemas);

        //asociamos nuestros objetos
        spTipoP=(Spinner)findViewById(R.id.spinnerTipoP);
        spAtrib1=(Spinner)findViewById(R.id.spinnerA2);
        spAtrib2=(Spinner)findViewById(R.id.spinnerA3);
        spAtrib3=(Spinner)findViewById(R.id.spinnerA4);
        txAtrib1=(TextView)findViewById(R.id.textViewA2);
        txAtrib2=(TextView)findViewById(R.id.textViewA3);
        txAtrib3=(TextView)findViewById(R.id.textViewA4);
        frOpcional=(LinearLayout)findViewById(R.id.form4);
        btBuscar=(Button)findViewById(R.id.buttonBuscar);
        btMostrarTodo=(Button)findViewById(R.id.buttonMostrar);


        //////////////////SETEO DE SPINNER TIPO DE PROBLEMA/////////////////////////////////
        //spinner
        final String[] tipopA = getResources().getStringArray(R.array.tipodeproblema);
        final String[] estadoCablesA = getResources().getStringArray(R.array.concables);
        final String[] visualizaRedesA = getResources().getStringArray(R.array.visredes);
        final String[] tipodeConexiones = getResources().getStringArray(R.array.tipored);
        final String[] tipoSwA = getResources().getStringArray(R.array.tipo);
        final String[] memoriaLlenaA = getResources().getStringArray(R.array.memoria);
        final String[] asociadoA = getResources().getStringArray(R.array.asociadoa);
        final String[] problemaA = getResources().getStringArray(R.array.problema);
        final String[] dispositivoA = getResources().getStringArray(R.array.dispositivos);
        final String[] enciendeA = getResources().getStringArray(R.array.enciende);
        final String[] funcionamalA = getResources().getStringArray(R.array.funcmal);

        // Creamos el ArrayAdapter
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipodeproblema, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spTipoP.setAdapter(adapter);
        tipop = tipopA[0];

        //Obtenemos el valor seleccionado
        spTipoP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indAtrib1=0;indAtrib2=0;indAtrib3=0;
                tipop = tipopA[i];
                if(tipop.equals("Red")){
                    //atributo 1
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(gestionproblemas.this,
                            R.array.concables, android.R.layout.simple_spinner_item);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAtrib1.setAdapter(adapter1);
                    atrib1 = estadoCablesA[0];
                    txAtrib1.setText("Estado de Cables:");
                    //atributo 2
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(gestionproblemas.this,
                            R.array.visredes, android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAtrib2.setAdapter(adapter2);
                    atrib2 = visualizaRedesA[0];
                    txAtrib2.setText("¿Visualiza Redes?");
                    //atributo 3
                    frOpcional.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(gestionproblemas.this,
                            R.array.tipored, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAtrib3.setAdapter(adapter3);
                    atrib3 = tipodeConexiones[0];
                    txAtrib3.setText("Tipo de Conexión:");
                }else if(tipop.equals("Software")){
                    //atributo 1
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(gestionproblemas.this,
                            R.array.tipo, android.R.layout.simple_spinner_item);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAtrib1.setAdapter(adapter1);
                    atrib1 = tipoSwA[0];
                    txAtrib1.setText("Tipo:");
                    //atributo 2
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(gestionproblemas.this,
                            R.array.memoria, android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAtrib2.setAdapter(adapter2);
                    atrib2 = memoriaLlenaA[0];
                    txAtrib2.setText("¿Memoria Llena?");
                    //atributo 3
                    frOpcional.setVisibility(View.INVISIBLE);

                }else if(tipop.equals("Hardware")){
                    //atributo 1
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(gestionproblemas.this,
                            R.array.dispositivos, android.R.layout.simple_spinner_item);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAtrib1.setAdapter(adapter1);
                    atrib1 = dispositivoA[0];
                    txAtrib1.setText("Dispositivo:");
                    //atributo 2
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(gestionproblemas.this,
                            R.array.enciende, android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAtrib2.setAdapter(adapter2);
                    atrib2 = enciendeA[0];
                    txAtrib2.setText("¿Enciende?");
                    //atributo 3
                    frOpcional.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(gestionproblemas.this,
                            R.array.funcmal, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAtrib3.setAdapter(adapter3);
                    atrib3 = funcionamalA[0];
                    txAtrib3.setText("¿Funciona mal?");
                }else if(tipop.equals("General")){
                    //atributo 1
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(gestionproblemas.this,
                            R.array.asociadoa, android.R.layout.simple_spinner_item);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAtrib1.setAdapter(adapter1);
                    atrib1 = asociadoA[0];
                    txAtrib1.setText("Tipo:");
                    //atributo 2
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(gestionproblemas.this,
                            R.array.problema, android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAtrib2.setAdapter(adapter2);
                    atrib2 = problemaA[0];
                    txAtrib2.setText("Duda:");
                    //atributo 3
                    frOpcional.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //////////////////////////////////////////////////////////////////////////////////////

        ////////////////SETEO DE ACCION AL PRESIONAR SPINNER///////////////////////////
        spAtrib1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indAtrib1 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spAtrib2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indAtrib2 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spAtrib3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indAtrib3 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //////////////////////////////////////////////////////////////////////////////////

        ///////////////////////OBTENCION DE REQUERIMIENTOS/////////////////////
        btMostrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            //obtener instancia de la lista
            listViewR=(ListView)findViewById(R.id.listViewProblemas);
            //textCuentaR=(TextView)findViewById(R.id.textViewIndContReq);
            //crear y setear adaptador
            int pr=1;
            if(tipop.equals("Red")){
                pr=2;
            }else if(tipop.equals("Software")){
                pr=3;
            }else if(tipop.equals("Hardware")){
                pr=1;
            }else if(tipop.equals("General")){
                pr=4;
            }
            adapterR=new problemaAdapter(gestionproblemas.this,pr);
            listViewR.setAdapter(adapterR);

            //acciones del clic de solicitud
            listViewR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("ClickList", "LLega hasta aca");
                    Log.d("Valor de Item Seleccionado","Item="+((Problema)adapterR.getItem(i)).getNombre());
                    Object auxproblema=adapterR.getItem(i);

                    String nombreP="",sintomasP="",detalleP="",solucionP="";

                    if(auxproblema instanceof PHardware){
                        Toast.makeText(gestionproblemas.this,"Problema de Hardware",Toast.LENGTH_LONG).show();
                        nombreP=((PHardware)auxproblema).getNombre();
                        sintomasP=((PHardware)auxproblema).getSintomas();
                        detalleP=((PHardware)auxproblema).getDetalle();
                        solucionP=((PHardware)auxproblema).getSolucion();
                    }else if(auxproblema instanceof PRed){
                        Toast.makeText(gestionproblemas.this,"Problema de Red",Toast.LENGTH_LONG).show();
                        nombreP=((PRed)auxproblema).getNombre();
                        sintomasP=((PRed)auxproblema).getSintomas();
                        detalleP=((PRed)auxproblema).getDetalle();
                        solucionP=((PRed)auxproblema).getSolucion();
                    }else if(auxproblema instanceof PSoftware){
                        Toast.makeText(gestionproblemas.this,"Problema de PSoftware",Toast.LENGTH_LONG).show();
                        nombreP=((PSoftware)auxproblema).getNombre();
                        sintomasP=((PSoftware)auxproblema).getSintomas();
                        detalleP=((PSoftware)auxproblema).getDetalle();
                        solucionP=((PSoftware)auxproblema).getSolucion();
                    }else if(auxproblema instanceof PUsuario){
                        Toast.makeText(gestionproblemas.this,"Problema de Usuario",Toast.LENGTH_LONG).show();
                        nombreP=((PUsuario)auxproblema).getNombre();
                        sintomasP=((PUsuario)auxproblema).getSintomas();
                        detalleP=((PUsuario)auxproblema).getDetalle();
                        solucionP=((PUsuario)auxproblema).getSolucion();
                    }


                    Bundle codS=new Bundle();
                    codS.putString("nombre",nombreP);
                    codS.putString("sintomas",sintomasP);
                    codS.putString("detalle",detalleP);
                    codS.putString("solucion",solucionP);
                    codS.putString("tipoP",tipop);
                    startActivity(new Intent(gestionproblemas.this,regsolucion.class).putExtras(codS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


                }
            });


            }
        });
        ///////////////////////////////////////////////////////////////////

        ///////////////////////BOTON BUSCAR////////////////////////////////////
        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //obtenemos los atributos

                ArrayList<String> atributos=new ArrayList<String>();
                //obtener instancia de la lista
                listViewR=(ListView)findViewById(R.id.listViewProblemas);
                //textCuentaR=(TextView)findViewById(R.id.textViewIndContReq);
                //crear y setear adaptador
                int pr=1;
                if(tipop.equals("Red")){
                    atrib1=spAtrib1.getSelectedItem().toString();
                    atrib2=spAtrib2.getSelectedItem().toString();
                    atrib3=spAtrib3.getSelectedItem().toString();
                    atributos.add(atrib1);atributos.add(atrib2);atributos.add(atrib3);
                    pr=2;
                }else if(tipop.equals("Software")){
                    atributos.add("No");atributos.add("No");atributos.add("No");atributos.add("No");
                    atrib1=spAtrib1.getSelectedItem().toString();
                    atrib2=spAtrib2.getSelectedItem().toString();
                    Log.d("Atrib1",atrib1);
                    if(atrib1.equals("Desempeño")){
                        atributos.set(0,"Si");
                    }
                    if(atrib1.equals("Inestabilidad")){
                        atributos.set(1,"Si");
                    }
                    if(atrib1.equals("Reseteo")){
                        atributos.set(2,"Si");
                    }
                    atributos.set(3,atrib2);
                    pr=3;
                }else if(tipop.equals("Hardware")){
                    atrib1=spAtrib1.getSelectedItem().toString();
                    atrib2=spAtrib2.getSelectedItem().toString();
                    atrib3=spAtrib3.getSelectedItem().toString();
                    pr=1;
                    atributos.add(atrib1);atributos.add(atrib2);atributos.add(atrib3);
                }else if(tipop.equals("General")){
                    atributos.add("No");atributos.add("No");atributos.add("No");atributos.add("No");
                    atributos.add("No");atributos.add("No");atributos.add("No");
                    atrib1=spAtrib1.getSelectedItem().toString();
                    atrib2=spAtrib2.getSelectedItem().toString();
                    Log.d("Atrib1",atrib1);
                    if(atrib1.equals("Inicio de Sesión del Sistema")){
                        atributos.set(0,"Si");
                    }
                    if(atrib1.equals("Inicio de Sesión de Aplicación")){
                        atributos.set(1,"Si");
                    }
                    if(atrib1.equals("Uso General del Sistema")){
                        atributos.set(2,"Si");
                    }
                    if(atrib1.equals("Uso de Aplicación o Dispositivo")){
                        atributos.set(3,"Si");
                    }
                    if(atrib2.equals("Desconocer Procedimiento")){
                        atributos.set(4,"Si");
                    }
                    if(atrib2.equals("Desconocer Método de Ingreso")){
                        atributos.set(5,"Si");
                    }
                    if(atrib2.equals("Desconocer Lo que Debe Ingresar")){
                        atributos.set(6,"Si");
                    }
                    pr=4;
                }
                adapterR=new problemaAdapter(gestionproblemas.this,pr,atributos);
                listViewR.setAdapter(adapterR);

                //acciones del clic de solicitud
                listViewR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.d("ClickList", "LLega hasta aca");
                        Log.d("Valor de Item Seleccionado","Item="+((Problema)adapterR.getItem(i)).getNombre());
                        Object auxproblema=adapterR.getItem(i);

                        String nombreP="",sintomasP="",detalleP="",solucionP="";

                        if(auxproblema instanceof PHardware){
                            Toast.makeText(gestionproblemas.this,"Problema de Hardware",Toast.LENGTH_LONG).show();
                            nombreP=((PHardware)auxproblema).getNombre();
                            sintomasP=((PHardware)auxproblema).getSintomas();
                            detalleP=((PHardware)auxproblema).getDetalle();
                            solucionP=((PHardware)auxproblema).getSolucion();
                        }else if(auxproblema instanceof PRed){
                            Toast.makeText(gestionproblemas.this,"Problema de Red",Toast.LENGTH_LONG).show();
                            nombreP=((PRed)auxproblema).getNombre();
                            sintomasP=((PRed)auxproblema).getSintomas();
                            detalleP=((PRed)auxproblema).getDetalle();
                            solucionP=((PRed)auxproblema).getSolucion();
                        }else if(auxproblema instanceof PSoftware){
                            Toast.makeText(gestionproblemas.this,"Problema de PSoftware",Toast.LENGTH_LONG).show();
                            nombreP=((PSoftware)auxproblema).getNombre();
                            sintomasP=((PSoftware)auxproblema).getSintomas();
                            detalleP=((PSoftware)auxproblema).getDetalle();
                            solucionP=((PSoftware)auxproblema).getSolucion();
                        }else if(auxproblema instanceof PUsuario){
                            Toast.makeText(gestionproblemas.this,"Problema de Usuario",Toast.LENGTH_LONG).show();
                            nombreP=((PUsuario)auxproblema).getNombre();
                            sintomasP=((PUsuario)auxproblema).getSintomas();
                            detalleP=((PUsuario)auxproblema).getDetalle();
                            solucionP=((PUsuario)auxproblema).getSolucion();
                        }


                        Bundle codS=new Bundle();
                        codS.putString("nombre",nombreP);
                        codS.putString("sintomas",sintomasP);
                        codS.putString("detalle",detalleP);
                        codS.putString("solucion",solucionP);
                        codS.putString("tipoP",tipop);
                        startActivity(new Intent(gestionproblemas.this,regsolucion.class).putExtras(codS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


                    }
                });


            }
        });

        ///////////////////////////////////////////////////////////////////////





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
        final String[] opciones = {"Panel de Control", "Nueva Incidencia", "Control de Solicitudes","Control de Laboratorios"};
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
                    Intent lanzar_panel = new Intent(gestionproblemas.this, administrador.class);
                    finish();
                    startActivity(lanzar_panel);
                }
                if (opciones[arg2].equals("Nueva Incidencia")) {
                    Toast.makeText(gestionproblemas.this, "Ya se encuentra en esta sección", Toast.LENGTH_LONG);
                }
                if (opciones[arg2].equals("Control de Solicitudes")) {
                    Intent lanzar_control = new Intent(gestionproblemas.this, control.class);
                    finish();
                    startActivity(lanzar_control);
                }
                if (opciones[arg2].equals("Control de Laboratorios")) {
                    Intent lanzar_controlab = new Intent(gestionproblemas.this, contlab.class);
                    finish();
                    startActivity(lanzar_controlab);
                }

            }
        });

        //Mostramos el botón en la barra de la aplicación
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //Activamso el click en el icono de la aplicación
        getActionBar().setHomeButtonEnabled(true);

        ////////////////////////////////////////////////////////////////////

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
