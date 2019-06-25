public class MapsFragment extends Fragment implements OnMapReadyCallback ...{
    private MapView mapView;    // Vista que contiene el mapa de Google Maps
    private GoogleMap goMap;    // API de google maps para modificar el mapa
    ... // Resto de variables
    private Button buttonARStart;   // Boton que lanza el ARNavigation.class
    private ArrayList<ULLSite> allSites= new ArrayList<ULLSite>(); // Todos las instalaciones
    private ArrayList<Marker> allMarkers = new ArrayList<Marker>(); // Sus marcadores
    private Circle circleMax;       // Circulo mayor dibujado en el mapa
    private Circle circleMin;       // Circulo menor
    private int maxRadius;          // Radio del circulo mayor
    private int minRadius;          // Radio del circulo menor
    private boolean showRadius;     // Se indica si se dibujan los circulos o no
    // Metodo que se ejecuta cuando se lanza el fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container ...) {
        View rootView = inflater.inflate(R.layout.fragment_maps...); // Se infla con el layout
        getRadius(); // Se obtienen los radios de la settingsPref
        getSitesFromDB(); // Se obtienen las instalaciones de la base de datos
        return rootView; // Se devuelve la vista
    }
    // Metodo que se ejecuta cuando la vista ya esta creada
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mapView = (MapView) rootView.findViewById(R.id.mapView); // Se busca el MapView del layout
        ... // Se configura el mapa y se pide el mapa de Google Maps de la zona actual
    }
    // Metodo que se ejecuta cuando el mapa de Google Maps ya este creado 
    public void onMapReady(GoogleMap googleMap) {
        goMap = googleMap;  // Se guarda el objeto que tiene el mapa
        LatLng currentPos = getCurrentPos(); // Se obtiene la posicion GPS
        actualPosMarker = goMap.addMarker(new MarkerOptions()..));// Marcador en la posicion del GPS
        drawAllSites();   // Se dibujan todas las instalaciones de la base de datos
        showSitesOnRadius(getCurrentPos());     // Se dibujan los circulos circleMax y circleMin 
        ... 
    }
    // Metodo que actualiza los dibujos de los mapas a la ubicacion actual cuando cambia
    public void onLocationChanged(Location location) {
        drawActualPos(); // Se dibuja la posicion actual
        LatLng position = getCurrentPos(); // Se obtiene la posicion del gps
        redrawCircles(position);           // Se actualizan los circulos         
        showSitesOnRadius(position);      // Se muestran las instalaciones dentro del los circulos
    }
    // Metodo que muestra las ubicaciones entre "circleMax" y "circleMin"
    private void showSitesOnRadius(LatLng position){
        for (int i = 0; i < allSites.size(); i++) { // Para todas las instalaciones
            ... // Se calcula la distancia entre la instalacion y la ubicacion GPS
            if(distance > minRadius && distance < maxRadius){ // Si la instalacion esta dentro del 
                                                                    // rango
                allMarkers.get(i).setVisible(true);  // Se muestra su marcador 
            }else { ... } // Si no, se oculta
        }
    } 
    private void getRadius() { ... } // Se obtienen maxRadius y minRadius de las SharedPrefences
    private void getSitesFromDB() { ... } // Se obtienen las instalaciones de la BD
    private LatLng getCurrentPos(){ ... } // Posicion actual del GPS
    private void drawAllSites() { ... } // Se crea un marcador en el mapa para cada instalacion
    private void redrawCircles(LatLng position) { ... } // Se actualizan los centros de los cirulos
    .... // Resto de metodos necesarios
}
