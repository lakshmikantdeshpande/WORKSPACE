package com.mithi.androidnotifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class OpenApps extends Activity  {
	
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		String receivedmessage=getIntent().getStringExtra("message");		

		//Here, we need to know exact package name to get main intent of that application
		//The package name is available on Google Play URL at the end of the URL
		
		Intent k9 = getPackageManager().getLaunchIntentForPackage("com.fsck.k9");
		Intent acal=getPackageManager().getLaunchIntentForPackage("com.morphoss.acal");
		
		if (receivedmessage != null && !receivedmessage.isEmpty()) 
		{
			if(receivedmessage.equals("New Email"))
			{
				Log.d("ReceivedMessage",receivedmessage);
				if(k9!=null)
				{
					startActivity(k9);
					finish();					
				}
				else
				{
					Toast.makeText(getApplicationContext(),"It seems that K-9 Mail is not installed",Toast.LENGTH_LONG).show();
					finish();
				}

			}
			else if(receivedmessage.equals("New Event"))
			{
				Log.d("ReceivedMessage",receivedmessage);
				if(acal!=null)
				{
					startActivity(acal);
					finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(),"It seems that aCal CalDAV Client is not installed",Toast.LENGTH_LONG).show();
					finish();
				}
			}
			else
			{
				//TODO : Check whether other notification is simply displayed in the browser...
			//	Context context=getApplicationContext();
			//	displayMessage(context,receivedmessage);
				Log.d("ReceivedMessage",receivedmessage);
				alert.showAlertDialog(OpenApps.this, "Received message !", receivedmessage, false);
			}
		}
        //finish();
		
	}
}
