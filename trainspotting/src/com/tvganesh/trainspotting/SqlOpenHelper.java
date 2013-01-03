package com.tvganesh.trainspotting;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlOpenHelper extends SQLiteOpenHelper {
	 // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "train_no.db";
 
    // Contacts table name
    private static final String TRAINS_TABLE = "trains_table";
 
    // Contacts Table Columns names
    private static final String TRAIN_ID = "id";
    private static final String TRAIN_NO = "number";
    private static final String TRAIN_NAME = "name";
    
    public SqlOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
    }
 
    public void createDatabase(SQLiteDatabase db) {
    	//Create TRAIN_TABLE
		String CREATE_TRAIN_TABLE = "CREATE TABLE IF NOT EXISTS " +
                        TRAINS_TABLE + "(" +  TRAIN_ID + " INTEGER PRIMARY KEY," + 
                        TRAIN_NO + " INTEGER , " + TRAIN_NAME + " TEXT" + ")";               
      db.execSQL(CREATE_TRAIN_TABLE);
     }
    
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TRAINS_TABLE);
 
        // Create tables again
        onCreate(db);
    }
    
    // Add a train method
    public void addTrain(int trainNo,String trainName) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(TRAIN_NO, trainNo); 
        values.put(TRAIN_NAME, trainName); 
       
        // Insert a new  row
        db.insert(TRAINS_TABLE, null, values);   
        db.close(); // Closing database connection
    }
    
    // Getting all trains in SQLiteDB
    public List<Train> getAllTrains() {
    	//Create a List 
        List<Train> trainList = new ArrayList<Train>();
        
        // Create the "Select query"
        String selectQuery = "SELECT  * FROM " + TRAINS_TABLE;
 
        //Open the DB in read mode
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // loop through all rows and add trains to the List
        if (cursor.moveToFirst()) {
            do {
                Train train = new Train();
                train.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TRAIN_ID))));
                train.setTrainNo(cursor.getString(cursor.getColumnIndex(TRAIN_NO)));
                train.setTrainName(cursor.getString(cursor.getColumnIndex(TRAIN_NAME)));
                // Adding contact to list
                trainList.add(train);
            } while (cursor.moveToNext());
        }
        //Close DB
        db.close();
        // return the train list
        return trainList;
    }


	
  // Get count of trains
    public int getTrainCount() {
    	int count;
    	//Create SQL Query
        String countQuery = "SELECT  * FROM " + TRAINS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();
        db.close();
        return count; //Return the count of trains
    }
	
   // Update a single train
   public int updateTrain(Train train) {
    SQLiteDatabase db = this.getWritableDatabase();
 
    ContentValues values = new ContentValues();
    values.put(TRAIN_NO, train.getTrainNo());
    values.put(TRAIN_NAME, train.getTrainName());
 
    // Update a single row
    return db.update(TRAINS_TABLE, values, TRAIN_ID + " = ?",
            new String[] { String.valueOf(train.getID()) });
  }

    // Delete a  single train
    public void deleteTrain(Train train) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TRAINS_TABLE, TRAIN_ID + " = ?",
            new String[] { String.valueOf(train.getID()) });
    db.close();
   }
   
   // Delete train given the record number
   public void deleteTrain(long item) {
	   String selectQuery = "SELECT  * FROM " + TRAINS_TABLE;
	   ContentValues values = new ContentValues();
	   SQLiteDatabase db = this.getWritableDatabase();
	   Cursor cursor = db.rawQuery(selectQuery, null);
	   
	   // Move to the first record
	   if (cursor != null) {
            cursor.moveToFirst();
       }
	   // Skip to the selected record
	   for(int i =0; i<item; i++) {
		   cursor.moveToNext();
	   }
	   
	   //Get the TRAIN_ID from the record
	   String columnValue = cursor.getString(cursor.getColumnIndex(TRAIN_ID));

	   //Delete the record with th specified train ID
	   db.delete(TRAINS_TABLE,TRAIN_ID + " = ?", new String[] {columnValue});
	  
	   db.close();
   }
   
   //Delete a set of Trains
   public void deleteTrains(Integer[] trains) {
	   ArrayList<String> trainNos = new ArrayList<String>();
	   
	   String selectQuery = "SELECT  * FROM " + TRAINS_TABLE;
	   ContentValues values = new ContentValues();
	   SQLiteDatabase db = this.getWritableDatabase();
	   Cursor cursor = db.rawQuery(selectQuery, null);
	   if (cursor != null) {
            cursor.moveToFirst();
       }
	   // Move to the first record
	   for (int i = 0; i < trains.length; i ++) {
		   cursor.moveToFirst();
	   
		   // Move to the selected record
		   for(int j =0; j<trains[i]; j++) {
			   cursor.moveToNext();
		   }
	   
	      //Get the TRAIN_NOs from the record
	      String columnValue = cursor.getString(cursor.getColumnIndex(TRAIN_NO));
	      trainNos.add(columnValue);
		  
	      
	   }
	   String[] trainArray = new String[ trainNos.size() ];
	   trainNos.toArray( trainArray );	   
	   for (int i = 0; i < trains.length; i ++) {
		   
		   //Delete the record with the specified train ID
		   db.delete(TRAINS_TABLE,TRAIN_NO + " = ?", new String[] {trainArray[i]});
	   }
	  
	   db.close();
   }
   
   
   public void deleteAllTrains(int noTrains) {
	   String selectQuery = "SELECT  * FROM " + TRAINS_TABLE;
	   
	   SQLiteDatabase db = this.getWritableDatabase();
	   Cursor cursor = db.rawQuery(selectQuery, null);
	   if (cursor != null) {
            cursor.moveToFirst();
       }
	   for (int i = 0; i < noTrains; i ++) {
		   	//Get the TRAIN_NOs from the record
		   	String columnValue = cursor.getString(cursor.getColumnIndex(TRAIN_NO));
			   //Delete the record with the specified train ID
			   db.delete(TRAINS_TABLE,TRAIN_NO + " = ?", new String[] {columnValue});
			   
			   cursor.moveToNext();
	   }

	   db.close();
   }
}