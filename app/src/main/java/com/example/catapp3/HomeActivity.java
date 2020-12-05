package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.database.CatOpenHelper;

public class HomeActivity extends AppCompatActivity {


    private String lastFeed;

    @Override
    protected void onResume(){
        super.onResume();
        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase catDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(catDatabase);
        try {
            lastFeed ="Last time the" + worker.getLastFeed().toString() + ".";
        } catch (CursorIndexOutOfBoundsException e) {
            lastFeed = "Never been fed yet";
        }
        TextView lastFeedInfo = findViewById(R.id.displayLastFeed);
        lastFeedInfo.setText(lastFeed);
        //TODO: make code less repetitive
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase dietDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(dietDatabase);

        setContentView(R.layout.activity_home);

        Button feedButtonOp = findViewById(R.id.button_feederOp);

        TextView lastFeedInfo = findViewById(R.id.displayLastFeed);

        try {
            lastFeed ="Last time the" + worker.getLastFeed().toString() + ".";
        } catch (CursorIndexOutOfBoundsException e) {
            lastFeed = "Never been fed yet";
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lastFeedInfo.setText(lastFeed);
            }
        });


        feedButtonOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FeederActivity.class);

                startActivity(intent);
            }
        });

    }

}

