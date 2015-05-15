package example.laba.laba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class main extends ActionBarActivity {
    ImageView iconoPrincipal;
    TextView textoBienvenida;
    Button botonIniciar;
    EditText edituser,editpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //asociaciones
        iconoPrincipal=(ImageView)findViewById(R.id.imageView_inicial);
        textoBienvenida=(TextView)findViewById(R.id.textView_bienvenida);
        botonIniciar=(Button)findViewById(R.id.button);
        edituser=(EditText)findViewById(R.id.editText_user);
        editpassword=(EditText)findViewById(R.id.editText_pass);

        //agregar animacion inicial
        Animation mostrar;
        mostrar= AnimationUtils.loadAnimation(this,R.animator.anim_inicio);
        mostrar.reset();
        iconoPrincipal.startAnimation(mostrar);
        textoBienvenida.startAnimation(mostrar);

        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edituser.getText().toString().equals("alumno")){
                    Intent iniciarAlumno=new Intent(main.this,alumno.class);
                    startActivity(iniciarAlumno);
                }else if(edituser.getText().toString().equals("admi")){
                    Intent iniciarAdmi=new Intent(main.this,administrador.class);
                    startActivity(iniciarAdmi);
                }
            }
        });

        Tag tag = new Tag("hola");
        tag.save();
        List<Tag> lista=Tag.listAll(Tag.class);
        Log.d("Sugar","elemento="+lista.get(0));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
