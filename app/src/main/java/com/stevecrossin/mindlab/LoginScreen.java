package com.stevecrossin.mindlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signUp = findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();
            }
        });

    }

    public void nextActivity(){
        Intent intent = new Intent(this, SignUpScreen.class);
        startActivity(intent);
    }

    // Commented out below code block - this code does not belong on this screen.

    /* *
     * This is an intent. When the method is called by the OnClick, it will navigate to the relevant screen
     **//*
    public void GoToDevices(View view) {
        Intent intent = new Intent(this, MyDevices.class);
        startActivity(intent);
    }

    *
     * This is an intent. When the method is called by the OnClick, it will navigate to the relevant screen
     **//*
    public void GoToAddNewDevices(View view) {
        Intent intent = new Intent(this, AddNewDevice.class);
        startActivity(intent);
    }*/
}