
public class HttpClientTitsa {

    private static final String BASE_URL = "http://apps.titsa.com/apps/apps_sae_llegadas_parada.asp?";
    //...
    public static  Map<String,String> getCorrectRoute(String stopNumber){
        try {
            Map<String,String> destinationMap = new HashMap<String,String>();
            Document doc = Jsoup.connect("http://www.titsa.com/correspondencias.php?idc=" + stopNumber).get();
            Log.i("INFO","Realizando peticion");
            Element table = doc.select("table").get(0);
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                Element row = rows.get(i);
                Elements cols =row.select("td");
                //Second element corresponds to Destination route.
                String[] elementos = cols.get(1).toString().split("-", 2);
                destinationMap.put(Html.fromHtml(elementos[0].replaceAll(" ","")).toString(),Html.fromHtml(elementos[1].replaceAll(" ","")).toString());
            }
            return destinationMap;
        } catch (Exception e) {
        }
        return null;
    }
    //...
    
}
