package com.alehp.ull_navigation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alehp.ull_navigation.Fragments.HomeFragment;
import com.alehp.ull_navigation.Fragments.MapsFragment;
import com.alehp.ull_navigation.R;

public class MapsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new MapsFragment()).commit();
        drawerLayout.closeDrawers();
        ;
    }
}
