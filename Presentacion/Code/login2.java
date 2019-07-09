private void handleSignInResult(GoogleSignInResult result) {
    if(result.isSuccess()== true) { // Exito en el inicio de sesion
        // Correo de la cuenta
        String userEmail = result.getSignInAccount().getEmail(); 
        // Se comprueba si es un correo de la ULL
        if(userEmail.matches("(.*)@ull.edu.es") ) { 
            Intent intent = new Intent(this, MainActivity.class);
            // Se ejecuta la ventana de ``Inicio'' de la aplicacion
            startActivity(intent);  
        }else{ 
            logoutNotULLAcount();  // No es un correo universitario  
            }
    }else{ ... } // Fallo al conectar con Google
}