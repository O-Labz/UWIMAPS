package com.labz04.omri.uwimaps.uwimaps;

/**
 * Created by Tan on 3/14/2016.
 */
public class Student {
    // Labels table name
    public static final String TABLE = "Student";

    // Labels Table Columns names
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_lat = "latitude";
    public static final String KEY_long = "longitude";

    // property help us to keep data
    public int student_ID;
    public String name;
    public double latitude;
    public double longitude;
}