package com.stevecrossin.mindlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginScreen extends AppCompatActivity {
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn =findViewById(R.id.login);
        loginBtn.setOnClickListener(new LoginBtnOnClickLis());
    }

    private class LoginBtnOnClickLis implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginScreen.this,ProfileForAged.class);
            startActivity(intent);
        }
    }
}