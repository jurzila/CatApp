package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

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

public class HomeActivity extends AppCompatActivity {

    public static String EXTRA_CAT_ID;
    private String lastFeed;
    private int currentCatId;
    private ImageView catPicture;
    private TextView catName;
    //private int feedingCount=0;



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
        Cat currentCat = worker.getCat(currentCatId);

        try{
            Bitmap pictureFull = currentCat.getPicture();
        Bitmap pictureSmall = Bitmap.createScaledBitmap(pictureFull, 120, 120, false);
        catPicture.setImageBitmap(pictureSmall);
        }catch (NullPointerException nP){
            Drawable profileDefault = getResources().getDrawable(R.drawable.profile_default);
            catPicture.setImageDrawable(profileDefault);
        }

        catName = findViewById(R.id.catNameDisplay);
        catName.setText(currentCat.getName());

        Button feedButtonOp = findViewById(R.id.button_feederOp);

        TextView lastFeedInfo = findViewById(R.id.displayLastFeed);

        try {
            lastFeed ="Last time the" + worker.getLastFeed(currentCatId).toString() + "."; // Cat was fed " + feedingCount + " times today.";
        } catch (CursorIndexOutOfBoundsException e) {
            lastFeed = "Never been fed yet";
        }catch (NullPointerException npe){
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
            lastFeed ="Last time the" + worker.getLastFeed(currentCatId).toString() + ".";
        } catch (CursorIndexOutOfBoundsException e) {
            lastFeed = "Never been fed yet";
        }catch (NullPointerException npe){
            lastFeed = "Never been fed yet";
        }
        TextView lastFeedInfo = findViewById(R.id.displayLastFeed);
        lastFeedInfo.setText(lastFeed);
        //TODO: make code less repetitive
    }

}

