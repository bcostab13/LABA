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
public class alumno extends Activity{
    Button izquierdo,derecho,izquierdo2;
    String tipoUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        tipoUs=getIntent().getStringExtra("tipo");
        Toast.makeText(this,"tipo="+tipoUs,Toast.LENGTH_LONG).show();

        //asociamos
        izquierdo=(Button)findViewById(R.id.buttonOpNIncidenciaA);
        derecho=(Button)findViewById(R.id.buttonNuevoRequerimientoA);
        izquierdo2=(Button)findViewById(R.id.buttonOpDiagnosticoA);


        Animation mov_izquierda;
        mov_izquierda= AnimationUtils.loadAnimation(this, R.animator.ladoizquierdo);
        mov_izquierda.reset();
        //izquierdo.startAnimation(mov_izquierda);

        Animation mov_derecha;
        mov_derecha= AnimationUtils.loadAnimation(this,R.animator.ladoderecho);
        mov_derecha.reset();
        derecho.startAnimation(mov_derecha);

        mov_izquierda.reset();
        if(tipoUs.equals("oficina")){
            izquierdo2.setVisibility(View.INVISIBLE);
        }else {
            izquierdo2.startAnimation(mov_izquierda);
        }

        if(tipoUs.equals("externo")){
            izquierdo.setVisibility(View.INVISIBLE);
        }else {
            izquierdo.startAnimation(mov_izquierda);
        }

        izquierdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Paso de parametro: tipo=",""+tipoUs);
                Bundle tipo=new Bundle();
                tipo.putString("tipo",tipoUs);
                Intent lanzar_registro=new Intent(alumno.this,regincidenciaA.class).putExtras(tipo).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lanzar_registro);
            }
        });

        derecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cod_op=getIntent().getIntExtra("codigo",0);
                Log.d("bundle1.5",""+cod_op);
                Bundle cod=new Bundle();
                cod.putInt("codigo",cod_op);
                Intent lanzar_registro=new Intent(alumno.this,regrequerimientoA.class).putExtras(cod).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lanzar_registro);
            }
        });

        izquierdo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle cod=new Bundle();
                cod.putString("usuario","alumno");
                Intent lanzar_diagnostico=new Intent(alumno.this,gestionproblemas.class).putExtras(cod).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lanzar_diagnostico);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        // Esto es lo que hace mi botón al pulsar ir a atrás
            AlertDialog.Builder builder = new AlertDialog.Builder(alumno.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(alumno.this);
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
                                        Intent lanzar_sincronizar=new Intent(alumno.this,sincronizar.class);
                                        startActivity(lanzar_sincronizar);
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.MnuOpc2:
                Log.i("ActionBar", "Salir");
                AlertDialog.Builder builder2 = new AlertDialog.Builder(alumno.this);
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
