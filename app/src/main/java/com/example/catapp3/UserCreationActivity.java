package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.model.User;
import com.google.android.material.snackbar.Snackbar;

public class UserCreationActivity extends AppCompatActivity {

    public static String EXTRA_USERNAME;

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

                if (v == createButton) {
                    boolean error = false;
                    if (isEmpty(usernameEditText)){
                        error = true;
                        usernameEditText.setError("Fill in username");
                    }
                    if (isEmpty(emailEditText)) {
                        error = true;
                        emailEditText.setError("Fill in email");
                    }
                    if (isEmpty(passwordEditText)) {
                        error = true;
                        passwordEditText.setError("Enter a password");
                    }
                    if (isEmpty(passwordRepEditText)) {
                        error = true;
                        passwordRepEditText.setError("Repeat the password");
                    }
                    if (!error) {

                        String username = usernameEditText.getText().toString();
                        String email = emailEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        String passwordRep = passwordRepEditText.getText().toString();

                        if (!password.equals(passwordRep)) {
                            String passBadMessage = "Passwords do not match";
                            Snackbar.make(v, passBadMessage, Snackbar.LENGTH_LONG).show();
                        } else {
                            int insertValidation = worker.insertUser(username, email, password);
                            if (insertValidation == 0) {
                                User newUser = new User(username, email, password);
                                intent.putExtra(EXTRA_USERNAME, newUser.getUserName());
                                startActivity(intent);
                                finish();
                            } else if (insertValidation == 1) {
                                String text = "Username already exists";
                                Toast toastUser = Toast.makeText(UserCreationActivity.this, text, Toast.LENGTH_LONG);
                                toastUser.show();
                            } else if (insertValidation == 2) {
                                String text = "Email already exists";
                                Toast toastUser = Toast.makeText(UserCreationActivity.this, text, Toast.LENGTH_LONG);
                                toastUser.show();
                            }else if (insertValidation == 3) {
                                String text = "Username and email already exists";
                                Toast toastUser = Toast.makeText(UserCreationActivity.this, text, Toast.LENGTH_LONG);
                                toastUser.show();
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean isEmpty(EditText etText){
        return etText.getText().toString().trim().length() == 0;
    }

}
