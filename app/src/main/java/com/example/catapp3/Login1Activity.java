package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.model.User;
import com.google.android.material.snackbar.Snackbar;

public class Login1Activity extends AppCompatActivity {

    public static String EXTRA_CAT_ID;
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

        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase catDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(catDatabase);

        try {
            User currentUser = worker.getUserPassword(username);
            String password = currentUser.getPassword();
            if(userPassword.equals(password)){

                Intent intentLogin = new Intent(Login1Activity.this, HomeActivity.class);
                intentLogin.putExtra(EXTRA_CAT_ID, worker.getCatID(currentUser.getUsernameId()));
                startActivity(intentLogin);
            }else{
                counter--;

                String wrongPass = "Failed to log in. Attempts left: " + counter + ".";
                Snackbar.make(findViewById(android.R.id.content), wrongPass, Snackbar.LENGTH_LONG).show();

                if(counter == 0){
                    loginButton.setEnabled(false);
                }
            }
        }catch(Exception e){
            String noUserMessage = "Username does not exist";
            Snackbar.make(findViewById(android.R.id.content), noUserMessage, Snackbar.LENGTH_LONG).show();
        }




    }


}