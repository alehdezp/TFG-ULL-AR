 // Se escuchan los cambios en el sensor y se hacen los calculos
public void onSensorChanged(SensorEvent event) {
    // Valor del sensor en grados
    double radians = event.values[0]; 
    // Se convierte a radianes
    radians = Math.toRadians(radians);
    // Se obtiene la ultima posicion registrada del GPS
    LatLng lastPosition = getCurrentPos();
    if (auxpos != null) { // Si la posicion no es nula
        // Se le pregunta al objeto de la clase ``Navigation'' las instalaciones 
        // que se encuentran en esa direccion
        allResultsSites = navULL.whatCanSee(lastPosition, radians);
    }
    // Si se obtiene al menos un resultado
    if (allResultsSites != null) {
        // Se obtiene la instalacion mas cercana, el indice 0 corresponde a la mas cercana
        nearSiteResult = allResultsSites.get(0);
        ... // Se muestra su informacion por pantalla para que usuario conozca la instalacion
        ... // Elementos a mostrar si se encuentra mas de una instalacion