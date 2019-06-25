public class Navigation implements Serializable {
    // Distancia en metros a partir en la que una instalacion se considera con "cercana"
    private static final double NEAR_VALUE = 150; 
    // Cuando la distancia de una instalacion sea cercarna se utilizaran los valores
    // *_NEAR para los calculos en caso contrario se utilizaran *_FAR
    private static final double MAX_CONE_GRADS_NEAR = Math.PI / 2; // Maximo valor del cono 
    private static final double MAX_CONE_GRADS_FAR = Math.PI / 8; // Maximo valor del cono
    private static final double MIN_CONE_GRADS = Math.PI / 20; // Minimo valor del cono
    // Las variable SCALE_CONE son valores con los que al escalar las dimensiones de los conos la 
    // aplicacion reconocia las instalaciones de forma precisa
    private static final double SCALE_CONE_NEAR = 0.0078; // Variable que escala el cono
    private static final double SCALE_CONE_FAR = 0.00078; // Variable que escala el cono
    private Location location;
    // Distancia maxima por defecto para considerar una instalacion en metros 
    private double maxDist = 200; 
    // Distancia minima por defecto para considerar una instalacion en metros 
    private double minDist = 0;   
    private Vector2D currentPos;  // Posicion actual del dispositivo
    private double currentDir;    // Direccion actual del dispositivo
    private ArrayList<ULLSite> allSites = new ArrayList<>();  // Todas las instalaciones
    // Instalaciones que se encuentran dentro del rango de maxDist y minDist
    private ArrayList<ULLSite> destSites = new ArrayList<>();   
    // Se construye el array de allSites con todas las instalaciones a partir de un JSON
    public Navigation(JSONArray jsonULLSitesAux) { ... }
    // Se realizan los calculos que permiten indentificar las instalaciones
    public ArrayList<ULLSite> whatCanSee(LatLng currentPosAux, double actualDir) { ... }
    // Calcula la distancia entre dos ubicaciones geograficas
    public double getDistanceBetween(Vector2D v1, Vector2D v2) { ... }
    // Se comprueba si la direccion del dispositivo se encuentra dentro de su cono de
    // identificacion
    private boolean isInCone(double directionToSite, double coneValue) { ... }
    // Sirve para reorientar a el norte magnetico el angulo dado por el 
    // Vector2D.getAngleRad(Vector2D v)
    private double recalculeAngVector2D(double angleRad) { ... }
    // Se invierte el angulo
    public double invertAng(double rad) { ... }
    // Se rota -90 el angulo
    public double rotateRad(double rad) { ... }
    // Se calcula el valor del cono
    public double calculateCone(double dist) { ... }
    ... // Metodos Get() y Set() de las variables
}
