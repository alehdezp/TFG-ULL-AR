private void getSitesFromDB() { 
    GetData getSites = new GetData();
    String sites = getSites.execute("https://server-ull-ar.herokuapp.com/api/ull-sites").get(); //Conectamos con el servidor
    JSONArray array = new JSONArray(sites);
    // Se crea una instancia de la clase "Navigation" con todas las instalaciones obtenidas de la base de datos
    navULL = new Navigation(array);
}