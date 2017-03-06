package com.santiagogil.a100units.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiagogil.a100units.R;

public class FragmentMain extends Fragment {

    private RecyclerView recyclerView;



    public FragmentMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        UnitRecyclerAdapter unitRecyclerAdapter = new UnitRecyclerAdapter(getContext());
        recyclerView.setAdapter(unitRecyclerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 10);
        recyclerView.setLayoutManager(gridLayoutManager);

        return view;
    }

}