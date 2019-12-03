package com.example.salesagt.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salesagt.LoginActivity;
import com.example.salesagt.Model.UserModel;
import com.example.salesagt.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView namaSales,emailSales,noHp,editProfile;
    ImageView profilePicture;
    Button btnLogout;
    View v;
    FirebaseAuth mAuth;
    private GoogleApiClient mGoogleSignInClient;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth=FirebaseAuth.getInstance();
        namaSales=v.findViewById(R.id.boxid_name);
        emailSales=v.findViewById(R.id.boxid_email);
        noHp=v.findViewById(R.id.boxid_nohp);
        profilePicture=v.findViewById(R.id.boxid_photo);
        btnLogout=v.findViewById(R.id.logoutBtn);

        getDataFirebase();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                if (mGoogleSignInClient!=null){
                    Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {

                        }
                    });
                }
                startActivity(new Intent(getContext(),LoginActivity.class));
                getActivity().finish();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        if (mGoogleSignInClient == null){
            mGoogleSignInClient = new GoogleApiClient.Builder(getContext())
                    .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(getActivity(), "Something Error", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        return v;
    }

    private void getDataFirebase(){
        final FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser!=null){
            DatabaseReference dbf= FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
            dbf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserModel user=dataSnapshot.getValue(UserModel.class);
                    if (user.getName().isEmpty()){
                        namaSales.setText("Nama Sales");
                    }else{
                        namaSales.setText(user.getName());
                    }
                    if (user.getEmail().isEmpty()){
                        emailSales.setText("email Sales");
                    }else{
                        emailSales.setText(user.getEmail());
                    }
                    if (user.getIdPhone().isEmpty()){
                        noHp.setText("No HP Sales");
                    }else{
                        noHp.setText(user.getIdPhone());
                    }
                    Picasso.get().load(user.getPicture()).into(profilePicture);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
