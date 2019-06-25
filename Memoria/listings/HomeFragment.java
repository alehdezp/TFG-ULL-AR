public class HomeFragment extends Fragment implements View.OnClickListener {
    private List<ItemHome> itemsHome;  // Lista de items a representar en la vista
    private RecyclerView recyclerView; // Vista que contendra todos los items
    private RecyclerView.Adapter homeAdapter;   // Contendra el objeto de la clase ItemHomeAdapter 
    private RecyclerView.LayoutManager homeLayoutManager; // Layout del RecyclerView 
    public View onCreateView(LayoutInflater inflater... ) { // Primer metodo que se ejecuta
        setAllItems(); // Se instancia los items
        // Se infla la vista con el layout fragment_home.xml
        return inflater.inflate(R.layout.fragment_home, container, false); 
    }
    // Se instancia cada item con su nombre, imagen y si es un link externo y su url
    private void setAllItems(){ 
        ArrayList<ItemHome> auxItems = new ArrayList<ItemHome>();
        auxItems.add(new ItemHome("Navegacion en modo RA", "home_ar_inicio", false, null));
        auxItems.add(new ItemHome("Pagina de la ULL", "home_ull_site", true, "www.ull.es"));
        ... // Resto de items
        itemsHome = (List) auxItems; // Se guardan en atributo itemsHome
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView =getActivity().findViewById(R.id.recyclerView_Home); // RecyclerView 
        homeLayoutManager = new LinearLayoutManager(getContext()); // Se asigna un LinearLayout
        // Se instancia el objeto ItemHomeAdapter
        homeAdapter = new ItemHomeAdapter(itemsHome, R.layout.adapter_home_item, 
        new ItemHomeAdapter.OnItemClickListener(){ 
            // Se indica el comportamiento cuando se selecciona un item
            public void onItemClick(ItemHome item, int position){
                    switch (position) {
                        case 0: ... // Se lanzan los activities y fragments correspondientes
                        default: // Por defecto se considerara un enlace web externo
                            String url = item.getLink();    // Se obtiene el link
                            Intent i = new Intent(Intent.ACTION_VIEW); 
                            // Se le dice a Android la accion y se le pasa la url
                            i.setData(Uri.parse("http://" + url));
                            startActivity(i);   // Abre la url en el navegador externo
                            break;              // del dispositivo
                    }
            }
        });
        recyclerView.setLayoutManager(homeLayoutManager); // Se asigna el layout a recyclerView
        recyclerView.setAdapter(homeAdapter);             // y el adaptador
    }
}
