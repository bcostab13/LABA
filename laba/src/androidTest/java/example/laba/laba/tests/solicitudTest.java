package example.laba.laba.tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

import example.laba.laba.R;
import example.laba.laba.regincidencia;

/**
 * Created by Brenda on 12/07/2015.
 */
public class solicitudTest extends ActivityInstrumentationTestCase2<regincidencia> {

    public solicitudTest(){
        super(regincidencia.class);

    }

    public void testActivityExists() {
        Activity rinc = getActivity();
        assertNotNull(rinc);
    }

    public void testEnviarSimple(){
        Activity rinc = getActivity();
        final EditText det=(EditText)rinc.findViewById(R.id.desc);
        Button btEnviar =
                (Button) rinc.findViewById(R.id.buttonRegistrar);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                det.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Problema grave");


        TouchUtils.clickView(this, btEnviar);

    }

    public void testEnviarMultiple(){
        Activity rinc = getActivity();
        final EditText det=(EditText)rinc.findViewById(R.id.desc);
        Button btEnviar =
                (Button) rinc.findViewById(R.id.buttonRegistrar);

        for(int i=0;i<20;i++) {

            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {
                    det.requestFocus();
                }
            });

            getInstrumentation().waitForIdleSync();
            getInstrumentation().sendStringSync("Problema grave");


            TouchUtils.clickView(this, btEnviar);
        }
    }

}
