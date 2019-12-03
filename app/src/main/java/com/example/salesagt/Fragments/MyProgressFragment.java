package com.example.salesagt.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.salesagt.Activity.DetailProgresActivity;
import com.example.salesagt.Adapter.MyProgressAdapter;
import com.example.salesagt.Model.MyProgressModel;
import com.example.salesagt.Model.ProgressModel;
import com.example.salesagt.R;
import com.example.salesagt.View.AddProgressActivity;
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
public class MyProgressFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<MyProgressModel> progressList;
    private RecyclerView.Adapter adapter;
    private View emptyView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private FloatingActionButton fabMyProgres;
    ProgressDialog progressDialog;
    DatabaseReference dbf;
    FirebaseUser firebaseUser;
    String id;


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
        fabMyProgres=view.findViewById(R.id.fab_myprogress);
        progressList=new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);


        fabMyProgres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AddProgressActivity.class));
            }
        });
    }

    private void generateView(final View view) {
        displayLoader();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        dbf=FirebaseDatabase.getInstance().getReference("progress");
        dbf.child("allprogress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                MyProgressModel myProgressModel;
                for (DataSnapshot dataSnap:dataSnapshot.getChildren()){
                  myProgressModel=dataSnap.getValue(MyProgressModel.class);
                    myProgressModel.setId(dataSnap.getKey());
                    Log.d("progres", "onDataChangemy: "+dataSnapshot.getKey().toString());
                    if (myProgressModel.getUidSales().equalsIgnoreCase(firebaseUser.getUid())){
                        progressList.add(myProgressModel);
                    }
                    Log.d("progress", "onDataChangesmy: "+myProgressModel.getId());
                }
                adapter= new MyProgressAdapter(getContext(), progressList, emptyView, new MyProgressAdapter.ClickHandler() {
                    @Override
                    public void onItemClick(int pos) {
                        Intent intent=new Intent(view.getContext(),DetailProgresActivity.class);
                        intent.putExtra("id",progressList.get(pos));
                        intent.putExtra("myprog",2);
                        view.getContext().startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                updateEmptyView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void updateEmptyView(){
        if (progressList.size()==0){
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
