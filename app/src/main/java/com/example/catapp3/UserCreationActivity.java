package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.model.User;
import com.google.android.material.snackbar.Snackbar;

public class UserCreationActivity extends AppCompatActivity {

    public static String EXTRA_USERNAME = "Username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);

        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase catDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(catDatabase);

        final EditText usernameEditText = findViewById(R.id.userNameBox);
        final EditText emailEditText = findViewById(R.id.userEmailBox);
        final EditText passwordEditText = findViewById(R.id.userPasswordBox);
        final EditText passwordRepEditText = findViewById(R.id.userPasswordRepBox);
        final Button createButton = findViewById(R.id.createUserButton);
        Intent intent = new Intent(UserCreationActivity.this, CatCreationActivity.class);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordRep = passwordRepEditText.getText().toString();

                if(!password.equals(passwordRep)){
                    String passBadMessage = "Passwords do not match";
                    Snackbar.make(v, passBadMessage, Snackbar.LENGTH_LONG).show();
                }else {
                    User newUser = new User(username, email, password);
                    worker.insertUser(username, email, password);
                    intent.putExtra(EXTRA_USERNAME, newUser.getUserName());
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}