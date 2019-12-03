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
import com.example.salesagt.Adapter.DoneAdapter;
import com.example.salesagt.Adapter.MyProgressAdapter;
import com.example.salesagt.Model.DoneModel;
import com.example.salesagt.Model.MyProgressModel;
import com.example.salesagt.Model.ProgressModel;
import com.example.salesagt.R;
import com.example.salesagt.View.AddProgressActivity;
import com.github.clans.fab.FloatingActionMenu;
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
    private RecyclerView recyclerView,doneRecylerView;
    private ArrayList<MyProgressModel> progressList;
    private ArrayList<DoneModel>doneList;
    private RecyclerView.Adapter adapter;
    private View emptyView;
    private LinearLayoutManager linearLayoutManager,donelinearLayoutManager;
    private DividerItemDecoration dividerItemDecoration,donedividerItemDecoration;
    private FloatingActionButton fabMyProgres;
    ProgressDialog progressDialog;
    DatabaseReference dbf;
    FirebaseUser firebaseUser;
    String id;
    com.github.clans.fab.FloatingActionButton fab1,fab2,fab3;

    FloatingActionMenu fam;


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
        generateAnotherView(view);
        return view;
    }



    private void setUpView(View view) {
        recyclerView=view.findViewById(R.id.recyle_myprogress);
        doneRecylerView=view.findViewById(R.id.doneProgres_rec);
        emptyView=view.findViewById(R.id.emptyview_myprogress);
        fam=view.findViewById(R.id.fab_myprogress);
        fab1=view.findViewById(R.id.fabadd_progress);
        fab2=view.findViewById(R.id.progress_fab);
        fab3=view.findViewById(R.id.done_fab);
        progressList=new ArrayList<>();
        doneList=new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        doneRecylerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        donelinearLayoutManager=new LinearLayoutManager(getContext());
        donelinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        donedividerItemDecoration=new DividerItemDecoration(doneRecylerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        doneRecylerView.setLayoutManager(donelinearLayoutManager);
        doneRecylerView.addItemDecoration(donedividerItemDecoration);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AddProgressActivity.class));
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneRecylerView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                doneRecylerView.setVisibility(View.VISIBLE);
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
    private void generateAnotherView(View view) {
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        dbf=FirebaseDatabase.getInstance().getReference("doneprogress");
        dbf.child("alldoneprogres").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DoneModel doneModel;
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    doneModel=ds.getValue(DoneModel.class);
                    doneModel.setId(ds.getKey());
                    if (doneModel.getUidSales().equalsIgnoreCase(firebaseUser.getUid())){
                        doneList.add(doneModel);
                    }
                }
                adapter=new DoneAdapter(getContext(),doneList,emptyView);
                doneRecylerView.setAdapter(adapter);
                updateEmptyDoneView();
                Log.d("isidonelist", "onDataChange: "+doneList.size());
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
    private void updateEmptyDoneView(){
        if (doneList.size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else
            emptyView.setVisibility(View.GONE);

    }
    private void displayLoader(){
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Getting Data...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}
