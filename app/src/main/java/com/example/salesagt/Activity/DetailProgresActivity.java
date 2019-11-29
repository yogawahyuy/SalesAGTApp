package com.example.salesagt.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salesagt.Model.MyProgressModel;
import com.example.salesagt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailProgresActivity extends AppCompatActivity {
    TextView textCompany,textStatus,textDate,textIncome,textSales;
    MyProgressModel myProgressModel;
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
        myProgressModel = (MyProgressModel)getIntent().getSerializableExtra("id");
        Log.d("idnya", "onCreate: "+id);
        getDataProgress();
    }
    private void getDataProgress(){

                textCompany.setText(myProgressModel.getCompanyName());
                textStatus.setText(myProgressModel.getCheckStatus());
                textDate.setText(myProgressModel.getDate());
                textIncome.setText(myProgressModel.getIncome());
                textSales.setText(myProgressModel.getSalesName());

    }
}
