package com.bulletpoint.ull.bulletpoint.scanFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bulletpoint.ull.bulletpoint.R;
import com.bulletpoint.ull.bulletpoint.attendanceclasses.AttendanceFragment;
import com.bulletpoint.ull.bulletpoint.beaconInterfaces.BeaconAttInfo;
import com.bulletpoint.ull.bulletpoint.beaconInterfaces.BeaconGuideLocator;
import com.bulletpoint.ull.bulletpoint.beaconInterfaces.BeaconParkingLocator;
import com.bulletpoint.ull.bulletpoint.busclasses.CheckStop;
import com.bulletpoint.ull.bulletpoint.eventclasses.CheckRssResults;
import com.bulletpoint.ull.bulletpoint.guideclasses.GuideFragment;
import com.bulletpoint.ull.bulletpoint.locatorclasses.Area;
import com.bulletpoint.ull.bulletpoint.locatorclasses.NonLinearLeastSquaresSolver;
import com.bulletpoint.ull.bulletpoint.locatorclasses.Point;
import com.bulletpoint.ull.bulletpoint.locatorclasses.TrilaterationFunction;
import com.bulletpoint.ull.bulletpoint.variables.staticVars;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.geometry.spherical.twod.Circle;

import java.util.Collection;
import java.util.List;

import pl.droidsonroids.gif.GifTextView;

/**
 * Created by Bullcito27 on 12/06/2016.
 */
public class ScanFragment extends Fragment implements BeaconConsumer {

    protected static final String info = "MonitoringActivity";
    private BeaconManager beaconManager;
    public final static String BBI = "InfoBeacon";

    protected int functionality;
    protected Area area;

    private static final Region myregion = new Region("All", null,null,null);

