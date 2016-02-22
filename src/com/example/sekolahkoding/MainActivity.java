package com.example.sekolahkoding;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity  {
   
   WebView wv;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {      
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      
      wv = (WebView) findViewById(R.id.webView);
      wv.setWebViewClient(new MyBrowser());
      wv.getSettings().setLoadsImagesAutomatically(true);
      wv.getSettings().setJavaScriptEnabled(true);
      wv.loadUrl("https://www.sekolahkoding.com"); 
     
   }
   
   private class MyBrowser extends WebViewClient {
	      @Override
	      public boolean shouldOverrideUrlLoading(WebView view, String url) {
	         view.loadUrl(url);
	         return true;
	      }
  }
}