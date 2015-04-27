package com.mithi.androidnotifier;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks 
{

	private NavigationDrawerFragment mNavigationDrawerFragment;

	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();

		switch (position+1) {
		case 1:
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,
					PlaceholderFragment.newInstance(position + 1)).commit();
			break;
		case 2:
			fragmentManager.beginTransaction()
			.replace(R.id.container,
					SecondFragment.newInstance(position + 1)).commit();
			break;
		}
	}

	
	//Store screen title
	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) 
		{
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		
		//MainActivity menu on-tap actions are defined here 
		switch(id)
		{
		case R.id.action_settings:
			Intent intent=new Intent(this,SettingsActivity.class);
			startActivity(intent);
			return true;
				
		case R.id.action_example:
			Intent intent1=new Intent(this,WebBrowser.class);
			Bundle bundle1=new Bundle();
			bundle1.putString("website","http://www.mithi.com");
			intent1.putExtras(bundle1);
			startActivity(intent1);
			return true;
		
		case R.id.action_logout:
			Intent intent2=new Intent(this,Logout.class);
			startActivity(intent2);
			return true;
		
		case R.id.action_quit:
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	//Simple class for main fragment screen 
	public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
		Button button1;

		private static final String ARG_SECTION_NUMBER = "section_number";

		
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			button1=(Button)rootView.findViewById(R.id.button1);
			button1.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}

		
		//On button tap, one entry will be added to the database for simulation of new notification 
		@Override
		public void onClick(View v) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String currentDateandTime = sdf.format(new Date());
			DatabaseHandler dbh=new DatabaseHandler("Hello",currentDateandTime);
			dbh.save();
		}


	}

}
