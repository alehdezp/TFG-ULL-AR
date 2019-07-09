public class SitesListActivity extends AppCompatActivity { ...
    private ListView listSites; // Vista  con la lista de instalaciones
    private SitesArray sitesToShow; // Lista de instalaciones
    private SearchView searchView;  // Barra superior de busqueda
    SiteAdapter siteAdapter;        // Adaptador de las instalaciones
    protected void onCreate(Bundle savedInstanceState) { ...
        //La lista de instalaciones se obtiene del activity anterior
        sitesToShow = (SitesArray) getIntent().getSerializableExtra("sitesToShow");
    }
    // Metodo que instancia el objeto la clase "SiteAdapter" 
    private void showDinamicSites() {  
        // ListView que muestra la lista de las instalaciones
        listSites = findViewById(R.id.listSites);
        //Instanciamos el Adaptador con la lista de instalaciones sitesToShow
        siteAdapter = new SiteAdapter(this, R.layout.site_item, sitesToShow); 
        listSites.setAdapter(siteAdapter); //Adaptador del ListView
        //Manejo de eventos, se ejecuta la ventana con la ficha de informacion
        listSites.setOnItemClickListener(...){} //de la instalacion 
    }


