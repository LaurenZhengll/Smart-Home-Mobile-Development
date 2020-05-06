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

public class LoginScreen extends AppCompatActivity {
    public EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnLogin;

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }*/

    private void updateUI (FirebaseUser user){
        if (user != null) {
            inputEmail.setText(getString(R.string.google_status_fmt, user.getEmail()));
            inputPassword.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.signup).setVisibility(View.GONE);
        } else {
            inputEmail.setText(null);
            inputPassword.setText(null);

            findViewById(R.id.signup).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
    }

    public void onStop() {
        super.onStop();
        auth.getInstance().signOut();
    }

    public void tryAuth(View view) {
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginScreen.this, MainActivity.class));
            finish();
        }


        inputEmail = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();
        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

/*        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }*/
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }

    public void goToSignup(View view){
        Intent intent = new Intent(this, SignUpScreen.class);
        startActivity(intent);
    }
}