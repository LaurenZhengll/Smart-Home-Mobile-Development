package com.stevecrossin.mindlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;



public class LoginScreen extends AppCompatActivity {
    public EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnLogin;
    //private Button loginBtn;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null)
        {
            startActivity(new Intent(LoginScreen.this, MainActivity.class));
            finish();
        }
        else return;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
       // loginBtn = findViewById(R.id.login);
        //loginBtn.setOnClickListener(new LoginBtnOnClick());
    }

    /*private class LoginBtnOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginScreen.this,MainActivity.class);
            startActivity(intent);
        }
    }*/


    /*public void bypassLogin(View view) {
        Intent intent = new Intent(this, SettingsScreen.class);
        startActivity(intent);
    }
*/

    public void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().signOut();
    }

/*
    This is the sign-out function which will be included in the settings page.
*/
//    public void onStop() {
//        super.onStop();
//        FirebaseAuth.getInstance().signOut();
//    }


    /**
     * Authentication task. This method is invoke through onClick of login button in activity_login.
     * Will initialise the elements in the view, as well as firebase auth.
     *
     * Take contents of email and password editText, convert to string, validate if those fields are empty, and if so throw an error.
     *
     * If none of these errors exist, it will perform auth task in firebase to determine if entered valiues match a record in Firebase with correct details (e.g. username/pass)
     *
     * If authentication is successful, this activity will be destroyed and user will be taken to MainActivity. Otherwise they will be returned an error that authentication failed
     *
     * Note: This error needs to be made more granular e.g. user does not exist, please sign in/ or incorrect password.
     */
    public void tryAuth(View view) {
            inputEmail = findViewById(R.id.username);
            inputPassword = findViewById(R.id.password);
            btnLogin = findViewById(R.id.login);
            boolean cancel = false;
            View focusView = null;

            auth = FirebaseAuth.getInstance();
            String email = inputEmail.getText().toString();
            final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Email address cannot be blank");
            focusView = inputEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Password cannot be blank");
            focusView = inputPassword;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        }
        else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                startActivity(new Intent(LoginScreen.this, MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(LoginScreen.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                inputPassword.getText().clear();
                            }
                        }
                    });
            }
        }

    public void goToSignup(View view){
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


        //loginBtn.setOnClickListener(new LoginBtnOnClickLis());


    /**
     * Commented out this code. This is not the correct way to implement an onClick listener - but also this does not belong here,
     * as this is the login screen and for testing, it is fine to implement this, but this shouldn't be pushed as final code.
     **/
    /*private class LoginBtnOnClickLis implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginScreen.this,ProfileForAged.class);
            startActivity(intent);
               }
            }
        */




