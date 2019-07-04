public class SiteAdapter extends BaseAdapter implements Filterable { ...
    private ArrayList<ULLSiteSerializable> allSites;  // Todas las instalaciones
    // Instalaciones a mostrar con el filtro de busqueda aplicado
    private ArrayList<ULLSiteSerializable> filteredSites; 
    public SiteAdapter(Context context, int layout, SitesArray sitesULL) {  // Constructor
        ... // layout y context
        allSites = sitesULL.getUllSiteSerializables(); // Se guarda el array con todas las instalaciones
        filteredSites = allSites;  // Instalaciones a mostrar en un inicio
    }
    // Se crea la vista de cada item
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;  // vista 
        LayoutInflater layoutInflater = LayoutInflater.from(context); 
        // Se infla la vista con el layout de la instalacion "site_item.xml"
        v = layoutInflater.inflate(R.layout.site_item, null); 
        ... // Se enlazan en la vista el nombre, imagen y descripcion de la instalacion
        // La libreria Glide  permite cargar imagenes de enlaces de la web
        RequestManager requestManager = Glide.with(v.getContext());
        RequestBuilder requestBuilder = requestManager.load(filteredSites.get(position).getImageLink()); // Se obtiene la url de la imagen de la instalacion
        requestBuilder.into(imageSite); // Se carga la imagen en la vista
        return v; // Se devuelve la vista
    }
    public Filter getFilter() {
        return new Filter() { // Se instancia un objeto "Filter"
            FilterResults performFiltering(CharSequence charSequence) { // Aplicamos el filtro
                String charString = charSequence.toString();  // String con el filtro
                if (charString.isEmpty()) {      // Si esta vacio
                    filteredSites = allSites;   // No hay filtro y se muestran todas las instalaciones
                } else { // Si no
                    ArrayList<ULLSiteSerializable> auxFilteredList = new ArrayList<>();
                    for (ULLSiteSerializable site : allSites) { // Para todas las instalaciones 
                        // Se comprueba si el nombre la filtro coincide con la instalacion
                        if (site.getName().toLowerCase().contains(charString.toLowerCase())) 
                            auxFilteredList.add(site); // Si coincide se agregan a la lista
                                                           // auxiliar
                    }
                    filteredSites = auxFilteredList; // La lista auxiliar es igual a la de 
                }                                       // las instalaciones filtradas a mostrar
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredSites;
                return filterResults; // Se devuelve el resultado del filtro
            }
            @Override // Metodo que se ejecuta cuando se aplica el filtro
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredSites = (ArrayList<ULLSite...>) filterResults.values; 
                notifyDataSetChanged(); // Se le dice a adaptador que el array con las instalaciones
            }                             // ha sido modificado
        };
    }
}