    public ScanFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);
        this.functionality= this.getArguments().getInt("functionality");
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beaconManager = BeaconManager.getInstanceForApplication(getActivity());
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.setBackgroundScanPeriod(1000l);
        beaconManager.setBackgroundBetweenScanPeriod(2000l);
        beaconManager.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        beaconManager.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        startScanning();
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                try {
                    Log.i("INFO", "Changing to ranging");
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    Log.i("INFO", "Changing to ranging failed");
                }
            }

            @Override
            public void didExitRegion(Region region) {
                try {
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {

                }
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {

            }
        });
        beaconManager.setRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                String message = "Comprobando regiones";
                if (region.getUniqueId().equals("All")) {
                    if (beacons != null && beacons.iterator().hasNext()) {
                        if((functionality==1)) {
                            try {
                                beaconManager.stopMonitoringBeaconsInRegion(region);
                                displayView(functionality, beacons);
                            } catch (Exception e) {
                            }
                        }
                        else if(functionality==2){
                            try {
                                beaconManager.stopMonitoringBeaconsInRegion(region);
                                displayView(functionality,beacons);
                            } catch (Exception e) {
                            }
                        }
                        else if(functionality==4){
                            if (beacons.size() >= 3) {
                                int count = 0;
                                String[] ids = new String[beacons.size()];
                                double[] dist = new double[beacons.size()];
                                for (Beacon selBeacon : beacons) {
                                    ids[count] = selBeacon.getBluetoothAddress();
                                    dist[count] = selBeacon.getDistance();
                                    count++;
                                }
                                try {
                                    checkPerimeter(beacons,ids, dist, BeaconGuideLocator.getAreas(),staticVars.SCALEMATH, BeaconGuideLocator.getImage());
                                } catch (Exception e) {
                                }
                            }
                        }
                        else if(functionality==5){
                            if (beacons.size() >= 3) {
                                int count = 0;
                                String[] ids = new String[beacons.size()];
                                double[] dist = new double[beacons.size()];
                                for (Beacon selBeacon : beacons) {
                                    ids[count] = selBeacon.getBluetoothAddress();
                                    dist[count] = selBeacon.getDistance();
                                    count++;
                                }
                                try {
                                    checkPerimeter(beacons,ids, dist, BeaconAttInfo.getAreas(), staticVars.SCALEETSII, BeaconAttInfo.getImage());
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
            }
        });
        beaconManager.setBackgroundScanPeriod(1100l);
    }

    @Override
    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        getActivity().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return getActivity().bindService(intent,serviceConnection,i);
    }

    public void startScanning(){
        try {
            beaconManager.startRangingBeaconsInRegion(myregion);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopScan(){
        if(beaconManager.isBound(this)){
            try {
                beaconManager.stopMonitoringBeaconsInRegion(myregion);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public String getNearestBeaconId(Collection<Beacon> beacons){
        Double distance=9999.9;
        String beaconId="";
        for (Beacon beacon: beacons) {
            if(beacon.getDistance()<distance){
                distance= beacon.getDistance();
                beaconId=beacon.getBluetoothAddress();
            }
        }
        return beaconId;
    }

    private void displayView(int position, Collection<Beacon> beacons) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        Bundle bnd;
        switch (position) {
            case 0:
                //fragment = new NoBeaconsFound();
                break;
            case 1:
                fragment = new CheckStop();
                bnd = new Bundle();
                bnd.putString(BBI, getNearestBeaconId(beacons));
                fragment.setArguments(bnd);
                break;
            case 2:
                fragment = new CheckRssResults();
                bnd = new Bundle();
                bnd.putString(BBI, getNearestBeaconId(beacons));
                fragment.setArguments(bnd);
                break;
            case 4:
                fragment = new GuideFragment();
                bnd = new Bundle();
                bnd.putString(BBI, area.getLocationName());
                fragment.setArguments(bnd);
                break;
            case 5:
                fragment = new AttendanceFragment();
                bnd = new Bundle();
                bnd.putString(BBI, area.getLocationName());
                fragment.setArguments(bnd);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        } else {
            // error in creating fragment
            Log.e("FindFragment", "Error in creating fragment");
        }
    }

    public void checkPerimeter(Collection<Beacon> beacons, String[]ids, double[] distances, List<Area> areas, double scale, int resource){
        double[][] positions = new double[3][3];
        try {
            for (int i =0; i< ids.length;i++ ) {
                Point myPoint = BeaconAttInfo.getPositionInfo(ids[i]);
                positions[i][0]= myPoint.getXCoordinate();
                positions[i][1]= myPoint.getYCoordinate();
                distances[i] = distances[i]* scale;
            }
            Point pos= calculatePosition(positions, distances);
            for (Area area: areas) {
                if(area.contains(pos) && functionality==5){
                    this.area = area;
                    displayView(functionality,beacons);
                }
                if(area.contains(pos) && functionality==4){
                    this.area = area;
                    showInfo(area);
                }
                drawPointInPosition(pos.getXCoordinate(), pos.getXCoordinate(), 8 , areas, resource);
            }
        }catch(Exception e){
            Log.i("INFO", "Exception: " + e.getMessage());
        }
    }

    public Point calculatePosition(double[][] positions, double[] distances ) {
        //double[][] positions = new double[][] { { 863.0, 354.0 }, { 740.0, 338.0 }, { 756.0, 384.0 } };
        //double[] distances = new double[] { 42.5, 85.0, 85.0 };

        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, distances);
        NonLinearLeastSquaresSolver nlSolver = new NonLinearLeastSquaresSolver(trilaterationFunction, new org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer());

        LeastSquaresOptimizer.Optimum optimum = nlSolver.solve();
        // the center
        double[] centroid = optimum.getPoint().toArray();
        return new Point(centroid[0],centroid[1]);
    }

    public void showInfo(final Area area){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView location = (TextView) getActivity().findViewById(R.id.statusLocation);
                //location.setText(getResources().getIdentifier(area.getLocationName(), "string", getActivity().getPackageName()));
                location.setText("Hola");
                Log.i("INFO","Hola");
            }
        });
    }

    public void drawPointInPosition(final double posX, final double posY, final double radius, final List<Area> areas, final int imageResource) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BitmapFactory myFactory= new BitmapFactory();
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inScaled = false;
                opt.inMutable = true;

                Bitmap bitmap = myFactory.decodeResource(getResources(), imageResource,opt);

                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLUE);

                Paint paintZone = new Paint();
                paintZone.setAntiAlias(true);
                paintZone.setStyle(Paint.Style.STROKE);
                paintZone.setColor(Color.GREEN);

                Canvas canvas = new Canvas(bitmap);
                for (Area area: areas) {
                    canvas.drawRect(new Rect(area.getLeft(),area.getTop(),area.getRight(),area.getBottom()), paintZone);
                }
                canvas.drawCircle((float) posX, (float) posY, (float) radius, paint);

                GifTextView loadingIcon = (GifTextView) getActivity().findViewById(R.id.view);
                loadingIcon.setVisibility(View.GONE);
                TextView location = (TextView) getActivity().findViewById(R.id.statusLocation);
                location.setText(R.string.infoArea);
                ImageView imageView = (ImageView) getActivity().findViewById(R.id.location);
                imageView.setAdjustViewBounds(true);
                imageView.setImageBitmap(bitmap);

                String messageString = "X:" + posX + " Y: " + posY;
                Log.i("INFO", messageString);
            }
        });
    }
}
