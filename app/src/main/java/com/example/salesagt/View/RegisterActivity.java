package com.example.salesagt.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salesagt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText fullName,email,noHp,password,conPassword;
    Button btnRegister;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //inisialisasi
        mFirebaseAuth=FirebaseAuth.getInstance();
        fullName=findViewById(R.id.reg_fullname);
        email=findViewById(R.id.reg_email);
        password=findViewById(R.id.reg_password);
        noHp=findViewById(R.id.reg_numberhp);
        conPassword=findViewById(R.id.reg_confirmationPassword);
        btnRegister=findViewById(R.id.btn_registerAkun);

        //onclick
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerLogic();
            }
        });

    }

    private void registerLogic(){
        String stEmail=email.getText().toString().trim(),
        stPassword=password.getText().toString().trim(), stConPassword=conPassword.getText().toString().trim();

        if (TextUtils.isEmpty(stEmail)){
            Toast.makeText(this, "Enter Email Address !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(stPassword)){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (stPassword.length()<6){
            Toast.makeText(this, "Password too Short", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!stPassword.equals(stPassword)){
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
        }else{
            mFirebaseAuth.createUserWithEmailAndPassword(stEmail,stPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Your email is Already registered", Toast.LENGTH_SHORT).show();
                    }else{
                        final FirebaseUser currentUser=mFirebaseAuth.getCurrentUser();
                        final HashMap<String ,Object> user=new HashMap<>();
                        String name=currentUser.getDisplayName();
                        final DatabaseReference dbf=FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
                        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String gambar="https://firebasestorage.googleapis.com/v0/b/salesagt-feb5e.appspot.com/o/User%2Fuser.png?alt=media&token=121c2c87-b3bc-467e-880a-350c12747470";
                                user.put("email",email.getText().toString().trim());
                                user.put("idEmail",currentUser.getUid());
                                user.put("idPhone",noHp.getText().toString().trim());
                                user.put("name",fullName.getText().toString());
                                user.put("picture",gambar);

                                assert currentUser!=null;
                                if (!currentUser.isEmailVerified()){
                                    currentUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            dbf.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(RegisterActivity.this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
                                                    Intent toLogin=new Intent(RegisterActivity.this,LoginEmailActivity.class);
                                                    toLogin.putExtra("name",fullName.getText().toString());
                                                    startActivity(toLogin);
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Error :"+e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(RegisterActivity.this, "Can't send Verification Email", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }
}
