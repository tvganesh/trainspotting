package com.tvganesh.trainspotting;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;


public class displayTrains extends ListActivity {
	SqlOpenHelper helper;
	ArrayList<String> results = new ArrayList<String>();
	String selectedFromList;
	long selectedItem;
	ArrayAdapter listAdapter;
	Integer[] itemArray;
	ArrayList<Integer> items = new ArrayList<Integer>(); 
	
	 protected void onCreate(Bundle savedInstanceState) {
		   
			super.onCreate(savedInstanceState);
			SqlOpenHelper helper = new SqlOpenHelper(this);	
			int count = helper.getTrainCount();
			
			//Get the list of trains and populate the List
			results = populateResults();	
			
			// Create a List View by passing the List Adapter. Add a checkbox
			listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,results);
			this.setListAdapter(listAdapter);
			ListView lv = getListView();
			lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
			
			lv.setOnItemClickListener(new OnItemClickListener(){
		            @Override
		            public void onItemClick(AdapterView<?> parent, View view, int position,
		                    long id) {
 
		            // Do nothing here. The list of checked items checked in delete
		            // Checking here results in issues when running on read device.

		            }
		        });	 			

	
    }
	 

	 public ArrayList<String> populateResults()
	 {
		    ArrayList<String> r = new ArrayList<String>();  
			SqlOpenHelper helper = new SqlOpenHelper(this);	

			
			//Create a list of trains
			List<Train> train = helper.getAllTrains();       
 
			for (Train tn : train) {
				String log = tn.getTrainName() +"," + tn.getTrainNo();
				r.add(log);
				//Log.d("Trains: ", log);		
			}		 
		return r;
		 
	 }
	 
	@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		      MenuInflater inflater = getMenuInflater();
		      inflater.inflate(R.menu.options_menu, menu);
		      return true;
		}

		 public boolean onOptionsItemSelected(MenuItem item) {
			 Intent intent;
			 int count;
			 SqlOpenHelper helper = new SqlOpenHelper(this);
			 ArrayList<String> r = new ArrayList<String>();
			 final Context context  = this;
		    	switch (item.getItemId()) {
		    	case R.id.add:
                    // Switch to the addTrain Activity
				    intent = new Intent(context, addTrain.class);
				    startActivity(intent);
		    		return true;
		    	case R.id.delete:
		    		ListView lv = getListView();
		    		SparseBooleanArray a = new SparseBooleanArray();
		    		
		    		//Get the total train count 
			    	count = helper.getTrainCount();	
			    	
			    	// Get the count of checked items. This is a sparse array
			    	a = lv.getCheckedItemPositions();	
			    	if(count == 0){
			    		Toast.makeText(this, "Oops.Empty List!",
			            		Toast.LENGTH_SHORT).show();
			    	}			    	
			    	else if (a.size() == 0){ // No checked items?
			    		Toast.makeText(this, "Oops. No items selected.",
			            		Toast.LENGTH_SHORT).show();
			    	}
			    	else { // There are checked items

			    		AlertDialog.Builder builder = new AlertDialog.Builder(context);
			    		// Set title
						builder.setTitle("Confirm delete");
						
						a = lv.getCheckedItemPositions();
					    // Set the  dialog message
				        builder.setMessage("Do you want to delete these " + a.size() + " items?");
			    		
						// Add the  Yes & No buttons
						builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						               // User clicked Yes button						        	   
						        	   // Delete selected items
	                                   delete();
						           }
						       });
						builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						              // User No the dialog						        	  
						        	  // Uncheck the checked items
						        	  uncheck();
						        	  dialog.cancel();
						           }
						       });
						
						// Create the AlertDialog
						AlertDialog dialog = builder.create();
						
						// show it
						dialog.show();	
   
			    	}
		    		
				  	return true;
		    	case R.id.deleteAll:
		
			    	count = helper.getTrainCount();	
			    	if(count == 0){
			    		Toast.makeText(this, "Oops.Empty List!",
			            		Toast.LENGTH_SHORT).show();
			    	}
			    	else {
			    		

			    		AlertDialog.Builder builder = new AlertDialog.Builder(context);
			    		// Set the title
						builder.setTitle("Confirm delete");
						
					    // Set the  dialog message
				        builder.setMessage("Do you want to delete all " + count + " items?");
			    		
						// Add the Yes & No buttons
						builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						               // User clicked Yes button						        	   
	                                   deleteAll();
						           }
						       });
						builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						              // User clicked the No button
						        	  						        	   
						        	  dialog.cancel();
						           }
						       });
						
						// Create the AlertDialog
						AlertDialog dialog = builder.create();
						
						// show it
						dialog.show();
			    		
			    	}
		    		return true;
		    	default:
		    		return super.onOptionsItemSelected(item);
		    	}
		    }
		 
		 public void delete()
		 {
		    SqlOpenHelper helper = new SqlOpenHelper(this);
			ArrayList<String> r = new ArrayList<String>();
		    ListView lv = getListView();
			SparseBooleanArray a = new SparseBooleanArray();
				 
			// Create a sparse array of checked positions
			a = lv.getCheckedItemPositions();
			
			 // Determine the positions which are checked
			 for(int pos=0;pos<lv.getCount(); pos++){
				 
				 //Log.d("val","pos:"+ pos + "  " + a.get(pos));
				 if(a.get(pos)){
					 // If item is checked add it to the items ArrayList
					 items.add(pos);
				 }
			 }
		   
		   //Convert the integer ArrayList to an Integer Array
	       Integer[] itemArray = new Integer[ items.size() ];
	       items.toArray( itemArray );	
	       
    	   //Delete all selected items from SQLiteDatabase by passing in the itemArray
    	   helper.deleteTrains(itemArray);
    	   
    	   // Clear the ArrayList
    	   items.clear();
    	   
	       //Re-populate the list
  		   r = populateResults();		
  		   listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,r);
  		   this.setListAdapter(listAdapter);
  		   listAdapter.notifyDataSetChanged();
 		   lv = getListView();
		   lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		 }
		 
		 public void deleteAll()
		 {
			 SqlOpenHelper helper = new SqlOpenHelper(this);
			 ArrayList<String> r = new ArrayList<String>();	
			 ListView lv = getListView();
			 
			 // Clear the database
	    	 helper.deleteAllTrains(lv.getCount());
	    	 
	    	 // Re-populate the list
	    	 r = populateResults();		
	    	 listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,r);
	    	 this.setListAdapter(listAdapter);
	    	 listAdapter.notifyDataSetChanged();
		 }
		 
		 public void uncheck()
		 {
			 ListView lv = getListView();
			 SparseBooleanArray a = new SparseBooleanArray();
			 long val[] = new long[items.size()];
			 
			 // Get the checked positions in a Sparse Array
			 a = lv.getCheckedItemPositions();
			 
			 for(int i=0;i<lv.getCount(); i++){
				 
				 //Log.d("val","i:"+ i + "  " + a.get(i));
				 // Uncheck the checked positions
				 if(a.get(i)){
					 lv.setItemChecked(i, false);
				 }
			 }
			 
			 // Clear the sparse Array. Clear the ArrayList
			 a.clear();
			 items.clear();
			
		 }

}