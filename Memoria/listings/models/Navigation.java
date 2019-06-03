package com.alehp.ull_navigation.Models;





import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


public class Navigation implements Serializable {

    private static double PI = Math.PI;
    private static final double MAX_CONE_GRADS_NEAR = Math.PI / 2; //
    private static final double MAX_CONE_GRADS_FAR = Math.PI / 8; //
    private static final double MIN_CONE_GRADS = Math.PI / 20;
    private static final double SCALE_CONE_NEAR = 0.0078; //relacion (pi/2)/200
    private static final double SCALE_CONE_FAR = 0.00078; //relacion (pi/2)/2000
    private static final double NEAR_VALUE = 150; //Valor a partir del cual se empieza tomar la distancia como lejana
    //En funcion del escalado del cono se empieza a restar el angulo del cono en funcion
    //de su distancia
    private double maxDist = 200;
    private double minDist = 0;

    private Location location;
    private JSONArray jsonSites;


    private Vector2D currentPos = new Vector2D();
    private double currentDir;

    private ArrayList<ULLSite> allSites = new ArrayList<>();
    private ArrayList<ULLSite> destSites = new ArrayList<>();


    public Navigation(JSONArray jsonULLSitesAux) {
        //Construimos el array de allSites a partir JSON
    }

    public ArrayList<ULLSite> whatCanSee(LatLng currentPosAux, double actualDir) {
        currentPos = currentPos;
        currentDir = actualDir;

        int id = -1;
        double nearSite = maxDist;
        ArrayList<ULLSite> result = new ArrayList<>();
        destSites.clear();

        for (int i = 0; i < allSites.size(); i++) {
            double distToSite = getDistanceBetween(currentPos, allSites.get(i).getPoint());
            if ((distToSite < maxDist) && (distToSite > minDist)) {
                destSites.add(allSites.get(i));
            }
        }
        for (int i = 0; i < getDestSites().size(); i++) {
            double dirToSite = recalculeAng(getCurrentPos().getAngleRad(getDestSites().get(i).getPoint()));
            double distToSite = getDistanceBetween(getCurrentPos(), getDestSites().get(i).getPoint());
            double coneValue = calculateCone(distToSite);

            if (isInCone(dirToSite, coneValue)) {
                ...

                result.add(getDestSites().get(i));
                if (nearSite > distToSite) {
                    nearSite = distToSite;
                    id = i;
                }
            }
        }
        if (id != -1) {
            result.add(0, getDestSites().get(id));
            return result;
        } else
            return null;
    }

    public double getDistanceBetween(Vector2D v1, Vector2D v2) {
        float result[] = new float[3];
        Location.distanceBetween(v1.getY(), v1.getX(), v2.getY(), v2.getX(), result);
        return result[0];
    }

    private boolean isInCone(double directionToSite, double coneValue) {

        double auxCurrentDir = getCurrentDir();
        double auxMinCone = directionToSite - coneValue / 2;
        double auxMaxCone = directionToSite + coneValue / 2;

        if (auxCurrentDir >= auxMinCone && auxCurrentDir <= auxMaxCone)
            return true;
        else
            return false;
    }

    private double recalculeAng(double angleRad) {
        double aux = rotateRad(angleRad);
        aux = invertAng(aux);
        return aux;
    }

    public double invertAng(double rad) {
        rad = 2 * PI - rad;
        return rad;
    }

    public double rotateRad(double rad) {
        rad -= PI / 2;
        if (rad < 0)
            rad += PI * 2;
        return rad;
    }

    public double calculateCone(double dist) {

        if (dist <= NEAR_VALUE){
            double aux = MAX_CONE_GRADS_NEAR - dist * SCALE_CONE_NEAR;
            return MAX_CONE_GRADS_NEAR - dist * SCALE_CONE_NEAR;
        }else {
            double auxCone = MAX_CONE_GRADS_FAR - dist * SCALE_CONE_FAR;
            if (auxCone < MIN_CONE_GRADS) {
                return MIN_CONE_GRADS;
            } else {
                return auxCone;
            }
        }
    }
}
