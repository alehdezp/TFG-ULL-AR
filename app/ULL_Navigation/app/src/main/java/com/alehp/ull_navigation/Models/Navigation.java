package com.alehp.ull_navigation.Models;





import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class Navigation {

    private static double PI = Math.PI;
    private static final double MAX_DIST = 200;
    private static final double MAX_CONE_GRADS = Math.PI / 2;
    private static final double MIN_CONE_GRADS = 0;

    private Location location;
    private JSONArray jsonSites;


    private Vector2D currentPos = new Vector2D();
    private double currentDir;

    private ArrayList<ULLSite> allSites= new ArrayList<>();
    private ArrayList<ULLSite> destSites;


    private Vector2D home = new Vector2D(-16.271370844238504, 28.467337376756998);
    private Vector2D ull = new Vector2D(-16.316877139026133, 28.481857638227176);

    public Navigation(JSONArray jsonAux){

        try {

            jsonSites = jsonAux;
            for (int i = 0; i < jsonSites.length(); i++) {
                JSONObject row = null;
                allSites.add(new ULLSite(jsonSites.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        destSites = allSites;
        for(int i = 0; i < destSites.size(); i++){
            Log.d("position", destSites.get(i).getMapPoint().toString());
        }
    }

    public ArrayList<ULLSite> whatCanSee(LatLng actualPos, double actualDir){
        getCurrentPos().set(actualPos.longitude, actualPos.latitude);
        setCurrentDir(actualDir);



        int id = -1;
        double minDist = MAX_DIST;
        ArrayList<ULLSite> result = new ArrayList<>();

        for (int i = 0; i < getDestSites().size(); i++) {
            double dirToSite = recalculeAng(getCurrentPos().getAngleRad(getDestSites().get(i).getPoint()));
            double distToSite = getDistanceBetween(getCurrentPos(), getDestSites().get(i).getPoint());
            double coneValue = calculateCone(distToSite);

            if(isInCone(dirToSite, coneValue)){
                getDestSites().get(i).setConeValue(coneValue);
                getDestSites().get(i).setDirToSite(dirToSite);
                getDestSites().get(i).setDistToSite(distToSite);
                result.add(getDestSites().get(i));
                if (minDist > distToSite) {
                    minDist = distToSite;
                    id = i;
                }
            }
        }

        if(id != -1) {
            result.add(0,getDestSites().get(id));
            return result;
        }else
            return null;
    }

    public double getDistanceBetween(Vector2D v1, Vector2D v2){
        float result[] = new float[3];
        Location.distanceBetween(v1.getY(), v1.getX(), v2.getY(),v2.getX(), result);
        return result[0];
    }

    private boolean isInCone(double directionToSite, double coneValue ) {

        double auxCurrentDir = getCurrentDir();
        double auxMinCone = directionToSite - coneValue/2;
        double auxMaxCone = directionToSite + coneValue/2;

        if(auxCurrentDir >= auxMinCone && auxCurrentDir <= auxMaxCone)
            return true;
        else
            return false;

    }

    private double recalculeAng(double angleRad) {
//        Log.d("m", "angulo: " + angleRad);
        double aux = rotateRad(angleRad);
//        Log.d("s", "rotacion: " + aux);
        aux = invertAng(aux);
//        Log.d("n", "invertido: " + aux);
        return aux;

    }

    public double invertAng(double rad){
        rad = 2 * PI - rad;
        return rad;

    }

    public double rotateRad(double rad){
        rad -= PI/2;
        if(rad < 0)
            rad += PI * 2;


        return rad;
    }



    public double calculateCone(double dist){
        double totalRads = MAX_CONE_GRADS - MIN_CONE_GRADS;
        return (MAX_DIST - dist) * totalRads / MAX_DIST  + MIN_CONE_GRADS;

    }

    public ArrayList<ULLSite> getAllSites() {
        return allSites;
    }

    public void setAllSites(ArrayList<ULLSite> allSites) {
        this.allSites = allSites;
    }

    public ArrayList<ULLSite> getDestSites() {
        return destSites;
    }

    public void setDestSites(ArrayList<ULLSite> destSites) {
        this.destSites = destSites;
    }


    public double getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(double currentDir) {
        this.currentDir = currentDir;
    }


    public Vector2D getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Vector2D currentPos) {
        this.currentPos = currentPos;
    }




    public Vector2D getHome() {
        return home;
    }

    public void setHome(Vector2D home) {
        this.home = home;
    }




}


//        getDestSites().add(new ULLSite("Facultad de Fisica y Matemáticas", "nº 922222222",
//                new Vector2D(-16.271370844238504,28.467337376756998)));
//        getDestSites().add(new ULLSite("Facultad de Ingenieria Informatica", "nº 922222222",
//                new Vector2D(-16.272370844238504,28.466337376756998)));
//        getDestSites().add(new ULLSite("Parking ESIT", "nº 922222222",
//                new Vector2D(-16.273370844238504,28.467337376756998)));
//        getDestSites().add(new ULLSite("Punto detras", "nº 922222222",
//                new Vector2D(-16.270708,28.467699)));

//        getDestSites().add(new ULLSite("Edificio Fundacion de la Universidad de La Laguna", "Some info",
//                new Vector2D(-16.317462,28.481930 )));
//        getDestSites().add(new ULLSite("Edificio Central de la Universidad de La Laguna", "Some info",
//                new Vector2D(-16.316690, 28.481753)));
//        getDestSites().add(new ULLSite("Colegio Mayor San Fernando", "Some info",
//                new Vector2D( -16.3157322, 28.481173)));
//        getDestSites().add(new ULLSite("Parking de Estudiantes Universitarios", "Some info",
//                new Vector2D(-16.315613, 28.481604)));
//        getDestSites().add(new ULLSite("Campus Central - Torre Profesor Agustín Arévalo", "Some info",
//                new Vector2D(-16.317531,28.481173)));
//        getDestSites().add(new ULLSite("Deportes ULL", "Some info",
//                new Vector2D(-16.316478,28.479994)));
