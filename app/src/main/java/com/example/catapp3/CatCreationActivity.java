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
import com.example.catapp3.model.Cat;

import java.util.Date;

public class CatCreationActivity extends AppCompatActivity {

    private Date dateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_creation);

        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase catDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(catDatabase);

        final EditText nameEditText = findViewById(R.id.catNameBox);
        final EditText sexEditText = findViewById(R.id.catSexBox);
        final EditText breedEditText = findViewById(R.id.catBreedBox);
        final EditText dateOfBirthEditText = findViewById(R.id.dateOfBirthBox);
        final EditText weightEditText = findViewById(R.id.weightBox);
        final Button createButton = findViewById(R.id.createCatButton);
        Intent intent1 = getIntent();
        Intent intent2 = new Intent(CatCreationActivity.this, Login1Activity.class);
        String username = intent1.getExtras().get(UserCreationActivity.EXTRA_USERNAME).toString();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString();
                String sex = sexEditText.getText().toString();
                String breed = breedEditText.getText().toString();
                /*try {
                    dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthEditText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                String dateOfBirth = dateOfBirthEditText.getText().toString();
                Double weight = Double.parseDouble(weightEditText.getText().toString());
                int userId = worker.getUserID(username);
                Cat newCat = new Cat(name, sex, breed, dateOfBirth, weight, userId);
                worker.insertCat(userId, name, sex, breed, dateOfBirth, weight);
                //int catId = worker.getCatID(userId);

                startActivity(intent2);
            }
        });
    }
}