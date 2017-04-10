package com.example.root.drawertabtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 3/27/17.
 */

public class HangboardData {
    private SQLiteDatabase mDatabase;
    private HandboardDataDBHelper mDatabaseHelper;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HangboardDataEntry.TABLE_NAME + " ( " +
                    HangboardDataEntry._ID + " INTEGER PRIMARY KEY, " +
                    HangboardDataEntry.COLUMN_NAME + " TEXT, " +
                    HangboardDataEntry.COLUMN_X_DATA + " INTEGER, " +
                    HangboardDataEntry.COLUMN_Y_DATA + " INTEGER)";
    private static final String SQL_DEL_ENTRIES = "DROP TABLE IF EXISTS " + HangboardDataEntry.TABLE_NAME;

    public static class HangboardDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME = "names";
        public static final String COLUMN_X_DATA = "x_data";
        public static final String COLUMN_Y_DATA = "y_data";
    }

    public HangboardData(Context context){
        mDatabaseHelper = new HandboardDataDBHelper(context);
    }

    public void init() throws SQLException{
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }
    public void close(){
        mDatabaseHelper.close();
    }

    private String selectFromSQL = "SELECT * FROM ";

    public void deleteDataByName(String name){
        String[] param = {name};
        mDatabase.delete(HangboardDataEntry.TABLE_NAME, HangboardDataEntry.COLUMN_NAME + " = ?", param);
    }

    public void getHangboardData(String name, int[] x, int[] y) {
        String[] params = {name};
        Cursor c = mDatabase.rawQuery("SELECT " + HangboardDataEntry.COLUMN_X_DATA + ", " + HangboardDataEntry.COLUMN_Y_DATA + " FROM "  + HangboardDataEntry.TABLE_NAME + " WHERE " + HangboardDataEntry.COLUMN_NAME + " = ? ", params);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            x[i] = c.getInt(0);
            y[i] = c.getInt(1);
            c.moveToNext();
        }
    }

    public void getNames(List<String> names){
        //Cursor c = mDatabase.rawQuery("SELECT " + HangboardDataEntry.COLUMN_NAME + " FROM " + HangboardDataEntry.TABLE_NAME, null );
        String[] columns = {HangboardDataEntry.COLUMN_NAME};
        Cursor c = mDatabase.query(true,HangboardDataEntry.TABLE_NAME, columns,null, null, null, null, null, null);
        c.moveToFirst();
        for(int i = 0;i<c.getCount();i++){
            names.add(c.getString(0));
            c.moveToNext();
        }
    }
    public int getHangboardDataSize(){
        Cursor c = mDatabase.rawQuery(selectFromSQL + HangboardDataEntry.TABLE_NAME, null );
        return c.getCount();
    }
    public int getNumberOfNames(){
        //Cursor c = mDatabase.rawQuery("SELECT DISTINCT " + HangboardDataEntry.COLUMN_NAME + " FROM " + HangboardDataEntry.TABLE_NAME, null );
        String[] columns = {HangboardDataEntry.COLUMN_NAME};
        Cursor c = mDatabase.query(true,HangboardDataEntry.TABLE_NAME, columns,null, null, null, null, null, null);
        return c.getCount();
    }

    public void addHangboardData(String name, int x, int y){
        // Gets the data repository in write mode

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HangboardDataEntry.COLUMN_NAME, name);
        values.put(HangboardDataEntry.COLUMN_X_DATA, x);
        values.put(HangboardDataEntry.COLUMN_Y_DATA, y);

// Insert the new row, returning the primary key value of the new row
        long newRowId = mDatabase.insert(HangboardDataEntry.TABLE_NAME, null, values);
    }

    private class HandboardDataDBHelper extends SQLiteOpenHelper{
        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "new.db";

        public HandboardDataDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DEL_ENTRIES);
            onCreate(db);
        }
    }
}
