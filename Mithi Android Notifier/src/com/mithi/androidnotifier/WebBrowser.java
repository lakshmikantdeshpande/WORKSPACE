package com.mithi.androidnotifier;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebBrowser extends Activity {

	WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.twitter);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
				Window.PROGRESS_VISIBILITY_ON);
		mWebView = (WebView) findViewById(R.id.MyWebview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.loadUrl("http://www.twitter.com");
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				view.loadUrl("file:///android_asset/offline.htm");
			}

		});

		final Activity MyActivity = this;

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				MyActivity.setTitle("Loading...");
				MyActivity.setProgress(progress * 100);
				if (progress == 100)
					MyActivity.setTitle(R.string.app_name);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menus, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.disclaimer:
			AlertDialog.Builder alertbox1 = new AlertDialog.Builder(this);

			alertbox1.setTitle("DISCLAIMER");

			alertbox1
					.setMessage("All the contents and logos(images) , shown or listed in this application are the sole property of the respective owners. The software/application developers have no control or responsibility of the contents. WE DO NOT HOST OR STREAM THE CONTENTS OR CHANNELS IN THIS APPILICATION. This application provides quick links to channels available over the internet, and it is INTENDED FOR THE PEOPLE, WHO ABIDE THE POLICIES OF RESPECTIVE CONTENT OWNER AND HAVE THE RESPECTIVE RIGHTS TO ACCESS THE CONTENT. We take absolutely no responsibility of disruption of service. We are not affiliated with any of the owners of the videos/streaming content, linked in this application. We urge to all the copyright holders, to recognize the links contained within this app are located somewhere else over the internet. The embedded links only point to the location of the video on the web. We take no responsibility of the content shown, the discontinuation of the service and anything else about the service.");

			alertbox1.setNeutralButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
			alertbox1.show();
			return true;

		case R.id.about:
			AlertDialog.Builder alertbox2 = new AlertDialog.Builder(this);

			alertbox2.setTitle("ABOUT");

			alertbox2
					.setMessage("This application is developed by Lakshmikant D. For any queries,complaints,suggestions,feedback, & claims contact me at: \n\n Email: sd0389@gmail.com \n\n");

			alertbox2.setNeutralButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
			alertbox2.show();
			return true;

		case R.id.exit:
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}