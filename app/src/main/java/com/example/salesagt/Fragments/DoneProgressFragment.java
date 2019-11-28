package com.example.salesagt.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    ProgressDialog progressDialog;
    DatabaseReference dbf;
    FirebaseUser firebaseUser;
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

        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);


        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        dbf=FirebaseDatabase.getInstance().getReference("doneprogress");
    }
    private void generateView(View view) {
        displayLoader();
        dbf.child("alldoneprogres").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap:dataSnapshot.getChildren()){
                    DoneModel doneModel=dataSnap.getValue(DoneModel.class);
                    doneModel.setId(dataSnap.getKey());
                    doneList.add(doneModel);
                }
                progressDialog.dismiss();
                adapter= new DoneAdapter(getContext(),doneList,emptyView);
                recyclerView.setAdapter(adapter);
                updateEmptyView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateEmptyView(){
        if (doneList.size()==0){
        emptyView.setVisibility(View.VISIBLE);
        }else{
        emptyView.setVisibility(View.GONE);
        }
    }
    private void displayLoader(){
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Getting Data...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}
