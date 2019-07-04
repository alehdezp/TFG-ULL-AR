@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsULLActivity extends AppCompatPreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences settingsPref; // "Shared Preferences" con los valores de las preferencias
    ...   // Constantes para acceder a los valores "maxRadius" "minRadius" y "showRadius" de las Shared            Prefences y su vista asociada en el XML
    
    public void onCreate(Bundle savedInstanceState) {
        ...
        addPreferencesFromResource(R.xml.pref_nav_settings); // XML con las preferencias
        setTopActionBar(); // barra superior de la aplicacion
        ... // Preferencias del layout y su contenido 
    }
    // Metodo que se lanza cuando las preferencias son editadas 
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case SHOW_RADIUS_STRING: // Se activa o desactiva la edicion radios de forma manual
                maxRadiusPreference.setEnabled(sharedPreferences.getBoolean(key, false));
                minRadiusPreference.setEnabled(sharedPreferences.getBoolean(key, false));
                break;
            case MAX_RADIUS_STRING: // Se actualiza el valor de la vista de maxRadius
                ....
            case MIN_RADIUS_STRING:// Se actualiza el valor de minRadius y se comprueba si es mayor 
                ...                   // que maxRadius 
        }
    }
    ...
}
