package com.yakuphanbigic.attendancetracker.ui.main;

import java.io.Serializable;

public class Course implements Serializable{

    private String mName;
    private String mDay;
    private int mQuantity;

    public Course(){}

    public Course(String mName, String mDay, int mQuantity) {
        this.mName = mName;
        this.mQuantity = mQuantity;
        this.mDay = mDay;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getmDay() {
        return mDay;
    }

    public String getmName() {
        return mName;
    }

    public int getmQuantity(){
        return mQuantity;
    }

    public void addToQuantity(){
        this.mQuantity += 1;
    }

    public void removeFromQuantity(){
        if(this.mQuantity >= 1){
            this.mQuantity -= 1;
        }
    }

}