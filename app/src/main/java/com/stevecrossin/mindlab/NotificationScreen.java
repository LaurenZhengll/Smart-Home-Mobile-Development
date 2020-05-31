package com.stevecrossin.mindlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.io.OptionalDataException;
import java.util.ArrayList;

public class NotificationScreen extends AppCompatActivity {
    private RecyclerView Views;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<NotificationItem> listItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_screen);

        ArrayList<NotificationItem> listItem = listItems();

        this.Views = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.Views.setLayoutManager(mLayoutManager);

        adapter = new NotificationAdapter(listItem);
        this.Views.setAdapter(adapter);

    }

    private ArrayList<NotificationItem> listItems() {
        ArrayList<NotificationItem> list = new ArrayList<>();

            list.add(new NotificationItem("Update Reminder", "10:20am, Last Friday, By Mind Lab Translators", "A new version 2.0.10 is now available for downloading from Google Play Store"));
            list.add(new NotificationItem("Assist Requirement", "9:20am, Last Sunday, By Alice Barber", "Medicine for reliving hypertension is running out in two weeks."));

        return list;
    }
}



