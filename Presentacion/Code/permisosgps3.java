locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);
locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 5, this);