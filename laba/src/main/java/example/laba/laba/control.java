package example.laba.laba;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Brenda on 13/05/2015.
 */
public class control extends Activity{
    //El drawerLayout esn el que se desplega y
    //contiene dentro el menú, normalmente un listview
    private DrawerLayout mDrawerLayout;
    //Declaremos el ListView
    private ListView mDrawerList;
    //ActionBarDrawerToggle es donde aparecerá el boton
    //para desplegar el menú
    private ActionBarDrawerToggle mDrawerToggle;

    //////////////ATRIBUTOS DE TAB1///////////////////////
    //atributos de la interfaz
    ListView listView;
    ArrayAdapter adapter;

    //////////////////////////////////////////////////////

    //////////////ATRIBUTOS DE TAB2///////////////////////
    //atributos de la interfaz
    ListView listViewR;
    ArrayAdapter adapterR;
    TextView textCuenta,textCuentaR;

    //////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        /////////////////CODIGO DE MENU DESPLEGABLE////////////////////////
        //relacionamos con el XML
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_cont);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_cont);
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
                    Intent lanzar_panel = new Intent(control.this, administrador.class);
                    finish();
                    startActivity(lanzar_panel);
                }
                if (opciones[arg2].equals("Nueva Incidencia")) {
                    Intent lanzar_nuevaIncidencia=new Intent(control.this,regincidencia.class);
                    finish();
                    startActivity(lanzar_nuevaIncidencia);
                }
                if (opciones[arg2].equals("Control de Solicitudes")) {
                    //Intent lanzar_control = new Intent(regincidencia.this, control.class);
                    //finish();
                    //startActivity(lanzar_control);
                    Toast.makeText(control.this, "Ya se encuentra en esta sección", Toast.LENGTH_LONG).show();
                }
                if (opciones[arg2].equals("Control de Laboratorios")) {
                    final CharSequence[] items = {"Lab 1", "Lab 2", "Lab 3", "Lab 4","Lab 5","Lab 6","Lab 7",
                            "Lab 8"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(control.this);
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
                            Intent lanzar_contlab = new Intent(control.this, contlab.class);
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
                    Intent lanzar_gestproblemas = new Intent(control.this, gestionproblemas.class);
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

        ///////////////////CONFIGURACION DE TABS///////////////////////////
        Resources res = getResources();
        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("Incidencias");
        spec.setContent(R.id.tab1);
        spec.setIndicator("INCIDENCIAS",
                res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("Requerimientos");
        spec.setContent(R.id.tab2);
        spec.setIndicator("REQUERIMIENTOS",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
        ////////////////////////////////////////////////////////////////////

        ///////////////////////OBTENCION DE INCIDENCIAS/////////////////////
        //obtener instancia de la lista
        listView=(ListView)findViewById(R.id.listViewSolicitudes);
        textCuenta=(TextView)findViewById(R.id.textViewIndContSol);

        //crear y setear adaptador
        adapter=new incidenciaAdapter(this,textCuenta);

        listView.setAdapter(adapter);

        //acciones del clic de solicitud
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ClickList", "LLega hasta aca");
                TextView aux=(TextView)view.findViewById(R.id.textViewNomSol);
                //Toast.makeText(getApplicationContext(),"Nombre="+aux.getText(),Toast.LENGTH_LONG).show();
                //enviamos el codigo de solicitud a la siguiente actividad
                Bundle codS=new Bundle();
                codS.putString("codigo",aux.getText().toString());
                startActivity(new Intent(control.this,descincidencia.class).putExtras(codS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        ///////////////////////////////////////////////////////////////////

        ///////////////////////OBTENCION DE REQUERIMIENTOS/////////////////////
        //obtener instancia de la lista
        listViewR=(ListView)findViewById(R.id.listViewRequerimientos);
        textCuentaR=(TextView)findViewById(R.id.textViewIndContReq);
        //crear y setear adaptador
        adapterR=new requerimientoAdapter(this,textCuentaR);

        listViewR.setAdapter(adapterR);

        //acciones del clic de solicitud
        listViewR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ClickList", "LLega hasta aca");
                TextView aux=(TextView)view.findViewById(R.id.textViewNomReq);
                //Toast.makeText(getApplicationContext(),"Nombre="+aux.getText(),Toast.LENGTH_LONG).show();
                //enviamos el codigo de solicitud a la siguiente actividad
                Bundle codS=new Bundle();
                codS.putString("codigo",aux.getText().toString());
                startActivity(new Intent(control.this,descrequerimiento.class).putExtras(codS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

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
        switch (item.getItemId()) {
            case R.id.MnuActualizar:
                //actualizar incidencias
                adapter=new incidenciaAdapter(this,textCuenta);
                listView.setAdapter(adapter);
                //actualizar requerimientos
                adapterR=new requerimientoAdapter(this,textCuentaR);
                listViewR.setAdapter(adapterR);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_actualizar, menu);
        return true;
    }


}
