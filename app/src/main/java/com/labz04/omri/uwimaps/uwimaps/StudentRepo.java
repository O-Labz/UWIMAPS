package com.labz04.omri.uwimaps.uwimaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.labz04.omri.uwimaps.database.DBHelper;

/**
 * Created by Tan on 3/14/2016.
 */


public class StudentRepo {
    private DBHelper dbHelper;




    public StudentRepo(Context context) {
        dbHelper = new DBHelper(context);
    }


    public int insert(Student student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_long, student.longitude);
        values.put(Student.KEY_lat,student.latitude);
        values.put(Student.KEY_name, student.name);

        // Inserting Row
        long student_Id = db.insert(Student.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }
    public Cursor  getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  rowid as " +
                Student.KEY_ROWID + "," +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_lat + "," +
                Student.KEY_long +
                " FROM " + Student.TABLE;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }


    public Cursor  getStudentListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  rowid as " +
                Student.KEY_ROWID + "," +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_lat + "," +
                Student.KEY_long +
                " FROM " + Student.TABLE +
                " WHERE " +  Student.KEY_name + "  LIKE  '%" +search + "%' "
                ;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }

    public Student getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT " +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_lat + "," +
                Student.KEY_long +
                " FROM " + Student.TABLE
                + " WHERE " +
                Student.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Student student = new Student();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student.student_ID =cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(Student.KEY_name));
                student.latitude  =cursor.getDouble(cursor.getColumnIndex(Student.KEY_lat));
                student.longitude =cursor.getDouble(cursor.getColumnIndex(Student.KEY_long));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }




}