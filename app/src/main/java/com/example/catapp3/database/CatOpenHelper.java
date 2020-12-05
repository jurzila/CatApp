package com.example.catapp3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CatOpenHelper extends SQLiteOpenHelper {
   public static final String DATABASE_NAME = "CatApp.db";
   public static final int DATABASE_VERSION = 1;

   public CatOpenHelper(Context context) {
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }
   @Override
   public void onCreate(SQLiteDatabase db) {
       db.execSQL(CatDatabaseContract.FeedingEntry.SQL_CREATE_TABLE);
       db.execSQL(CatDatabaseContract.userTable.SQL_CREATE_TABLE);
       db.execSQL(CatDatabaseContract.catTable.SQL_CREATE_TABLE);
   }
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

   }
}
