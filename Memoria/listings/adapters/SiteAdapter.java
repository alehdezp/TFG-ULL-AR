public class SiteAdapter extends BaseAdapter implements Filterable {
    ... //layout, context y elementos de la vista
    private ArrayList<ULLSiteSerializable> allSites;  //Todas las instalaciones
    //Instalaciones a mostrar con el filtro de busqueda aplicado
    private ArrayList<ULLSiteSerializable> filteredSites; 
    //Constructor
    public SiteAdapter(Context context, int layout, SitesArray sitesULL){
        ... //layout y context
        allSites = sitesULL.getUllSiteSerializables(); //Guardamos el array con todas las instalaciones
        filteredSites = allSites;  //Instalaciones a mostrar en un inicio
    }
    ... //@Override Metodos a implementar de la clase "BaseAdapter"
    //Creamos la vista de cada item
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;  //vista 
        LayoutInflater layoutInflater = LayoutInflater.from(context); 
        //Inflamos la vista con el layout de la instalacion "site_item.xml"
        v = layoutInflater.inflate(R.layout.site_item, null); 
        ... //Enlazamos en la vista el nombre, imagen y descripcion de la instalacion
        //La libreria Glide nos permite cargar imagenes de enlaces de la web
        RequestManager requestManager = Glide.with(v.getContext());
        RequestBuilder requestBuilder = requestManager.load(filteredSites.get(position).getImageLink()); //Obtenemos la url de la imagen de la instalacion
        requestBuilder.into(imageSite); //Cargamos la imagen en la vista
        return v; //Devolvemos la vista
    }
    public Filter getFilter() {
        return new Filter() { //Instanciamos un objete "Filter"
            @Override //Metodo que aplica el filtro en funcion de los caracteres que se le pasen
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();  //String con el filtro
                if (charString.isEmpty()) {      //Si esta vacio
                    filteredSites = allSites;   //No hay filtro y se muestran todas las instalaciones
                } else { //Si no
                    ArrayList<ULLSiteSerializable> auxFilteredList = new ArrayList<>();
                    for (ULLSiteSerializable site : allSites) { //Para todas las instalaciones 
                        //Comprobamos si el nombre la filtro coincide con la instalacion
                        if (site.getName().toLowerCase().contains(charString.toLowerCase())) 
                            auxFilteredList.add(site); //Si coincide los agregammos a la lista
                                                           // auxiliar
                    }
                    filteredSites = auxFilteredList; //La lista auxiliar es igual a la de 
                }                                       //las instalaciones filtradas a mostrar
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredSites;
                return filterResults; //Devolvemos el resultado del filtro
            }
            @Override //Metodo que se ejecuta cuando se aplica el filtro
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredSites = (ArrayList<ULLSite...>) filterResults.values; 
                notifyDataSetChanged(); //Le decimos a adaptador que el array con las instalaciones
            }                             //ha sido modificado
        };
    }
}
