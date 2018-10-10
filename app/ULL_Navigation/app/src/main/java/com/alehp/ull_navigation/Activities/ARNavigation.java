package com.alehp.ull_navigation.Activities;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.alehp.ull_navigation.Models.GetData;
import com.alehp.ull_navigation.Models.Navigation;
import com.alehp.ull_navigation.Models.ULLSite;
import com.alehp.ull_navigation.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import eu.kudan.kudan.ARAPIKey;
import eu.kudan.kudan.ARActivity;
import eu.kudan.kudan.ARArbiTrack;
import eu.kudan.kudan.ARGyroPlaceManager;
import eu.kudan.kudan.ARImageNode;
import eu.kudan.kudan.ARImageTrackable;
import eu.kudan.kudan.ARImageTracker;


public class ARNavigation extends ARActivity implements GestureDetector.OnGestureListener, LocationListener, SensorEventListener {

    private Navigation navULL;

    private SensorManager mSensorManager;
    private Sensor compass;
    private PackageManager m;

    private LocationManager locationManager;
    private Location currentLocation;
    private Boolean locationEnable = false;

    private TextView topRightText;
    private Button moreSitesButton;


    private GestureDetectorCompat gestureDetect;


    public static final int CAMERA_REQUEST_CODE = 101;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aractivity);


        gestureDetect = new GestureDetectorCompat(this, this);
        if (checkPermissions()) {
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            Sensor compass = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            mSensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL);

            topRightText = findViewById(R.id.ullSiteText);
            moreSitesButton = findViewById(R.id.moreSitesButton);
            isGPSEnabled();
            getSites();


        }



        ARAPIKey key = ARAPIKey.getInstance();
        key.setAPIKey("Y8/NRauyJ4RvhIsPcHHd7xhYLwiBZsn3+cqswOaTTIMlmRpw9Sw4OJM78CIarRJ3ysRFdFVJDtIcmyfyypN8lAkNA4+ZZt5QVLaty7BFleng3YmPs7QA19WwLWM7x7ZVy/N44Anjf89OBk/zIhcVS+38bN9FNvJvVwsfFKmPLnmqYYJYHvG0DSVOVATME3BwWU9ulJXYyLAJ4jt1tO9CzGr6Z0oaKkZh4zeW3AyCQiq7VB4oxnYV2hBsrDeTPDcekfPDaAbb1JfYtZJZZse4LOBtk7/Pi8t3shVQkFwvwF0lU9znoN5E34adFU2CG3jCfnTIy1+6Rg6vlvWpd/StvpBMn/HnZ5SBNHD6BDDmWVHLIA16xaAOrJnTKMrpIDRRHq0g6cG6W+q15RS8RbXv8h1spR6crJOLP2u03Cv6lbJMMpQLvjremRKcN7cfNoO7ot8X0LUMOssKxjGpaIt6qIx/6DbQJ+b3Wx5j+DH1DUo/Z+pcLyb+lBFGkVr44AS3vb8c5eE4qP/lgzSS+nkfIQ4/x/vDkWc3jjnMseCpN7BQLRL26eOm3ApvFbHoQJpC5KC4eQnYzrWjQqgQilFIldR5xtkLfArzOaD+8V18lNWGlKWwHAeedHO7iaRebJJm0R2wqRMMfnfc6cZBqjE20Vp2R9D67GnZactxlbyA3No=");
    }


    @Override
    public void setup() {
        super.setup();

        arbiTracker();

    }

    public void getSites(){
        GetData getSites = new GetData();

        try {
            String sites = getSites.execute("https://server-ull-navigation.herokuapp.com/api/ull-sites").get();
            JSONArray array = new JSONArray(sites);
            navULL = new Navigation(array);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        double radians = event.values[0];
        if (radians >= 360)
            radians = radians - 360;
        radians = Math.toRadians(radians);

        ArrayList<ULLSite> allResults = null;
        ULLSite nearResult = null;
        if (getCurrentPos() != null) {
            allResults = navULL.whatCanSee(getCurrentPos(), radians);
        }
        if (allResults != null) {
            nearResult = allResults.get(0);
            String AuxCanFound = "";
            for(int i = 0; i < nearResult.getCanFound().size(); i++){
                AuxCanFound += nearResult.getCanFound().get(i).first + "\n";
            }

            int brujula = Math.round(event.values[0]);
            int objetivoDir = Math.round(Math.round(Math.toDegrees(nearResult.getDirToSite())));
            int distanceDir = Math.round((float)nearResult.getDistToSite());
            int coneValue = Math.round((float)Math.toDegrees(nearResult.getConeValue()));

            topRightText.setText("Lugar: " + nearResult.getId() + "\nBrújula: " + brujula + "º"
                    + "\nDireccion objetivo: " + objetivoDir + "º" + "\nDistancia: " + distanceDir + "m."
                    + "\nValor del cono: " + coneValue + "º" + "\nAquí se encuentra:\n" + AuxCanFound );
            if(allResults.size() > 2) {

                moreSitesButton.setText("Encontradas " + (allResults.size() - 2) + " ubicacion(es) más en esta dirección");
                moreSitesButton.setVisibility(View.VISIBLE);
            }
        } else {
            topRightText.setText("Apunte a algun centro universitario para mostrar su informacion");
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


    @SuppressLint("MissingPermission")
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

    private void enableGPSAlert() {
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


    public void markerTracker() {

//         Initialise the image trackable and load the image
        ARImageTrackable imageTrackable = new ARImageTrackable("Lego Marker");
        imageTrackable.loadFromAsset("Kudan Lego Marker.jpg");

        // Get the single instance of the image tracker.
        ARImageTracker imageTracker = ARImageTracker.getInstance();
        imageTracker.initialise();
        //Add the image trackable to the image tracker.
        imageTracker.addTrackable(imageTrackable);// AR Content to be set up here

        // Initialise the image node with our image
        ARImageNode imageNode = new ARImageNode("ull_logo.jpeg");

        // Add the image node as a child of the trackable's world
        imageTrackable.getWorld().addChild(imageNode);

        imageNode.setName("Cow");
        imageTrackable.setName("Lego Marker");

    }

    public void arbiTracker() {

        ARArbiTrack arbiTrack = ARArbiTrack.getInstance();
        arbiTrack.initialise();

        // Initialise gyro placement.
        ARGyroPlaceManager gyroPlaceManager = ARGyroPlaceManager.getInstance();
        gyroPlaceManager.initialise();


        // Create a node to be used as the target.
        ARImageNode targetNode = new ARImageNode("cow_target.png");

        // Add it to the Gyro Placement Manager's world so that it moves with the device's Gyroscope.
        gyroPlaceManager.getWorld().addChild(targetNode);

        // Rotate and scale the node to ensure it is displayed correctly.
        targetNode.rotateByDegrees(90.0f, 1.0f, 0.0f, 0.0f);
        targetNode.rotateByDegrees(180.0f, 0.0f, 1.0f, 0.0f);

        targetNode.scaleByUniform(0.3f);

        // Set the ArbiTracker's target node.
        arbiTrack.setTargetNode(targetNode);

        // Create a node to be tracked.
        ARImageNode trackingNode = new ARImageNode("cow_tracking.png");

        // Rotate the node to ensure it is displayed correctly.
        trackingNode.rotateByDegrees(90.0f, 1.0f, 0.0f, 0.0f);
        trackingNode.rotateByDegrees(180.0f, 0.0f, 1.0f, 0.0f);

        // Add the node as a child of the ArbiTracker's world.
        arbiTrack.getWorld().addChild(trackingNode);

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



    @Override
    public void onLocationChanged(Location location) {

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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gestureDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        ARArbiTrack arbiTrack = ARArbiTrack.getInstance();
        Toast.makeText(this, "Error al autentificar con Google", Toast.LENGTH_LONG).show();

        // If arbitrack is tracking, stop tracking so that its world is no longer rendered, and make the target node visible.
        if (arbiTrack.getIsTracking())
        {
            arbiTrack.stop();
            arbiTrack.getTargetNode().setVisible(true);
        }

        // If it's not tracking, start tracking and hide the target node.
        else
        {
            arbiTrack.start();
            arbiTrack.getTargetNode().setVisible(false);
        }

        return false;
    }

    // We also need to implement the other overrides of the GestureDetector, though we don't need them for this sample.
    @Override
    public boolean onDown(MotionEvent e)
    {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        return false;
    }


}