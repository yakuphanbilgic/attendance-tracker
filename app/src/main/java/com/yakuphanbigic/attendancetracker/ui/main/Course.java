package com.yakuphanbigic.attendancetracker.ui.main;

import java.io.Serializable;

public class Course implements Serializable{

    private  String mName;
    private int mAmount;
    private int mQuantity;

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public Course(){}

    public Course(String mName) {
        this.mName = mName;
        this.mQuantity = 0;
    }

    public String getmName() {
        return mName;
    }

    public int getmAmount() {
        return mAmount;
    }

    public int getmQuantity(){
        return mQuantity;
    }

    public void addToQuantity(){
        this.mQuantity += 1;
    }

    public void removeFromQuantity(){
        if(this.mQuantity > 1){
            this.mQuantity -= 1;
        }
    }

}