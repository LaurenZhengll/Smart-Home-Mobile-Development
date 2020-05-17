package com.stevecrossin.mindlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button button;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.goTo_settings);

    }


    public void goToSettings(View view) {
        if (user != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(this, SettingsScreen.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            return;}
    }

    public void signout(View view) {
        if (user != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            return;}
    }
}
