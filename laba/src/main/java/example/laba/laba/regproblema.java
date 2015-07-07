package example.laba.laba;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Brenda on 22/06/2015.
 */
public class regproblema extends Activity{
    Button bSelCat,bSiguiente;
    EditText tCateg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regproblema);

        //asociamos los elementos
        bSelCat=(Button)findViewById(R.id.buttonSelectCat);
        bSiguiente=(Button)findViewById(R.id.buttonSiguiente);
        tCateg=(EditText)findViewById(R.id.editCateg);

        bSelCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Soporte al Usuario", "Soporte al Hardware",
                        "Soporte al Software", "Soporte a la Red"};
                AlertDialog.Builder builder = new AlertDialog.Builder(regproblema.this);
                builder.setTitle("Categorías");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        tCateg.setText(items[item]);
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        bSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tCateg.getText().toString().equals("Soporte al Usuario")){
                    finish();
                    Intent lanzar_soporteusuario=new Intent(regproblema.this,soporteusuario.class);
                    startActivity(lanzar_soporteusuario);
                }else if(tCateg.getText().toString().equals("Soporte al Software")){
                    finish();
                    Intent lanzar_soportesoftware=new Intent(regproblema.this,soportesoftware.class);
                    startActivity(lanzar_soportesoftware);
                }else if(tCateg.getText().toString().equals("Soporte al Hardware")){
                    finish();
                    Intent lanzar_soportehardware=new Intent(regproblema.this,soportehardware.class);
                    startActivity(lanzar_soportehardware);
                }else if(tCateg.getText().toString().equals("Soporte a la Red")){
                    finish();
                    Intent lanzar_soportered=new Intent(regproblema.this,soportered.class);
                    startActivity(lanzar_soportered);
                }
            }
        });
    }
}
