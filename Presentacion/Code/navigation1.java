public class Navigation implements Serializable {
    // Distancia en la que una instalacion se considera "cercana" en metros
    private static final double NEAR_VALUE = 150; 
    // Cuando la distancia de una instalacion sea cercarna se utilizaran los 
    // valores *_NEAR para los calculos en caso contrario se utilizaran *_FAR
    // Maximo valor del cono 
    private static final double MAX_CONE_GRADS_NEAR = Math.PI / 2; 
    private static final double MAX_CONE_GRADS_FAR = Math.PI / 8;
    // Minimo valor del cono
    private static final double MIN_CONE_GRADS = Math.PI / 20; 
    // Las variables SCALE_CONE contienen los valores que ajustan las dimensiones del cono en funcion su distancia
    // Variable que escala el cono
    private static final double SCALE_CONE_NEAR = 0.0078; 
    private static final double SCALE_CONE_FAR = 0.00078; 
    // Distancia maxima y minima por defecto para considerar una instalacion
    private double maxDist = 200; 
    private double minDist = 0;
    private Vector2D currentPos;  // Posicion actual del dispositivo
    private double currentDir;    // Direccion actual del dispositivo
    // Todas las instalaciones
    private ArrayList<ULLSite> allSites = new ArrayList<>();