public class SitesListActivity extends AppCompatActivity {
    private SitesArray sitesToShow; //Array de instalaciones
    private ListView listSites;     //Vista que contendra el adapatador con las instalaciones
    private SearchView searchView;  //Barra de busqueda
    SiteAdapter siteAdapter;        //Adaptador de las instalaciones
    @Override //Metodo que crea el activity
    protected void onCreate(Bundle savedInstanceState) { ...
        setContentView(R.layout.activity_sites_list); //Layout principal
        ... //Configuracion de la barra superior
        //Obtenemos lista de las instalaciones enviadas por el activity anterior
        sitesToShow = (SitesArray) getIntent().getSerializableExtra("sitesToShow"); 
        showDinamicSites(); //Mostramos las instalaciones contenidas en la lista 
    }
    //Metodo que instancia la clase "SiteAdapter" par mostar las instalaciones 
    private void showDinamicSites(){
        listSites = findViewById(R.id.listSites); //ListView que contendra las instalaciones
        //Instanciamos el Adaptador
        siteAdapter = new SiteAdapter(this, R.layout.site_item, sitesToShow); 
        listSites.setAdapter(siteAdapter); //Indicamos al adaptador del ListView 
        //Cuando se seleccione una instalacion
        listSites.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SiteDescriptionActivity.class);
                //Instanciamos el activity "SiteDescriptionActivity" que muestra la informacion  
                //detallada de la instalacion que le pasamos como extra
                intent.putExtra("actualULLSite", siteAdapter.getFilteredSites().get(position));
                startActivity(intent); //Lanzamos el activity
            }
        });
    }
    @Override //Instanciamos la barra de busqueda y su comportamiento a eventos
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar,menu); //Layout con la barra de busqueda
        MenuItem searchItem = menu.findItem(R.id.app_bar_search); //Barra de busqueda
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem); //Filtro a aplicar
        searchView.setIconifiedByDefault(true);  
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override //Cuando se presione el boton de busqueda 
            public boolean onQueryTextSubmit(String query) {
                siteAdapter.getFilter().filter(query); //Le indicamos al adaptador que aplique
                return false;                              //el filtro
            }
            @Override //Cuando el texto cambie
            public boolean onQueryTextChange(String newText) {
                siteAdapter.getFilter().filter(newText); //Le indicamos al adaptador que aplique
                return false;                                //el filtro
            }
        });
        return super.onCreateOptionsMenu(menu); //Devolvemos la barra de busqueda
    }
    ...
}
