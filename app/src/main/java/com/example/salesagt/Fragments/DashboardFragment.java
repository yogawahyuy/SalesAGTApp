package com.example.salesagt.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.salesagt.Adapter.ViewAdapterDashboard;
import com.example.salesagt.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ViewPager viewPager=view.findViewById(R.id.viewPager_dash);
        viewPager.setAdapter(new ViewAdapterDashboard(getContext(),getChildFragmentManager()));
        TabLayout tabLayout=view.findViewById(R.id.tablayout_dash);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
