package com.example.catapp3.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.catapp3.model.Cat;
import com.example.catapp3.model.Feeder;
import com.example.catapp3.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

public class DatabaseDataWorker {

   private SQLiteDatabase db;
    private User currentUser;
    private byte[] imageBytes;
    private Cat currentCatProf;

    public DatabaseDataWorker(SQLiteDatabase db) {
       this.db = db;
   }

   public void insertDiet(int catId, Double calories,String date, String time, String whoFed){

       //TODO: Isert cat picture into database as well.
       ContentValues values = new ContentValues();

       values.put(CatDatabaseContract.FeedingEntry.COLUMN_CATID, catId);
       values.put(CatDatabaseContract.FeedingEntry.COLUMN_DATE, String.valueOf(date));
       values.put(CatDatabaseContract.FeedingEntry.COLUMN_TIME, String.valueOf(time));
       values.put(CatDatabaseContract.FeedingEntry.COLUMN_CALORIES, calories);
       values.put(CatDatabaseContract.FeedingEntry.COLUMN_NAME, whoFed);

       db.insert(CatDatabaseContract.FeedingEntry.TABLE_NAME, null, values);
   }

    public int insertUser(String username,String email, String password){
        int result = 0;
        if(!isUsernameExist(username) && !isEmailExist(email)){
        ContentValues values = new ContentValues();

        values.put(CatDatabaseContract.userTable.COLUMN_USERNAME, username);
        values.put(CatDatabaseContract.userTable.COLUMN_EMAIL, email);
        values.put(CatDatabaseContract.userTable.COLUMN_PASSWORD, password);

        db.insert(CatDatabaseContract.userTable.TABLE_NAME, null, values);
            result = 0;
        }else if(isUsernameExist(username) && isEmailExist(email)){
            result = 3;
        }else if(isUsernameExist(username)){
            result = 1;
        }else if (isEmailExist(email)){
            result = 2;
        }
        return result;
    }

    public void insertCat(Integer id, String name,String sex, String breed, String dateOfBirth, Double weight, String path){

        ContentValues values = new ContentValues();

        values.put(CatDatabaseContract.catTable.COLUMN_USER_ID, id);
        values.put(CatDatabaseContract.catTable.COLUMN_NAME, name);
        values.put(CatDatabaseContract.catTable.COLUMN_SEX, sex);
        values.put(CatDatabaseContract.catTable.COLUMN_BREED, breed);
        values.put(CatDatabaseContract.catTable.COLUMN_DOB, String.valueOf(dateOfBirth));
        values.put(CatDatabaseContract.catTable.COLUMN_WEIGHT, weight);
        values.put(CatDatabaseContract.catTable.COLUMN_IMAGE_DATA, readFile(path));

        db.insert(CatDatabaseContract.catTable.TABLE_NAME, null, values);
    }

