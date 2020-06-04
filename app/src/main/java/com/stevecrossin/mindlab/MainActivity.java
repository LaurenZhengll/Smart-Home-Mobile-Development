package com.stevecrossin.mindlab;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    //Button button;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    ArrayList<Event> Events = new ArrayList<>();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mChildReference = mRootReference.child("User2");
    private DatabaseReference userNode = firebaseDatabase.getReference("User2");
    private String personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //button = findViewById(R.id.settingsButton);
        /*mRootReference.child("User3").setValue(1);*/
        /* writeNewUser("123","Jay","abcd@gmail.com", "Password1");*/
        ArrayList<Event> Events = new ArrayList<>();
        try {
            filterLog();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeNewUser("890","Sam","12jk@gmail.com", "alskdf12", "80", "80/130" );

        // setContentView(R.layout.activity_main);

       /* try {
            ReadLogFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //LogReader();
        try {
            filterLog();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public void goToProfile(View view) {
        if (user != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(this, Profile.class);
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

    public void goToPatientDetial(View view) {
        if (user != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(this, PatientDetail.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show(); }
    }

/*    public void signout(View view) {
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

    } */

    public void writeNewUser(String userId, String name, String email, String password, String HeartRate, String BloodPressure) {
        personId = mRootReference.push().getKey();

        User user = new User(name, email, password, HeartRate, BloodPressure);
        // mRootReference.child("users").child(userId).setValue(user);
        //userNode.setValue(user);
        mChildReference.child(personId).setValue(user);

        for(Event event: Events) {
            DatabaseReference eventReference = mChildReference.child(personId).child("Events").push();
            eventReference.setValue(event);
        }
    }


    public void LogReader()
    {
        //File file = new File();
        //Regualar expression method
        InputStream inputStream  =  getResources().openRawResource(R.raw.alerts);
        Scanner scanner = new Scanner(inputStream);
        String patterncoordinattuple = (".*\\[    \\]*");
        String eventypepattern=("\\bFall\\b");
        String timestamppattern;
        //Pattern pattern = Pattern.compile("(\\bFall)");


        scanner.useDelimiter("(\\s\\W)");

        //scanner.useDelimiter("(\\s*\\bINFO\\b\\s*)");
        while (scanner.hasNext()) {
            String ds = scanner.next().toString();
            String bd = " ";
            boolean gd =scanner.hasNext(eventypepattern);

            if(scanner.hasNext(eventypepattern))
            {
                bd = scanner.next().toString();
            }

            System.out.println(bd);

        }
    }
    public void  filterLog() throws IOException {
        InputStream inputStream  =  getResources().openRawResource(R.raw.alerts);
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader read = new BufferedReader(isr);
        String line;
        while ((line = read.readLine()) != null) {
            if(Events == null){
                Events = new ArrayList<>();
            }

            Events.add(parseLineItem(line));
            //parseLineItem(line);
        }
    }

    public Event parseLineItem(String lineItem) {
        // Split by space character.
        //        //splits columns by whitespace.
        String[] columns = lineItem.split(" ");
        Event event = new Event();


        //Event eventproperties = new Event();
        // String alert_type =eventproperties.getAlert_type();
        // The last three entries will be unit price, quantity and price
        String alerttype;
        String date;
        String time;
        String startOfcoordinateTuple;
        String endOfcoordinateTuple;
        int columnEnd;
        int numberoftabs = 0;
        //eventproperties.getAlert_type() = Float.parseFloat(columns[columns.length - 1]);

        alerttype = columns[7];
        date = columns[0];
        time = columns[1];
        startOfcoordinateTuple = columns[9];


        //  String coordinatetext =  lineItem.substring()
        //int  gap = columns.length - 3;
        int startfirstpositon = startOfcoordinateTuple.indexOf('[');
        //int  endpositon = columns[columns.length -1].indexOf(']');
        int starofTuple = 0;
        char tuplebracket = columns[9].charAt(0);
        char[] linearray = lineItem.toCharArray();

        for (int i = 0; i < linearray.length; ++i) {
            if (linearray[i] == tuplebracket) {
                starofTuple = i;
                break;
            }
        }


//       char positonoftuple = linearray[columns[9]]


        //need to create char array and get the tab spaces between the start of the tuple and the end of the tuple.
        char tabvalue = ' ';
        char[] finalvalue;
        StringBuilder sb = new StringBuilder();


        for (int i = starofTuple; i < linearray.length; i++) {

            sb.append(linearray[i]);


        }

        for (int i = starofTuple; i < linearray.length; i++) {
            // char ch=lineItem.charAt(i);
            if (linearray[i] == tabvalue) {
                numberoftabs++;
            }

        }

        System.out.println(sb);
        String datetime = date + ' ' + time;
        event.setDate(datetime);
        event.setAlert_type(alerttype);
        int numberofTuples = (numberoftabs + 1) / 3;
        String tuplestring = sb.toString();
        String[] tuplearray = new String[numberofTuples];
        int j = 0;
        int startpoint = 1;
        String tuplethrowaway = "";

        for (int i = 1; i < tuplestring.length(); i++) {


            if (i > startpoint) {
                tuplethrowaway = tuplestring.substring(startpoint, i);
            }

            if (tuplestring.charAt(i) == ')') {

                if (!tuplethrowaway.isEmpty()) {
                    StringBuilder stb = new StringBuilder();
                    stb.append(tuplethrowaway);
                    //stb.append(')');
                    tuplearray[j] = stb.toString();
                }

                j++;
                startpoint = i + 2;
            }
        }
        //  String [] tuplesarry = tuplestring.split("");
        for (int i = 0; i < tuplearray.length; i++)
        {

            String [] tuplecolumns = tuplearray[i].split(",");
            String x = tuplecolumns[1];
            String y = tuplecolumns[2];

            event.AddCoordinates(x,y);
            //  coordinates.setCoordinateX(x);

        }
        System.out.println("I think this will work");
        return event;
    }

}

















