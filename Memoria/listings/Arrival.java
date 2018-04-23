package com.bulletpoint.ull.bulletpoint.busclasses;

public class Arrival {
    //This class is used to store the data comming from TITSA.
    private String stopCode; //Stop code, each beacon is associated with one of these.
    private String stopName; 
    private String destination; //It's comming faulty from TITSA, so we will parse TITSA webpage looking for it.
    private String hour;
    private String travelId;
    private String lineNumber;
    private String minutesForArrival;


    public String getStopCode() {
        return stopCode;
    }

    public void setStopCode(String stopCode) {
        this.stopCode = stopCode;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

//...
}