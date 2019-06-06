public class SiteDescriptionActivity extends ListActivity {
    ULLSiteSerializable actualULLSite;  //Instalacion con su informacion
    ... //Elementos de la interfaz a configurar
    ArrayList<String> listItems=new ArrayList<String>(); //Lista de enlaces la instalacion 
    ArrayAdapter<String> adapter;   //Adaptador con los enlaces
    @Override //Metodo que inicia el activity
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_site_description); //Layout con la vista
        //Configuramos la barra superior de la ventana
        actualULLSite = (ULLSiteSerializable) getIntent().getSerializableExtra("actualULLSite");
        listItems = actualULLSite.getInterestPoints();
        setUI();        //Introducimos los elementos visuales de layout
        setListSites();     //Creamos el adaptador con los enlaces las instalaciones
    }
    //Metodo que carga los textos, imagenes y enlaces de la instalacion a mostrar en la vista
    public void setUI(){
        ... //Introducimos el resto de elementos del layout 
        imageMaps.setOnClickListener(new View.OnClickListener() { 
            @Override //Comportamiento de boton que nos abre la ubicacion de Google Maps
            public void onClick(View v) { //Le pasamos a la url de maps + las coordenadas
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr="+ actualULLSite.getPoint().getY() + "," + actualULLSite.getPoint().getX());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps"); //Paquete de Google Maps
                startActivity(mapIntent);   //Lanzamos el intent que nos abre la ubicacion
            }
        });
    }
    //Este metodo crea una adapatador con los enlaces de la instalacion
    public void setListSites(){
        adapter = new ArrayAdapter<String>(this, R.layout.link_item, android.R.id.text1,
                listItems) { //
            @Override //Configuramos la vista de cada enlace
            public View getView(int position, View convertView, ViewGroup parent) {
                ... //Indicamos que la vista esta contenida  en el fichero "link_item.xml"
            }       //Introducimos el nombre del enlace
        };
        setListAdapter(adapter); //Indicamos al ListView por defecto de Android su adaptador
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                ... //Lanzamos la url en el navegador externo
            }
        });
        justifyListViewHeightBasedOnChildren(getListView()); 
    }
    //Metodo que recalcula las dimensiones del layout para poder hacer scroll horizontal
    public static void justifyListViewHeightBasedOnChildren (ListView listView) { ... }
}
