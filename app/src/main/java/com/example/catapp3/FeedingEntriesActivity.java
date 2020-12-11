package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.model.Cat;
import com.example.catapp3.model.Feeder;

import java.util.ArrayList;
import java.util.List;

public class FeedingEntriesActivity extends AppCompatActivity {

    private List<Feeder> allFeeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_entries);

        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase dietDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(dietDatabase);

        Intent openListIntent = getIntent();
        int currentCatId = Integer.valueOf(openListIntent.getExtras().get(HomeActivity.EXTRA_CAT_ID).toString());

        allFeeds = new ArrayList<>();
        allFeeds = worker.getAllFeeds(currentCatId);
        ListView feedList = findViewById(R.id.feedingEntriesListView);
        ArrayAdapter<Feeder> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allFeeds);
        feedList.setAdapter(adapter);

    }
}