package com.example.salesagt.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.salesagt.DashboardActivity;
import com.example.salesagt.Model.UserModel;
import com.example.salesagt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginEmailActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edtEmail,edtPassword;
    Button btnLogin;
    TextView forgetPassword;
    FirebaseUser currentUser;
    ProgressDialog progressDialog;
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        mAuth=FirebaseAuth.getInstance();
        edtEmail=findViewById(R.id.log_email);
        edtPassword=findViewById(R.id.log_password);
        forgetPassword=findViewById(R.id.log_forgot);
        btnLogin=findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginEmailActivity.this,ResetPasswordActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser= mAuth.getCurrentUser();
        if (currentUser!=null){
            if (!currentUser.isEmailVerified()){
                Intent intent=getIntent();
                if (intent.getStringExtra("name")!=null){
                    CFAlertDialog.Builder builder=new CFAlertDialog.Builder(this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                            .setTitle("HELLO "+intent.getStringExtra("name"))
                            .setMessage("Please check your email to verify and you can login to your account");
                    builder.show();
                }
            }
        }
    }

    private void checkUser(){
        displayLoader();
        String email=edtEmail.getText().toString(),password=edtPassword.getText().toString();
        Pattern pattern=Pattern.compile(regEx);
        Matcher matcher=pattern.matcher(email);
        if (email.length()==0 || password.length()==0){
            Toast.makeText(this, "You Must Enter Email or Password", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else if (!matcher.find()){
            Toast.makeText(this, "Your Email is Unregistered", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginEmailActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(LoginEmailActivity.this, "Your Email is Unregistered", Toast.LENGTH_SHORT).show();
                    }else{
                        currentUser=mAuth.getCurrentUser();
                        if (Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()){
                            DatabaseReference dbUser= FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
                            dbUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    startActivity(new Intent(LoginEmailActivity.this,DashboardActivity.class));

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }else{
                            Toast.makeText(LoginEmailActivity.this, "Please Verify your Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
    private void displayLoader(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Login..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
