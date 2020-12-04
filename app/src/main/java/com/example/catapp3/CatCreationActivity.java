package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CatCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_creation);

        final EditText nameEditText = findViewById(R.id.catNameBox);
        final EditText sexEditText = findViewById(R.id.catSexBox);
        final EditText breedEditText = findViewById(R.id.catBreedBox);
        final EditText dateOfBirthEditText = findViewById(R.id.dateOfBirthBox);
        final EditText weightEditText = findViewById(R.id.weightBox);
        final Button createButton = findViewById(R.id.createCatButton);
        Intent intent = new Intent(CatCreationActivity.this, LoginActivity.class);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}