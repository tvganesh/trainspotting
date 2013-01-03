package com.tvganesh.trainspotting;


import java.util.ArrayList;
import android.os.Bundle;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Context context  = this;
		
	
		SQLiteDatabase db = null;
		super.onCreate(savedInstanceState);
		
		
		SqlOpenHelper helper = new SqlOpenHelper(this);
		
		//Open SQLiteDB
		db = this.openOrCreateDatabase("train_no.db", MODE_PRIVATE, null);
		helper.onCreate(db);
		
		// Add 5 sample trains to the SQLiteDatabase if the list is empty. First time.
		int count = helper.getTrainCount();
		if(count == 0 ){
			helper.addTrain(12658, "Chennai Mail");
			helper.addTrain(12677, "Ernakulam Express");
			helper.addTrain(12952, "Rajdhani Express");
			helper.addTrain(12809, "Howrah Mail");
			helper.addTrain(12786, "Kacheguda Express");
			
		}
		
		
		// Create 3 tabs. Favorites, Locate, About
        TabHost tabHost = getTabHost();
        
        // Favorite trains tab
        TabSpec favspec = tabHost.newTabSpec("Favorites");
        // setting Title and Icon for the Tab
        favspec.setIndicator("Favorites", getResources().getDrawable(R.drawable.star));
        Intent favoritesIntent = new Intent(this, displayTrains.class);
        favspec.setContent(favoritesIntent);
 
        // Locate Train tab
        TabSpec locatespec = tabHost.newTabSpec("Locate");
        locatespec.setIndicator("Locate", getResources().getDrawable(R.drawable.binoculars));
        Intent locateIntent = new Intent(this, locateTrain.class);
        locatespec.setContent(locateIntent);
               
        // About Tab
        TabSpec aboutspec = tabHost.newTabSpec("About");
        aboutspec.setIndicator("About", getResources().getDrawable(R.drawable.help));
        Intent aboutIntent = new Intent(this, about.class);
        aboutspec.setContent(aboutIntent);
 
        // Add TabSpec to TabHost
        tabHost.addTab(favspec); 
        tabHost.addTab(locatespec);
        tabHost.addTab(aboutspec); 
		db.close();
       
	}
	
}
