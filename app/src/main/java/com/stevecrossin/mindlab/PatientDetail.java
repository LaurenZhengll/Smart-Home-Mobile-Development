package com.stevecrossin.mindlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PatientDetail extends AppCompatActivity {
    ImageView patientImg;
    TextView name;
    TextView age;
    TextView state;
    TextView mobile;
    TextView condition;
    TextView allergy;
    TextView address;
    TextView contactName;
    TextView contactNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        patientImg = findViewById(R.id.patientImg);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        state = findViewById(R.id.state);
        mobile = findViewById(R.id.mobileInfoText);
        condition = findViewById(R.id.conditionInfoText);
        allergy = findViewById(R.id.allergyInfo);
        address = findViewById(R.id.addressInfo);
        contactNumber = findViewById(R.id.contactNumber);
        contactName = findViewById(R.id.contactName);
    }

    /**Commented out this - android has inbuilt navigation and this sort of element is discouraged **/
/*    public void goBackToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/

}
