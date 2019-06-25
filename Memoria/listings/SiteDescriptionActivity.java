public class SiteDescriptionActivity extends ListActivity {
    ULLSiteSerializable actualULLSite;  // Instalacion a mostrar con su informacion
    ... // Elementos de la interfaz a configurar
    ArrayList<String> listItems=new ArrayList<String>(); // Lista de enlaces la instalacion 
    ArrayAdapter<String> adapter;   // Adaptador con los enlaces
    @Override // Metodo que inicia el activity
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_site_description); // Layout con la vista
        ... // Se configura la barra superior de la ventana
        // Se obtiene el objeto con la instalacion a mostrar
        actualULLSite = (ULLSiteSerializable) getIntent().getSerializableExtra("actualULLSite");
        listItems = actualULLSite.getInterestPoints(); // Se obtiene la lista de enlaces
        setUI();        // Se muestra la informacion de las instalacion en la vista
        setListSites();     // Se crea un adaptador con los enlaces las instalaciones
    }
    // Metodo que carga los textos, imagenes y enlaces de la instalacion a mostrar en la vista
    public void setUI() {
        ... // Se introduce la informacion del objeto de la instalacion en el layout 
        imageMaps.setOnClickListener(new View.OnClickListener() { 
            @Override // Comportamiento de boton que abre la ubicacion de Google Maps
            public void onClick(View v) { // Se le pasa a la url de maps + las coordenadas
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr="+ actualULLSite.getPoint().getY() + "," + actualULLSite.getPoint().getX());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps"); // Paquete de Google Maps
                startActivity(mapIntent);   // Se lanza el intent que abre la ubicacion
            }
        });
    }
    // Este metodo crea una adapatador con los enlaces de la instalacion
    public void setListSites() {
        adapter = new ArrayAdapter<String>(this, R.layout.link_item, android.R.id.text, listItems){
            @Override // Se configura la vista de cada enlace
            public View getView(int position, View convertView, ViewGroup parent) {
                ... // Se indica que la vista esta contenida  en el fichero "link_item.xml"
            }       // Se introduce el nombre del enlace
        };
        setListAdapter(adapter); // Se indica al ListView por defecto de Android su adaptador
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                ... // Se lanza la url en el navegador externo
            }
        });
        justifyListViewHeightBasedOnChildren(getListView()); // Se ajustan las dimensiones de
    }                                                              // la ventana
    // Metodo que recalcula las dimensiones del layout para poder hacer scroll horizontal
    public static void justifyListViewHeightBasedOnChildren (ListView listView) { ... }
}
