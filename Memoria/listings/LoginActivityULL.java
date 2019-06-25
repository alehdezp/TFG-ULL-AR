public class LoginActivityULL extends AppCompatActivity implements ... {
    private GoogleApiClient googleApiClient;
    //Metodo que se ejecuta cuando se lanza la ventana
    protected void onCreate(Bundle savedInstanceState) {
        ... 
        //Opciones de la autentificacion que deseamos realizar
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //Creamos una instancia de la API de google con nuestras opciones
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    }
    //Manejo de eventos
    public void onClick(View v) {
        if (v.getId() == loginButton.getId()) { //Si pulsamos loginButton
            //Creamos y lanzamos el cuadro de dialogo de Google que permite autentificarse
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(intent, 777);
    }
    //Metodo que obtiene el resultado de la autentificacion
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 777){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSingInResult(result); //Manejamos el resultado de la autentificacion
        }
    }
    //Metodo que comprueba si ha sido correcta la autentificacion con Google
    private void handleSingInResult(GoogleSignInResult result){
        if(result.isSuccess()== true){ //Exito en el inicio de sesion con Google
            String userEmail = result.getSignInAccount().getEmail(); //Correo de la cuenta
            if(userEmail.matches("(.*)@ull.edu.es") ) { //Comprobamos si es un correo de la ULL
                Intent intent = new Intent(this, MainActivity.class); //Ejecutamos la ventana 
                startActivity(intent);  //principal de la aplicacion
            }else{ logoutNotULLAcount(); } //No es un correo universitario  
        }else{ ... } //Fallo al conectar con Google
    }
    //Metodo que realiza el logout de la cuenta cuando la cuenta no pertenece a la ULL
    private void logoutNotULLAcount(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback( ... )>(){
                    ... //Mensaje que indica que el correo no es valido y el tipo de 
            }             //correo necesario "aluxxxxxxxxx@ull.edu.es"
        });
    }
}

