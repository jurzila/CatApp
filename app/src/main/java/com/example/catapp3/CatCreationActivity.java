package com.example.catapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.catapp3.database.CatOpenHelper;
import com.example.catapp3.database.DatabaseDataWorker;
import com.example.catapp3.model.Cat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CatCreationActivity extends AppCompatActivity {

    public static String EXTRA_CAT_ID;
    private Date dateOfBirth;
    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView catImageView;
    private String picturePath;
    private Bitmap importedPicture;
    private EditText dateOfBirthEditText;
    private Calendar myCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_creation);

        CatOpenHelper helper = new CatOpenHelper(this);
        final SQLiteDatabase catDatabase = helper.getReadableDatabase();
        final DatabaseDataWorker worker = new DatabaseDataWorker(catDatabase);

        final EditText nameEditText = findViewById(R.id.catNameBox);

        Spinner sexSpinner = findViewById(R.id.sexSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sex_arrays, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        final EditText breedEditText = findViewById(R.id.catBreedBox);
        dateOfBirthEditText = findViewById(R.id.dateOfBirthBox);

        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateOfBirthEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CatCreationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final EditText weightEditText = findViewById(R.id.weightBox);
        final Button createButton = findViewById(R.id.createCatButton);
        catImageView = findViewById(R.id.catProfilePicture);
        Intent intentLogin = getIntent();
        Intent intentCreateNewCat = new Intent(CatCreationActivity.this, HomeActivity.class);
        int userId = Integer.valueOf(intentLogin.getExtras().get(LoginActivity.EXTRA_USER_ID).toString());

        Bitmap importedPicture = BitmapFactory.decodeResource(getResources(),R.drawable.profile_default);
        catImageView.setImageBitmap(importedPicture);

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
                        String sex = sexSpinner.getSelectedItem().toString();
                        String breed = breedEditText.getText().toString();
                    /*try {
                        dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthEditText.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                        String dateOfBirth = dateOfBirthEditText.getText().toString();
                        Double weight = Double.parseDouble(weightEditText.getText().toString());
                        worker.insertCatBtm(userId, name, sex, breed, dateOfBirth, weight, importedPicture);
                        int catId = worker.getCatIDMultipleCats(userId, name);
                        intentCreateNewCat.putExtra(EXTRA_CAT_ID, catId);
                        startActivity(intentCreateNewCat);
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
                importedPicture = BitmapFactory.decodeFile(picturePath);
                cursor.close();

                catImageView.setImageBitmap(importedPicture);

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
    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dateOfBirthEditText.setText(sdf.format(myCalendar.getTime()));
    }


    private boolean isEmpty(EditText etText){
        return etText.getText().toString().trim().length() == 0;
    }

    }