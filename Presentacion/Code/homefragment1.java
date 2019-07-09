public class ItemHomeAdapter extends RecyclerView.Adapter<...> {
    private List<ItemHome> items; //Lista de items a mostrar
    private int layout;           //Vista con los items
    private OnItemClickListener itemClickListener; //Manejo de eventos
    //Constructor con los atributos 
    public ItemHomeAdapter(List<ItemHome> items, int layout ...){ ... }
    // Metodo que asigna "layout" a la vista del parametro "parent"
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent...){ ...}
    // Se enlaza cada item con su vista
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos){ ... }
    // Clase que muestra y conecta cada item con su vista
    public static class ViewHolder extends RecyclerView.ViewHolder { 
        public TextView itemName;   //Atributos con los elementos
        ...                         //de la vista
        public ViewHolder(View itemView) { // Constructor con el layout
            this.itemName = itemView.findViewById(R.id.textView_home_item);     
            ... // Se enlaza los atributos de los con su vista
        }
        // Se asigna a cada vista el contenido de su item correspondiente 
        public void bind(final ItemHome itemHome, final ...) {
            this.itemName.setText(itemHome.getName()); // Se asigna el texto
            ... //Se asignan el resto de elementos de la vista
    }
