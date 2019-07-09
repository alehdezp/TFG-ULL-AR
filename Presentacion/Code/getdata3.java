private void getRadius() {
    //Obtenemos las preferencias que contiene los radios de las circunferencias
    settingsPref = PreferenceManager.getDefaultSharedPreferences(getContext());
    String auxMaxRadius = settingsPref.getString("maxRadius", "null");
    String auxMinRadius = settingsPref.getString("minRadius", "null");
    // Se guarda el valor "maxRadius" y "minRadius" en el objeto navULL
    navULL.setMaxDist(Integer.parseInt(auxMaxRadius)); 
    navULL.setMinDist(Integer.parseInt(auxMinRadius)); 
}