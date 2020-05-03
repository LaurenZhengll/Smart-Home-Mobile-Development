package com.stevecrossin.mindlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MyDevices extends AppCompatActivity {
    Integer[] imageList = { R.drawable.avatar, R.drawable.avatar};
    String[] statusList = {"Operating", "Unavailable"};
    String[] priorityList = {"HIGH", "Medium"};

    RecyclerView propertyRecyclerView;
    PropertyAdapter propertyAdapter;
    List<DeviceBean> propertyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_devices);

        propertyRecyclerView = findViewById(R.id.recyclerView);
        // Create adapter passing in the sample user data
        propertyAdapter = new PropertyAdapter(propertyList, MyDevices.this);
        // Attach the adapter to the recyclerview to populate items
        propertyRecyclerView.setAdapter(propertyAdapter);
        // Set layout manager to position the items
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        propertyRecyclerView.setLayoutManager(layoutManager);


        //Filling our recycler view list
        for (int i= 0; i< imageList.length; i++ ) {
            int image = imageList[i];
            String status = statusList[i];
            String priority = priorityList[i];

            com.stevecrossin.mindlab.DeviceBean property = new com.stevecrossin.mindlab.DeviceBean (i, image, status, priority);
            propertyList.add(property);

        }
    }
}