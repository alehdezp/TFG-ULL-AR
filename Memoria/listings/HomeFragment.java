public class HomeFragment extends Fragment implements View.OnClickListener {
    private List<ItemHome> itemsHome;  //Lista de items a representar en la vista
    private RecyclerView recyclerView; //Vista que contendra todos los items
    private RecyclerView.Adapter homeAdapter;   //Contendra el objeto de la clase ItemHomeAdapter 
    private RecyclerView.LayoutManager homeLayoutManager; //Layout del RecyclerView 
    public View onCreateView(LayoutInflater inflater... ) { //Primer metodo que se ejecuta
        setAllItems(); //Instanciamos los items
        //Inflamos la vista con el layout fragment_home.xml
        return inflater.inflate(R.layout.fragment_home, container, false); 
    }
    //Instanciamos cada item con su nombre, imagen y si es un link externo y su url
    private void setAllItems(){ 
        ArrayList<ItemHome> auxItems = new ArrayList<ItemHome>();
        auxItems.add(new ItemHome("Navegacion en modo RA", "home_ar_inicio", false, null));
        auxItems.add(new ItemHome("Pagina de la ULL", "home_ull_site", true, "www.ull.es"));
        ... //Resto de items
        itemsHome = (List) auxItems; //Los guardamos en atributo itemsHome
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView =getActivity().findViewById(R.id.recyclerView_Home); //RecyclerView 
        homeLayoutManager = new LinearLayoutManager(getContext()); //Asignamos un LinearLayout
        //Instanciamos el objeto ItemHomeAdapter
        homeAdapter = new ItemHomeAdapter(itemsHome, R.layout.adapter_home_item, 
        new ItemHomeAdapter.OnItemClickListener(){ 
            //Indicamos el comportamiento cuando se selecciona un item
            public void onItemClick(ItemHome item, int position){
                    switch (position) {
                        case 0: ... //Lanzamos los activitys y fragments correspondientes
                        default: //Por defecto se considerara un enlace web externo
                            String url = item.getLink();    //Obtenemos el link
                            Intent i = new Intent(Intent.ACTION_VIEW); 
                            //Le decimos a Android la accion y le pasamos a url
                            i.setData(Uri.parse("http://" + url));
                            startActivity(i);   //Nos lanza a la pagina en el navegador externo
                            break;              //del dispositivo
                    }
            }
        });
        recyclerView.setLayoutManager(homeLayoutManager); //Asignamos el layout a recyclerView
        recyclerView.setAdapter(homeAdapter);             //y el adaptador
    }
}
