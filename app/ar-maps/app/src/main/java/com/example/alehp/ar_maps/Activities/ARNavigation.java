package com.example.alehp.ar_maps.Activities;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alehp.ar_maps.Models.Navigation;
import com.example.alehp.ar_maps.R;
import com.google.android.gms.maps.model.LatLng;

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


    private GestureDetectorCompat gestureDetect;


    public static final int CAMERA_REQUEST_CODE = 101;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aractivity);


        gestureDetect = new GestureDetectorCompat(this,this);
        if(checkPermissions()) {
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            Sensor compass = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            mSensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL);

            topRightText = findViewById(R.id.MainText);


            navULL = new Navigation();

        }

        ARAPIKey key = ARAPIKey.getInstance();
        key.setAPIKey("Y8/NRauyJ4RvhIsPcHHd7xhYLwiBZsn3+cqswOaTTIMlmRpw9Sw4OJM78CIarRJ3ysRFdFVJDtIcmyfyypN8lAkNA4+ZZt5QVLaty7BFleng3YmPs7QA19WwLWM7x7ZVy/N44Anjf89OBk/zIhcVS+38bN9FNvJvVwsfFKmPLnmqYYJYHvG0DSVOVATME3BwWU9ulJXYyLAJ4jt1tO9CzGr6Z0oaKkZh4zeW3AyCQiq7VB4oxnYV2hBsrDeTPDcekfPDaAbb1JfYtZJZZse4LOBtk7/Pi8t3shVQkFwvwF0lU9znoN5E34adFU2CG3jCfnTIy1+6Rg6vlvWpd/StvpBMn/HnZ5SBNHD6BDDmWVHLIA16xaAOrJnTKMrpIDRRHq0g6cG6W+q15RS8RbXv8h1spR6crJOLP2u03Cv6lbJMMpQLvjremRKcN7cfNoO7ot8X0LUMOssKxjGpaIt6qIx/6DbQJ+b3Wx5j+DH1DUo/Z+pcLyb+lBFGkVr44AS3vb8c5eE4qP/lgzSS+nkfIQ4/x/vDkWc3jjnMseCpN7BQLRL26eOm3ApvFbHoQJpC5KC4eQnYzrWjQqgQilFIldR5xtkLfArzOaD+8V18lNWGlKWwHAeedHO7iaRebJJm0R2wqRMMfnfc6cZBqjE20Vp2R9D67GnZactxlbyA3No=");
    }

    public void markerTracker(){

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

    public void arbiTracker(){

        ARArbiTrack arbiTrack = ARArbiTrack.getInstance();
        arbiTrack.initialise();

        // Initialise gyro placement.
        ARGyroPlaceManager gyroPlaceManager = ARGyroPlaceManager.getInstance();
        gyroPlaceManager.initialise();


        // Create a node to be used as the target.
        ARImageNode targetNode = new ARImageNode("Cow Target.png");

        // Add it to the Gyro Placement Manager's world so that it moves with the device's Gyroscope.
        gyroPlaceManager.getWorld().addChild(targetNode);

        // Rotate and scale the node to ensure it is displayed correctly.
        targetNode.rotateByDegrees(90.0f, 1.0f, 0.0f, 0.0f);
        targetNode.rotateByDegrees(180.0f, 0.0f, 1.0f, 0.0f);

        targetNode.scaleByUniform(0.3f);

        // Set the ArbiTracker's target node.
        arbiTrack.setTargetNode(targetNode);

        // Create a node to be tracked.
        ARImageNode trackingNode = new ARImageNode("Cow Tracking.png");

        // Rotate the node to ensure it is displayed correctly.
        trackingNode.rotateByDegrees(90.0f, 1.0f, 0.0f, 0.0f);
        trackingNode.rotateByDegrees(180.0f, 0.0f, 1.0f, 0.0f);

        // Add the node as a child of the ArbiTracker's world.
        arbiTrack.getWorld().addChild(trackingNode);

    }

    @Override
    public void setup()
    {
        super.setup();

        arbiTracker();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        double radians = event.values[0];
        if(radians >= 180)
            radians = radians - 360;
        radians = Math.toRadians(radians);


        boolean result = navULL.canSee(getCurrentPos(), radians);

        topRightText.setText("Brujula: "+event.values[0]+ "\nRadianes: " + radians + "\nEn rango: "
                + result + "\nDireccion objetivo: " + navULL.getDestDir() );
    }

    @SuppressLint("MissingPermission")
    private LatLng getCurrentPos(){
        if (!isGPSEnabled()) {
            this.enableGPSAlert();
        }
        if (this.checkPermissions() && enableLocation()) {
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




    public boolean isGPSEnabled(){
        try {
            int gpsSignal = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE);
            if(gpsSignal == 0){
                return false;
            }else
                return true;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }



    @SuppressLint("MissingPermission")
    private boolean enableLocation() {

        if(locationEnable == true)
            return true;

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (!checkPermissions()) {
            locationEnable = false;
            return false;

        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 5, this);
            locationEnable = true;
            return  true;
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



    public boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
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
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        ARArbiTrack arbiTrack = ARArbiTrack.getInstance();

        // If arbitrack is tracking, stop tracking so that its world is no longer rendered, and make the target node           visible.
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

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }



    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
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


}