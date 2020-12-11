package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.database.DatabaseDataWorker;

public class FeedingMonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_monitor);

        Intent monitorIntent = getIntent();
        int currentCatId = Integer.valueOf(monitorIntent.getExtras().get(HomeActivity.EXTRA_CAT_ID).toString());
        TextView dayData = findViewById(R.id.monitorLastDay);
        TextView weekData = findViewById(R.id.monitorLastWeek);
        TextView monthData = findViewById(R.id.monitorLastMonth);

        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase dietDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(dietDatabase);

        double dayAverage = worker.selectLastDayData(currentCatId);
        dayData.setText("Last day's average cat calories gain: " + dayAverage);

        double weekAverage = worker.selectLastWeekData(currentCatId);
        weekData.setText("Last week average cat calories gain: "+ weekAverage);

        double monthAverage = worker.selectLastMonthData(currentCatId);
        monthData.setText("Last month average cat calories gain: "+ monthAverage);

    }

    //private int getAge(int catId){

    //}

}