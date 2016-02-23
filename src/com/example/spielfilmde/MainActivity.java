package com.example.spielfilmde;

import com.example.sekolahkoding.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity  {

   WebView wv;
   
   @SuppressLint("SetJavaScriptEnabled")
   @Override
   protected void onCreate(Bundle savedInstanceState) {      
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      
      wv = (WebView) findViewById(R.id.webView);
      wv.setWebViewClient(new MyBrowser());
      wv.getSettings().setLoadsImagesAutomatically(true);
      wv.getSettings().setJavaScriptEnabled(true);
      if (isNetworkAvailable(getBaseContext())) {
          wv.loadUrl("http://www.spielfilm.de"); 
      }else{
          String summary = "<html><body>Sie sind nicht mit Internet verbunden!</body></html>";
          wv.loadData(summary, "text/html", null);
      }
     
   }
   
   private class MyBrowser extends WebViewClient {
	   @Override
       public void onPageStarted(WebView view, String url, Bitmap favicon) {
           // TODO Auto-generated method stub
           super.onPageStarted(view, url, favicon);
       }
	   
	   @Override
	   public boolean shouldOverrideUrlLoading(WebView view, String url) {
	      view.loadUrl(url);
	      return true;
       }
  }
   
   @SuppressLint("SetJavaScriptEnabled")
   protected void onResume(Bundle savedInstanceState) {
       super.onResume();
       setContentView(R.layout.activity_main);
       
       wv = (WebView) findViewById(R.id.webView);
       wv.addJavascriptInterface(new WebAppInterface(this), "Android");
       wv.setWebViewClient(new MyBrowser());
       wv.getSettings().setLoadsImagesAutomatically(true);
       wv.getSettings().setJavaScriptEnabled(true);
       if (isNetworkAvailable(getBaseContext())) {
           wv.loadUrl("http://www.spielfilm.de"); 
       }else{
           wv.loadUrl("http://www.google.de"); 
       }
   }
   
   public boolean isNetworkAvailable(final Context context) {
	    final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
	    return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
   }
   
   public class WebAppInterface {
	    Context mContext;

	    /** Instantiate the interface and set the context */
	    WebAppInterface(Context c) {
	        mContext = c;
	    }

	    /** Show a toast from the web page */
	    @JavascriptInterface
	    public void showToast(String toast) {
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    }
	}
   
   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
       // Check if the key event was the Back button and if there's history
       if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
    	   wv.goBack();
           return true;
       }
       // If it wasn't the Back key or there's no web page history, bubble up to the default
       // system behavior (probably exit the activity)
       return super.onKeyDown(keyCode, event);
   }
}