package com.stevecrossin.mindlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginScreen extends AppCompatActivity {
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbtn = findViewById(R.id.login);
        loginbtn.setOnClickListener(new LoginBtnOnClick());
    }

    private class LoginBtnOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginScreen.this,MainActivity.class);
            startActivity(intent);
        }
    }
}