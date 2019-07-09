public class SiteDescriptionActivity extends ListActivity { ...
    ULLSiteSerializable actualULLSite;  // Instalacion a mostrar
    // Lista de enlaces de la instalacion 
    ArrayList<String> listItems = new ArrayList<String>(); 
    @Override // Metodo que inicia el activity
    public void onCreate(Bundle savedInstanceState) { ...
        // Vista principal
        setContentView(R.layout.activity_site_description); 
        // El objeto con la instalacion proviene del activity anterior
        actualULLSite = getIntent().getSerializableExtra("actualULLSite");
        setUI();    
        setListSites();
    }
    // Metodo que carga los textos, imagenes y enlaces de la instalacion a mostrar en la vista
    public void setUI() { ... }
    // Este metodo crea un adapatador con los enlaces de la instalacion y carga en el navegador del dispositivo el enlace cuando es seleccionado
    public void setListSites() { ... }                                            
}
