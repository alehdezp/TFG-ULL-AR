package com.alehp.ull_navigation.Models;

import android.util.Log;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ULLSite implements Serializable {

    private String id;
    private String name;
    private LatLng mapPoint;
    private Vector2D point;

    private String desc;
    private String imageLink;
    private ArrayList<String> interestPoints;
    private ArrayList<String> interestPointsLink;


    private double coneValue = 0;
    private double distToSite = -1;
    private double dirToSite = -1;



    public ULLSite(JSONObject object){

        interestPoints = new ArrayList<>();
        interestPointsLink = new ArrayList<>();

        try {
            id = object.getString("id");
            name = object.getString("name");
            double lat = object.getJSONObject("position").getDouble("lat");
            double lng = object.getJSONObject("position").getDouble("long");
            mapPoint = new LatLng(lat, lng);
            point = new Vector2D(lng, lat);
            desc = object.getString("desc");
            imageLink = object.getString("imageLink");
            JSONArray aux = object.getJSONArray("canFind");
            for(int i = 0; i < aux.length(); i++){
               interestPoints.add(aux.getJSONObject(i).getString("id"));
               interestPointsLink.add(aux.getJSONObject(i).getString("link"));
            }


            Log.d("ullsite", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ULLSite(String idx, String desc, Vector2D point){
        setId(idx);

        setPoint(point);
        setMapPoint(new LatLng(point.getY(), point.getX()));
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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
