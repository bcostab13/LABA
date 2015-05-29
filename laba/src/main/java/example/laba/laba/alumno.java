package example.laba.laba;

import android.app.Activity;
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
public class alumno extends Activity{
    Button izquierdo,derecho,izquierdo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        //asociamos
        izquierdo=(Button)findViewById(R.id.buttonOpNIncidenciaA);
        derecho=(Button)findViewById(R.id.buttonNuevoRequerimientoA);
        izquierdo2=(Button)findViewById(R.id.buttonOpDiagnosticoA);


        Animation mov_izquierda;
        mov_izquierda= AnimationUtils.loadAnimation(this, R.animator.ladoizquierdo);
        mov_izquierda.reset();
        izquierdo.startAnimation(mov_izquierda);

        Animation mov_derecha;
        mov_derecha= AnimationUtils.loadAnimation(this,R.animator.ladoderecho);
        mov_derecha.reset();
        derecho.startAnimation(mov_derecha);

        mov_izquierda.reset();
        izquierdo2.startAnimation(mov_izquierda);

        izquierdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cod_op=getIntent().getIntExtra("codigo",0);
                Log.d("bundle1.5",""+cod_op);
                Bundle cod=new Bundle();
                cod.putInt("codigo",cod_op);
                Intent lanzar_registro=new Intent(alumno.this,regincidenciaA.class).putExtras(cod).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
    }
}
