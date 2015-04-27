package com.mithi.androidnotifier;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;


public class Logout extends Activity {
	SharedPreferences sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logout);
		
		//to avoid network on main thread exception
		enableStrictMode();
		//actual logout function
		logout(getApplicationContext());			

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu on the UI
		getMenuInflater().inflate(R.menu.logout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//This method gets called when a menu option is clicked.. 
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//Used to get rid of "Network on main thread exception"
	
	public void enableStrictMode()
	{
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	
	public void logout(Context context)
	{
		sharedPref=context.getSharedPreferences("USER_PREFS",Context.MODE_PRIVATE);

		String server_url=CommonUtilities.LOGOUT_URL;
		String regid=GCMRegistrar.getRegistrationId(context);
		
		if (regid==null)
			Log.d("logout", "logout: GCM ID =null");
		else
			Log.d("logout", regid);

		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regid);
		
		if(regid!=null)
		{
			try{
				ServerUtilities.logout(server_url, params);
				Log.d("word","no");
				//reset all sharedpreferences flags
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString("receive_notifications", "true");
				editor.putString("navigation_learned", "false");
				editor.putString("INTRO_SHOWN", "false");
				editor.putString("receive", "true");
				GCMRegistrar.unregister(context);
				GCMRegistrar.setRegisteredOnServer(context, false);
				editor.commit();
				Toast.makeText(context, "Logged out successfully !!", Toast.LENGTH_SHORT).show();
				finish();
				
			    //Restart current app 
				Intent mStartActivity = new Intent(context, CheckRegistration.class);
				int mPendingIntentId = 123456;
				PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
				AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
				mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
				System.exit(0);
			}
			catch(Exception e)
			{
				Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
				finish();				
			}

		}
		else
		{
			Log.d("logout","logout: could not log out");
		}


	}
}
