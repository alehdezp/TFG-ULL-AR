package com.alehp.ull_navigation.Models;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ULLSiteSerializable implements Serializable {

    private String id;
    private String name;
    private Vector2D point;


    private String desc;
    private String imageLink;
    private ArrayList<String> interestPoints;
    private ArrayList<String> interestPointsLink;

    public ULLSiteSerializable(ULLSite ullSite){
        this.id = ullSite.getId();
        this.name = ullSite.getName();
        this.point =ullSite.getPoint();
        this.desc = ullSite.getDesc();
        this.imageLink = ullSite.getImageLink();
        this.interestPoints = ullSite.getInterestPoints();
        this.interestPointsLink = ullSite.getInterestPointsLink();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector2D getPoint() {
        return point;
    }

    public void setPoint(Vector2D point) {
        this.point = point;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageLink() {
        return imageLink;
    }


    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public ArrayList<String> getInterestPoints() {
        return interestPoints;
    }

    public void setInterestPoints(ArrayList<String> interestPoints) {
        this.interestPoints = interestPoints;
    }

    public ArrayList<String> getInterestPointsLink() {
        return interestPointsLink;
    }

    public void setInterestPointsLink(ArrayList<String> interestPointsLink) {
        this.interestPointsLink = interestPointsLink;
    }

}
