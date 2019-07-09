public class BaseActivity {
    DrawerLayout drawerLayout; // Atributo del Navigation Drawer
    ActionBarDrawerToggle actionBarDrawerToggle; // Boton del menu
    protected void onCreate(Bundle savedInstanceState) { //Constructor
        //Layout con el Navigation Drawer
        setContentView(R.layout.navigation_draw); 
        // Se enlaza el Navigation Drawer de la vista
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Se incorpora el boton de menu a la barra superior izquierda
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout...);
        // Vista del menu del Navigation Drawer
        NavigationView navigationView = findViewById(R.id.navigation_view);
        // Se enlazan los elementos del menu con su respuesta
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // Cuando un elemento del menu sea seleccionado ejecutamos las acciones correspondientes
            public boolean onNavigationItemSelected(MenuItem item) {...});
        } 
}