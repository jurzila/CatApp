package com.example.catapp3.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.catapp3.model.Feeder;
import com.example.catapp3.model.User;

import java.util.Date;

public class DatabaseDataWorker {

   private SQLiteDatabase db;

   public DatabaseDataWorker(SQLiteDatabase db) {
       this.db = db;
   }

   public void insertDiet(Double calories,String date, String time, String whoFed){

       //TODO
       ContentValues values = new ContentValues();

       values.put(CatDatabaseContract.FeedingEntry.COLUMN_DATE, String.valueOf(date));
       values.put(CatDatabaseContract.FeedingEntry.COLUMN_TIME, String.valueOf(time));
       values.put(CatDatabaseContract.FeedingEntry.COLUMN_CALORIES, calories);
       values.put(CatDatabaseContract.FeedingEntry.COLUMN_NAME, whoFed);

       db.insert(CatDatabaseContract.FeedingEntry.TABLE_NAME, null, values);
   }

    public void insertUser(String username,String email, String password){

        ContentValues values = new ContentValues();

        values.put(CatDatabaseContract.userTable.COLUMN_USERNAME, username);
        values.put(CatDatabaseContract.userTable.COLUMN_EMAIL, email);
        values.put(CatDatabaseContract.userTable.COLUMN_PASSWORD, password);

        db.insert(CatDatabaseContract.userTable.TABLE_NAME, null, values);
    }

    public void insertCat(Integer id, String name,String sex, String breed, String dateOfBirth, Double weight){

        ContentValues values = new ContentValues();

        values.put(CatDatabaseContract.catTable.COLUMN_USER_ID, id);
        values.put(CatDatabaseContract.catTable.COLUMN_NAME, name);
        values.put(CatDatabaseContract.catTable.COLUMN_SEX, sex);
        values.put(CatDatabaseContract.catTable.COLUMN_BREED, breed);
        values.put(CatDatabaseContract.catTable.COLUMN_DOB, String.valueOf(dateOfBirth));
        values.put(CatDatabaseContract.catTable.COLUMN_WEIGHT, weight);

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

        if (csr.moveToFirst()) {
            int IdIndex = csr.getColumnIndex(CatDatabaseContract.userTable.COLUMN_ID);
            userId = csr.getInt(IdIndex);
        }
        return userId;
    }

    public int getCatID(int userId){

        int catId = 0;
        String whereClause = "UserId=?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        String[] columns = {
                CatDatabaseContract.catTable.COLUMN_USER_ID,
                //CatDatabaseContract.catTable.COLUMN_CATID,
                CatDatabaseContract.catTable.COLUMN_NAME,
                CatDatabaseContract.catTable.COLUMN_SEX,
                CatDatabaseContract.catTable.COLUMN_BREED,
                CatDatabaseContract.catTable.COLUMN_DOB,
                CatDatabaseContract.catTable.COLUMN_WEIGHT,
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




    public Feeder getLastFeed() {
        String[] columns = {
                CatDatabaseContract.FeedingEntry.COLUMN_DATE,
                CatDatabaseContract.FeedingEntry.COLUMN_TIME,
                CatDatabaseContract.FeedingEntry.COLUMN_CALORIES,
                CatDatabaseContract.FeedingEntry.COLUMN_NAME};
        Cursor cursor = db.query(CatDatabaseContract.FeedingEntry.TABLE_NAME, columns,
                null,
                null,
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

        Feeder lastFeed = new Feeder(cal, date, time, whoFed);

        return lastFeed;
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
