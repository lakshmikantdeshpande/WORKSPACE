package com.mithi.androidnotifier;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
    
	//This should be a hard-coded URL of MITHI servers
   // Registration URL
   //static final String SERVER_URL = "http://192.168.188.1/gcmdemo/register.php";
   static final String SERVER_URL = "http://192.168.43.194/gcmdemo/register.php";
   static final String LOGOUT_URL = "http://192.168.43.194/gcmdemo/logout.php";

   // Google project id ... to be created by Mithi
   static final String SENDER_ID = "69552156334"; 

   static final String TAG = "Mithi GCM";

   static final String DISPLAY_MESSAGE_ACTION = "com.mithi.androidnotifier.DISPLAY_MESSAGE";

   static final String EXTRA_MESSAGE = "message";

   //Used to display received message as a text on screen...THIS METHOD IS NOT USED ANYMORE IN THE CODE 
   static void displayMessage(Context context, String message) {
       Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
       intent.putExtra(EXTRA_MESSAGE, message);
       context.sendBroadcast(intent);
   }
}