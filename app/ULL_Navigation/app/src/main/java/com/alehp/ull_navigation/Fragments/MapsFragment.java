package com.alehp.ull_navigation.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.alehp.ull_navigation.Activities.ARNavigation;
import com.alehp.ull_navigation.Models.ULLSite;
import com.alehp.ull_navigation.Models.Vector2D;
import com.alehp.ull_navigation.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        View.OnClickListener, LocationListener {


    private View rootView;
    private MapView mapView;
    private GoogleMap goMap;
    private FloatingActionButton buttonCurrentPos;
    private Marker actualPosMarker;



    private Button buttonARStart;

    private LocationManager locationManager;
    private Location currentLocation;
    private Boolean locationEnable = false;

    private LatLng ull = new LatLng( 28.481857638227176, -16.316877139026133);
    private LatLng myHome = new LatLng(28.46749041, -16.27173752);


    private ArrayList<ULLSite> allSites= new ArrayList<ULLSite>();

    public MapsFragment() {
   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Required empty public constructor
        getAllSites().add(new ULLSite("Facultad de Fisica y Matemáticas", "nº 922222222",
                new Vector2D(-16.271370844238504,28.467337376756998)));
        getAllSites().add(new ULLSite("Facultad de Ingenieria Informatica", "nº 922222222",
                new Vector2D(-16.272370844238504,28.466337376756998)));
        getAllSites().add(new ULLSite("Parking ESIT", "nº 922222222",
                new Vector2D(-16.273370844238504,28.467337376756998)));
        getAllSites().add(new ULLSite("Facultad de Fisica y Matemáticas", "nº 922222222",
                new Vector2D(-16.320805, 28.481849 )));
        getAllSites().add(new ULLSite("ESIT, Facultad de Ingenieria Informatica", "nº 922222222",
                new Vector2D(-16.322039,28.483075)));
        getAllSites().add(new ULLSite("Parking ESIT", "nº 922222222",
                new Vector2D(-16.321946,28.481952)));
        getAllSites().add(new ULLSite("Parking Facultad", "Some info",
                new Vector2D(-16.320909,28.482755)));

        getAllSites().add(new ULLSite("Edificio Fundacion de la Universidad de La Laguna", "Some info",
                new Vector2D(-16.317462,28.481930 )));
        getAllSites().add(new ULLSite("Edificio Central de la Universidad de La Laguna", "Some info",
                new Vector2D(-16.316690, 28.481753)));
        getAllSites().add(new ULLSite("Colegio Mayor San Fernando", "Some info",
                new Vector2D( -16.3157322, 28.481173)));
        getAllSites().add(new ULLSite("Parking de Estudiantes Universitarios", "Some info",
                new Vector2D(-16.315613, 28.481604)));
        getAllSites().add(new ULLSite("Campus Central - Torre Profesor Agustín Arévalo", "Some info",
                new Vector2D(-16.317531,28.481173)));
        getAllSites().add(new ULLSite("Deportes ULL", "Some info",
                new Vector2D(-16.316478,28.479994)));



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


        //Necesita permisos de localizacion
        if (checkLocationPermission()) {
            enableLocation();
        }

        goMap.getUiSettings().setZoomControlsEnabled(true);

        //Zoom minimo de la app
        goMap.setMinZoomPreference(13);

        LatLng currentPos = getCurrentPos();
        if (currentPos != null)
            actualPosMarker = goMap.addMarker(new MarkerOptions().position(currentPos).title("Im Here!"));


        goMap.moveCamera(CameraUpdateFactory.newLatLng(ull));
        goMap.setOnMapClickListener(this);

        drawAllSites();

    }



    private void drawAllSites() {
        for (int i = 0; i < getAllSites().size(); i++) {
            goMap.addMarker(new MarkerOptions().position(getAllSites().get(i).getMapPoint()).title("Im Here!")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {

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

    private void startARNavigation(){
        Intent myIntent = new Intent(getActivity(), ARNavigation.class);
        getActivity().startActivity(myIntent);
    }

    private LatLng getCurrentPos(){
        if (!isGPSEnabled()) {
            this.enableGPSAlert();
        }
        if (this.checkLocationPermission() && enableLocation()) {
            try {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng currentPos = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                return currentPos;
            } catch (Exception e) {
                Toast.makeText(getContext(), "Asegurate de que tienes el GPS activado" +
                        " o que se ha establecido tu ubicacion", Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }

    private void drawActualPos(){
        LatLng currentPos = getCurrentPos();
        if(currentPos != null) {
            if (actualPosMarker == null)
                actualPosMarker = goMap.addMarker(new MarkerOptions().position(currentPos).title("Im Here!"));

            actualPosMarker.setPosition(currentPos);
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


    public boolean isGPSEnabled(){
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if(gpsSignal == 0){
                return false;
            }else
                return true;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
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

    @Override
    public void onLocationChanged(Location location) {
        drawActualPos();
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

    private void enableGPSAlert(){
        new AlertDialog.Builder(getContext())
                .setTitle("Señal GPS")
                .setMessage("Activa la señal GPS para poder obtener tu ubicación actual")
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


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermissions( //Fragment request
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();

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

