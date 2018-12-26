package com.alehp.ull_navigation.Models.Adapters;

import android.content.Context;
import android.text.Layout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alehp.ull_navigation.Activities.BaseActivity;
import com.alehp.ull_navigation.Models.SitesArray;
import com.alehp.ull_navigation.R;

import java.util.ArrayList;
import java.util.List;

public class SiteAdapter extends BaseAdapter {

    Context context;
    int layout;
    private SitesArray sitesULL;

    public SiteAdapter(Context context, int layout, SitesArray sitesULL){
        this.context= context;
        this.layout = layout;
        this.sitesULL = sitesULL;
    }

    @Override
    public int getCount() {
        return sitesULL.getUllSiteSerializables().size();
    }

    @Override
    public Object getItem(int position) {
        return sitesULL.getUllSiteSerializables().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.site_item, null);

        String name = sitesULL.getUllSiteSerializables().get(position).getName();
        ArrayList<String> interestPoints = sitesULL.getUllSiteSerializables().get(position).getInterestPoints();

        TextView textViewName = v.findViewById(R.id.textName);
        textViewName.setText(name);
//
//        TextView textViewInfo = v.findViewById(R.id.textName);
//        String auxText = "";
//        for (int i = 0; i < interestPoints.size(); i++) {
//            auxText += interestPoints.get(i);
//        }
//        textViewInfo.setText(auxText);

        return v;
    }
}
