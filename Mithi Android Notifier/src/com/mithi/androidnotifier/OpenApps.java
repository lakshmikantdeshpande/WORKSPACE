package com.mithi.androidnotifier;

import static com.mithi.androidnotifier.CommonUtilities.EXTRA_MESSAGE;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class OpenApps extends Activity  {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		String receivedmessage=getIntent().getStringExtra("message");		

		//Here, we need to know exact package name to get main intent of that application
		Intent k9 = getPackageManager().getLaunchIntentForPackage("com.fsck.k9");
		Intent acal=getPackageManager().getLaunchIntentForPackage("com.morphoss.acal");
		
		if (receivedmessage != null && !receivedmessage.isEmpty()) 
		{
			if(receivedmessage.equals("New Email"))
			{

				startActivity(k9);
				finish();
			}
			else if(receivedmessage.equals("New Event"))
			{
				startActivity(acal);
				finish();	
			}
		}
        finish();
		
	}
}
