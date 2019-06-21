public class ULLSite  {
    private String id; //ID de la instalacion
    private String name; //Nombre
    private LatLng mapPoint; //Localizacion greografica
    private Vector2D point; //Vector2D de la ubicacion
    private String desc; //descripcion de la instalacion
    private String imageLink; //imagen de la instalacion
    //Enlaces de interes a las instituciones, grados, etc.
    private ArrayList<String> interestPoints;  //Nombres
    private ArrayList<String> interestPointsLink; //Enlaces
    //Variables necesarias para identificar la direccion de la instalcion
    private double distToSite = -1;  //Distancia a la instalacion
    private double dirToSite = -1;  //Direccion en la que se encuentra
    private double coneValue = 0;  //Valor del cono

    public ULLSite(JSONObject object){
        ... //Construimos el objeto con los atributos que se encuentran en el objeto JSON de la instalacion.
    }
    ... //Metodos set() y get() de las variables
}
