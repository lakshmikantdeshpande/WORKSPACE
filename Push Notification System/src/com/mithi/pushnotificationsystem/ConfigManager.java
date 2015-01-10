package com.mithi.pushnotificationsystem;

import android.content.Context;
import android.content.Intent;

public final class ConfigManager {

	// Log messages
	static final String TAG = "GCM Demo";

	static final String SERVER_URL = "http://100.72.73.147/gcm/register.php";

	// Google project id
	static final String SENDER_ID = "69552156334";

	static final int MAX_ATTEMPTS = 5;

	static final int BACKOFF_MILLI_SECONDS = 2000;

	static final String DISPLAY_MESSAGE_ACTION = "com.mithi.pushnotificationsystem.DISPLAY_MESSAGE";

	static final String EXTRA_MESSAGE = "message";

	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}
