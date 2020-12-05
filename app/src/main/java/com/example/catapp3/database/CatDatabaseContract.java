package com.example.catapp3.database;

public class CatDatabaseContract {

    private CatDatabaseContract() {

    }

    public static final class FeedingEntry {
        public static String TABLE_NAME = "Diet";
        //public static String COLUMN_CATID= "CatId";
        public static String COLUMN_DATE= "Date";
        public static String COLUMN_TIME = "Time";
        public static String COLUMN_CALORIES = "CaloriesFed";
        public static String COLUMN_NAME = "Feeder";
        //...
        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                //COLUMN_CATID + " INTEGER PRIMARY KEY NOT NULL, " +
                COLUMN_DATE + " TIMESTAMP, " +
                COLUMN_TIME + " TIMESTAMP, " +
                COLUMN_CALORIES + " FLOAT, " +
                COLUMN_NAME + " Text " +
                "); ";
    }

    public static final class catTable{
        public static String TABLE_NAME = "Cats";
        public static String COLUMN_USER_ID= "UserId";
        public static String COLUMN_CATID= "CatId";
        public static String COLUMN_NAME = "Name";
        public static String COLUMN_SEX = "Sex";
        public static String COLUMN_BREED = "Breed";
        public static String COLUMN_DOB = "DateOfBirth";
        public static String COLUMN_WEIGHT = "Weight";
        //...
        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_USER_ID + " INTEGER NOT NULL, " +
                COLUMN_CATID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_SEX + " TEXT NOT NULL, " +
                COLUMN_BREED + " TEXT NOT NULL, " +
                COLUMN_DOB + " TEXT NOT NULL, " +
                COLUMN_WEIGHT + " FLOAT " +
                "); ";
    }

    public static final class userTable{
        public static String TABLE_NAME = "Users";
        public static String COLUMN_ID= "ID";
        public static String COLUMN_USERNAME = "Username";
        public static String COLUMN_EMAIL = "Email";
        public static String COLUMN_PASSWORD = "Password";
        //...
        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
                COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COLUMN_PASSWORD + " TEXT NOT NULL " +
                "); ";
    }


}
