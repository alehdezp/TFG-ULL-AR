public class SitesListActivity extends AppCompatActivity { ...
    private ListView listSites; // Vista que contendra el adaptador con la lista de instalaciones
    private SearchView searchView;  // Barra superior de busqueda
    SiteAdapter siteAdapter;        // Adaptador de las instalaciones
    protected void onCreate(Bundle savedInstanceState) { 
        setContentView(R.layout.activity_sites_list); // Layout principal
        ... // Configuracion de la barra superior
        // Se obtiene lista de las instalaciones enviadas por el activity anterior
        sitesToShow = (SitesArray) getIntent().getSerializableExtra("sitesToShow"); 
        showDinamicSites(); // Se muestran las instalaciones contenidas en la lista 
    }
    // Metodo que crea un objeto de la clase "SiteAdapter" para mostar las instalaciones 
    private void showDinamicSites() {
        listSites = findViewById(R.id.listSites); // ListView que contendra las instalaciones 
        siteAdapter = new SiteAdapter(this, R.layout.site_item, sitesToShow); // Adaptador
        listSites.setAdapter(siteAdapter); // Se indica al ListView su adaptador 
        // Cuando se seleccione una instalacion de la lista
        listSites.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SiteDescriptionActivity.class);
                // Se instancia el activity "SiteDescriptionActivity" que muestra la informacion  
                // detallada de la instalacion que se le pasa como extra
                intent.putExtra("actualULLSite", siteAdapter.getFilteredSites().get(position));
                startActivity(intent); }  // Se lanza el activity
        });
    }
    @Override // Barra de busqueda y su comportamiento a eventos
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar,menu); // Layout con la barra de busqueda
        MenuItem searchItem = menu.findItem(R.id.app_bar_search); // Barra de busqueda
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem); // Texto con el filtro
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override // Cuando se presione el boton de busqueda 
            public boolean onQueryTextSubmit(String query) {
                siteAdapter.getFilter().filter(query); // Le Se indica al adaptador que aplique
                return false; }                              // el filtro
            @Override // Cuando el texto cambie
            public boolean onQueryTextChange(String newText) {...} // Se aplica el filtro 
        });
        return super.onCreateOptionsMenu(menu); // Se devuelve la barra de busqueda
    } 
    ...
}
