package com.mithi.androidnotifier;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.content.ComponentName; 

import com.google.android.gcm.GCMBaseIntentService;
 

import com.mithi.androidnotifier.R;

import static com.mithi.androidnotifier.CommonUtilities.SENDER_ID;
import static com.mithi.androidnotifier.CommonUtilities.displayMessage;
 
public class GCMIntentService extends GCMBaseIntentService {
	
    private static final String TAG = "GCMIntentService";
 
    public GCMIntentService() {
        super(SENDER_ID);
    }
 
    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
       // displayMessage(context, "Your device has been registered with GCM");
        Toast.makeText(context, "Your device has been registered with GCM", Toast.LENGTH_SHORT).show();
        Log.d("Email :", RegistrationActivity.email);
        ServerUtilities.register(context, RegistrationActivity.email, RegistrationActivity.password, registrationId);
    }
 
    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Your device has been unregistered from GCM");
        //displayMessage(context, getString(R.string.gcm_unregistered));
        Toast.makeText(context, "Your device has been unregistered from GCM", Toast.LENGTH_SHORT).show();
        ServerUtilities.unregister(context, registrationId);
    }
 

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        
        //price field is used to send message through the web
        String message = intent.getExtras().getString("price"); 
        //displayMessage(context, message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        
        generateNotification(context, message);
    }

    
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        //displayMessage(context, message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        
        generateNotification(context, message);
    }

    
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        //displayMessage(context, getString(R.string.gcm_error, errorId));
        Toast.makeText(context, "GCM Error !", Toast.LENGTH_SHORT).show();
    }
 
    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        //displayMessage(context, getString(R.string.gcm_recoverable_error,errorId));
        Toast.makeText(context, "GCM recoverable error !", Toast.LENGTH_SHORT).show();
        return super.onRecoverableError(context, errorId);
    }
 
    // Issue notification in notification bar
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
         
        String title = context.getString(R.string.app_name);
         
        
        // It will enable us to open K9 or aCal or any other application 
        Intent notificationIntent = new Intent(context, OpenApps.class);
        // Set message to check whether a mail or calender notification has been received 
        notificationIntent.putExtra("message", message);
        
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, (int)System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        //FLAG_UPDATE_CURRENT will allow passing parameters 
        
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
         
        //adding LED lights to notification
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.ledARGB = 0xff00ff00;
        notification.ledOnMS = 300;
        notification.ledOffMS = 1000;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
         
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        
        //We can set different notification ID for different notifications...
        notificationManager.notify((int)when, notification);
        
 
    }
 
}