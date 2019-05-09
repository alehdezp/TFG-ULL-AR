package com.alehp.ull_navigation.Models.Adapters;

import android.content.Context;
import android.text.Layout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.alehp.ull_navigation.Activities.BaseActivity;
import com.alehp.ull_navigation.Models.SitesArray;
import com.alehp.ull_navigation.Models.ULLSite;
import com.alehp.ull_navigation.Models.ULLSiteSerializable;
import com.alehp.ull_navigation.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class SiteAdapter extends BaseAdapter implements Filterable {

    Context context;
    int layout;
    private SitesArray sitesULL;
    private ArrayList<ULLSiteSerializable> allSites;
    private ArrayList<ULLSiteSerializable> filteredSites;

    private ImageView imageSite;
    private TextView nameText;
    private TextView descText;

    public SiteAdapter(Context context, int layout, SitesArray sitesULL){
        this.context= context;
        this.layout = layout;
        this.sitesULL = sitesULL;
        allSites = sitesULL.getUllSiteSerializables();
        filteredSites = allSites;
    }

    @Override
    public int getCount() {
        return filteredSites.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredSites.get(position);
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

        nameText = v.findViewById(R.id.nameTextSiteList);
        descText = v.findViewById(R.id.descTextSiteList);
        imageSite = v.findViewById(R.id.imageSiteList);
        RequestManager requestManager = Glide.with(v.getContext());
        RequestBuilder requestBuilder = requestManager.load(filteredSites.get(position).getImageLink());
        requestBuilder.into(imageSite);

        nameText.setText(filteredSites.get(position).getName());
        descText.setText(filteredSites.get(position).getDesc());

        return v;
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredSites = allSites;
                } else {
                    ArrayList<ULLSiteSerializable> auxFilteredList = new ArrayList<>();
                    for (ULLSiteSerializable site : allSites) {

                        if (site.getName().toLowerCase().contains(charString.toLowerCase())) {
                            auxFilteredList.add(site);
                        }
                    }
                    filteredSites = auxFilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredSites;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredSites = (ArrayList<ULLSiteSerializable>) filterResults.values;
//                filterResults = filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public ArrayList<ULLSiteSerializable> getFilteredSites() {
        return filteredSites;
    }

    public void setFilteredSites(ArrayList<ULLSiteSerializable> filteredSites) {
        this.filteredSites = filteredSites;
    }
}
