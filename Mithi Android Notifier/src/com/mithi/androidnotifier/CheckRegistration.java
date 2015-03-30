package com.mithi.androidnotifier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class CheckRegistration extends Activity
{

	SharedPreferences sharedPref;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		sharedPref=getBaseContext().getSharedPreferences("USER_PREFS",Context.MODE_PRIVATE);

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
						public void run()
						{
							Toast.makeText(getApplicationContext(), "Couldn't display splash screen !", Toast.LENGTH_SHORT).show();
						}
					});
				}
				finally
				{
					finish();
					if (GCMRegistrar.isRegisteredOnServer(getApplicationContext())) {
						//Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();

						handler.post(new Runnable(){
							public void run()
							{
								Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
							}
						});

						
						//It will check whether the splash screen has been displayed before or not
						if(sharedPref.getString("INTRO_SHOWN", null)==null)
						{
							Intent intent=new Intent(getApplicationContext(),ScreenSlideActivity.class);
							startActivity(intent);
						}
						else
						{
							Intent intent=new Intent(getApplicationContext(),MainActivity.class);
							startActivity(intent);
						}
					}
					else
					{
						// we can launch new activity from here too...
						// Intent intent=new Intent(getApplicationContext(),ClassName.class)
						//startActivity(intent)
						//For now, lets have some toast
						//Toast.makeText(getApplicationContext(), "You have not registered it seems !", Toast.LENGTH_LONG).show();

						handler.post(new Runnable(){
							public void run()
							{
								Toast.makeText(getApplicationContext(), "You have not registered it seems !", Toast.LENGTH_LONG).show();
							}
						});

						// Intent intent=new Intent(getApplicationContext(),ScreenSlideActivity.class);
						Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
						startActivity(intent);        


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
