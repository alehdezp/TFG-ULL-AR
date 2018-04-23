public class RssBeaconInfo {

    private static final Map<String, String> rssMap;
    static {
        Map<String,String> auxMap = new HashMap<String,String>();
        //Deportes y ocio
        auxMap.put("20:C3:8F:F1:AA:11", "http://eventos.ull.es/rss/category/5/1001/deporte-y-ocio-general.rss");
        //Informatica y telecomunicaciones
        auxMap.put("20:C3:8F:F1:52:ED", "http://eventos.ull.es/rss/category/13/1001/informatica-y-telecomunicaciones-general.rss");
        //Musica teatro y danza
        auxMap.put("D4:F5:13:7A:1D:24", "http://eventos.ull.es/rss/category/17/1001/musica-teatro-y-danza-general.rss");

        rssMap = Collections.unmodifiableMap(auxMap);
    }

    public static String getRssLink(String maccAddress){
        return rssMap.get(maccAddress);
    }
}
