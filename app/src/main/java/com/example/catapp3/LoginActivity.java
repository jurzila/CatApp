package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.loginUsername);
        final EditText passwordEditText = findViewById(R.id.loginPassword);
        final Button loginButton = findViewById(R.id.loginButton);
        final Button registerButton = findViewById(R.id.registerButton);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(LoginActivity.this, UserCreationActivity.class);
                startActivity(intentRegister);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intentLogin);
            }
        });


    }


}