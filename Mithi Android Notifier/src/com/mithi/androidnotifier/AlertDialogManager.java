package com.mithi.androidnotifier;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
@SuppressWarnings("deprecation")
public class AlertDialogManager extends Activity {

	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// To set dialog Title
		alertDialog.setTitle(title);

		// To set dialog Message
		alertDialog.setMessage(message);

		if(status != null)
			alertDialog.setIcon(R.drawable.fail);

		// To Set OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;

			}
		});


		// To show alert dialog
		alertDialog.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

}