public class HomeFragment extends Fragment implements View.OnClickListener {
    private List<ItemHome> itemsHome;  // Lista de items a mostrar
    private RecyclerView recyclerView; // Vista de la lista de items
    private RecyclerView.Adapter homeAdapter;   // Adaptador 
    private RecyclerView.LayoutManager homeLayoutManager; //Layout
    public View onCreateView( ... ) { ... } //Constructor
    // Creamos la lista de items con su nombre, imagen y si es un enlace web
    private void setAllItems() { 
        ... // Resto de items
        itemsHome.add(new ItemHome("Pagina de la ULL", "home_ull_site", true, "www.ull.es")); // Item con enlace externo
    }
    public void onViewCreated(View view, @Nullable Bundle ...) {
        // RecyclerView 
        recyclerView =getActivity().findViewById(R.id.recyclerView_Home); 
        // Se instancia el objeto ItemHomeAdapter con las lista de items
        homeAdapter = new ItemHomeAdapter(itemsHome, R.layout.adapter_home_item new ItemHomeAdapter.OnItemClickListener() {
            public void onItemClick(ItemHome item, int position) { ... }});
        //Se asigna el tipo layout y el adaptador a la vista RecyclerView
        homeLayoutManager = new LinearLayoutManager(getContext()); 
        recyclerView.setLayoutManager(homeLayoutManager); 
        recyclerView.setAdapter(homeAdapter);          
    }
