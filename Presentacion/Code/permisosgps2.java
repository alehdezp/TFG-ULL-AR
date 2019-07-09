// Si el usuario no ha concedido los permisos para utilizar el GPS en la aplicacion
if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) { 
    // Se solicita el permiso para acceder a los datos de GPS de dispositivo 
    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);  
}