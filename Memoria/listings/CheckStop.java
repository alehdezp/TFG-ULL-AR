package com.bulletpoint.ull.bulletpoint.busclasses;

import android.app.Fragment;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.bulletpoint.ull.bulletpoint.R;
import com.bulletpoint.ull.bulletpoint.beaconInterfaces.BeaconBusStop;
import com.bulletpoint.ull.bulletpoint.scanFragment.ScanFragment;
import com.bulletpoint.ull.bulletpoint.adapters.BusCursorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Bullcito27 on 12/06/2016.
 */
public class CheckStop extends Fragment {

    private CursorAdapter busInfoAdapter;
    private MatrixCursor myCursor;
    static final String[] NAMES_OF_COLUMN_DATA = new String[]{"_id", "lineDestination", "minutesUntilBus"};
    private ListView busListView;

    public CheckStop(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup  container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_busses, container, false);

        busListView = (ListView) rootView.findViewById(R.id.busListView);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            checkStop();
        }
        busListView.setAdapter(busInfoAdapter);
        return rootView;

    }

    public void checkStop() {
        myCursor = new MatrixCursor(NAMES_OF_COLUMN_DATA);
        String beaconId = this.getArguments().getString(ScanFragment.BBI);
        String stopId = BeaconBusStop.getStopId(beaconId);
        HttpClientTitsa titsaClient = new HttpClientTitsa();
        try {
            Map<String, String> destinationMap = titsaClient.getCorrectRoute(stopId);
            List<Arrival> llegadas = new ArrayList<Arrival>();
            llegadas = titsaClient.getTimetablesBasedOnLineNumber(stopId);

            for (Arrival llegada : llegadas) {
                if (destinationMap == null) {
                    llegada.setDestination("Destination not found");
                } else {
                    llegada.setDestination(destinationMap.get(llegada.getLineNumber()));
                }
                myCursor.addRow(new Object[]{llegada.getLineNumber(), llegada.getDestination(), llegada.getMinutesForArrival() + " minutos."});
            }

        } catch (Exception e) {
            Log.i("INFO", "Fallo al realizar la peticion: " + e.getMessage());
        }

        String[] fromTheseColumns = new String[]{"_id", "lineDestination", "minutesUntilBus"};
        int[] toTheseViews = new int[]{R.id.busLineNumberBig, R.id.busLineNumber_SmallTV, R.id.busLineNumber_HeaderTV};

        try {
            busInfoAdapter = new BusCursorAdapter(getActivity(), R.layout.fragment_list_busses, myCursor, fromTheseColumns, toTheseViews, 0);
        } catch (Exception e) {
            Log.i("INFO", "Falla el cursor porque" + e.getMessage());
        }
    }

     @Override
    public void onResume(){
         super.onResume();
     }
}

