package com.tvganesh.trainspotting;



import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class addTrain extends Activity {
	   private EditText text1,text2;
	   private Button button1,button2;
	   SqlOpenHelper helper;
	   ArrayList<String> results = new ArrayList<String>();
	   
	   protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.addtrain);
					
	   }
	   
	    // Save the new train to the SQLiteDB
		public void save(View view) {
			SqlOpenHelper helper = new SqlOpenHelper(this);
			final Context context  = this;
			text1 = (EditText) findViewById(R.id.editText1);
			text2 = (EditText) findViewById(R.id.editText2);
			button1 = (Button) findViewById(R.id.button1);
			button2 = (Button) findViewById(R.id.button2);
			//Get train number
			if(text1.getText().length() == 0){
				Toast.makeText(this,"Please enter a valid number", Toast.LENGTH_LONG).show();
				return;
			}
			
			 // Get Train No & the Train name
		    int trainNo = Integer.parseInt(text1.getText().toString());		   
		    String trainName = text2.getText().toString();
		    //Log.d("Adding","values"+ trainNo + " " + trainName);
		    
		    //Add the train number & train name
		    helper.addTrain(trainNo, trainName);
		    
		    // Switch to the displayTrains activity and display new list
		    Intent intent = new Intent(context, MainActivity.class);
		    startActivity(intent);

		}
		
		// Cancel 
		public void cancel(View view) {
			text1 = (EditText) findViewById(R.id.editText1);
			text2 = (EditText) findViewById(R.id.editText2);
			text1.setText("");
			text2.setText("");
			return;
		}
		
		// Back. Return back to Favorites.
		public void back(View view) {
			final Context context  = this;
		    // Switch to the displayTrains activity and display new list
		    Intent intent = new Intent(context, MainActivity.class);
		    startActivity(intent);
			return;
		}
}