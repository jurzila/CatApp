package com.example.catapp3.database;

public class DietDatabaseContract {

    private DietDatabaseContract() {

    }

    public static final class FeedingEntry {
        public static String TABLE_NAME = "Diet";
        public static String COLUMN_TIME = "Time";
        public static String COLUMN_CALORIES = "CaloriesFed";
        public static String COLUMN_NAME = "Feeder";
        //...
        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_TIME + " TIMESTAMP, " +
                COLUMN_CALORIES + " FLOAT, " +
                COLUMN_NAME + " Text " +
                "); ";
    }


}
