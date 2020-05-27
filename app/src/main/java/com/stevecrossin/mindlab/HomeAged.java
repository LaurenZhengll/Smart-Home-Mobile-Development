package com.stevecrossin.mindlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeAged extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_patient);
    }

    public void goToProfile(View view) {
        if (user != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(this, ProfileForAged.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show(); }
    }

    public void goToSettings(View view) {
        if (user != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(this, SettingsScreen.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show(); }
    }

    public void goToMyDevices(View view) {
        if (user != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(this, MyDevices.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show(); }
    }
}
