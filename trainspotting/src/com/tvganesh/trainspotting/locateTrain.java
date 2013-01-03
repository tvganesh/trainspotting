package com.tvganesh.trainspotting;

 
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
 
public class locateTrain extends Activity {
	Spinner sp1,sp2;
	SqlOpenHelper helper;
	String[]  dayArray = {"today","yesterday","2 days back","3 days back","4 days back","5 days back"};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_train);  	
        ArrayList<String> r = new ArrayList<String>(); 
        SqlOpenHelper helper = new SqlOpenHelper(this);	
        List<Train> train = helper.getAllTrains();
        
        // Create an ArrayList of trains
        for (Train tn : train) {
        	String log = tn.getTrainNo() + "," + tn.getTrainName();
        	r.add(log);
        }
        
        // Create a String array of trains
        String[] trainArray = new String[ r.size() ];
        r.toArray( trainArray );
        
        // Create an ArrayAdapter by passing trainArray
		ArrayAdapter<String>  trainAdapter = new ArrayAdapter<String> (this, 
		android.R.layout.simple_spinner_item,trainArray);
		sp1 = (Spinner) findViewById(R.id.spinner1);
		
		// Set the spinner to the ArrayAdapter
        sp1.setAdapter(trainAdapter);
        
		ArrayAdapter<String>  dayAdapter = new ArrayAdapter<String> (this, 
		android.R.layout.simple_spinner_item,dayArray);
		sp2 = (Spinner) findViewById(R.id.spinner2);
		
		// Set the spinner to the dayAdapter
        sp2.setAdapter(dayAdapter);
        
        
    }
    
    public void onResume() {
    	
    	super.onResume();
        setContentView(R.layout.locate_train);  	
        ArrayList<String> r = new ArrayList<String>(); 
        SqlOpenHelper helper = new SqlOpenHelper(this);	
        List<Train> train = helper.getAllTrains();
        
        // Create an ArrayList of trains
        for (Train tn : train) {
        	String log = tn.getTrainNo() + "," + tn.getTrainName();
        	r.add(log);
        }
        
        // Create a String array of trains
        String[] trainArray = new String[ r.size() ];
        r.toArray( trainArray );
        
        // Create an ArrayAdapter by passing trainArray
		ArrayAdapter<String>  trainAdapter = new ArrayAdapter<String> (this, 
		android.R.layout.simple_spinner_item,trainArray);
		sp1 = (Spinner) findViewById(R.id.spinner1);
		
		// Set the spinner to the ArrayAdapter
        sp1.setAdapter(trainAdapter);
        
		ArrayAdapter<String>  dayAdapter = new ArrayAdapter<String> (this, 
		android.R.layout.simple_spinner_item,dayArray);
		sp2 = (Spinner) findViewById(R.id.spinner2);
		
		// Set the spinner to the dayAdapter
        sp2.setAdapter(dayAdapter);
    	
    }
    
    
	public void locate(View view) {
		final Context context  = this;
		String dayValue = "";
		sp1 = (Spinner) findViewById(R.id.spinner1);
		sp2 = (Spinner) findViewById(R.id.spinner2);
		int item1 = sp1.getSelectedItemPosition();		
		int item2 = sp2.getSelectedItemPosition();
		
		String value = (String) sp1.getItemAtPosition(item1);
		if (value == null)
		{
    		Toast.makeText(this, "Empty List! Please add to list and try again.",
            		Toast.LENGTH_SHORT).show();
		    // Switch to the displayTrains activity and display new list
		    Intent intent = new Intent(context, MainActivity.class);
		    startActivity(intent);
			return;
		}
		else { 
			String train_tokens[] = value.split(",");

		
			if(item2 == 0)
				dayValue = "0";
			else if (item2 == 1 )
				dayValue = "-1";
			else if (item2 == 2)
				dayValue = "-2";
			else if (item2 == 3)
				dayValue = "-3";
			else if(item2 == 4)
				dayValue = "-4";
			else if(item2 == 5)
				dayValue = "-5";
		

			Intent intent = new Intent(context, trainAt.class);	
	    
			//Setup to pass parameters to new activity
	    
			// Pass the train & the day to the trainAt Activity
			Bundle b = new Bundle();
			b.putString("train", train_tokens[0]); 
			b.putString("day", dayValue);
			intent.putExtras(b);
			startActivity(intent);
		}
	}
		
}
