package com.morphoss.xo.memorize.settings;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.morphoss.xo.memorize.R;

public class WebViewActivity extends Activity {
	/**
	 * This class displays a webview for the credits.html
	 */
	private WebView webView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		if(Locale.getDefault().getLanguage().contains("fr")){
			webView.loadUrl("file:///android_asset/help_fr.html");
		}else{
		webView.loadUrl("file:///android_asset/help.html");
		}
	}
}