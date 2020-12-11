package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.model.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    public static String EXTRA_CAT_ID;
    public static String EXTRA_USER_ID;
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

        //createDefaultDatabase();//Inserts default database for testing;

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
                if(worker.isCatCreated(currentUser.getUsernameId()) == 1) {
                    Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                    intentLogin.putExtra(EXTRA_CAT_ID, worker.getCatID(currentUser.getUsernameId()));
                    startActivity(intentLogin);
                }else if(worker.isCatCreated(currentUser.getUsernameId()) > 1) {
                    Intent intentLoginWithCatSelection = new Intent(LoginActivity.this, CatSelectionActivity.class);
                    intentLoginWithCatSelection.putExtra(EXTRA_USER_ID, currentUser.getUsernameId());
                    startActivity(intentLoginWithCatSelection);
                }else if(worker.isCatCreated(currentUser.getUsernameId()) == 0){
                    Intent intentLoginCatCreation = new Intent(LoginActivity.this, CatCreationActivity.class);
                    intentLoginCatCreation.putExtra(EXTRA_USER_ID, currentUser.getUsernameId());
                    startActivity(intentLoginCatCreation);
                }
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

    public void createDefaultDatabase(){
        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase catDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(catDatabase);
        Bitmap importedPicture = BitmapFactory.decodeResource(getResources(),R.drawable.profile_default);

        worker.insertUser("Jacob", "e.jacob@email.com", "GetBanned");
        worker.insertUser("Aaron", "aaron@email.com", "Snow");
        worker.insertUser("Amber", "sunshine@email.com", "Salty");
        worker.insertCatBtm(1, "Biscuit", "Male", "Bengalian", "2010/06/29", 4.6, importedPicture);
        worker.insertCatBtm(2, "Blackie", "Female", "Mixed", "2020/05/05", 2.0, importedPicture);
        worker.insertCatBtm(3, "Snowflake", "Male", "Persian", "2015/12/24", 3.8, importedPicture);
        worker.insertCatBtm(3, "Princess", "Female", "Persian", "2014/03/25", 4.2, importedPicture);

        worker.insertDiet(1, 333.33, "2020/11/27", "08:45", "Dad");
        worker.insertDiet(1, 454.20, "2020/11/28", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/11/28", "18:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/11/27", "18:45", "Dad");
        worker.insertDiet(1, 363.24, "2020/11/29", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/11/29", "18:45", "Dad");
        worker.insertDiet(1, 352.35, "2020/11/30", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/11/30", "18:45", "Dad");
        worker.insertDiet(1, 224.0, "2020/12/01", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/12/01", "18:45", "Dad");
        worker.insertDiet(1, 448.0, "2020/12/02", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/12/02", "18:45", "Dad");
        worker.insertDiet(1, 400.23, "2020/12/03", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/12/03", "18:45", "Dad");
        worker.insertDiet(1, 358.0, "2020/12/04", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/12/04", "18:45", "Dad");
        worker.insertDiet(1, 252.0, "2020/12/05", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/12/05", "18:45", "Dad");
        worker.insertDiet(1, 100.36, "2020/12/06", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/12/06", "18:45", "Dad");
        worker.insertDiet(1, 777.3, "2020/12/07", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/12/07", "18:45", "Dad");
        worker.insertDiet(1, 777.3, "2020/12/07", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/12/08", "18:45", "Dad");
        worker.insertDiet(1, 650.0, "2020/12/08", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/12/09", "18:45", "Dad");
        worker.insertDiet(1, 350.0, "2020/12/09", "08:45", "Dad");
        worker.insertDiet(1, 200.00, "2020/12/10", "18:45", "Dad");
        worker.insertDiet(1, 255.0, "2020/12/10", "08:45", "Dad");
        worker.insertDiet(1, 200.25, "2020/12/11", "08:45", "Dad");

    }


}