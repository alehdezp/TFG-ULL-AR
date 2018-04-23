package com.bulletpoint.ull.bulletpoint.beaconInterfaces;

import com.bulletpoint.ull.bulletpoint.locatorclasses.Point;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by a610860 on 15/04/2016.
 */
public class BeaconParkingLocator {

    private static final Map<String, Point> BeaconMapPosition;
    static {
        Map<String,Point> auxMap = new HashMap<String,Point>();
        //Rojo Punto A
        auxMap.put("20:C3:8F:F1:AA:11", new Point(346,246));
        //Azul Punto B
        auxMap.put("20:C3:8F:F1:52:ED", new Point(224,232));
        //Verde Punto C
        auxMap.put("D4:F5:13:7A:1D:24", new Point(239,277));

        BeaconMapPosition = Collections.unmodifiableMap(auxMap);
    }

    public static Point getBeaconMapPosition(String maccAddress){
            return BeaconMapPosition.get(maccAddress);
    }

}