    public int getUserID(String username){

        int userId = 0;
        String whereClause = "Username=?";
        String[] whereArgs = new String[]{String.valueOf(username)};
        String[] columns = {
                CatDatabaseContract.userTable.COLUMN_ID,
                CatDatabaseContract.userTable.COLUMN_USERNAME,
                CatDatabaseContract.userTable.COLUMN_EMAIL,
                CatDatabaseContract.userTable.COLUMN_PASSWORD
        };
        Cursor csr = db.query(CatDatabaseContract.userTable.TABLE_NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        while(csr.moveToNext()) {
            int IdIndex = csr.getColumnIndex(CatDatabaseContract.userTable.COLUMN_ID);
            userId = csr.getInt(IdIndex);
        }
        return userId;
    }

    public User getUserPassword(String givenUsername){

        String whereClause = "Username=?";
        String[] whereArgs = new String[]{String.valueOf(givenUsername)};
        String[] columns = {
                CatDatabaseContract.userTable.COLUMN_ID,
                CatDatabaseContract.userTable.COLUMN_USERNAME,
                CatDatabaseContract.userTable.COLUMN_EMAIL,
                CatDatabaseContract.userTable.COLUMN_PASSWORD
        };
        Cursor csr = db.query(CatDatabaseContract.userTable.TABLE_NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        while(csr.moveToNext()) {

            int idIndex = csr.getColumnIndex(CatDatabaseContract.userTable.COLUMN_ID);
            int id = csr.getInt(idIndex);
            int passIndex = csr.getColumnIndex(CatDatabaseContract.userTable.COLUMN_PASSWORD);
            String password = csr.getString(passIndex);
            int emailIndex = csr.getColumnIndex(CatDatabaseContract.userTable.COLUMN_EMAIL);
            String email = csr.getString(emailIndex);
            int usernameIndex = csr.getColumnIndex(CatDatabaseContract.userTable.COLUMN_USERNAME);
            String username = csr.getString(usernameIndex);

            currentUser = new User(id, username, password, email);


        }

        return currentUser;

    }


    public int getCatID(int userId){

        int catId = 0;
        String whereClause = "UserId=?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        String[] columns = {
                CatDatabaseContract.catTable.COLUMN_USER_ID,
                CatDatabaseContract.catTable.COLUMN_CATID,
                CatDatabaseContract.catTable.COLUMN_NAME,
                CatDatabaseContract.catTable.COLUMN_SEX,
                CatDatabaseContract.catTable.COLUMN_BREED,
                CatDatabaseContract.catTable.COLUMN_DOB,
                CatDatabaseContract.catTable.COLUMN_WEIGHT,
                CatDatabaseContract.catTable.COLUMN_IMAGE_DATA,
        };
        Cursor csr = db.query(CatDatabaseContract.catTable.TABLE_NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        if (csr.moveToFirst()) {
            int IdIndex = csr.getColumnIndex(CatDatabaseContract.catTable.COLUMN_CATID);
            catId = csr.getInt(IdIndex);
        }
        return catId;
    }

    public Cat getCat(int currentCat){
        String whereClause = "CatId=?";
        String[] whereArgs = new String[]{String.valueOf(currentCat)};

        String[] columns = {
                CatDatabaseContract.catTable.COLUMN_USER_ID,
                CatDatabaseContract.catTable.COLUMN_CATID,
                CatDatabaseContract.catTable.COLUMN_NAME,
                CatDatabaseContract.catTable.COLUMN_SEX,
                CatDatabaseContract.catTable.COLUMN_BREED,
                CatDatabaseContract.catTable.COLUMN_DOB,
                CatDatabaseContract.catTable.COLUMN_WEIGHT,
                CatDatabaseContract.catTable.COLUMN_IMAGE_DATA,
        };
        Cursor csr = db.query(CatDatabaseContract.catTable.TABLE_NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        if (csr.moveToFirst()) {

            int userIdIndex = csr.getColumnIndex(CatDatabaseContract.catTable.COLUMN_USER_ID);
            int userId = csr.getInt(userIdIndex);
            //int catIdIndex = csr.getColumnIndex(CatDatabaseContract.catTable.COLUMN_CATID);
            //int catId = csr.getInt(catIdIndex);
            int nameIndex = csr.getColumnIndex(CatDatabaseContract.catTable.COLUMN_NAME);
            String name = csr.getString(nameIndex);
            int dateIndex = csr.getColumnIndex(CatDatabaseContract.catTable.COLUMN_DOB);
            String date = csr.getString(dateIndex);
            int imgDataIndex = csr.getColumnIndex(CatDatabaseContract.catTable.COLUMN_IMAGE_DATA);
            imageBytes = csr.getBlob(imgDataIndex);
            Bitmap catPicture = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

            currentCatProf = new Cat(name, date, userId, catPicture);

        }


        return currentCatProf;

    }

    public Feeder getLastFeed(int currentCat) {

        String whereClause = "CatId=?";
        String[] whereArgs = new String[]{String.valueOf(currentCat)};

        String[] columns = {
                CatDatabaseContract.FeedingEntry.COLUMN_CATID,
                CatDatabaseContract.FeedingEntry.COLUMN_DATE,
                CatDatabaseContract.FeedingEntry.COLUMN_TIME,
                CatDatabaseContract.FeedingEntry.COLUMN_CALORIES,
                CatDatabaseContract.FeedingEntry.COLUMN_NAME};
        Cursor cursor = db.query(CatDatabaseContract.FeedingEntry.TABLE_NAME, columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        cursor.moveToLast();

        int dateIndex = cursor.getColumnIndex(CatDatabaseContract.FeedingEntry.COLUMN_DATE);
        String date = cursor.getString(dateIndex);
        int timeIndex = cursor.getColumnIndex(CatDatabaseContract.FeedingEntry.COLUMN_TIME);
        String time = cursor.getString(timeIndex);
        int calIndex = cursor.getColumnIndex(CatDatabaseContract.FeedingEntry.COLUMN_CALORIES);
        Double cal = cursor.getDouble(calIndex);
        int nameIndex = cursor.getColumnIndex(CatDatabaseContract.FeedingEntry.COLUMN_NAME);
        String whoFed = cursor.getString(nameIndex);

       /* int imgDataIndex = cursor.getString("ImageData");
        byte[] imageBytes =  cursor.getBlob(imgDataIndex);*/
        Feeder lastFeed = new Feeder(cal, date, time, whoFed);

        return lastFeed;
    }

    private boolean isUsernameExist(String value){

        String whereClause = "Username=?";
        String[] whereArgs = new String[]{String.valueOf(value)};

        String[] columns = {
                CatDatabaseContract.userTable.COLUMN_ID,
                CatDatabaseContract.userTable.COLUMN_USERNAME,
                CatDatabaseContract.userTable.COLUMN_EMAIL,
                CatDatabaseContract.userTable.COLUMN_PASSWORD};
        Cursor cursor = db.query(CatDatabaseContract.userTable.TABLE_NAME, columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        int count = cursor.getCount();
        cursor.close();
        return count >= 1;

    }

    private boolean isEmailExist(String value){

        String whereClause = "Email=?";
        String[] whereArgs = new String[]{String.valueOf(value)};

        String[] columns = {
                CatDatabaseContract.userTable.COLUMN_ID,
                CatDatabaseContract.userTable.COLUMN_USERNAME,
                CatDatabaseContract.userTable.COLUMN_EMAIL,
                CatDatabaseContract.userTable.COLUMN_PASSWORD};
        Cursor cursor = db.query(CatDatabaseContract.userTable.TABLE_NAME, columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        int count = cursor.getCount();
        cursor.close();

        return count >= 1;

    }

    private boolean isCatCreated(int userId){
        String whereClause = "UserId=?";
        String[] whereArgs = new String[]{String.valueOf(userId)};

        String[] columns = {
                CatDatabaseContract.catTable.COLUMN_USER_ID,
                CatDatabaseContract.catTable.COLUMN_CATID,
                CatDatabaseContract.catTable.COLUMN_NAME,
                CatDatabaseContract.catTable.COLUMN_SEX,
                CatDatabaseContract.catTable.COLUMN_BREED,
                CatDatabaseContract.catTable.COLUMN_DOB,
                CatDatabaseContract.catTable.COLUMN_WEIGHT};
        Cursor cursor = db.query(CatDatabaseContract.catTable.TABLE_NAME, columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        int count = cursor.getCount();
        cursor.close();

        return count >= 1;
    }

    private byte[] readFile(String path) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return bos != null ? bos.toByteArray() : null;
    }



    /* public User getUser(String username){



    }*/

   /*public List<Feeder> getAllDietEntries() throws ParseException {
       String[] columns = {
               CatDatabaseContract.FeedingEntry.COLUMN_TIME,
               CatDatabaseContract.FeedingEntry.COLUMN_CALORIES,
               CatDatabaseContract.FeedingEntry.COLUMN_NAME};
       Cursor cursor = db.query(CatDatabaseContract.FeedingEntry.TABLE_NAME,
               columns,
               null,
               null,
               null,
               null,
               null);
       List<Feeder> feederList = new ArrayList<>();

       while(cursor.moveToNext()){

           int calIndex = cursor.getColumnIndex(CatDatabaseContract.FeedingEntry.COLUMN_CALORIES);
           Double cal = cursor.getDouble(calIndex);

           int timeIndex = cursor.getColumnIndex(CatDatabaseContract.FeedingEntry.COLUMN_TIME);
           String time = cursor.getString(timeIndex);
           Date time1 = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").parse(time);

           int nameIndex = cursor.getColumnIndex(CatDatabaseContract.FeedingEntry.COLUMN_NAME);
           String whoFed = cursor.getString(timeIndex);

           feederList.add(new Feeder(cal, time1, whoFed));
       }

       return feederList;

   }*/

}
