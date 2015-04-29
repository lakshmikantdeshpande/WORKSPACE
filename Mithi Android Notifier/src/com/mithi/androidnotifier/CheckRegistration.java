package com.mithi.androidnotifier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class CheckRegistration extends Activity
{

	SharedPreferences sharedPref;

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
					sleep(1000);		//Show splash screen for one second
				}
				catch(InterruptedException ie)
				{
					//Handler is used to run a process on UI thread (and not in the background)
					handler.post(new Runnable(){
						public void run()
						{
							Toast.makeText(getApplicationContext(), "Couldn't display splash screen !", Toast.LENGTH_SHORT).show();
						}
					});
				}
				finally
				{
					finish();		//Close splash screen
					if (GCMRegistrar.isRegisteredOnServer(getApplicationContext())) {
						//Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();

						handler.post(new Runnable(){
							public void run()
							{
								Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
							}
						});


						//This flag will decide whether introduction screen is to be shown next time or not 
						//INTRO_SHOWN flag will be stored in SharedPreferences

						if((sharedPref.getString("INTRO_SHOWN", null)==null) || (sharedPref.getString("INTRO_SHOWN", null).equals("false"))) //if intro is not shown
						{
							Intent intent=new Intent(getApplicationContext(),ScreenSlideActivity.class);
							startActivity(intent);

							SharedPreferences.Editor editor = sharedPref.edit();
							editor.putString("receive", "true");
							editor.commit();
						}
						else	//if intro is shown
						{
							Intent intent=new Intent(getApplicationContext(),MainActivity.class);
							startActivity(intent);
						}
					}
					
					else		//if user is not registered on server
					{
						handler.post(new Runnable(){
							public void run()
							{
								Toast.makeText(getApplicationContext(), "You have not registered it seems !", Toast.LENGTH_LONG).show();
							}
						});

						//Open Registration Screen
						Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
						startActivity(intent);        

					}

				}
			}
		};
		splashThread.start();

		//  mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		/* Either Preferences XML can be used, or isRegisteredOnServer method to check registration on server    
    // second argument is the default to use if the preference can't be found in sharedPreferences
    Boolean isRegistered = mPrefs.getBoolean(prefs, false);
		 */

	}
}
