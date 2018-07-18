package com.alehp.ull_navigation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alehp.ull_navigation.Fragments.AboutFragment;
import com.alehp.ull_navigation.Fragments.HomeFragment;
import com.alehp.ull_navigation.Fragments.MapsFragment;
import com.alehp.ull_navigation.R;

public class BaseActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_draw);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentTransaction tx;
                Intent intent;
                switch (item.getItemId()) {
                    
                    case R.id.menu_home:
                        tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.content_frame, new HomeFragment());
                        tx.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_maps:
                        tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.content_frame, new MapsFragment());
                        tx.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_ar:
                        intent = new Intent(getApplicationContext(), ARNavigation.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_settings:
                        intent = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_about:
                        tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.content_frame, new AboutFragment());
                        tx.commit();
                        drawerLayout.closeDrawers();
                        break;

                }
                return false;
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

}