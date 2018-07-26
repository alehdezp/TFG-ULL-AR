package com.alehp.ull_navigation.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alehp.ull_navigation.Activities.ARNavigation;
import com.alehp.ull_navigation.Activities.MapsActivity;
import com.alehp.ull_navigation.Models.GetData;
import com.alehp.ull_navigation.R;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    private Button startMapsButton;

    private Button startRAButton;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        startMapsButton = view.findViewById(R.id.home_button_maps);
        startRAButton = view.findViewById(R.id.home_button_RA);

        startMapsButton.setOnClickListener(this);
        startRAButton.setOnClickListener(this);

//        GetData testGet = new GetData();
//        String test;
//        try {
//            test = testGet.execute("https://server-ull-navigation.herokuapp.com/api/ull-sites").get();
//            TextView text = view.findViewById(R.id.textView);
//            text.setText(test);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_button_maps:
                getFragmentManager().beginTransaction().add(R.id.content_frame, new MapsFragment()).addToBackStack("fragBack").commit();
                break;
            case R.id.home_button_RA:
                startActivity(new Intent(getContext(), ARNavigation.class));
                break;
        }

    }

}
