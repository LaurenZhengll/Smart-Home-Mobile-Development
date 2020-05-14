package com.stevecrossin.mindlab;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

public class SettingsScreen extends AppCompatActivity {

    private TextInputLayout settingsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
