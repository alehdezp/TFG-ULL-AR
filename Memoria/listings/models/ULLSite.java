public class ULLSite  {

    private String id; //ID de la instalacion
    private String name; //Nombre
    private LatLng mapPoint; //Localizacion greografica
    private Vector2D point; //Vector2D de la localizacion

    private String desc; //descripcion de la instalacion
    private String imageLink; //imagen
    //Enlaces de interes a las instituciones, grados, etc.
    private ArrayList<String> interestPoints; 
    private ArrayList<String> interestPointsLink;

    //Variables necesarias para identificar la direccion de la instalcion
    //La direccion en la que se encuentra, la distancia y el valor del cono
    private double coneValue = 0;  
    private double distToSite = -1;
    private double dirToSite = -1;

    public ULLSite(JSONObject object){
        ... //Construimos el objeto con los atributos que se encuentran en el objeto JSON de la instalacion.
    }
    ... //Metodos set() y get() de las variables
}
