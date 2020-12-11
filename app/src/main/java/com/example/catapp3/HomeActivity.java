package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.model.Cat;
import com.example.catapp3.model.Feeder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    public static String EXTRA_CAT_ID;
    public static String EXTRA_USER_ID;
    private String lastFeed;
    private int currentCatId;
    private ImageView catPicture;
    private TextView catName;
    private Feeder lastFeedData;
    private Cat currentCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase dietDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(dietDatabase);
        Intent loginIntent = getIntent();

        setContentView(R.layout.activity_home);

        currentCatId = Integer.valueOf(loginIntent.getExtras().get(LoginActivity.EXTRA_CAT_ID).toString());
        currentCatId = Integer.valueOf(loginIntent.getExtras().get(CatCreationActivity.EXTRA_CAT_ID).toString());

        catPicture = findViewById(R.id.catPictureDisplay);
        currentCat = worker.getCat(currentCatId);

        try{
            Bitmap pictureFull = currentCat.getPicture();
            catPicture.setImageBitmap(pictureFull);
            }catch (NullPointerException nP){
            Drawable profileDefault = getResources().getDrawable(R.drawable.profile_default);
            catPicture.setImageDrawable(profileDefault);
        }

        catPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openProfileIntent = new Intent(HomeActivity.this, ProfileViewActivity.class);
                openProfileIntent.putExtra(EXTRA_CAT_ID, currentCatId);
                startActivity(openProfileIntent);
            }
        });

        catName = findViewById(R.id.catNameDisplay);
        catName.setText(currentCat.getName());

        Button feedButtonOp = findViewById(R.id.button_feederOp);

        TextView lastFeedInfo = findViewById(R.id.displayLastFeed);



        try {
            lastFeedData = worker.getLastFeed(currentCatId);
            lastFeed ="Last time the cat was fed at " +
                    lastFeedData.getTimeFed() +
                    ", fed by " + lastFeedData.getWhoFed() +
                    ". Cat was fed " + worker.countHowManyTimesFed(currentCatId) +
                    " times today.";
        } catch (CursorIndexOutOfBoundsException e) {
            lastFeed = "Never been fed yet";
        }catch (NullPointerException npe){
            lastFeed = "Never been fed yet";
        }

        lastFeedInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openListIntent = new Intent(HomeActivity.this, FeedingEntriesActivity.class);
                openListIntent.putExtra(EXTRA_CAT_ID, currentCatId);
                startActivity(openListIntent);
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lastFeedInfo.setText(lastFeed);
            }
        });




        feedButtonOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //feedingCount++;
                    Intent feedIntent = new Intent(HomeActivity.this, FeederActivity.class);
                    feedIntent.putExtra(EXTRA_CAT_ID, currentCatId);
                    startActivity(feedIntent);

            }
        });

        Button feedingMonitor = findViewById(R.id.feedingMonitorNav);

        feedingMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monitorIntent = new Intent(HomeActivity.this, FeedingMonitorActivity.class);
                monitorIntent.putExtra(EXTRA_CAT_ID, currentCatId);
                startActivity(monitorIntent);
            }
        });

        FloatingActionButton addCat = findViewById(R.id.addCatActionButton);
        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent catCreationIntent = new Intent(HomeActivity.this, CatCreationActivity.class);
                        int currentUserId = worker.getUserIdFromCat(currentCatId);
                        catCreationIntent.putExtra(EXTRA_USER_ID, currentUserId);
                        startActivity(catCreationIntent);
                        finish();

                    }
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder
                        .setMessage("Do you want to add new cat?")
                        .setPositiveButton("Yes", positiveListener)
                        .setNegativeButton("No", null)
                        .show();

            }
        });
    }

    @Override
    protected void onResume(){

        super.onResume();
        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase catDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(catDatabase);
        Intent loginIntent = getIntent();
        currentCatId = Integer.valueOf(loginIntent.getExtras().get(LoginActivity.EXTRA_CAT_ID).toString());

        try {
            lastFeedData = worker.getLastFeed(currentCatId);
            lastFeed ="Last time the cat was fed at " +
                    lastFeedData.getTimeFed() +
                    ", fed by " +lastFeedData.getWhoFed() +
                    ". Cat was fed " + worker.countHowManyTimesFed(currentCatId) +
                    " times today.";
        } catch (CursorIndexOutOfBoundsException e) {
            lastFeed = "Never been fed yet";
        }catch (NullPointerException npe){
            lastFeed = "Never been fed yet";
        }
        TextView lastFeedInfo = findViewById(R.id.displayLastFeed);
        lastFeedInfo.setText(lastFeed);

        try{
            Bitmap pictureFull = currentCat.getPicture();
            catPicture.setImageBitmap(pictureFull);
        }catch (NullPointerException nP){
            Drawable profileDefault = getResources().getDrawable(R.drawable.profile_default);
            catPicture.setImageDrawable(profileDefault);
        }

        //TODO: make code less repetitive
    }

}

