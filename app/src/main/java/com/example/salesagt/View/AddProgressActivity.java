package com.example.salesagt.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.salesagt.DashboardActivity;
import com.example.salesagt.Model.ProgressModel;
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
    Button btnTambah;
    Spinner spinnerNego;
    List<String > categori=new ArrayList<>();
    DatabaseReference dbRefrence;
    FirebaseUser firebaseUser;
    String itemSpiner;
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

        categori.add("Negosiasi phase 1");
        categori.add("Negosiasi phase 2");
        categori.add("Negosiasi phase 3");
        categori.add("Done");
        ArrayAdapter<String > categoriAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,categori);
        categoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNego.setAdapter(categoriAdapter);
        setDatePicker();
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });



    }
    private void addData(){
        String namaPerusahaan=editNamaPerusahaan.getText().toString();
        String tanggalNego=editTanggalNego.getText().toString();
        String pendapatan=editPendapatan.getText().toString();
        if (namaPerusahaan.length()==0||tanggalNego.length()==0||pendapatan.length()==0){
            Toast.makeText(this, "Please fill all field", Toast.LENGTH_SHORT).show();
        }else{
            dbRefrence=FirebaseDatabase.getInstance().getReference("progress");
            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
            dbRefrence.child(firebaseUser.getUid()).push().setValue(new ProgressModel(namaPerusahaan,firebaseUser.getDisplayName(),spinnerNego.getSelectedItem().toString(),editPendapatan.getText().toString(),editTanggalNego.getText().toString()))
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(AddProgressActivity.this,DashboardActivity.class));
                            Toast.makeText(AddProgressActivity.this, "Progress Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
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
