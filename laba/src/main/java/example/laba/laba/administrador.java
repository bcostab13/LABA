package example.laba.laba;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by Brenda on 20/04/2015.
 */
public class administrador extends Activity{
    Button izquierdo,derecho,izquierdo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        //asociamos
        izquierdo=(Button)findViewById(R.id.buttonprueba);
        derecho=(Button)findViewById(R.id.buttonprueba2);
        izquierdo2=(Button)findViewById(R.id.buttonprueba3);

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
    }
}
