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

import com.example.salesagt.Adapter.MyProgressAdapter;
import com.example.salesagt.Model.MyProgressModel;
import com.example.salesagt.Model.ProgressModel;
import com.example.salesagt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProgressFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<MyProgressModel> progressList;
    private RecyclerView.Adapter adapter;
    private View emptyView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;


    public MyProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_progress, container, false);
        setUpView(view);
        generateView(view);
        return view;
    }

    private void setUpView(View view) {
        recyclerView=view.findViewById(R.id.recyle_myprogress);
        emptyView=view.findViewById(R.id.emptyview_myprogress);
        progressList=new ArrayList<>();
        adapter= new MyProgressAdapter(getContext(),progressList,emptyView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
    }

    private void generateView(View view) {
        MyProgressModel myProgressModel=new MyProgressModel();
        for (int i = 0; i <5 ; i++) {
            String id=String.valueOf(i);
            myProgressModel.setId(id);
            myProgressModel.setCompanyName("Autentic guards");
            myProgressModel.setSalesName("yoga");
            myProgressModel.setCheckStatus("Deal");
            myProgressModel.setIncome("Rp.200.000.000");
            myProgressModel.setDate("7 Desember 2017");
            progressList.add(myProgressModel);
        }
        Log.d("my fragment gen", "generateView: "+myProgressModel.getCompanyName());
        if (progressList.size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }

    }

}
