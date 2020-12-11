package com.example.catapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.model.AgeCalculator;
import com.example.catapp3.model.Cat;

public class ProfileViewActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath;
    private Bitmap importedPicture;
    private ImageView catPictureView;
    public static String EXTRA_USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        TextView catName = findViewById(R.id.nameCtextView);
        TextView catSex = findViewById(R.id.sexCtextView);
        TextView catBreed = findViewById(R.id.breedCtextView);
        TextView catAge = findViewById(R.id.agetextView);
        EditText catWeight = findViewById(R.id.weightCeditText);
        catPictureView = findViewById(R.id.pictureimageView);

        Intent openProfileIntent = getIntent();
        int currentCatId = Integer.valueOf(openProfileIntent.getExtras().get(HomeActivity.EXTRA_CAT_ID).toString());

        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase dietDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(dietDatabase);

        Cat currentCat = worker.getCat(currentCatId);

        catName.setText("Name: "+currentCat.getName());
        catSex.setText("Sex: "+currentCat.getSex());
        catBreed.setText("Breed: "+currentCat.getBreed());

        AgeCalculator ac = new AgeCalculator();
        int age = ac.calculateAge(currentCat.getBirthday());
        catAge.setText("Age: "+age);
        catWeight.setText(currentCat.getWeight().toString());
        catPictureView.setImageBitmap(currentCat.getPicture());

        Button updateWeight = findViewById(R.id.updateWeightButton);
        Button changePicture = findViewById(R.id.pictureChangeButton);
        Button selectCat = findViewById(R.id.otherCatButton);

        updateWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double newWeight = Double.parseDouble(catWeight.getText().toString());
                        if(newWeight != currentCat.getWeight()) {
                            worker.updateCatWeight(currentCatId, newWeight);
                        }else{
                            String pictureMessage = "Inserted weight is same as the one before.";
                            Toast weightToast = Toast.makeText(ProfileViewActivity.this, pictureMessage, Toast.LENGTH_LONG);
                            weightToast.show();
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileViewActivity.this);
                builder
                        .setMessage("Are you sure you want to update weight?")
                        .setPositiveButton("Yes", positiveListener)
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        catPictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProfileViewActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 1);
                } else {
                    Intent imageLoadIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageLoadIntent, RESULT_LOAD_IMAGE);
                }
            }
        });

        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    worker.updateCatPicture(currentCatId, importedPicture);
                }catch (NullPointerException e){
                    String pictureMessage = "Please select new picture by pressing on profile picture.";
                    Toast pictureToast = Toast.makeText(ProfileViewActivity.this, pictureMessage, Toast.LENGTH_LONG);
                    pictureToast.show();
                }
            }
        });

        selectCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectCatIntent = new Intent(ProfileViewActivity.this, CatSelectionActivity.class);
                selectCatIntent.putExtra(EXTRA_USER_ID, currentCat.getUserId());
                startActivity(selectCatIntent);
                finish();

            }
        });

            }


    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            importedPicture = BitmapFactory.decodeFile(picturePath);
            cursor.close();

            catPictureView.setImageBitmap(importedPicture);
        }
    }

    @Override
    //Creating a method to ask user for permission to access photos;
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}