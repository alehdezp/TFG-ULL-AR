package com.alehp.ull_navigation.Activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.alehp.ull_navigation.Models.ULLSiteSerializable;
import com.alehp.ull_navigation.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;


import java.util.ArrayList;


public class SiteDescriptionActivity extends ListActivity {

    ULLSiteSerializable actualULLSite;
    ArrayList<String> listItems=new ArrayList<String>();
    TextView nameText;
    TextView descText;
    ImageView imageSite;
    ImageView imageMaps;
    Toolbar toolbar;
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_description);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setActionBar(toolbar);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);

        actualULLSite = (ULLSiteSerializable) getIntent().getSerializableExtra("actualULLSite");
        listItems = actualULLSite.getInterestPoints();
        BindUI();
        setListSites();
    }


    public void BindUI(){
        nameText = findViewById(R.id.nameTextSiteDesc);
        descText = findViewById(R.id.descTextSiteDesc);
        imageSite = findViewById(R.id.imageSiteDesc);
        imageMaps = findViewById(R.id.sendMapsSiteDesc);
        imageMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr="+ actualULLSite.getPoint().getY() + "," + actualULLSite.getPoint().getX());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        RequestManager requestManager = Glide.with(this);
        RequestBuilder requestBuilder = requestManager.load(actualULLSite.getImageLink());
        requestBuilder.into(imageSite);
        nameText.setText(actualULLSite.getName());
        descText.setText(actualULLSite.getDesc());
    }


    public void setListSites(){
        adapter = new ArrayAdapter<String>(this,
                R.layout.link_item, android.R.id.text1,
                listItems) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setPaintFlags(text1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                return view;
            }
        };
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String url = actualULLSite.getInterestPointsLink().get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        justifyListViewHeightBasedOnChildren(getListView());
    }


    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() * 1.4;
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}
