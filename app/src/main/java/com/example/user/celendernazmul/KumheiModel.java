package com.example.user.celendernazmul;

import java.util.ArrayList;

public class KumheiModel {
	
	String year ;

    ArrayList<DataModel> dataList = new ArrayList<DataModel>();

    public KumheiModel() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ArrayList<DataModel> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<DataModel> dataList) {
        this.dataList = dataList;
    }

}
