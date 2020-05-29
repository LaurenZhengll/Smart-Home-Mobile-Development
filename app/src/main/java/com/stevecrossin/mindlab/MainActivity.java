package com.stevecrossin.mindlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button button;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mChildReference = mRootReference.child("User4");
    private DatabaseReference userNode = firebaseDatabase.getReference("User4");

    TextView heartRateValue;
    TextView pressureValue;

    private String personId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //button = findViewById(R.id.settingsButton);


        writeNewUser("child5", "Zongyu", "abc@gmail.com", "123123", "120", "140/70");

    }

    public void writeNewUser(String userId, String username, String email, String password, String HeartRate,  String BloodPressure) {
        personId = mRootReference.push().getKey();
        User user = new User(username, email, password, HeartRate, BloodPressure);

        mChildReference.child(personId).setValue(user);



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
        }
        //Why is this line of code here?
        //setContentView(R.layout.layout_carer_one);

    }

    @Override
    protected void onStart() {

        super.onStart();
        mChildReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //gets all the children in the rootnode

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //Getting User object from dataSnapshot

                    User user = data.getValue(User.class);

                    if (user.getUsername().toString().equals("Zongyu")) {
                        heartRateValue = (TextView) findViewById(R.id.heartRateValue);
                        pressureValue = (TextView) findViewById(R.id.pressureValue);
                        heartRateValue.setText(user.getHeartRate().toString());
                        pressureValue.setText(user.getBloodPressure().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
