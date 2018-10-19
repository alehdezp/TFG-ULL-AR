package com.alehp.ull_navigation.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alehp.ull_navigation.Activities.ARNavigation;
import com.alehp.ull_navigation.Activities.MapsActivity;
import com.alehp.ull_navigation.Models.GetData;
import com.alehp.ull_navigation.Models.SectionModel;
import com.alehp.ull_navigation.Models.SectionRecyclerViewAdapter;
import com.alehp.ull_navigation.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    protected static final String RECYCLER_VIEW_TYPE = "recycler_view_type";
    private RecyclerView recyclerView;

    private Button startMapsButton;

    private Button startRAButton;
    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.sectioned_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    //populate recycler view
    private void populateRecyclerView() {
        ArrayList<SectionModel> sectionModelArrayList = new ArrayList<>();
        //for loop for sections
        ArrayList<String> itemArrayList = new ArrayList<>();
        //for loop for items
        for (int j = 1; j <= 10; j++) {
            itemArrayList.add("Item " + j);
        }
        sectionModelArrayList.add(new SectionModel("Section " + 1, itemArrayList));
        SectionRecyclerViewAdapter adapter = new SectionRecyclerViewAdapter(getContext(), sectionModelArrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        startMapsButton = view.findViewById(R.id.home_button_maps);
//        startRAButton = view.findViewById(R.id.home_button_RA);
        this.view = view;
        setUpRecyclerView();
        populateRecyclerView();

//        startMapsButton.setOnClickListener(this);
//        startRAButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.home_button_maps:
//                getFragmentManager().beginTransaction().add(R.id.content_frame, new MapsFragment()).addToBackStack("fragBack").commit();
//                break;
//            case R.id.home_button_RA:
//                startActivity(new Intent(getContext(), ARNavigation.class));
//                break;
//        }
    }


}
