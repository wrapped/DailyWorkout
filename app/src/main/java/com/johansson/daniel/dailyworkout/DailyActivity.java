package com.johansson.daniel.dailyworkout;

//Get and set methods for user input

public class DailyActivity {
    private String activity;
    private String miles;
    private String date;

    DailyActivity(String activity, String miles, String date){
        this.activity = activity;
        this.miles = miles;
        this.date = date;
    }

    public void setActivity(String activity){
        this.activity = activity;
    }

    public void setMiles(String miles){
        this.miles = miles;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getActivity(){
        return activity;
    }

    public String getMiles(){
        return miles;
    }

    public String getDate(){
        return date;
    }
}