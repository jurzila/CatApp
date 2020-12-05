package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login1Activity extends AppCompatActivity {


    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.loginUsername);
        passwordEditText = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);




        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(Login1Activity.this, UserCreationActivity.class);
                startActivity(intentRegister);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });


        }

    private void validate(String username, String userPassword){
        if(username.equals("Admin") && userPassword.equals("1234")){
            Intent intentLogin = new Intent(Login1Activity.this, HomeActivity.class);
            startActivity(intentLogin);
        }else{
            counter--;

            //TODO add info box about failed log in attempt

            if(counter == 0){
                loginButton.setEnabled(false);
            }
        }


    }


}