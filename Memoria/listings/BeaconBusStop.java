//The class BeaconBusStop, stores the information that relates each beacon to a stop identifier.
public class BeaconBusStop {

    private static final Map<String, String> stopsMapId;
    static {
        /*Each becon MAC address is associated with the stop ID*/
        Map<String,String> auxMap = new HashMap<String,String>();
        //Intercambiador La Laguna
        auxMap.put("20:C3:8F:F1:AA:11", "2625");
        //Intercambiador Santa Cruz
        auxMap.put("20:C3:8F:F1:52:ED", "7140");
        //Intercambiador Costa Adeje
        auxMap.put("D4:F5:13:7A:1D:24", "7142");

        stopsMapId = Collections.unmodifiableMap(auxMap);
    }

    public static String getStopId(String maccAddress){
        return stopsMapId.get(maccAddress);
    }
}

