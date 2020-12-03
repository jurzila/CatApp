package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.database.DietOpenHelper;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DietOpenHelper helper = new DietOpenHelper(this);
        final SQLiteDatabase dietDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(dietDatabase);

        setContentView(R.layout.activity_home);

        Button feedButtonOp = findViewById(R.id.button_feederOp);

        TextView lastFeedInfo = findViewById(R.id.displayLastFeed);

        String lastFeed = worker.getLastFeed().toString();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lastFeedInfo.setText("Last time the" + lastFeed + ".");
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

