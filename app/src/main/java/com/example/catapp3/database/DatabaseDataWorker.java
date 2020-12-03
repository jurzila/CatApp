package com.example.catapp3.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.catapp3.model.Feeder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseDataWorker {

   private SQLiteDatabase db;

   public DatabaseDataWorker(SQLiteDatabase db) {
       this.db = db;
   }

   public void insertDiet(Double calories,String date, String time, String whoFed){

       //TODO
       ContentValues values = new ContentValues();

       values.put(DietDatabaseContract.FeedingEntry.COLUMN_DATE, String.valueOf(date));
       values.put(DietDatabaseContract.FeedingEntry.COLUMN_TIME, String.valueOf(time));
       values.put(DietDatabaseContract.FeedingEntry.COLUMN_CALORIES, calories);
       values.put(DietDatabaseContract.FeedingEntry.COLUMN_NAME, whoFed);

       long newRowId = db.insert(DietDatabaseContract.FeedingEntry.TABLE_NAME, null, values);
   }

   public Feeder getLastFeed() {
       String[] columns = {
               DietDatabaseContract.FeedingEntry.COLUMN_DATE,
               DietDatabaseContract.FeedingEntry.COLUMN_TIME,
               DietDatabaseContract.FeedingEntry.COLUMN_CALORIES,
               DietDatabaseContract.FeedingEntry.COLUMN_NAME};
       Cursor cursor = db.query(DietDatabaseContract.FeedingEntry.TABLE_NAME, columns,
               null,
               null,
               null,
               null,
               null);

       cursor.moveToLast();

       int dateIndex = cursor.getColumnIndex(DietDatabaseContract.FeedingEntry.COLUMN_DATE);
       String date = cursor.getString(dateIndex);
       int timeIndex = cursor.getColumnIndex(DietDatabaseContract.FeedingEntry.COLUMN_TIME);
       String time = cursor.getString(timeIndex);
       int calIndex = cursor.getColumnIndex(DietDatabaseContract.FeedingEntry.COLUMN_CALORIES);
       Double cal = cursor.getDouble(calIndex);
       int nameIndex = cursor.getColumnIndex(DietDatabaseContract.FeedingEntry.COLUMN_NAME);
       String whoFed = cursor.getString(nameIndex);

       Feeder lastFeed = new Feeder(cal, date, time, whoFed);

       return  lastFeed;

   }

   /*public List<Feeder> getAllDietEntries() throws ParseException {
       String[] columns = {
               DietDatabaseContract.FeedingEntry.COLUMN_TIME,
               DietDatabaseContract.FeedingEntry.COLUMN_CALORIES,
               DietDatabaseContract.FeedingEntry.COLUMN_NAME};
       Cursor cursor = db.query(DietDatabaseContract.FeedingEntry.TABLE_NAME,
               columns,
               null,
               null,
               null,
               null,
               null);
       List<Feeder> feederList = new ArrayList<>();

       while(cursor.moveToNext()){

           int calIndex = cursor.getColumnIndex(DietDatabaseContract.FeedingEntry.COLUMN_CALORIES);
           Double cal = cursor.getDouble(calIndex);

           int timeIndex = cursor.getColumnIndex(DietDatabaseContract.FeedingEntry.COLUMN_TIME);
           String time = cursor.getString(timeIndex);
           Date time1 = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").parse(time);

           int nameIndex = cursor.getColumnIndex(DietDatabaseContract.FeedingEntry.COLUMN_NAME);
           String whoFed = cursor.getString(timeIndex);

           feederList.add(new Feeder(cal, time1, whoFed));
       }

       return feederList;

   }*/

}
