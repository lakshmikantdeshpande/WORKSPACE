package com.mithi.pushnotificationsystem;

import static com.mithi.pushnotificationsystem.ConfigManager.SENDER_ID;
import static com.mithi.pushnotificationsystem.ConfigManager.displayMessage;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(SENDER_ID);
	}

	// This method gets called when device is registered...
	@Override
	protected void onRegistered(Context context, String registrationId) {
		// Log the registration status
		Log.i(TAG, "Device registered: regId = " + registrationId);
		Log.d("NAME", MainActivity.name);
		displayMessage(context, "GCM registration was successful !!");

		ServerUtilities.register(context, MainActivity.name,
				MainActivity.email, registrationId);
	}

	// Called when device is unregistered
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		displayMessage(context, getString(R.string.gcm_unregistered));
		ServerUtilities.unregister(context, registrationId);
	}

	// New message
	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		String message = intent.getExtras().getString("price");
		displayMessage(context, message);

		// Notify user
		generateNotification(context, message);
	}

	// Deleted push notification
	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		displayMessage(context, message);

		// notifies user
		generateNotification(context, message);
	}

	// Error handling
	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Log.i(TAG, "Received recoverable error: " + errorId);
		displayMessage(context,
				getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

	// Notify user
	private static void generateNotification(Context context, String message) {
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);
		String title = context.getString(R.string.app_name);

		// set intent, so new activity is not started
		Intent notificationIntent = new Intent(context, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, title, message, intent);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;

		// notification.sound = Uri.parse("android.resource://" +
		// context.getPackageName() + "mithi.mp3");
		notification.defaults |= Notification.DEFAULT_VIBRATE;

		// New Notification ID for each notification, so previous notification
		// doesn't get overlapped...
		int id = (int) System.currentTimeMillis();
		notificationManager.notify(id, notification);

	}

}
