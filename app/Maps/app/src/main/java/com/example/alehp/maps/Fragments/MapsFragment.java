package com.example.alehp.maps.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.alehp.maps.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        View.OnClickListener, LocationListener{


    private View rootView;
    private MapView mapView;
    private GoogleMap goMap;
    private FloatingActionButton buttonCurrentPos;

    private Button buttonARStart;

    private LocationManager locationManager;
    private Location currentLocation;
    private ArrayList<LatLng> ullSites = new ArrayList<LatLng>() {{
        add(new LatLng(28.468, -16.273));
        add(new LatLng(28.467, -16.272));
        add(new LatLng(28.466, -16.271));
        add(new LatLng(28.466, -16.273));
    }};


    private LatLng myHome = new LatLng(28.46749041, -16.27173752);
    private float rotate = 0;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        buttonCurrentPos = (FloatingActionButton) rootView.findViewById(R.id.buttonCurrentPos);
        buttonARStart = (Button) rootView.findViewById(R.id.buttonARStart);

        buttonCurrentPos.setOnClickListener(this);
        buttonARStart.setOnClickListener(this);

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
        if(checkLocationPermission()) {
            goMap.setMyLocationEnabled(true);
            goMap.getUiSettings().setMyLocationButtonEnabled(false);
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 5, this);
        }

        goMap.getUiSettings().setZoomControlsEnabled(true);

        //Obtenemos la localizacion actual y manejamos sus eventos


        //Zoom minimo de la app
        goMap.setMinZoomPreference(14);


        goMap.addMarker(new MarkerOptions().position(myHome).title("MY HOME"));

        for (int i = 0; i < ullSites.size(); i++){
            goMap.addMarker(new MarkerOptions().position(ullSites.get(i)));
        }

        goMap.moveCamera(CameraUpdateFactory.newLatLng(myHome));
        goMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(LatLng latLng) {

//        rotateCam(90);
    }


    //Rotamos la camara en funcion del angulo que queramos desplazar
    private void rotateCam(float changeRotation) {
        rotate += changeRotation;

        CameraPosition mCam = new CameraPosition.Builder()
                .target(myHome)
                .zoom(20)
                .bearing(rotate)
                .tilt(90)
                .build();

        goMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCam));
    }




    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.buttonARStart:

                break;
            case R.id.buttonCurrentPos:
                centerMaps();
                break;

        }



    }

    private void centerMaps() {
        if (!isGPSEnabled()) {
            this.enableGPSAlert();

        }
        if (isGPSEnabled() && this.checkLocationPermission()) {
            try {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng currentPos = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                CameraPosition mCam = new CameraPosition.Builder().target(currentPos).zoom(18).build();
                goMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCam));

            } catch (Exception e) {
                Toast.makeText(getContext(), "Asegurate de que tienes el GPS activado" +
                        " o que se ha establecido tu ubicacion", Toast.LENGTH_LONG).show();
            }
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

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
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
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
//                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }


    @Override
    public void onLocationChanged(Location location) {
//        Toast.makeText(getContext(), "Lat: " + location.getLatitude() + ",Long: " + location.getLongitude(), Toast.LENGTH_LONG).show();
        LatLng currentPos = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition mCam = new CameraPosition.Builder().target(currentPos).build();
        goMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCam));
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
}
