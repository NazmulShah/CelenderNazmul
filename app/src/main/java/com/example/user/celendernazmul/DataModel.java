package com.example.user.celendernazmul;

/**
 * Created by NevadaSoft on 12/24/2015.
 */
public class DataModel {
    String date ;
    String name ;

    boolean isTick;

    public DataModel() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTick() {
        return isTick;
    }

    public void setIsTick(boolean isTick) {
        this.isTick = isTick;
    }
}
