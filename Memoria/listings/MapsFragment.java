public class MapsFragment extends Fragment implements OnMapReadyCallback ...{
    private MapView mapView;    //Vista que contiene el mapa de Google Maps
    private GoogleMap goMap;    //API de google maps para modificar el mapa
    ... //Resto de variables
    private Button buttonARStart;   //Boton que lanza el ARNavigation.class
    private ArrayList<ULLSite> allSites= new ArrayList<ULLSite>(); //Todos las instalaciones
    private ArrayList<Marker> allMarkers = new ArrayList<Marker>(); //Sus marcadores
    private Circle circleMax;       //Circulo mayor dibujado en el mapa
    private Circle circleMin;       //Circulo menor
    private int maxRadius;          //Radio del circulo mayor
    private int minRadius;          //Radio del circulo menor
    private boolean showRadius;     //Indicamos si dibujamos los circulos o no
    //Metodo que se ejecuta cuando se lanza el fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container ...) {
        View rootView = inflater.inflate(R.layout.fragment_maps...); //Inflamos con el layout
        getRadius(); //Obtenemos los radios de la settingsPref
        getSitesFromDB(); //Obtenemos las instalciones de la base de datos
        return rootView; //Devolvemos la vista
    }
    //Metodo que se ejecuta cuando la vista ya esta creada
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mapView = (MapView) rootView.findViewById(R.id.mapView); //Buscamos el MapView del layout
        ... //Configuramos el mapa y pedimos el mapa de Google Maps de nuestra zona
    }
    //Metodo que se ejecuta cuando el mapa de Google Maps ya este creado 
    public void onMapReady(GoogleMap googleMap) {
        goMap = googleMap;  //Guardamos el objeto que tiene el mapa
        LatLng currentPos = getCurrentPos(); //Obtenemos la posicion GPS
        actualPosMarker = goMap.addMarker(new MarkerOptions()..));//Marcador en la posicion del GPS
        drawAllSites();   //Dibujamos todas las instalaciones de la base de datos
        showSitesOnRadius(getCurrentPos());     //Dibujamos los circulos circleMax y circleMin 
        ... 
    }
    //Metodo que actualiza los dibujos de los mapas a la ubicacion actual cuando cambia
    public void onLocationChanged(Location location) {
        drawActualPos(); //dibujamos la posicion actual
        LatLng position = getCurrentPos(); //Obtenemos la posicion del gps
        redrawCircles(position);           //Actualizamos los circulos         
        showSitesOnRadius(position);      //Mostramos las instalaciones dentro del los circulos
    }
    //Metodo que muestra las ubicaciones entre "circleMax" y "circleMin"
    private void showSitesOnRadius(LatLng position){
        for (int i = 0; i < allSites.size(); i++) { //Para todas las instalaciones
            ... //Calculamos la distancia entre la instalacion y la ubicacion GPS
            if(distance > minRadius && distance < maxRadius){ //Si la instalacion esta dentro del 
                                                                    //rango
                allMarkers.get(i).setVisible(true);  //mostramos su marcador 
            }else { ... } //Si no, lo ocultamos 
        }
    } 
    private void getRadius() { ... } //Obtenemos maxRadius y minRadius de las SharedPrefences
    private void getSitesFromDB() { ... } //Obtenemos las instalaciones de la BD
    private LatLng getCurrentPos(){ ... } //Posicion actual del GPS
    private void drawAllSites() { ... } //Creamos un marcador en el mapa para cada instalacion
    private void redrawCircles(LatLng position) { ... } //Actualizamos los centros de los cirulos
    .... //Resto de metodos necesarios
}
