/***********************************************
 *
 *    This is the helper class (it extends SQLliteopenhelper)
 *    It is responsible for creating the database
 *    Note that there is no direct call to the "onCreate" method
 *    that contains the dB creation code.  This method is run by the system
 *    when a request is made to access the database, and if the database doesn't
 *    exist yet.
 *    DEC 2022
 *
 *************************************************/

package com.example.librarymanagementsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "libDb";

    // USERS TABLE
    public static final String TABLE_USERS = "users";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_ADMIN = "admin";

    // BOOKS TABLE
    public static final String TABLE_BOOKS = "books";
    public static final String KEY_ISBN = "isbn";
    public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_EDITION = "edition";
    public static final String KEY_YEAR = "year";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_RESERVED = "reserved";

    // RESERVATIONS TABLE
    public static final String TABLE_RESERVATION = "reservations";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "("
                + KEY_USERNAME + " TEXT PRIMARY KEY,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_FIRSTNAME + " TEXT,"
                + KEY_SURNAME + " TEXT,"
                + KEY_MOBILE + " INTEGER,"
                + KEY_ADMIN + " INTEGER"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_BOOKS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKS + "("
                + KEY_ISBN + " TEXT PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_AUTHOR + " TEXT,"
                + KEY_EDITION + " INTEGER,"
                + KEY_YEAR + " INTEGER,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_RESERVED + " INTEGER"
                + ")";
        db.execSQL(CREATE_BOOKS_TABLE);

        String CREATE_RESERVATIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_RESERVATION + "("
                + KEY_ISBN + " TEXT,"
                + KEY_USERNAME + " TEXT,"
                + "PRIMARY KEY (" + KEY_ISBN + ", " + KEY_USERNAME + ")"
                + ")";
        db.execSQL(CREATE_RESERVATIONS_TABLE);
    }

    // update database structure
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATION);

        // Create tables again
        onCreate(db);
    }
}