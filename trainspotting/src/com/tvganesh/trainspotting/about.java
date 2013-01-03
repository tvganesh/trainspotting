package com.tvganesh.trainspotting;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;
 
public class about extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
 	            
        // Add the trainspot.html in assets/
        WebView webView = (WebView) findViewById(R.id.trainspot); 
        webView.loadUrl("file:///android_asset/trainspot.html");
  
    }
}