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
 * Created by Bullcito27 on 16/06/2016.
 */
public class BeaconAttInfo {

    private static final Map<String, Point> classInfoMap;
    static {
        Map<String,Point> auxMap = new HashMap<String,Point>();
        //Punto verde
        auxMap.put("20:C3:8F:F1:AA:11", new Point(578,356));
        //Punto rojo
        auxMap.put("20:C3:8F:F1:52:ED", new Point(578,27));
        //Punto azul
        auxMap.put("D4:F5:13:7A:1D:24", new Point(111,232));

        classInfoMap = Collections.unmodifiableMap(auxMap);
    }

    private static final List<Area> areas = new ArrayList<Area>() {
        {
            add(new Area(111,27,578,356,"Aula 2.1"));
        }
    };

    public static Point getPositionInfo(String maccAddress){
        return classInfoMap.get(maccAddress);
    }

    public static List<Area> getAreas(){
        return areas;
    }

    public static int getImage(){ return R.drawable.aula21; }

}
