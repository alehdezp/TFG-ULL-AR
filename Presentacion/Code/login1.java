public class LoginActivityULL extends AppCompatActivity implements ... {
    private GoogleApiClient googleApiClient;
    // Metodo que se ejecuta cuando se lanza la ventana
    protected void onCreate(Bundle savedInstanceState) {
        ... 
        // Opciones de la autentificacion que se desea realizar
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        // Se crea una instancia de la API de google con las opciones
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    }
    // Manejo de eventos
    public void onClick(View v) {
        if (v.getId() == loginButton.getId()) { // Si se presiona loginButton
            // Se crea y lanza el cuadro de dialogo de Google que permite autentificarse
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(intent, 777);
    }