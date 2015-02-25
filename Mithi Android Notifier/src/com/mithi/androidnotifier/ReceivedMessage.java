package com.mithi.androidnotifier;

import android.content.Context;
import android.content.Intent;

public class ReceivedMessage
{
	static final String EXTRA_MESSAGE = "message";
	static final String DISPLAY_MESSAGE_ACTION = "com.mithi.androidnotifier.DISPLAY_MESSAGE";
	
	static void displayMessage(Context context,String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}