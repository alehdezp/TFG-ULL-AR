    public boolean onCreateOptionsMenu(Menu menu) { 
        ...//Configuramos la barra de busqueda
        //Eventos cuando se escriba se aplica el filtro en el adaptador
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override // Cuando se presione el boton de busqueda 
            public boolean onQueryTextSubmit(String query) {
                siteAdapter.getFilter().filter(query); // Filtro a aplicar 
                return false; }
            @Override // Cuando el texto cambia 
            public boolean onQueryTextChange(String newText) {...} 
        });
        return super.onCreateOptionsMenu(menu); // Se devuelve la barra de busqueda
    } 