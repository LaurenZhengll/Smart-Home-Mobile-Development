package com.stevecrossin.mindlab;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateCaretakerProfile extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mChildReference = mRootReference.child("User4");
    private DatabaseReference userNode = firebaseDatabase.getReference("User4");
    private TextView givenname;
    private TextView familyname;
    private TextView DOB;
    private TextView addressfield;
    private TextView occupancyfield;
    private  TextView institutionfield;
    private  TextView contactnumberfield;
    private String state;
    private String personId;
    private Spinner statespinner;
    private List<String> spinnerlist;
    private Button savebutton;
    private String password;
    private  String username;
    private String email;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Victoria");
        spinnerArray.add("QueensLand");
        spinnerArray.add("Tasmania");
        spinnerArray.add("Western Australia");
        spinnerArray.add("New South Wales");
        spinnerArray.add("South Australia");
       spinnerlist = spinnerArray;



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sStates= (Spinner) findViewById(R.id.stateSpinner);
        sStates.setAdapter(adapter);

       // String getselectedState = sStates.getSelectedItem().toString();
        //state =getselectedState;
        statespinner=sStates;


        savebutton = findViewById(R.id.saveButton);
       givenname = findViewById(R.id.givennameField);
       familyname = findViewById(R.id.familynameField);
       DOB = findViewById(R.id.dobField);
       addressfield = findViewById(R.id.addressField);
       occupancyfield= findViewById(R.id.contactField);
       institutionfield = findViewById(R.id.contactField);
       contactnumberfield = findViewById(R.id.allergyField);


      savebutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String familynamevalue = familyname.getText().toString();
              String givennamevalue = givenname.getText().toString();
              String dobvalue = DOB.getText().toString();
              String address =  addressfield.getText().toString();
              String occupancy = occupancyfield.getText().toString();
              String institution = institutionfield.getText().toString();
              String contactnumber = contactnumberfield.getText().toString();
              String getselectedState = statespinner.getSelectedItem().toString();



              UpdateCaretaker(username,email,password,address,getselectedState,givennamevalue,familynamevalue,dobvalue,contactnumber,occupancy,institution);

          }
      });




    }

    @Override
    protected void onStart() {
        super.onStart();
       mChildReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot data : dataSnapshot.getChildren()) {

                     Caretaker caretaker = data.getValue(Caretaker.class);
                     if(data.getKey().equals(personId))
                     {
                         String state = caretaker.getState();
                         int stateindex =spinnerlist.indexOf(state);



                         givenname.setText(caretaker.getGivenname());
                         familyname.setText(caretaker.getFamilyname());
                         DOB.setText(caretaker.getDob());
                         addressfield.setText(caretaker.getAddress());
                         occupancyfield.setText(caretaker.getOccupancy());
                         institutionfield.setText(caretaker.getInstitution());
                         contactnumberfield.setText(caretaker.getContactnumber());
                         statespinner.setSelection(stateindex);
                         username = caretaker.getUsername();
                         password = caretaker.getPassword();
                         email = caretaker.getEmail();

                     }



               }


           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });



    }








    public void UpdateCaretaker(String username, String email, String password, String address, String state, String givenname, String familyname, String dob, String contactnumber,String occupancy,String institution)
    {
          Caretaker caretaker  = new Caretaker(username,email,password,address,state,givenname,familyname,dob,contactnumber,occupancy,institution);
          personId = mRootReference.push().getKey();
          mChildReference.child(personId).setValue(caretaker);


    }












}
