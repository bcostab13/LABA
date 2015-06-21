package example.laba.laba;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Brenda on 06/06/2015.
 */
public class bienvenido extends Activity{
    ImageView iconoPrincipal;
    TextView textoBienvenida;
    LinearLayout contenedor;
    Button bContinuar,bCerrar;
    int codigo=0;
    UsuarioGeneral usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bienvenido);

        //asociaciones
        iconoPrincipal=(ImageView)findViewById(R.id.imageView_inicial);
        textoBienvenida=(TextView)findViewById(R.id.textView_bienvenida);
        contenedor=(LinearLayout)findViewById(R.id.contenedorBienvenida);
        bContinuar=(Button)findViewById(R.id.buttonContinuar);
        bCerrar=(Button)findViewById(R.id.buttonCerrar);
        List<UsuarioGeneral> listaUser=UsuarioGeneral.listAll(UsuarioGeneral.class);
        usuarioActual=listaUser.get(0);
        String nomaux="";
        for (int i=0;i<usuarioActual.getNombre().length();i++){
            if (usuarioActual.getNombre().charAt(i) == ' ') {
                break;
            }
            nomaux+=usuarioActual.getNombre().charAt(i);
        }
        textoBienvenida.setText("Hola de nuevo, "+nomaux);
        contenedor.setGravity(Gravity.CENTER);

        //agregar animacion inicial
        Animation mostrar;
        mostrar= AnimationUtils.loadAnimation(this, R.animator.anim_inicio);
        mostrar.reset();
        iconoPrincipal.startAnimation(mostrar);
        textoBienvenida.startAnimation(mostrar);

        //asignamos evento al boton continuar
        bContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //iniciar conteo de solicitudes
                try {
                    List<Marca> lista = Marca.listAll(Marca.class);
                    //Tag.deleteAll(Tag.class);
                    codigo = lista.get(0).getCode();
                }catch (Exception e){
                    Marca marca=new Marca(0);
                    marca.save();
                    List<Marca> lista = Marca.listAll(Marca.class);
                    codigo = lista.get(0).getCode();
                }
                Log.d("Codigo", "elemento=" + codigo);

                //lanzar actividad
                Bundle cod=new Bundle();
                cod.putInt("codigo",codigo);

                //enviando tipo de usuario
                String tip=usuarioActual.getTipo();
                Bundle tipUs=new Bundle();
                tipUs.putString("tipo",tip);

                if(usuarioActual.getTipo().equals("alumno")||usuarioActual.getTipo().equals("oficina")){
                    Log.d("bundle1", "" + codigo);
                    finish();
                    Intent iniciarAlumno=new Intent(bienvenido.this,alumno.class).putExtras(cod).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    iniciarAlumno.putExtras(tipUs);
                    startActivity(iniciarAlumno);
                }else if(usuarioActual.getTipo().equals("administrador")){
                    finish();
                    Intent iniciarAdmi=new Intent(bienvenido.this,administrador.class).putExtras(cod).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(iniciarAdmi);
                }

            }
        });

        //asignamos evento al boton cerrar
        bCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(bienvenido.this);
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
                                        Intent lanzar_sincronizar=new Intent(bienvenido.this,sincronizar.class);
                                        startActivity(lanzar_sincronizar);
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
}
