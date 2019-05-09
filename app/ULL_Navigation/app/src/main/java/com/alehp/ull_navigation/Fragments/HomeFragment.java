package com.alehp.ull_navigation.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alehp.ull_navigation.Activities.ARNavigation;
import com.alehp.ull_navigation.Models.Adapters.ItemHomeAdapter;
import com.alehp.ull_navigation.Models.ItemHome;
import com.alehp.ull_navigation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private List<ItemHome> itemsHome;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter homeAdapter;
    private RecyclerView.LayoutManager homeLayoutManager;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setAllItems();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.view = view;
        recyclerView =getActivity().findViewById(R.id.recyclerView_Home);
        homeLayoutManager = new LinearLayoutManager(getContext());
        homeAdapter = new ItemHomeAdapter(itemsHome, R.layout.adapter_home_item, new ItemHomeAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ItemHome item, int position){
                    switch (position) {
                        case 0:
                            Intent intent = new Intent(getContext().getApplicationContext(), ARNavigation.class);
                            startActivity(intent);
                            break;
                        case 1:
                           getFragmentManager().beginTransaction().add(R.id.content_frame, new MapsFragment()).addToBackStack("fragBack").commit();
                           break;
                        default:
                            String url = item.getLink();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("http://" + url));
                            startActivity(i);
                            break;
                    }

            }
        });

        recyclerView.setLayoutManager(homeLayoutManager);
        recyclerView.setAdapter(homeAdapter);

    }

    private void setAllItems(){
        ArrayList<ItemHome> auxItems = new ArrayList<ItemHome>();
        auxItems.add(new ItemHome("AR Navigation", "home_ar_navigation", false, null));
        auxItems.add(new ItemHome("Mapa ULL", "home_mapa_ull", false, null));
        auxItems.add(new ItemHome("Página oficial de la ULL", "home_ull_site", true, "www.ull.es"));
        auxItems.add(new ItemHome("Donde ULL", "home_donde_ull", true, "donde.ull.es"));

        itemsHome = (List) auxItems;

    }

    @Override
    public void onClick(View v) {

    }


}
