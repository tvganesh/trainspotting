package com.tvganesh.trainspotting;

public class Train {
 
    // private variables
    int id;
    String trainNo;
    String trainName;
 
    // Empty constructor
    public Train(){
 
    }
    
    // Constructor
    public Train(int id, String number, String name){
        this.id = id;
        this.trainNo = number;
        this.trainName = name;
    }
 
    // Constructor
    public Train(String number, String name){
        this.trainNo = number;
        this.trainName = name;
    }
    // Setters and Getters
    
    // get ID
    public int getID(){
        return this.id;
    }
 
    // set id
    public void setID(int id){
        this.id = id;
    }
 
    // get name
    public String getTrainName(){
        return this.trainName;
    }
 
    // set name
    public void setTrainName(String name){
        this.trainName = name;
    }
 
    // get train number
    public String getTrainNo(){
        return this.trainNo;
    }
 
    // setting phone number
    public void setTrainNo(String trainNo){
        this.trainNo = trainNo;
    }
}