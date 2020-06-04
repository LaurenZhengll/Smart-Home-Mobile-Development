package com.stevecrossin.mindlab;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class DatabaseReading extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mChildReference = mRootReference.child("User4");
    private DatabaseReference userNode = firebaseDatabase.getReference("User4");
    private TextView usernameoutput;
    private TextView passwordoutput;
    private TextView emailoutput;
    private  TextView eventoutput;
    private String wanteduser;
    private String personId;
    private Button updatebutton;

    ArrayList<Event> Events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
         ArrayList<Event> Events = new ArrayList<>();
        // MsgTxt = (TextView)findViewById(R.id.msgTxt);
        //MsgTxt.setText("Message Appear Here...");
        usernameoutput = findViewById(R.id.display_username);
        passwordoutput = findViewById(R.id.display_password);
        emailoutput = findViewById(R.id.display_email);
        eventoutput = findViewById(R.id.displayevent);
         updatebutton = findViewById(R.id.UpdateProfile);

        try {
            filterLog();
        } catch (IOException e) {
            e.printStackTrace();
        }

       // writeNewUser("child7", "Simon", "simon@gmail.com", "password123");

       /* try {
            ReadLogFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //Creates a Node that's directly below Root Node
        // mRootReference.child("User4").setValue(1);
       //  writeNewUser("child2","Barnacle","Swanna@gmail.com", "electanbomabarda", "27 avenue street");

      //  writeNewUser("child7", "Yawen", "whatever@gmail.com", "dafffddfe","4th  corner street");
        //writeNewUser("child5", "Zongyu", "xoned@gmail.com", "fhkehihfe", "6 belovard drive");
        //   writeNewUser("child3","Riya","Lila@gmail.com", "uraedf");
        // personId= mRootReference.push().getKey();

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameoutput.getText().toString();
                String password = passwordoutput.getText().toString();
                String email =  emailoutput.getText().toString();



            ///    UpdateUser("123", username, email,password,"balobo street garden");


            }
        });






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

                    User  user = data.getValue(User.class);

                    if (user.getUsername().toString().equals("Yawen")) {
                        usernameoutput.setText(user.getUsername().toString());
                        passwordoutput.setText(user.getPassword().toString());
                        emailoutput.setText(user.getEmail().toString());
                         String nodeid = data.getKey();

                         DataSnapshot dataentry =data.child("Events");
                           user.setPassword("Blimey3323");
                           personId =nodeid;
                        mChildReference.child(personId).setValue(user);

                      //you cannot access the child event node inside the  parent user node this way. It will give you a null value. The data cannot be accessed this way. This method does not work on the child nodes.
                        Event event = dataentry.getValue(Event.class);
                          String date2 = event.getDate();
         ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
          //This code block here I did is  to retireve data on child nodes of the user objects which here is the event object. We can't directly access this information from the datasnapshot object like we did with the user object.
           //For the child node I made a hashmapto to reorgainse the data inside the object. We can simply fetch the data we want by grabbing the value with the key specified.

                         HashMap<String, Object> hm = (HashMap<String, Object>)dataentry.getValue();

                          Set set = hm.entrySet();
                          //you could use either a iterator or a simple for loop to iterate through the Hashmap. Here we are looping through a collection of child event nodes inside the user parent node.
                          Iterator iterator = set.iterator();
                        while(iterator.hasNext()) {
                            Map.Entry evententry = (Map.Entry)iterator.next();

                        }
                        for(Map.Entry<String, Object> entry: hm.entrySet()) {

                            Object obj = entry.getValue();

                            HashMap<String, Object> secondhmap = (HashMap<String, Object>)entry.getValue();
                            String date = secondhmap.get("date").toString();
                            String alerttype = secondhmap.get("alert_type").toString();
                            ArrayList<Coordinates> coordinates = (ArrayList<Coordinates>) secondhmap.get("coordinates");



                            System.out.println(obj);


                        }








                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void writeNewUser(String userId, String name, String email, String password, String address) {
        personId = mRootReference.push().getKey();
        User user = new User(name, email, password, address);


        mChildReference.child(personId).setValue(user);


       for(Event e : Events){

           if(name.equals("Yawen"))
           {
              if(e.getAlert_type().equals("Tap")){

                  DatabaseReference eventReference = mChildReference.child(personId).child("Events").push();
                  eventReference.setValue(e);

              }

            }
           else
           {
               DatabaseReference eventReference = mChildReference.child(personId).child("Events").push();
               eventReference.setValue(e);


           }



       }


    }







    public void UpdateUser(String userId, String name, String email, String password, String address)
    {





    }









    public void ReadLogFile() throws IOException {
        //File file  = new File("alert.log");

        //Token method
        InputStream inputStream = getResources().openRawResource(R.raw.alerts);
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader read = new BufferedReader(isr);
        String line = null;
        while ((line = read.readLine()) != null) {
            StringTokenizer tokens = new StringTokenizer(line);
            String date = tokens.nextToken();
            String time = tokens.nextToken();
            String alerts = tokens.nextToken();
            String token4 = tokens.nextToken();
            String token5 = tokens.nextToken();
            String token6 = tokens.nextToken();
            String token7 = tokens.nextToken();
            String token8 = tokens.nextToken();
            String token9 = tokens.nextToken();
            String token10 = tokens.nextToken();
            String token11 = tokens.nextToken();
            String token12 = tokens.nextToken();
            System.out.println(token12);


        }


    }


    public void LogReader() {
        //File file = new File();
        //Regualar expression method
        InputStream inputStream = getResources().openRawResource(R.raw.alerts);
        Scanner scanner = new Scanner(inputStream);
        String patterncoordinattuple = (".*\\[    \\]*");
        String eventypepattern = ("\\bFall\\b");
        String timestamppattern;
        //Pattern pattern = Pattern.compile("(\\bFall)");


        scanner.useDelimiter("(\\s\\W)");

        //scanner.useDelimiter("(\\s*\\bINFO\\b\\s*)");
        while (scanner.hasNext()) {
            String ds = scanner.next().toString();
            String bd = " ";
            boolean gd = scanner.hasNext(eventypepattern);

            if (scanner.hasNext(eventypepattern)) {
                bd = scanner.next().toString();
            }

            System.out.println(bd);

        }


    }


    public void filterLog() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.alerts);
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader read = new BufferedReader(isr);
        String line;
        while ((line = read.readLine()) != null) {
            if(Events == null){
                Events = new ArrayList<>();
            }
            Events.add(parseLineItem(line));
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
        for (int i = 0; i < tuplearray.length; i++) {

            String[] tuplecolumns = tuplearray[i].split(",");
            String x = tuplecolumns[1];
            String y = tuplecolumns[2];

            event.AddCoordinates(x, y);
            //  coordinates.setCoordinateX(x);

        }
       // System.out.println("I think this will work");


        return event;
    }


}








