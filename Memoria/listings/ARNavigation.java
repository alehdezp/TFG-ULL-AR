
/**
 * Clase que contiene el funcionamiento del AR de nuestro programa
 *  Muestra al usuario informacion sobre los centros de la ULL que se
 *  encuentran cerca mediante la pantalla y el lugar al que apunte
 *
 */
public class ARNavigation extends ARActivity {

    private Navigation navULL;

    private SensorManager mSensorManager;
    private Sensor compass;
    private PackageManager m;

    private LocationManager locationManager;
    private Location currentLocation;
    private Boolean locationEnable = false;

    private ArrayList<ULLSite> allResultsSites;
    private ArrayList<ULLSite> moreResultsSites;
    private ULLSite nearSiteResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aractivity);
        settingsPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        getSitesFromDB();   
        getRadius();
        bindUI();

        if (checkPermissions()) {
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            Sensor compass = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            mSensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL);
            isGPSEnabled();
        }

        gestureDetect = new GestureDetectorCompat(this, this);
        ARAPIKey key = ARAPIKey.getInstance();
        key.setAPIKey(apikey);
    }

    private void getRadius() {
        // Obtenemos los radios de los ajustes de configuracion de la aplicacion
    }

    private void bindUI() { ...}


    private void getSitesFromDB() {

            GetData getSites = new GetData();
            String sites = getSites.execute("https://server-ull-navigation.herokuapp.com/api/ull-sites").get();

            JSONArray arraySites = new JSONArray(sites);
            navULL = new Navigation(arraySites);
            Log.d("json", sites);
    }

    public void onSensorChanged(SensorEvent event) {
        double radians = event.values[0]; //Calculamos el valor de orientacion de la brujula del dispositivo
        if (radians >= 360)
            radians = radians - 360;
        radians = Math.toRadians(radians);
        LatLng auxpos = getCurrentPos();

        if (auxpos != null) {
            allResultsSites = navULL.whatCanSee(auxpos, radians);
        }
        if (allResultsSites != null) {
            nearSiteResult = allResultsSites.get(0);
            alternateUIInfo(true, nearSiteResult.getName());
            showHideUIMore(true);
            if(allResultsSites.size() > 2) {
                setMoreSites(true, allResultsSites);
            }
        } else {
            alternateUIInfo(false, "");
            showHideUIMore(false);
            setMoreSites(false, null);
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == moreSitesButton.getId()){
            Intent intent = new Intent(this, SitesListActivity.class);
            ArrayList aux = new ArrayList(moreResultsSites.subList(1, moreResultsSites.size()-1));
            SitesArray sitesArray = new SitesArray(aux);
            intent.putExtra("sitesToShow", sitesArray);
            startActivity(intent);
        }
        if(v.getId() == moreInfoButton.getId()){
            Intent intent = new Intent(getApplicationContext(), SiteDescriptionActivity.class);
            startActivity(intent);
        }

    }


    private void setMoreSites(boolean show, ArrayList<ULLSite> allSeenResults){

        if(show == true) {
            String found = "Encontrada";
            String localizations = "instalacion";
            if(allSeenResults.size() > 3){
                found += "s";
                localizations = "instalaciones";
            }
            moreSitesButton.setVisibility(View.VISIBLE);
            moreResultsSites = allSeenResults;
            moreSitesButton.setText(found + " " + (allResultsSites.size() - 2) + " " +localizations + "mas en esta direccion");

        }else {
            moreSitesButton.setVisibility(View.GONE);
        }
    }


    @SuppressLint("MissingPermission")
    private LatLng getCurrentPos() {

        if (this.checkPermissions() && enableLocation()) {
            try {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng currentPos = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                return currentPos;
            } catch (Exception e) {
            }
        }
        return null;
    }


    private boolean enableLocation() {

        if (locationEnable == true)
            return true;
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (!checkPermissions()) {
            locationEnable = false;
            return false;

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 5, this);
            locationEnable = true;
            return true;
        }
    }


    public boolean isGPSEnabled() {

        try {
            int gpsSignal = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal == 0) {
                enableGPSAlert();
                return false;
            } else
                return true;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void enableGPSAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("Senal GPS")
                .setMessage("Activa la senal GPS para poder obtener tu ubicacion actual")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }




    public boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            return false;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        this.recreate();
                    }
                }
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        enableLocation();
                        getCurrentPos();
                    }
                }
            }
        }
    }
}