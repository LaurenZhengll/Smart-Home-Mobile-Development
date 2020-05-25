package com.stevecrossin.mindlab;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileForAged extends AppCompatActivity {
    String[] profileQuesList = {"Date of Birth", "Address", "Contact Number","Medical Condition","Allergy","Emergency Contact","Emergency Number"};
    String[] profileInfoList = {"04/28/1946", "345 Burwood Highway", "0401001001", "Hypertension", "Penicillin","Adam Smith","0451000001"};

    RecyclerView profileRecyclerView;
    ProfileAdaptor profileAdaptor;
    List<ProfileItem> profileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileRecyclerView = findViewById(R.id.profileRecyclerView);
        // Create adapter passing in newsList
        profileAdaptor = new ProfileAdaptor(profileList,ProfileForAged.this);
        // Attach the adapter to the recyclerview to populate items
        profileRecyclerView.setAdapter(profileAdaptor);
        // Set layout manager to position the items
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProfileForAged.this, LinearLayoutManager.VERTICAL, false);
        profileRecyclerView.setLayoutManager(layoutManager);

        //Fill profileList
        for (int i= 0; i< profileQuesList.length; i++ ) {
            String profileQues = profileQuesList[i];
            String profileInfo = profileInfoList[i];
            ProfileItem profileItem = new ProfileItem (i, profileQues, profileInfo);
            profileList.add(profileItem);
        }

    }

}
