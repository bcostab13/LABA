package example.laba.laba;

import com.orm.SugarApp;
import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Brenda on 09/06/2015.
 */
public class App extends SugarApp{

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "jnzgcYO5pmbt2iLsqPLU39MuUtweFDPf0L5kp7gn", "OLFZlwSisplR0hGMMX80gpouwvy2JzANxsy0ZzCU");
        ParseInstallation instalacion=ParseInstallation.getCurrentInstallation();
        instalacion.saveInBackground();

    }

}
