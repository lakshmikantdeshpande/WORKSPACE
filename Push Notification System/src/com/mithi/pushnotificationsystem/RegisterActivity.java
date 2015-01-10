package com.mithi.pushnotificationsystem;

import static com.mithi.pushnotificationsystem.ConfigManager.SENDER_ID;
import static com.mithi.pushnotificationsystem.ConfigManager.SERVER_URL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {
	DialogManager alert = new DialogManager();
	ConnectionDetector cd;

	EditText txtName;
	EditText txtEmail;

	Button btnRegister;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		cd = new ConnectionDetector(getApplicationContext());

		// Check for internet
		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(RegisterActivity.this,
					"No internet connection", "Please connect to internet !",
					false);
			return;
		}

		// Check GCM Configuration from ConfigManager class
		if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
				|| SENDER_ID.length() == 0) {
			alert.showAlertDialog(RegisterActivity.this,
					"Configuration Error!",
					"Server URL or GCM Sender ID is invalid.", false);
			return;
		}

		txtName = (EditText) findViewById(R.id.txtName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		btnRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String name = txtName.getText().toString();
				String email = txtEmail.getText().toString();

				// form validation
				if (name.trim().length() > 0 && email.trim().length() > 0) {
					// Launch main activity
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);

					// Send registration details to MainActivity
					i.putExtra("name", name);
					i.putExtra("email", email);
					startActivity(i);
					finish();
				} else {
					alert.showAlertDialog(RegisterActivity.this,
							"Registration Error!", "Please enter your details",
							false);
				}
			}
		});
	}

	// Inflate menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	// Handle menu events
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.exitapp:
			finish();
			return true;

		case R.id.settingsmenu:
			// Invoke Settings activity
			Intent intent = new Intent(getApplicationContext(),
					SettingsActivity.class);
			startActivityForResult(intent, 0);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
