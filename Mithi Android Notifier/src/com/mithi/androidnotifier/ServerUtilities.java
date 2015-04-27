package com.mithi.androidnotifier;

import static com.mithi.androidnotifier.CommonUtilities.SERVER_URL;
import static com.mithi.androidnotifier.CommonUtilities.TAG;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

@SuppressWarnings("unused")

public final class ServerUtilities {
	private static final int MAX_ATTEMPTS = 1;
	private static final int BACKOFF_MILLI_SECONDS = 2000;
	private static final Random random = new Random();

	AlertDialogManager alert = new AlertDialogManager();


	/**
	 * Register this account/device pair within the server.
	 *
	 */
	static void register(final Context context, String email, String password, final String regId) 
	{
		Log.i(TAG, "registering device (regId = " + regId + ")");
		String serverUrl = SERVER_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regId);
		params.put("email", email);
		params.put("password", password);

		long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
		// Once GCM returns a registration id, we need to register on our server
		// As the server might be down, we will retry it a couple
		// times.
		for (int i = 1; i <= MAX_ATTEMPTS; i++) {
			Log.d(TAG, "Attempt #" + i + " to register");
			try {
				// No need to display technical registration progress to the user 
				//  displayMessage(context, context.getString(R.string.server_registering, i, MAX_ATTEMPTS));
				post(serverUrl, params);



				//If POST is successful, we can set setRegisteredOnServer to true
				GCMRegistrar.setRegisteredOnServer(context, true);
				String message = context.getString(R.string.server_registered);
				// CommonUtilities.displayMessage(context, message);

				/*				Handler handler =  new Handler(context.getMainLooper());
			    handler.post( new Runnable(){
			        public void run(){
			        	Toast.makeText(context, "Login Successful !!", Toast.LENGTH_SHORT).show(); 
			        }
			    });*/
				
				Intent intent=new Intent(context,CheckRegistration.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);


				return;
			} catch (IOException e) {
				// in final app, we can retry on unrecoverable errors only..

				Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
				if (i == MAX_ATTEMPTS) {
					break;
				}
				try {
					Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
					Thread.sleep(backoff);
				} 
				catch (InterruptedException ie) {
					Log.d(TAG, "Thread interrupted: abort remaining retries!");
					Thread.currentThread().interrupt();
					return;
				}
				// we can increase backoff time for ensuring registration on the server 
				// backoff *= 2;
			}
		}
		String message = context.getString(R.string.server_register_error,MAX_ATTEMPTS);
		// CommonUtilities.displayMessage(context, message);

				Handler handler =  new Handler(context.getMainLooper());
	    handler.post( new Runnable(){
	        public void run(){
	        	Toast.makeText(context, "Authentication Failure !!", Toast.LENGTH_SHORT).show(); 
	        }
	    });
	    
	    //Restart current app 
		Intent mStartActivity = new Intent(context, RegisterActivity.class);
		int mPendingIntentId = 123456;
		PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
		System.exit(0);

		Toast.makeText(context, "Authentication Failure !!", Toast.LENGTH_SHORT).show();
		
		
	}

	/**
	 * Unregister this account/device pair within the server.
	 */
	static void unregister(final Context context, final String regId) {
		Log.i(TAG, "unregistering device (regId = " + regId + ")");
		String serverUrl = SERVER_URL + "/unregister";
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regId);
		try {
			post(serverUrl, params);
			GCMRegistrar.setRegisteredOnServer(context, false);
			String message = context.getString(R.string.server_unregistered);
			// CommonUtilities.displayMessage(context, message);
		} catch (IOException e) {
			// At this point the device is unregistered from GCM, but still
			// registered in the server.
			// We could try to unregister again, but it is not necessary:
			// if the server tries to send a message to the device, it will get
			// a "NotRegistered" error message and should unregister the device.
			String message = context.getString(R.string.server_unregister_error,
					e.getMessage());
			//  CommonUtilities.displayMessage(context, message);
		}
	}

	/**
	 * Issue a POST request to the server.
	 *
	 * @param endpoint POST address.
	 * @param params request parameters.
	 *
	 * @throws IOException propagated from POST.
	 */
	private static void post(String endpoint, Map<String, String> params)
			throws IOException {    

		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		// constructs the POST body using the parameters
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
			.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();
		Log.v(TAG, "Posting '" + body + "' to " + url);
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			Log.e("URL", "> " + url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			// post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			// handle the response
			int status = conn.getResponseCode();
			Log.d("serverutilities","response code is"+status);
			if (status != 200) {
				throw new IOException("Authentication failed with error code " + status);
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	public static void logout(String endpoint, Map<String, String> params)
			throws IOException {    

		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		// constructs the POST body using the parameters
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
			.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();
		Log.v(TAG, "Posting logout request '" + body + "' to " + url);
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			Log.e("URL", "> " + url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			// post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			// handle the response
			int status = conn.getResponseCode();
			Log.d("serverutilities","response code is"+status);
			if (status != 200) {
				throw new IOException("Could not log out... Response code is :  " + status);
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	
}