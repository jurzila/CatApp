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
import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.model.Feeder;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FeederActivity extends AppCompatActivity {

    public List<Feeder> feederList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase catDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(catDatabase);

        Intent feedIntent = getIntent();
        int currentCatId = Integer.valueOf(feedIntent.getExtras().get(HomeActivity.EXTRA_CAT_ID).toString());

        setContentView(R.layout.activity_feeder);
        Button feedButton = findViewById(R.id.button_fed);
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText caloriesBox = findViewById(R.id.calories_fed);
                //getActivity() is used to use findViewById ing fragment

                if (v == feedButton) {
                    boolean error = false;
                    if (caloriesBox.getText().toString().isEmpty()) {
                        error = true;
                        caloriesBox.setError("Fill in calories");
                    }
                    if (!error){

                        Double cal = Double.parseDouble(caloriesBox.getText().toString());

                        Calendar calendar = Calendar.getInstance();

                        SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
                        String date = dateOnly.format(calendar.getTime());

                        SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss");
                        String time = timeOnly.format(calendar.getTime());

                        final DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                feederList.add(new Feeder(cal, date, time, "Mom"));
                                worker.insertDiet(currentCatId, cal, date, time, "Mom");
                                finish();
                            }
                        };

                        final DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                feederList.add(new Feeder(cal, date, time, "Dad"));
                                worker.insertDiet(currentCatId, cal, date, time, "Dad");
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

                    }
                }
            }
        });
    }
}