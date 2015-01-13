package com.example.android.animationsdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class CheckRegistration extends Activity
{

SharedPreferences mPrefs;
final String prefs = "isRegistered";

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

    // second argument is the default to use if the preference can't be found
    Boolean isRegistered = mPrefs.getBoolean(prefs, false);

    if (!isRegistered) {
        // we can launch new activity from here too...
    	// Intent intent=new Intent(getApplicationContext(),ClassName.class)
    	//startActivity(intent)
    	//For now, lets have some toast
        Toast.makeText(getApplicationContext(), "You have not registered it seems !", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(prefs, true);
        editor.commit();
        finish();
    	}
    }

}
