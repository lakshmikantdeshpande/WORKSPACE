package com.mithi.androidnotifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class SettingsActivity extends PreferenceActivity {
	private CheckBoxPreference Check;
	SharedPreferences sharedPref;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		sharedPref=getBaseContext().getSharedPreferences("USER_PREFS",Context.MODE_PRIVATE);

		Check=(CheckBoxPreference)findPreference("receive_notifications");

		Check.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {
			public boolean onPreferenceChange(final Preference preference, final Object newValue) {
				if(newValue.toString().equals("true"))
				{		
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("receive", "true");
					editor.commit();}
				else
				{
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("receive", "false");
					editor.commit();
				}
				return true;
			}
		});

		//Back button 
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

}