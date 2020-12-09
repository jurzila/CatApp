package com.example.catapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.model.Cat;

import java.util.ArrayList;
import java.util.List;

public class CatSelectionActivity extends AppCompatActivity {

    private List<Cat> cats;
    public static String EXTRA_CAT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase dietDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(dietDatabase);

        setContentView(R.layout.activity_cat_selection);

        Intent loginIntent = getIntent();
        int currentUserId = Integer.valueOf(loginIntent.getExtras().get(LoginActivity.EXTRA_USER_ID).toString());

        cats = new ArrayList<>();
        cats = worker.getAllUserCats(currentUserId);
        ListView catList = findViewById(R.id.catListView);
        ArrayAdapter<Cat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cats);
        catList.setAdapter(adapter);
        catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentChooseCat = new Intent(CatSelectionActivity.this, HomeActivity.class);
                Cat clickedCat = cats.get(position);
                intentChooseCat.putExtra(EXTRA_CAT_ID, clickedCat.getCatId());

                startActivity(intentChooseCat);
            }
        });
    }
}