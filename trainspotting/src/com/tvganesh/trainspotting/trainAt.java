package com.tvganesh.trainspotting;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;
 
public class trainAt extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainat);
        
        // Receive the passed parameters
        Bundle b = getIntent().getExtras();
        int trainNo = Integer.parseInt(b.getString("train").toString());
        String value = b.getString("day").toString();

		// Invoke the web with passed parameters
		String url = "http://www.spoturtrain.com/status.php?tno=" + trainNo + "&date=" +value;  
		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl(url);	            
 
	
  
    }
}