package com.example.salesagt.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.salesagt.Adapter.ProgressAdapter;
import com.example.salesagt.Model.ProgressModel;
import com.example.salesagt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<ProgressModel> progressList;
    private RecyclerView.Adapter adapter;
    private View emptyView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;


    public ProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_progress, container, false);
        setUpView(view);
        generateView(view);
        return view;
    }

    private void setUpView(View view){
        recyclerView=view.findViewById(R.id.recyle_progress);
        emptyView=view.findViewById(R.id.emptyview_progress);
        progressList=new ArrayList<>();
        adapter= new ProgressAdapter(getContext(),progressList,emptyView);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
    }
    private void generateView(View view){
        ProgressModel progressModel=new ProgressModel();
        for (int i = 0; i <10 ; i++) {
            String id=String.valueOf(i);
            progressModel.setId(id);
            progressModel.setCompanyName("Authentic Guards");
            progressModel.setSalesName("Yoga");
            progressModel.setCheckStatus("Negotiation phase 2");
            progressModel.setIncome("Rp.100.0000.000");
            progressList.add(progressModel);
        }


        Log.d("Progress fragment gen", "generateView: "+progressModel.getCompanyName());
        if (progressList.size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }

}
