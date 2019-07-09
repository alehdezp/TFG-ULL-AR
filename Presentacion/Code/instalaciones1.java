public class SiteAdapter extends BaseAdapter implements Filterable { ...
    // Instalaciones a mostrar con el filtro de busqueda aplicado
    private ArrayList<ULLSiteSerializable> filteredSites; 
    public SiteAdapter( ... ) { .. } // Constructor 
    // Se crea la vista de cada item con su imagen, nombre y descripcion
    public View getView(int position ...) { ... }
    // Filtro de busqueda a aplicar sobre la lista a mostrar
    public Filter getFilter() {
        return new Filter() {
            FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString(); // Filtro
                if (charString.isEmpty()) { ... } // No hay filtro 
                else { ...  } // Se crea un lista las instalaciones que coinciden con los caracteres de "charString"
                // Instalaciones filtradas
                filterResults.values = filteredSites; 
                return filterResults; // Se devuelve el resultado del filtro
            }
            protected void publishResults(... FilterResults filterResults) {
                filteredSites = (ArrayList<ULLSite...>) filterResults.values; 
                notifyDataSetChanged(); // Se indica al adaptador que el 
            }                    // contenido de la lista ha sido modificado
        };
