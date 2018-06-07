package com.example.alehp.ar_maps.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.alehp.ar_maps.Fragments.MapsFragment;
import com.example.alehp.ar_maps.R;


public class MainActivity extends AppCompatActivity {

    android.support.v4.app.Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        setFragment( new MapsFragment());

    };

    public void setFragment(android.support.v4.app.Fragment current){
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_main, current).commit();
    }
}
