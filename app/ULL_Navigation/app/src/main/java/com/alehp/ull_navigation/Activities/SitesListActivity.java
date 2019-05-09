package com.alehp.ull_navigation.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.alehp.ull_navigation.Models.Navigation;
import com.alehp.ull_navigation.Models.Adapters.SiteAdapter;
import com.alehp.ull_navigation.Models.SitesArray;
import com.alehp.ull_navigation.Models.ULLSite;
import com.alehp.ull_navigation.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SitesListActivity extends AppCompatActivity {

    private SitesArray sitesToShow;

    private ListView listSites;
    private SearchView searchView;
    private Toolbar toolbar;
    SiteAdapter siteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sitesToShow = (SitesArray) getIntent().getSerializableExtra("sitesToShow");
        showDinamicSites();

    }

    private void showDinamicSites(){
        listSites = findViewById(R.id.listSites);
        siteAdapter = new SiteAdapter(this, R.layout.site_item, sitesToShow);
        listSites.setAdapter(siteAdapter);
        listSites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SiteDescriptionActivity.class);
                intent.putExtra("actualULLSite", siteAdapter.getFilteredSites().get(position));
                startActivity(intent);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar,menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                siteAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                siteAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
