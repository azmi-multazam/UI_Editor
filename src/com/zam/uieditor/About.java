package com.zam.uieditor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class About extends Activity{
	WebView webView;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	LayoutInflater inflater = LayoutInflater.from(this);
	View viewInflatedFromXml = inflater.inflate(R.layout.layout_info, null);
	setContentView(viewInflatedFromXml);
	webView = (WebView) findViewById(R.id.webView2);
	webView.getSettings().setJavaScriptEnabled(true);
	webView.loadUrl("file:///android_asset/www/data/about.html");
	
    }
 
}
