package com.labz04.omri.uwimaps.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by Omri on 6/16/2015.
 */
public class LocationDatabase extends SQLiteOpenHelper
{
    //Declearing Database Variables
    public static final String DATABASE_NAME = "location.db";
    public static final String TABLE_NAME = "location_table";
    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "NODE";
    public static final String COLUMN_3 = "LATITUDE";
    public static final String COLUMN_4 = "LONGITUDE";

    public LocationDatabase(Context context)
    {
        //Creates database
        super(context, DATABASE_NAME, null, 10);
        //Create Database and table
        SQLiteDatabase adb = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase adb)
    {
        //Creates Database Table
        adb.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NODE TEXT,LATITUDE REAL,LONGITUDE REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase adb, int oldVersion, int newVersion)
    {
        adb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(adb);
    }

    public boolean putInData(String node, double latitude, double longitude)
    {
        SQLiteDatabase adb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2, node);
        contentValues.put(COLUMN_3, latitude);
        contentValues.put(COLUMN_4, longitude);
        long result = adb.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAll_Data()
    {
        SQLiteDatabase adb = this.getWritableDatabase();
        Cursor rez = adb.rawQuery("select * from " + TABLE_NAME, null);
        return rez;

    }

    public void deleteAll_Data()
    {
        SQLiteDatabase adb = this.getWritableDatabase();
        adb.execSQL("DELETE FROM " + TABLE_NAME);
        adb.close();

    }

    public Cursor get_Data(LocationDatabase doh)
    {
        SQLiteDatabase adb = doh.getReadableDatabase();
        String columns [] = {COLUMN_2,COLUMN_3,COLUMN_4};
        Cursor cr = adb.query(TABLE_NAME, columns, null, null, null, null, null);
        return cr;
    }

}
