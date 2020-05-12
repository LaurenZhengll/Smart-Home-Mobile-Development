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

        loginBtn = findViewById(R.id.login);
        //loginBtn.setOnClickListener(new LoginBtnOnClickLis());
    }

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
}
