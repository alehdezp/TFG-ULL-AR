public class MapsFragment extends Fragment implements  ... { 
    // Todas las instalaciones y su marcadores
    private ArrayList<ULLSite> allSites= new ArrayList<ULLSite>(); 
    private ArrayList<Marker> allMarkers = new ArrayList<Marker>();
    private Circle circleMax;       // Circulo mayor dibujado en el mapa
    private Circle circleMin;       // Circulo menor
    private int maxRadius;          // Radio del circulo mayor
    private int minRadius;          // Radio del circulo menor
    private boolean showRadius;     // Se indica si se dibujan los circulos o no
    // Metodo que se ejecuta cuando se lanza el fragment
    public View onCreateView( ... ) {... }
    // Metodo que se ejecuta cuando la vista ya esta creada
    public void onViewCreated(View view, ...) { ... }
    // Metodo que dibuja cuando el mapa de Google Maps ya este creado 
    public void onMapReady(GoogleMap googleMap) { ... }
    // Metodo que actualiza los elementos de los mapas a la ubicacion actual
    public void onLocationChanged(Location location) { ... }
    // Metodo que muestra las ubicaciones entre "circleMax" y "circleMin"
    private void showSitesOnRadius(LatLng position) { ... } 
    private void drawAllSites() { ... } // Se dibujar las instalaciones en el mapa
    .... // Resto de metodos y atributos
}
