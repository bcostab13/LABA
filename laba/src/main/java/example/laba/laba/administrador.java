package example.laba.laba;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Brenda on 20/04/2015.
 */
public class administrador extends Activity{
    Button izquierdo,derecho,izquierdo2,derecho2;
    int cod_cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        //asociamos
        izquierdo=(Button)findViewById(R.id.buttonOpNIncidencia);
        derecho=(Button)findViewById(R.id.buttonOpContSolicitud);
        izquierdo2=(Button)findViewById(R.id.buttonOpContLabo);
        derecho2=(Button)findViewById(R.id.buttonOpContProblemas);
        cod_cont=getIntent().getIntExtra("codigo",0);
        Log.d("Bundle","Bundle admi="+cod_cont);

        Animation mov_izquierda;
        mov_izquierda= AnimationUtils.loadAnimation(this, R.animator.ladoizquierdo);
        mov_izquierda.reset();
        izquierdo.startAnimation(mov_izquierda);

        Animation mov_derecha;
        mov_derecha= AnimationUtils.loadAnimation(this,R.animator.ladoderecho);
        mov_derecha.reset();
        derecho.startAnimation(mov_derecha);

        mov_izquierda= AnimationUtils.loadAnimation(this, R.animator.ladoizquierdo);
        mov_izquierda.reset();
        izquierdo2.startAnimation(mov_izquierda);

        mov_derecha= AnimationUtils.loadAnimation(this,R.animator.ladoderecho);
        mov_derecha.reset();
        derecho2.startAnimation(mov_derecha);

        izquierdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle cod_op=new Bundle();
                cod_op.putInt("codigo",cod_cont);
                Intent lanzar_desplegable=new Intent(administrador.this,
                        regincidencia.class).putExtras(cod_op).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lanzar_desplegable);
            }
        });

        derecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar_control=new Intent(administrador.this,control.class);
                startActivity(lanzar_control);
            }
        });
        //control de labos
        izquierdo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Lab 1", "Lab 2", "Lab 3", "Lab 4","Lab 5","Lab 6","Lab 7",
                "Lab 8"};
                AlertDialog.Builder builder = new AlertDialog.Builder(administrador.this);
                builder.setTitle("Seleccionar Laboratorio");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        String ubic="";
                        switch (item){
                            case 0: ubic="LAB001";
                                break;
                            case 1: ubic="LAB002";
                                break;
                            case 2: ubic="LAB003";
                                break;
                            case 3: ubic="LAB004";
                                break;
                            case 4: ubic="LAB005";
                                break;
                            case 5: ubic="LAB006";
                                break;
                            case 6: ubic="LAB007";
                                break;
                            case 7: ubic="LAB008";
                                break;
                        }
                        Bundle ubicacion=new Bundle();
                        ubicacion.putString("ub", ubic);
                        Intent lanzar_contlab=new Intent(administrador.this,contlab.class);
                        lanzar_contlab.putExtras(ubicacion);
                        lanzar_contlab.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(lanzar_contlab);
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        derecho2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar_problema=new Intent(administrador.this,gestionproblemas.class);
                startActivity(lanzar_problema);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // Esto es lo que hace mi botón al pulsar ir a atrás
            AlertDialog.Builder builder = new AlertDialog.Builder(administrador.this);
            builder.setMessage("¿Deseas salir de Laba?")
                    .setTitle("Cerrar Aplicación")
                    .setCancelable(false)
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .setPositiveButton("Si",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MnuOpc1:
                Log.i("ActionBar", "Cerrar Sesión");
                AlertDialog.Builder builder = new AlertDialog.Builder(administrador.this);
                builder.setMessage("Al cerrar sesión, necesitará estar conectado a internet para volver" +
                        " a entrar. ¿Desea cerrar sesión sesión de todas formas?")
                        .setTitle("Cerrar Sesión")
                        .setCancelable(false)
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("Si",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        UsuarioGeneral.deleteAll(UsuarioGeneral.class);
                                        finish();
                                        Intent lanzar_sincronizar=new Intent(administrador.this,sincronizar.class);
                                        startActivity(lanzar_sincronizar);
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.MnuOpc2:
                Log.i("ActionBar", "Salir");
                AlertDialog.Builder builder2 = new AlertDialog.Builder(administrador.this);
                builder2.setMessage("¿Deseas salir de Laba?")
                        .setTitle("Cerrar Aplicación")
                        .setCancelable(false)
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("Si",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
