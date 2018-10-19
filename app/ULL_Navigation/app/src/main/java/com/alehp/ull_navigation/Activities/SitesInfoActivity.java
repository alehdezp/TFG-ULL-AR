package com.alehp.ull_navigation.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.alehp.ull_navigation.Models.Navigation;
import com.alehp.ull_navigation.Models.SiteAdapter;
import com.alehp.ull_navigation.Models.SitesArray;
import com.alehp.ull_navigation.Models.ULLSite;
import com.alehp.ull_navigation.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import com.alehp.ull_navigation.R.id;
public class SitesInfoActivity extends AppCompatActivity {

    private SitesArray sitesToShow;
    private SharedPreferences sharedPref;
    private ListView listSites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites_info);
  //      sharedPref = this.getPreferences(this.MODE_PRIVATE);
    //    getSitesFromShared();
        sitesToShow = (SitesArray) getIntent().getSerializableExtra("sitesToShow");
        showDinamicSites();

    }

    private void showDinamicSites(){
        listSites = findViewById(R.id.listSites);

        SiteAdapter siteAdapter = new SiteAdapter(this, R.layout.site_item, sitesToShow);
        listSites.setAdapter(siteAdapter);

    }

    private void getSitesFromShared(){

        String auxStringSites = sharedPref.getString("allSites", "");

        try {
            JSONArray array = new JSONArray(auxStringSites);
            Navigation navULL = new Navigation(array);
            ArrayList<ULLSite> auxSites = navULL.getAllSites();
            for (int i = 0; i < auxSites.size(); i++){

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
