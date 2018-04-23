package com.bulletpoint.ull.bulletpoint.beaconInterfaces;

import com.bulletpoint.ull.bulletpoint.R;
import com.bulletpoint.ull.bulletpoint.locatorclasses.Area;
import com.bulletpoint.ull.bulletpoint.locatorclasses.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a610860 on 15/04/2016.
 */
public class BeaconGuideLocator {

    private static final Map<String, Point> BeaconMapPosition;
    static {
        Map<String,Point> auxMap = new HashMap<String,Point>();
        //Rojo Punto A Papelera
        auxMap.put("20:C3:8F:F1:AA:11", new Point(592,43));
        //Azul Punto B Extintor
        auxMap.put("20:C3:8F:F1:52:ED", new Point(358,487));
        //Verde Punto C Azul
        auxMap.put("D4:F5:13:7A:1D:24", new Point(594,489));

        BeaconMapPosition = Collections.unmodifiableMap(auxMap);
    }

    public static Point getBeaconMapPosition(String maccAddress){
            return BeaconMapPosition.get(maccAddress);
    }

    private static final List<Area> areas = new ArrayList<Area>() {
        {
            add(new Area(357,375,547,496,"Zona4"));
            add(new Area(659,342,829,514,"Zona5"));
            add(new Area(637,552,737,878,"Zona6"));
            add(new Area(854,322,1325,417,"Zona7"));
        }
    };

    public static List<Area> getAreas(){
        return areas;
    }

    public static int getImage(){ return R.drawable.matentrance; }
}
