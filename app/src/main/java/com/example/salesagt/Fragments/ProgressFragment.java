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

import com.example.salesagt.Adapter.ProgressAdapter;
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
public class ProgressFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<ProgressModel> progressList;
    private RecyclerView.Adapter adapter;
    private View emptyView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    ProgressDialog progressDialog;
    DatabaseReference dbf;
    FirebaseUser firebaseUser;

    public ProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_progress, container, false);
        setUpView(view);
        generateView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setUpView(View view){
        recyclerView=view.findViewById(R.id.recyle_progress);
        emptyView=view.findViewById(R.id.emptyview_progress);
        progressList=new ArrayList<>();

        recyclerView.setHasFixedSize(true);

        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);


        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        dbf=FirebaseDatabase.getInstance().getReference().child("progress");
    }
    private void generateView(){
        displayLoader();
        dbf.child("allprogress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnap:dataSnapshot.getChildren()){
                    ProgressModel progressModel=datasnap.getValue(ProgressModel.class);
                    progressModel.setId(datasnap.getKey());
                    progressList.add(progressModel);
                    Log.d("progres", "onDataChange: "+datasnap.getKey().toString());

                }
                progressDialog.dismiss();
                adapter= new ProgressAdapter(getContext(),progressList,emptyView);
                recyclerView.setAdapter(adapter);
                updateEmptyView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
    private void displayLoader(){
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Getting Data...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void updateEmptyView(){
        if (progressList.size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }

}
