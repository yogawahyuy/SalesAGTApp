package com.example.salesagt.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salesagt.DashboardActivity;
import com.example.salesagt.Model.DoneModel;
import com.example.salesagt.Model.MyProgressModel;
import com.example.salesagt.Model.ProgressModel;
import com.example.salesagt.R;
import com.example.salesagt.View.AddProgressActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailProgresActivity extends AppCompatActivity {
    TextView textCompany,textStatus,textDate,textIncome,textSales;
    ImageView imageView;
    Button btnAction;
    MyProgressModel myProgressModel;
    ProgressModel progressModel;
    DoneModel doneModel;
    DatabaseReference dbf;
    FirebaseUser firebaseUser;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_progres);
        textCompany=findViewById(R.id.detail_txtcompany);
        textStatus=findViewById(R.id.detail_txtnego);
        textDate=findViewById(R.id.detail_txttgl);
        textIncome=findViewById(R.id.detail_txtincom);
        textSales=findViewById(R.id.detail_txtsales);
        imageView=findViewById(R.id.detail_imgView);
        btnAction=findViewById(R.id.btn_action);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        Log.d("idnya", "onCreate: "+id);
        if (getIntent().getIntExtra("myprog",0)==2){
            getDataMyProgress();
        }else if (getIntent().getIntExtra("prog",0)==1){
            getDataProgress();
        }else if (getIntent().getIntExtra("doneprog",0)==3){
            getDataDoneProgress();
        }
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelAction();
            }
        });

    }
    private void getDataMyProgress(){
        myProgressModel = (MyProgressModel)getIntent().getSerializableExtra("id");
        textCompany.setText(myProgressModel.getCompanyName());
        textStatus.setText(myProgressModel.getCheckStatus());
        textDate.setText(myProgressModel.getDate());
        textIncome.setText(myProgressModel.getIncome());
        textSales.setText(myProgressModel.getSalesName());

    }
    private void getDataProgress(){
        progressModel=(ProgressModel)getIntent().getSerializableExtra("id");
        textCompany.setText(progressModel.getCompanyName());
        textStatus.setText(progressModel.getCheckStatus());
        textDate.setText(progressModel.getDate());
        textIncome.setText(progressModel.getIncome());
        textSales.setText(progressModel.getSalesName());
    }
    private void getDataDoneProgress(){
        doneModel=(DoneModel)getIntent().getSerializableExtra("id");
        imageView.setImageResource(R.drawable.icons8tickbox160);
        textCompany.setText(doneModel.getCompanyName());
        textStatus.setText(doneModel.getCheckStatus());
        textDate.setText(doneModel.getDate());
        textIncome.setText(doneModel.getIncome());
        textSales.setText(doneModel.getSalesName());
    }

    private void modelAction(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Edit Or Delete");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProgres();
            }
        });
        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getIntent().getIntExtra("prog",0)==1){
                    if (progressModel.getUidSales().equalsIgnoreCase(firebaseUser.getUid())) {
                        editProgress();
                    }else{
                        Toast.makeText(DetailProgresActivity.this, "You don't have Perrmision to edit this progress", Toast.LENGTH_SHORT).show();
                    }
                }else if (getIntent().getIntExtra("myprog",0)==2){
                    if (myProgressModel.getUidSales().equalsIgnoreCase(firebaseUser.getUid())) {
                        editProgress();
                    }else
                        Toast.makeText(DetailProgresActivity.this, "You don't have Perrmision to edit this progress", Toast.LENGTH_SHORT).show();

                }else if (getIntent().getIntExtra("doneprog",0)==3){
                    if (doneModel.getUidSales().equalsIgnoreCase(firebaseUser.getUid())) {
                        editProgress();
                    }else
                        Toast.makeText(DetailProgresActivity.this, "You don't have Perrmision to edit this progress", Toast.LENGTH_SHORT).show();

                }
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private void editProgress(){
        Intent intent=new Intent(DetailProgresActivity.this,AddProgressActivity.class);
        intent.putExtra("data",getIntent().getSerializableExtra("id"));
        if (getIntent().getIntExtra("prog",0)==1){
            intent.putExtra("prog",1);
        }else if (getIntent().getIntExtra("myprog",0)==2){
            intent.putExtra("myprog",2);
        }else if (getIntent().getIntExtra("doneprog",0)==3){
            intent.putExtra("doneprog",3);
        }
        startActivity(intent);
    }

    private void deleteProgres(){
        if (getIntent().getIntExtra("prog",0)==1){
            if (progressModel.getUidSales().equalsIgnoreCase(firebaseUser.getUid())) {
                dbf = FirebaseDatabase.getInstance().getReference("progress");
                if (dbf != null) {
                    dbf.child("allprogress").child(progressModel.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DetailProgresActivity.this, "Progress Delete Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DetailProgresActivity.this, DashboardActivity.class));
                            finish();
                        }
                    });
                }
            }else {
                Toast.makeText(this, "You don't have permission to delete this progress", Toast.LENGTH_SHORT).show();
            }
        }else if (getIntent().getIntExtra("myprog",0)==2) {
            if (myProgressModel.getUidSales().equalsIgnoreCase(firebaseUser.getUid())) {
                dbf = FirebaseDatabase.getInstance().getReference("progress");
                if (dbf != null) {
                    dbf.child("allprogress").child(progressModel.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DetailProgresActivity.this, "Progress Delete Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DetailProgresActivity.this, DashboardActivity.class));
                            finish();
                        }
                    });

                }

            }else{
                Toast.makeText(this, "You don't have permission to delete this progress", Toast.LENGTH_SHORT).show();
            }
        }else if (getIntent().getIntExtra("doneprog",0)==3){
            if (doneModel.getUidSales().equalsIgnoreCase(firebaseUser.getUid())) {
                dbf = FirebaseDatabase.getInstance().getReference("doneprogress");
                if (dbf != null) {
                    dbf.child("alldoneprogres").child(doneModel.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DetailProgresActivity.this, "Done Progress Delete Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DetailProgresActivity.this, DashboardActivity.class));
                            finish();
                        }
                    });
                }
            }else{
                Toast.makeText(this, "You don't have permission to delete this progress", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
