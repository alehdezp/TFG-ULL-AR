// Metodo que se encarga identificar las instalaciones en frente al dispositivo
// Recibe la posicion y orientacion actual del dispositivo
// Devuelve una lista de instalaciones indicando cual es la mas cercana 
public ArrayList<ULLSite> whatCanSee(LatLng currentPosAux, double actualDir) {
     // Posicion actual del dispositivo
    currentPos.set(actualPos.longitude, actualPos.latitude);
    currentDir = actualDir;  // Orientacion actual del dispositivo
    int id = -1;
    ArrayList<ULLSite> result = new ArrayList<>(); //Instalaciones encontradas
    // Se calculan todas las instalaciones que se encuentran entre maxDist y minDist como posible instalaciones  a identificar
    for (int i = 0; i < allSites.size(); i++) {
        double distToSite = getDistanceBetween(currentPos, allSites.get(i).getPoint());
        if ((distToSite < maxDist) && (distToSite > minDist)) {
            destSites.add(allSites.get(i));
        }
    }
