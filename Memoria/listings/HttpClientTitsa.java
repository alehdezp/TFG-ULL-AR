public class HttpClientTitsa {
    private static final String BASE_URL = "http://apps.titsa.com/apps/apps_sae_llegadas_parada.asp?";
    //...
    public List<Arrival> getTimetablesBasedOnLineNumber(final String stopNumber) {
    
        RequestParams paramettersGet = new RequestParams();
        String relativeUrl = "IdApp=" + APIkey + "&idParada=" + stopNumber;
        Log.i("INFO", "Realizando peticion: " + BASE_URL + relativeUrl);
        get(relativeUrl, paramettersGet, new SaxAsyncHttpResponseHandler(new XmlHandler()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, DefaultHandler defaultHandler) {
                Log.i("INFO", "Finalizada peticion!");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, DefaultHandler defaultHandler) {
                resultados = null;
            }
            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                super.onPostProcessResponse(instance, response);
                resultados = XmlHandler.getXMLData().getArrivals();
                Log.i("INFO", "Procesada peticion!");
            }
        });
        return resultados;
    }
    //...
}
