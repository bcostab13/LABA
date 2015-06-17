package example.laba.laba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Brenda on 17/06/2015.
 */
public class Receiver extends ParsePushBroadcastReceiver {

    @Override
    public void onPushOpen(Context context,Intent intent){
        Log.d("Push", "Clicked");
        String uriString = null;
        try {

            JSONObject pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
            uriString = pushData.optString("alert");
            Log.d("pushinfo","alert="+uriString);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(uriString)
                    .setTitle("Alerta de LABA")
                    .setCancelable(false)
                    .setNeutralButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                }
                            });

            AlertDialog alert = builder.create();
            alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alert.show();

        } catch (JSONException e) {
            Log.v("com.parse.ParsePushReceiver", "Unexpected JSONException when receiving push data: ", e);
        }



    }
}
