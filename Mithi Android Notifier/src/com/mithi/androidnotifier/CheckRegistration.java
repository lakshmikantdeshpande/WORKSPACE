package com.mithi.androidnotifier;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
//import android.preference.PreferenceManager;
import android.widget.Toast;

public class CheckRegistration extends Activity
{

SharedPreferences mPrefs;
final String prefs = "isRegistered";

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash);
    
    final Handler handler=new Handler();
        
    Thread splashThread = new Thread()
    {
    	@Override
    	public void run()
    	{
    		try
    		{
    			sleep(1000);
    		}
    		catch(InterruptedException ie)
    		{
    			handler.post(new Runnable(){
    				@Override
					public void run()
    				{
    					Toast.makeText(getApplicationContext(), "Some kind of error has been occured !", Toast.LENGTH_SHORT).show();
    				}
    			});
    		}
    		finally
    		{
    			finish();
    			 if (GCMRegistrar.isRegisteredOnServer(getApplicationContext())) {
    			    	//Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();

    				 handler.post(new Runnable(){
    					 @Override
						public void run()
    					 {
    						 Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
    					 }
    				 });
    				 Intent intent=new Intent(getApplicationContext(),ScreenSlideActivity.class);
    				 startActivity(intent);
    			 }
    			 else
    			 {
    				 // we can launch new activity from here too...
    				 // Intent intent=new Intent(getApplicationContext(),ClassName.class)
    				 //startActivity(intent)
    				 //For now, lets have some toast
    				 //Toast.makeText(getApplicationContext(), "You have not registered it seems !", Toast.LENGTH_LONG).show();
    				 
    				 handler.post(new Runnable(){
    					 @Override
						public void run()
    					 {
    						 Toast.makeText(getApplicationContext(), "You have not registered it seems !", Toast.LENGTH_LONG).show();
    					 }
    				 });
    				 
    				 Intent intent=new Intent(getApplicationContext(),ScreenSlideActivity.class);
    				 //Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
    				 startActivity(intent);        
    				 /*      SharedPreferences.Editor editor = mPrefs.edit();
    			        editor.putBoolean(prefs, true);
    			        editor.commit();
    			        finish();
    				  */    	
    			 }

    		}
    	}
    };
    splashThread.start();
  
    //  mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

/* Either Preferences XML can be used, or isRegisteredOnServer method to check registration on server    
    // second argument is the default to use if the preference can't be found
    Boolean isRegistered = mPrefs.getBoolean(prefs, false);
*/

}
}
