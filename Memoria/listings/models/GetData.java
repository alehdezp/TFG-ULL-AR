public class GetData extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... strings) {
        StringBuilder result= new StringBuilder();  //Resultado a devolver de la peticion
        try {
            URL url = new URL(strings[0]);  //url a la que realizamos la peticion
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); 
            urlConnection.setConnectTimeout(10000); //Tiempo de espera maximo del conceccion
            urlConnection.setRequestMethod("GET");  //Metodo de la conexion
            urlConnection.setRequestProperty("Content-Type", "application/json"); //Contenido
            urlConnection.connect();                //Realizamos la conexion

            InputStream inputStream = urlConnection.getInputStream(); //Cuando recibamos la respuesta
            BufferedReader buffereReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;        
            while((line = buffereReader.readLine()) != null){  //Leemos el buffer con la respuesta
                result.append(line).append("\n");              //Lo guardamos en un string
            }
            return result.toString();                          //Devolvemos el string
        }catch (IOException e){ ... }
        return "Error";  //Si falla la conexion
    }
};