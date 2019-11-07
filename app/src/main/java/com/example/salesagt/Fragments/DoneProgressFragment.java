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

import com.example.salesagt.Adapter.DoneAdapter;
import com.example.salesagt.Adapter.ProgressAdapter;
import com.example.salesagt.Model.DoneModel;
import com.example.salesagt.Model.ProgressModel;
import com.example.salesagt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoneProgressFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<DoneModel> doneList;
    private RecyclerView.Adapter adapter;
    private View emptyView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;

    public DoneProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_done_progress, container, false);
        setUpView(view);
        generateView(view);
        return view;
    }

    private void setUpView(View view) {
        recyclerView=view.findViewById(R.id.recyle_done);
        emptyView=view.findViewById(R.id.emptyview_done);
        doneList=new ArrayList<>();
        adapter= new DoneAdapter(getContext(),doneList,emptyView);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
    }
    private void generateView(View view) {
        DoneModel doneModel= new DoneModel();
        for (int i = 0; i <10 ; i++) {
            String id=String.valueOf(i);
            doneModel.setId(id);
            doneModel.setCompanyName("Authentic Guards");
            doneModel.setSalesName("Yoga");
            doneModel.setCheckStatus("Done");
            doneModel.setIncome("Rp.100.0000.000");
            doneList.add(doneModel);
        }
        Log.d("done fragment gen", "generateView: "+doneModel.getCompanyName());
        if (doneList.size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }

}
