package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.database.DietOpenHelper;
import com.example.catapp3.model.Feeder;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeederActivity extends AppCompatActivity {

    public List<Feeder> feederList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DietOpenHelper helper = new DietOpenHelper(this);
        final SQLiteDatabase dietDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(dietDatabase);

        setContentView(R.layout.activity_feeder);
        Button feedButton = findViewById(R.id.button_fed);
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText caloriesBox = findViewById(R.id.calories_fed);
                //getActivity() is used to use findViewById ing fragment
                Double cal = Double.parseDouble(caloriesBox.getText().toString());
                //create exception for null

                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());

                if(!cal.equals(0)){

                    final DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            feederList.add(new Feeder(cal, date, "Mom"));
                            worker.insertDiet(cal, date, "Mom");
                            finish();
                        }
                    };

                    final DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            feederList.add(new Feeder(cal, date, "Dad"));
                            worker.insertDiet(cal, date, "Dad");
                            finish();

                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(FeederActivity.this);
                    //view.getContext while working in fragment to get dialog box working
                    builder
                            .setMessage("Who is feeding?")
                            .setPositiveButton("Mom", positiveListener/*TODO: add who fed cat to object Feeder*/)
                            .setNegativeButton("Dad", negativeListener)
                            .show();

                }else{
                    String message = "Please insert valid calories value";
                    Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}