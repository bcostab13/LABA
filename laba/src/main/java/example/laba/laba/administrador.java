package example.laba.laba;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by Brenda on 20/04/2015.
 */
public class administrador extends Activity{
    Button izquierdo,derecho,izquierdo2;
    int cod_cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        //asociamos
        izquierdo=(Button)findViewById(R.id.buttonOpNIncidencia);
        derecho=(Button)findViewById(R.id.buttonOpContSolicitud);
        izquierdo2=(Button)findViewById(R.id.buttonOpContLabo);
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
                final CharSequence[] items = {"Android OS", "iOS", "Windows Phone", "Meego"};
                AlertDialog.Builder builder = new AlertDialog.Builder(administrador.this);
                builder.setTitle("Tu OS móvil preferido?");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Intent lanzar_contlab=new Intent(administrador.this,contlab.class);
                        startActivity(lanzar_contlab);
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }
}
