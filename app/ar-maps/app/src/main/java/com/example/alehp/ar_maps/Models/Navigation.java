package com.example.alehp.ar_maps.Models;





import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class Navigation {

    private Vector2D currentPos;
    private ArrayList<Vector2D> destPoints = new ArrayList<Vector2D>();

    private double destDir;
    private double currentDir;


    private double distanceDest;
    private static final double MAX_DIST = 150;

    private double coneValue;
    private static final double MAX_CONE_GRADS = Math.PI;
    private static final double MIN_CONE_GRADS = Math.PI / 8;



    private Vector2D home = new Vector2D(-16.271370844238504, 28.467337376756998);
    private Vector2D ull = new Vector2D(-16.316877139026133, 28.481857638227176);

    public Navigation(){
//        destPoints.add(getHome());
        setCurrentPos(new Vector2D());
    }

    public boolean canSee(LatLng actualPos, double actualDir){
        getCurrentPos().set(actualPos.longitude, actualPos.latitude);
        setCurrentDir(actualDir);

        setDistanceDest(getCurrentPos().getDistance(ull));
        if (getDistanceDest() > MAX_DIST) return false;

        setDestDir(getCurrentPos().getAngleRad(ull));
        rotateRad(getDestDir());
        calculateCone();
        double auxCurrenDir = getCurrentDir();
        double auxMinCone = getDestDir() - getConeValue()/2;
        double auxMaxCone = getDestDir() + getConeValue()/2;

        if(auxMaxCone >= 0 && auxMinCone <  0) {
            auxMaxCone += Math.PI * 2;
            auxMinCone = Math.PI * 2 - auxMinCone;
        }else {
            if (auxMaxCone < 0)
                auxMaxCone = Math.PI * 2 - auxMaxCone;
            if (auxMinCone < 0)
                auxMinCone = Math.PI * 2 - auxMinCone;
        }

        if(auxCurrenDir >= auxMinCone && auxCurrenDir <= auxMaxCone)
            return true;
        else
            return false;
    }

    public void rotateRad(double rad){
        rad += Math.PI/2;
        if(rad >= Math.PI)
            rad = rad - Math.PI * 2;

        setDestDir(rad);
    }


    public void rotateDegrees(double degrees){
        degrees = degrees + 90;
        if (degrees >= 360){
            degrees = degrees - 360;
        }
        setDestDir(degrees);
    }

    public void calculateCone(){
        double totalRads = MAX_CONE_GRADS - MIN_CONE_GRADS;
        setConeValue((getDistanceDest() * (totalRads / MAX_DIST)) + MIN_CONE_GRADS);

    }

    public double getDestDir() {
        return destDir;
    }

    public void setDestDir(double destDir) {
        this.destDir = destDir;
    }

    public double getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(double currentDir) {
        this.currentDir = currentDir;
    }

    public double getConeValue() {
        return coneValue;
    }

    public void setConeValue(double coneValue) {
        this.coneValue = coneValue;
    }


    public Vector2D getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Vector2D currentPos) {
        this.currentPos = currentPos;
    }

    public ArrayList<Vector2D> getDestPoints() {
        return destPoints;
    }

    public void setDestPoints(ArrayList<Vector2D> destPoints) {
        this.destPoints = destPoints;
    }


    public void setConeValue(float coneValue) {
        this.coneValue = coneValue;
    }

    public Vector2D getHome() {
        return home;
    }

    public void setHome(Vector2D home) {
        this.home = home;
    }

    public double getDistanceDest() {
        return distanceDest;
    }

    public void setDistanceDest(double distanceDest) {
        this.distanceDest = distanceDest;
    }


}
