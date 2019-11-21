package com.example.salesagt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.salesagt.Model.UserModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private SlidingUpPanelLayout slidingUpPanelLayout;
    LinearLayout loginGmail,loginEmail;
    public static final int RC_SIGN=9001;
    public static final String TAG="LoginAct Err";
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    Button register,login;
    String name,email,numberPhone,gender,address,picture;



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if (currentUser!=null){
            if (currentUser.isEmailVerified()){
                startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //inisialisasi view
        register=findViewById(R.id.btn_register);
        login=findViewById(R.id.btnLogin);
        loginGmail=findViewById(R.id.login_gmail);
        loginEmail=findViewById(R.id.login_email);
        slidingUpPanelLayout=findViewById(R.id.slidinglayout);

        //inisialisasi google
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(LoginActivity.this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(LoginActivity.this, "Please Check Your Connectivity", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        firebaseAuth=FirebaseAuth.getInstance();
        //onclicklistener
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
            }
        });
        loginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loginGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loginWithGmail();
            }
        });
    }
    private void loginWithGmail(){
        Intent signIntent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signIntent,RC_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                GoogleSignInAccount account=result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else{
                firebaseAuth.signOut();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential=GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            final FirebaseUser currentUser=firebaseAuth.getCurrentUser();
                            final HashMap<String,Object> saveUser=new HashMap<>();
                            picture=currentUser.getPhotoUrl().toString();
                            name=currentUser.getDisplayName();
                            final DatabaseReference dbf=FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
                            dbf.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                                    }else {
                                        UserModel userModel=dataSnapshot.getValue(UserModel.class);
                                        if (dataSnapshot.child("picture").exists())picture=userModel.getPicture();
                                        if (dataSnapshot.child("name").exists())name=userModel.getName();
                                        if (dataSnapshot.child("numberPhone").exists())numberPhone=userModel.getNumberPhone();
                                        if (dataSnapshot.child("gender").exists())gender=userModel.getGender();
                                        if (dataSnapshot.child("address").exists())address=userModel.getAddress();

                                        saveUser.put("numberPhone",currentUser.getPhoneNumber());
                                        saveUser.put("idEmail",currentUser.getUid());
                                        saveUser.put("idPhone","");
                                        saveUser.put("name",currentUser.getDisplayName());
                                        saveUser.put("email",currentUser.getEmail());
                                        saveUser.put("gender",gender);
                                        saveUser.put("picture",picture);
                                        dbf.setValue(saveUser);
                                        startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }else{
                            Log.d(TAG, "onFailure: ",task.getException());
                        }
                    }
                });
    }
}
