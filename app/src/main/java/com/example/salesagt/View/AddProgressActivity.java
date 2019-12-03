package com.example.salesagt.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salesagt.Activity.DetailProgresActivity;
import com.example.salesagt.DashboardActivity;
import com.example.salesagt.Model.DoneModel;
import com.example.salesagt.Model.MyProgressModel;
import com.example.salesagt.Model.ProgressModel;
import com.example.salesagt.Model.UserModel;
import com.example.salesagt.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddProgressActivity extends AppCompatActivity {
    EditText editNamaPerusahaan,editTanggalNego,editPendapatan;
    TextView titleProgres;
    Button btnTambah;
    Spinner spinnerNego;
    List<String > categori=new ArrayList<>();
    DatabaseReference dbRefrence;
    FirebaseUser firebaseUser;
    String itemSpiner;
    ProgressModel progressModel;
    MyProgressModel myProgressModel;
    DoneModel doneModel;
    final Calendar calendarNego=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_progress);
        editNamaPerusahaan=findViewById(R.id.edittxt_namaperusahan);
        editTanggalNego=findViewById(R.id.edttext_tanggalnego);
        editPendapatan=findViewById(R.id.edttext_pendapatan);
        spinnerNego=findViewById(R.id.spinner_addprogress);
        btnTambah=findViewById(R.id.btn_saveprogres);
        titleProgres=findViewById(R.id.title_progres);

        categori.add("Negosiasi phase 1");
        categori.add("Negosiasi phase 2");
        categori.add("Negosiasi phase 3");
        categori.add("Done");
        ArrayAdapter<String > categoriAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,categori);
        categoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNego.setAdapter(categoriAdapter);
        setDatePicker();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getSerializableExtra("data")==null) addData();
                else updateData();
            }
        });

        if (getIntent().getSerializableExtra("data")!=null){
            getDataFromIntent();
            btnTambah.setText("Edit Progress");
        }else{

        }


    }
    private void getDataFromIntent(){
        titleProgres.setText("Edit Progress");
        if (getIntent().getIntExtra("prog",0)==1){
            progressModel=(ProgressModel)getIntent().getSerializableExtra("data");
            editNamaPerusahaan.setText(progressModel.getCompanyName());
            editTanggalNego.setText(progressModel.getDate());
            editPendapatan.setText(progressModel.getIncome());
            if (progressModel.getCheckStatus().equalsIgnoreCase("Negosiasi phase 1")){
                spinnerNego.setSelection(0);
            }else if (progressModel.getCheckStatus().equalsIgnoreCase("Negosiasi phase 2")){
                spinnerNego.setSelection(1);
            }else if (progressModel.getCheckStatus().equalsIgnoreCase("Negosiasi phase 3")){
                spinnerNego.setSelection(2);
            }else{
                spinnerNego.setSelection(3);
            }
        }else if (getIntent().getIntExtra("myprog",0)==2){
            myProgressModel=(MyProgressModel)getIntent().getSerializableExtra("data");
            editNamaPerusahaan.setText(myProgressModel.getCompanyName());
            editTanggalNego.setText(myProgressModel.getDate());
            editPendapatan.setText(myProgressModel.getIncome());
            if (myProgressModel.getCheckStatus().equalsIgnoreCase("Negosiasi phase 1")){
                spinnerNego.setSelection(0);
            }else if (myProgressModel.getCheckStatus().equalsIgnoreCase("Negosiasi phase 2")){
                spinnerNego.setSelection(1);
            }else if (myProgressModel.getCheckStatus().equalsIgnoreCase("Negosiasi phase 3")){
                spinnerNego.setSelection(2);
            }else{
                spinnerNego.setSelection(3);
            }
        }else if(getIntent().getIntExtra("doneprog",0)==3){
            doneModel=(DoneModel)getIntent().getSerializableExtra("data");
            editNamaPerusahaan.setText(doneModel.getCompanyName());
            editTanggalNego.setText(doneModel.getDate());
            editPendapatan.setText(doneModel.getIncome());
            if (doneModel.getCheckStatus().equalsIgnoreCase("Negosiasi phase 1")){
                spinnerNego.setSelection(0);
            }else if (doneModel.getCheckStatus().equalsIgnoreCase("Negosiasi phase 2")){
                spinnerNego.setSelection(1);
            }else if (doneModel.getCheckStatus().equalsIgnoreCase("Negosiasi phase 3")){
                spinnerNego.setSelection(2);
            }else{
                spinnerNego.setSelection(3);
            }
        }

    }
    private void addData(){
        String namaPerusahaan=editNamaPerusahaan.getText().toString();
        String tanggalNego=editTanggalNego.getText().toString();
        String pendapatan=editPendapatan.getText().toString();
        if (namaPerusahaan.length()==0||tanggalNego.length()==0||pendapatan.length()==0){
            Toast.makeText(this, "Please fill all field", Toast.LENGTH_SHORT).show();
        }else{
            if (spinnerNego.getSelectedItemPosition()!=3) {
                dbRefrence = FirebaseDatabase.getInstance().getReference("progress");

                dbRefrence.child("allprogress").push().setValue(new ProgressModel(namaPerusahaan, firebaseUser.getDisplayName(), spinnerNego.getSelectedItem().toString(), editPendapatan.getText().toString(), editTanggalNego.getText().toString(),firebaseUser.getUid()))
                        .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(AddProgressActivity.this, DashboardActivity.class));
                                Toast.makeText(AddProgressActivity.this, "Progress Added", Toast.LENGTH_SHORT).show();
                                Log.d("addprogres", "onSuccess: success ");
                                finish();
                            }
                        });
            }else{
                dbRefrence=FirebaseDatabase.getInstance().getReference("doneprogress");
                dbRefrence.child("alldoneprogres").push().setValue(new DoneModel(namaPerusahaan,firebaseUser.getDisplayName(),spinnerNego.getSelectedItem().toString(),editPendapatan.getText().toString(),editTanggalNego.getText().toString(),firebaseUser.getUid()))
                        .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(AddProgressActivity.this, DashboardActivity.class));
                                Toast.makeText(AddProgressActivity.this, "Done Progress Added", Toast.LENGTH_SHORT).show();
                                Log.d("adddone", "onSuccess: success ");
                                finish();
                            }
                        });
            }
        }

    }

    private void updateData(){
        if (editNamaPerusahaan.length()==0||editPendapatan.length()==0||editTanggalNego.length()==0){
            Toast.makeText(this, "You must fill all field", Toast.LENGTH_SHORT).show();
        }else{
            if (spinnerNego.getSelectedItemPosition()!=3){
                dbRefrence=FirebaseDatabase.getInstance().getReference("progress");
                if (getIntent().getIntExtra("myprog",0)==2){
                    dbRefrence.child("allprogress").child(myProgressModel.getId()).setValue(new ProgressModel(editNamaPerusahaan.getText().toString(),firebaseUser.getDisplayName(),spinnerNego.getSelectedItem().toString(),editPendapatan.getText().toString(),editTanggalNego.getText().toString(),firebaseUser.getUid()))
                            .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(AddProgressActivity.this,DashboardActivity.class));
                                    Toast.makeText(AddProgressActivity.this, "Edit Progress success", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }if (getIntent().getIntExtra("prog",0)==1){
                    dbRefrence.child("allprogress").child(progressModel.getId()).setValue(new ProgressModel(editNamaPerusahaan.getText().toString(),firebaseUser.getDisplayName(),spinnerNego.getSelectedItem().toString(),editPendapatan.getText().toString(),editTanggalNego.getText().toString(),firebaseUser.getUid()))
                            .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(AddProgressActivity.this,DashboardActivity.class));
                                    Toast.makeText(AddProgressActivity.this, "Edit Progress success", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }


            }else{
                dbRefrence=FirebaseDatabase.getInstance().getReference("doneprogress");
                if (getIntent().getIntExtra("doneprog",0)==3){
                    dbRefrence.child("alldoneprogres").child(doneModel.getId()).setValue(new DoneModel(editNamaPerusahaan.getText().toString(),firebaseUser.getDisplayName(),spinnerNego.getSelectedItem().toString(),editPendapatan.getText().toString(),editTanggalNego.getText().toString(),firebaseUser.getUid()))
                            .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(AddProgressActivity.this,DashboardActivity.class));
                                    Toast.makeText(AddProgressActivity.this, "Edit Progress success", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
                if (getIntent().getIntExtra("myprog",0)==2){
                    dbRefrence.child("alldoneprogres").push().setValue(new DoneModel(editNamaPerusahaan.getText().toString(),firebaseUser.getDisplayName(),spinnerNego.getSelectedItem().toString(),editPendapatan.getText().toString(),editTanggalNego.getText().toString(),firebaseUser.getUid()))
                            .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(AddProgressActivity.this,DashboardActivity.class));
                                    Toast.makeText(AddProgressActivity.this, "Edit Progress success", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                    dbRefrence=FirebaseDatabase.getInstance().getReference("progress");
                    dbRefrence.child("allprogress").child(myProgressModel.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                         //   Toast.makeText(AddProgressActivity.this, "Progress Delete Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddProgressActivity.this, DashboardActivity.class));
                            finish();
                        }
                    });
                }if (getIntent().getIntExtra("prog",0)==1){
                    dbRefrence.child("alldoneprogres").push().setValue(new DoneModel(editNamaPerusahaan.getText().toString(),firebaseUser.getDisplayName(),spinnerNego.getSelectedItem().toString(),editPendapatan.getText().toString(),editTanggalNego.getText().toString(),firebaseUser.getUid()))
                            .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(AddProgressActivity.this,DashboardActivity.class));
                                    Toast.makeText(AddProgressActivity.this, "Edit Progress success", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                    dbRefrence=FirebaseDatabase.getInstance().getReference("progress");
                    dbRefrence.child("allprogress").child(progressModel.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Toast.makeText(AddProgressActivity.this, "Progress Delete Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddProgressActivity.this, DashboardActivity.class));
                            finish();
                        }
                    });
                }
            }
        }
    }

    private void setDatePicker(){

        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarNego.set(Calendar.YEAR,year);
                calendarNego.set(Calendar.MONTH,month);
                calendarNego.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                formatCalendar();
            }
        };
        editTanggalNego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddProgressActivity.this,date,calendarNego.get(Calendar.YEAR),calendarNego.get(Calendar.MONTH),calendarNego.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void formatCalendar(){
        String myFormat="dd MMMM YYYY";
        SimpleDateFormat sdf=new SimpleDateFormat(myFormat,Locale.US);
        editTanggalNego.setText(sdf.format(calendarNego.getTime()));
    }

}
