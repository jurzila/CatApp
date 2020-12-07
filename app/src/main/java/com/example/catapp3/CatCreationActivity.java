package com.example.catapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import android.widget.Toast;

import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.model.Cat;

import java.util.Date;

public class CatCreationActivity extends AppCompatActivity {

    private Date dateOfBirth;
    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView catImageView;
    private String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_creation);

        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase catDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(catDatabase);

        final EditText nameEditText = findViewById(R.id.catNameBox);
        final EditText sexEditText = findViewById(R.id.catSexBox);
        final EditText breedEditText = findViewById(R.id.catBreedBox);
        final EditText dateOfBirthEditText = findViewById(R.id.dateOfBirthBox);
        final EditText weightEditText = findViewById(R.id.weightBox);
        final Button createButton = findViewById(R.id.createCatButton);
        catImageView = findViewById(R.id.catProfilePicture);
        Intent intent1 = getIntent();
        Intent intent2 = new Intent(CatCreationActivity.this, Login1Activity.class);
        String username = intent1.getExtras().get(UserCreationActivity.EXTRA_USERNAME).toString();

        Button buttonLoadCatImage = findViewById(R.id.catPictureButton);
        buttonLoadCatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //checking if permission is granted to app to access photos
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CatCreationActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 1);
                } else {
                    Intent imageLoadIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(imageLoadIntent, RESULT_LOAD_IMAGE);
                }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == createButton) {
                    boolean error = false;
                    if (isEmpty(nameEditText)) {
                        error = true;
                        nameEditText.setError("Fill in name");
                    }
                    if (isEmpty(sexEditText)) {
                        error = true;
                        sexEditText.setError("Fill in sex");
                    }
                    if (isEmpty(breedEditText)) {
                        error = true;
                        breedEditText.setError("Fill in breed");
                    }
                    if (isEmpty(dateOfBirthEditText)) {
                        error = true;
                        dateOfBirthEditText.setError("Fill in date of birth");
                    }
                    if (!error) {

                        String name = nameEditText.getText().toString();
                        String sex = sexEditText.getText().toString();
                        String breed = breedEditText.getText().toString();
                    /*try {
                        dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthEditText.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                        String dateOfBirth = dateOfBirthEditText.getText().toString();
                        Double weight = Double.parseDouble(weightEditText.getText().toString());
                        int userId = worker.getUserID(username);
                        Cat newCat = new Cat(name, sex, breed, dateOfBirth, weight, userId);
                        worker.insertCat(userId, name, sex, breed, dateOfBirth, weight, picturePath);
                        //int catId = worker.getCatID(userId);

                        startActivity(intent2);
                    }
                }
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
                cursor.close();

                catImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

               // TODO: load image view from byte array
            }
        }

    @Override
    //Creating a method to ask user for permission to access photos;
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

            }
            else{
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isEmpty(EditText etText){
        return etText.getText().toString().trim().length() == 0;
    }

    }