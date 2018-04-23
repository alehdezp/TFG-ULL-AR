package com.bulletpoint.ull.bulletpoint.busclasses;

import java.util.ArrayList;

/**
 * Created by Bullcito27 on 12/06/2016.
 */
public class XMLGettersSetters {

    private ArrayList<Arrival> arrivals = new ArrayList<Arrival>();

    public ArrayList<Arrival> getArrivals() {
        return arrivals;
    }
    public void setArrivals(Arrival arrival) {
        arrivals.add(arrival);
    }

}
