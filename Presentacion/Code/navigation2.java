// Constructor del atributo allSites con el objeto JSON con las instalaciones
public Navigation(JSONArray jsonULLSitesAux) { ... }
// Calculos que permiten indentificar las instalaciones
public ArrayList<ULLSite> whatCanSee(LatLng currentPosAux, double actualDir) { ... }
// Calcula la distancia entre dos ubicaciones geograficas
public double getDistanceBetween(Vector2D v1, Vector2D v2) { ... }
// Se comprueba si la direccion del dispositivo se encuentra dentro del cono de identificacion de la instalacion
private boolean isInCone(double directionToSite, double coneValue) { ... }
// Sirve para reorientar al norte magnetico, como inicio de rotacion, el angulo dado por el Vector2D.getAngleRad(Vector2D v)
private double recalculeAngVector2D(double angleRad) { ... }
// Se invierte el angulo
public double invertAng(double rad) { ... }
// Se rota -90 el angulo
public double rotateRad(double rad) { ... }
// Se calcula el valor del cono
public double calculateCone(double dist) { ... }
... // Metodos Get() y Set() de los atributos