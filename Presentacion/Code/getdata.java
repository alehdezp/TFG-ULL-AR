public class GetData extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);  // url a la que se realiza la peticion
            HttpURLConnection urlConnection = url.openConnection(); 
            urlConnection.setConnectTimeout(10000); // Tiempo de espera maximo
            urlConnection.setRequestMethod("GET");  // Metodo de la conexion
            urlConnection.setRequestProperty("Content-Type", "application/json"); // Tipo de contenido esperado como respuesta
            urlConnection.connect();            // Se realiza la conexion
            ...  // Cuando se recibe la respuesta se lee, se crea y se devulve un   string con su contenido
        }catch (IOException e) { ... }
    }
};