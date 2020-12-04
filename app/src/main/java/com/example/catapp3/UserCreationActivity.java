package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);


        final EditText usernameEditText = findViewById(R.id.userNameBox);
        final EditText emailEditText = findViewById(R.id.userEmailBox);
        final EditText passwordEditText = findViewById(R.id.userPasswordBox);
        final EditText passwordRepEditText = findViewById(R.id.userPasswordRepBox);
        final Button createButton = findViewById(R.id.createUserButton);
        Intent intent = new Intent(UserCreationActivity.this, CatCreationActivity.class);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });


    }
}