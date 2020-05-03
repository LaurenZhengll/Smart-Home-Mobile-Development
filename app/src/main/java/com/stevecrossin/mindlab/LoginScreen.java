package com.stevecrossin.mindlab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginScreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void nextActivity(View view){
        String button_text;
        button_text=((Button) view).getText().toString();
        if(button_text.equals("Sign up"))
        {
            Intent intent = new Intent(this, SignUpScreen.class);
        startActivity(intent);
        }
    }
}