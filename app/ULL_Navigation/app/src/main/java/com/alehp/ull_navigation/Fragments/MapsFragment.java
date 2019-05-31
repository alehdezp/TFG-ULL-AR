package com.alehp.ull_navigation.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.alehp.ull_navigation.Activities.ARNavigation;
import com.alehp.ull_navigation.Models.GetData;
import com.alehp.ull_navigation.Models.Navigation;
import com.alehp.ull_navigation.Models.ULLSite;
import com.alehp.ull_navigation.Models.Vector2D;
import com.alehp.ull_navigation.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        View.OnClickListener, LocationListener, SharedPreferences.OnSharedPreferenceChangeListener{



    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String SHOW_RADIUS_STRING = "showRadius";
    private static final String MAX_RADIUS_STRING = "maxRadius";
    private static final String MIN_RADIUS_STRING = "minRadius";

    private View rootView;
    private MapView mapView;
    private GoogleMap goMap;
    private FloatingActionButton buttonCurrentPos;
    private Marker actualPosMarker;
    private Circle circleMax;
    private Circle circleMin;

    private Button buttonARStart;

    private LocationManager locationManager;
    private Location currentLocation;
    private Boolean locationEnable = false;
    private LatLng currentPosition;

    private LatLng ull = new LatLng( 28.481857638227176, -16.316877139026133);
    private LatLng myHome = new LatLng(28.46749041, -16.27173752);

    private int maxRadius;
    private int minRadius;
    private boolean showRadius;

    private ArrayList<ULLSite> allSites= new ArrayList<ULLSite>();
    private ArrayList<Marker> allMarkers = new ArrayList<Marker>();

    private SharedPreferences sharedPref;
    private SharedPreferences settingsPref;
    private Context mContext;

    public MapsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        settingsPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        settingsPref.registerOnSharedPreferenceChangeListener(this);

        getRadius();
        getSites();

        rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        buttonCurrentPos = rootView.findViewById(R.id.buttonCurrentPos);
        buttonARStart = rootView.findViewById(R.id.buttonARStart);

        buttonCurrentPos.setOnClickListener(this);
        buttonARStart.setOnClickListener(this);
        checkLocationPermission();

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        goMap = googleMap;
        goMap.getUiSettings().setCompassEnabled(true);


        //Necesita permisos de localizacion
        if (checkLocationPermission()) {
            enableLocation();
        }
        goMap.getUiSettings().setZoomControlsEnabled(true);
        //Zoom minimo de la app
        goMap.setMinZoomPreference(13);

        LatLng currentPos = getCurrentPos();
        if (currentPos != null) {
            actualPosMarker = goMap.addMarker(new MarkerOptions().position(currentPos).title("Im Here!"));
            goMap.moveCamera(CameraUpdateFactory.newLatLng(currentPos));
        }else{
            goMap.moveCamera(CameraUpdateFactory.newLatLng(ull));
        }
        goMap.setOnMapClickListener(this);

        drawAllSites();
        showSitesOnRadius(getCurrentPos());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void getSites(){

            getSitesFromDB();                   //Lee de la base de datos los sitios

    }

    private void getSitesFromDB() {

        try {
            GetData getSites = new GetData();
            String sites = getSites.execute("https://server-ull-ar.herokuapp.com/api/ull-sites").get();
            JSONArray array = new JSONArray(sites);
            allSites = new Navigation(array).getAllSites();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



    private void getRadius() {
        try {
            String auxMaxRadius = settingsPref.getString(MAX_RADIUS_STRING, "null");
            String auxMinRadius = settingsPref.getString(MIN_RADIUS_STRING, "null");
            showRadius = settingsPref.getBoolean(SHOW_RADIUS_STRING, false);

            maxRadius = Integer.parseInt(auxMaxRadius);
            minRadius = Integer.parseInt(auxMinRadius);

        }catch (Exception e){
            Log.e("Error radio", "error al parsear los radios de las circunferencias");
        }
    }


    private void centerMaps() {

        LatLng currentPos = getCurrentPos();
        if(currentPos != null){
            CameraPosition mCam = new CameraPosition.Builder().target(currentPos).zoom(18).build();
            goMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCam));
            drawActualPos();
        }
    }

    private void startARNavigation(){
        Intent myIntent = new Intent(getActivity(), ARNavigation.class);
        getActivity().startActivity(myIntent);
    }

    private LatLng getCurrentPos(){
        if (!isGPSEnabled())
            this.enableGPSAlert();

        if (this.checkLocationPermission() && enableLocation()) {
            try {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                currentPosition = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                return currentPosition;

            } catch (Exception e) {
//                Toast.makeText(getContext(), "Asegurate de que tienes el GPS activado" +
//                        " o que se ha establecido tu ubicacion", Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }

    private void drawAllSites() {
        for (int i = 0; i < allSites.size(); i++) {
            if(allSites.get(i).getMapPoint() != null) {
                allMarkers.add(goMap.addMarker(new MarkerOptions().position(getAllSites().get(i).getMapPoint()).title(allSites.get(i).getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));
            }else {
                Toast.makeText(getContext(), i + " es nulo", Toast.LENGTH_LONG);
            }
        }
    }

    private void drawActualPos(){
        LatLng currentPos = getCurrentPos();
        if(currentPos != null) {
            if (actualPosMarker == null)
                actualPosMarker = goMap.addMarker(new MarkerOptions().position(currentPos).title("Im Here!"));
              actualPosMarker.setPosition(currentPos);
        }
    }

    private void createCircles(LatLng position){
        if(position != null) {
            circleMin = goMap.addCircle(new CircleOptions()
                    .center(position)
                    .radius(minRadius)
                    .strokeColor(Color.RED)
                    .fillColor(R.color.colorWhiteTransparent)
                    .visible(showRadius)
            );

            circleMax = goMap.addCircle(new CircleOptions()
                    .center(position)
                    .radius(maxRadius)
                    .strokeColor(Color.BLUE)
                    .fillColor(R.color.colorBlueTransparent)
                    .visible(showRadius));
        }
    }

    private void drawCircles(LatLng position) {
        if(circleMax == null || circleMin == null){
            createCircles(position);
        }
        if(position != null) {
            circleMax.setCenter(position);
            circleMax.setRadius(maxRadius);
            circleMax.setVisible(showRadius);

            circleMin.setCenter(position);
            circleMin.setRadius(minRadius);
            circleMin.setVisible(showRadius);
        }
    }



    private void showSitesOnRadius(LatLng position){
//        Toast.makeText(getContext(), "showSites entrando", Toast.LENGTH_SHORT).show();
        if(position != null && showRadius == true) {
            double lat1 = position.latitude;
            double long1 = position.longitude;
            double lat2;
            double long2;
            double distance;
            for (int i = 0; i < allSites.size(); i++) {
                lat2 = allSites.get(i).getMapPoint().latitude;
                long2 = allSites.get(i).getMapPoint().longitude;
                distance = getDistanceBetween(lat1, long1, lat2, long2);
                if(distance > minRadius && distance < maxRadius){
                    allMarkers.get(i).setVisible(true);
                }else{
                    allMarkers.get(i).setVisible(false);
                }

            }
        }else {
            for (int i = 0; i < allMarkers.size(); i++) {
                allMarkers.get(i).setVisible(true);
            }
        }
    }

    private boolean enableLocation() {
        goMap.getUiSettings().setMyLocationButtonEnabled(false);
        if(locationEnable == true)
            return true;

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat       .checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkLocationPermission();
            locationEnable = false;
            return false;

        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 5, this);
            locationEnable = true;
            return  true;
        }
    }


    public double getDistanceBetween(double lat1, double long1, double lat2, double long2) {
        float result[] = new float[3];
        Location.distanceBetween(lat1, long1, lat2, long2, result);
        return result[0];
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonARStart:
                startARNavigation();
                break;
            case R.id.buttonCurrentPos:
                centerMaps();
                break;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case SHOW_RADIUS_STRING:
                showRadius = sharedPreferences.getBoolean(SHOW_RADIUS_STRING, false);
                if(showRadius){         //Hacemos visibles los circulos
                    getRadius();
                    drawCircles(getCurrentPos());
                    showSitesOnRadius(getCurrentPos());
                }else{
                    drawCircles(getCurrentPos());
                    showSitesOnRadius(getCurrentPos());
                }


            case MAX_RADIUS_STRING:
                getRadius();
                drawCircles(getCurrentPos());
                showSitesOnRadius(getCurrentPos());
                break;
            case MIN_RADIUS_STRING:
                getRadius();
                drawCircles(getCurrentPos());
                showSitesOnRadius(getCurrentPos());
                break;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        drawActualPos();
        LatLng position = getCurrentPos();
        drawCircles(position);
        showSitesOnRadius(position);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    public boolean isGPSEnabled(){
        try {
            if(getActivity() != null) {
                int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
                if (gpsSignal == 0) {
                    return false;
                } else
                    return true;
            }else{
                return false;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void enableGPSAlert(){
//        new AlertDialog.Builder(getContext())
//                .setTitle("Señal GPS")
//                .setMessage("Activa la señal GPS para poder obtener tu ubicación actual")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(intent);
//                    }
//                })
//                .setNegativeButton("CANCEL", null)
//                .show();
    }



    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                new AlertDialog.Builder(getContext())
//                        .setTitle(R.string.title_location_permission)
//                        .setMessage(R.string.text_location_permission)
//                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                requestPermissions( //Fragment request
//                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                        MY_PERMISSIONS_REQUEST_LOCATION);
//                            }
//                        })
//                        .create()
//                        .show();

            }else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        enableLocation();
                        getCurrentPos();
                    }
                } else {

                }
            }
        }
    }

    public ArrayList<ULLSite> getAllSites() {
        return allSites;
    }

    public void setAllSites(ArrayList<ULLSite> allSites) {
        this.allSites = allSites;
    }



}
