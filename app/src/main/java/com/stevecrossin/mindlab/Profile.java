package com.stevecrossin.mindlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    String[] profileQuesList = {"Occupation", "Date of Birth", "Address", "Institution", "Contact Number"};
    String[] profileInfoList = {"Nurse", "18/08/1992", "220 Burwood Highway", "Aged Care", "0400000000"};

    RecyclerView profileRecyclerView;
    ProfileAdaptor profileAdaptor;
    List<ProfileItem> profileList = new ArrayList<>();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

   /* TextView profileName;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootRefer = firebaseDatabase.getReference();
    private DatabaseReference mChildRefer = mRootRefer.child("name");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //profileName = findViewById(R.id.profileName);

        profileRecyclerView = findViewById(R.id.profileRecyclerView);
        // Create adapter passing in newsList
        profileAdaptor = new ProfileAdaptor(profileList,Profile.this);
        // Attach the adapter to the recyclerview to populate items
        profileRecyclerView.setAdapter(profileAdaptor);
        // Set layout manager to position the items
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Profile.this, LinearLayoutManager.VERTICAL, false);
        profileRecyclerView.setLayoutManager(layoutManager);

        //Fill profileList
        for (int i= 0; i< profileQuesList.length; i++ ) {
            String profileQues = profileQuesList[i];
            String profileInfo = profileInfoList[i];
            ProfileItem profileItem = new ProfileItem (i, profileQues, profileInfo);
            profileList.add(profileItem);
        }

    }

    public void goToEditProfile(View view){
        Intent intent = new Intent(this, EditProfileScreen.class);
        startActivity(intent);
    }

    public void goBackToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

/*
    @Override
    protected void onStart() {
        super.onStart();
        mChildRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                profileName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}
