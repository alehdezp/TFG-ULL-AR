package com.alehp.ull_navigation.Models;

import com.google.android.gms.maps.model.LatLng;

public class ULLSite {

    private String id;

    private String info;
    private Vector2D point;

    private LatLng mapPoint;
    private double coneValue = 0;

    private double distToSite = -1;
    private double dirToSite = -1;




    public ULLSite(String idx, String infox, Vector2D point){
        setId(idx);
        setInfo(infox);
        setPoint(point);
        setMapPoint(new LatLng(point.getY(), point.getX()));
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Vector2D getPoint() {
        return point;
    }


    public void setPoint(Vector2D point) {
        this.point = point;
    }

    public LatLng getMapPoint() {
        return mapPoint;
    }
    public void setMapPoint(LatLng mapPoint) {
        this.mapPoint = mapPoint;
    }


    public double getDistToSite() {
        return distToSite;
    }

    public void setDistToSite(double distToSite) {
        this.distToSite = distToSite;
    }

    public double getDirToSite() {
        return dirToSite;
    }

    public void setDirToSite(double dirToSite) {
        this.dirToSite = dirToSite;
    }


    public double getConeValue() {
        return coneValue;
    }

    public void setConeValue(double coneValue) {
        this.coneValue = coneValue;
    }

}
