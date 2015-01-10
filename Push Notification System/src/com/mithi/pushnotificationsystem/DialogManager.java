package com.mithi.pushnotificationsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogManager {

	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Set dialog title
		alertDialog.setTitle(title);

		// Set message for dialog
		alertDialog.setMessage(message);

		if (status != null) {
			alertDialog.setIcon(R.drawable.fail);
		}

		// Set okay button for closing dialog
		alertDialog.setButton("Okay", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Show dialog message
		alertDialog.show();
	}
}
