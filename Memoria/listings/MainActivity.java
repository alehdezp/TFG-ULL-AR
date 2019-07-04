package com.alehp.ull_navigation.Activities;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Button;

import com.alehp.ull_navigation.Fragments.HomeFragment;
import com.alehp.ull_navigation.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getExtras().getBoolean("firstStart")) {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.content_frame, new HomeFragment());
            tx.commit();
        }
// 
//        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); // Remember this is the FrameLayout area within your activity_main.xml
//        getLayoutInflater().inflate(R.layout.activity_home, contentFrameLayout);
    }


}
