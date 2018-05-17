package com.example.alehp.maps.Activities;

import android.graphics.Camera;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.alehp.maps.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private CameraPosition mCam;
    private LatLng myHome = new LatLng(28.46749041, -16.27173752);
    private float rotate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Zoom minimo de la app
        mMap.setMinZoomPreference(12);

        mMap.addMarker(new MarkerOptions().position(myHome).title("MY HOME"));

        mCam = new CameraPosition.Builder()
                .target(myHome)
                .zoom(18)
                .tilt(30)
                .build();

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(myHome));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCam));
        mMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(LatLng latLng) {

        rotateCam(90);
    }


    //Rotamos la camara en funcion del angulo que queramos desplazar
    private void rotateCam(float changeRotation) {
        rotate += changeRotation;
        mCam = new CameraPosition.Builder()
                .target(myHome)
                .zoom(20)
                .bearing(rotate)
                .tilt(90)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCam));
    }

    private void changePoint() {

        mMap.addMarker(new MarkerOptions().position(myHome).title("MY HOME").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
    }
}
