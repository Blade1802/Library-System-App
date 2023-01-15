/*****************************************************************************************
 *
 *     This is a custom class to store CRUD operations for library database
 *     Note we've  added an open() method to instantiate the helper class, and make a call to
 *     use the database (getWriteableDatabase).
 *     Dec 2022
 *
 *******************************************************************************************/
package com.example.librarymanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import static com.example.librarymanagementsystem.MyDatabaseHelper.TABLE_USERS;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_USERNAME;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_PASSWORD;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_FIRSTNAME;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_SURNAME;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_MOBILE;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_ADMIN;

import static com.example.librarymanagementsystem.MyDatabaseHelper.TABLE_BOOKS;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_ISBN;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_TITLE;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_AUTHOR;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_EDITION;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_YEAR;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_CATEGORY;
import static com.example.librarymanagementsystem.MyDatabaseHelper.KEY_RESERVED;

import static com.example.librarymanagementsystem.MyDatabaseHelper.TABLE_RESERVATION;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseManager
{
    Context context;
    private MyDatabaseHelper LibrarydbHelper;
    private SQLiteDatabase database;


    public MyDatabaseManager(Context context)
    {
        this.context = context;
    }

    public MyDatabaseManager open() throws SQLException {
        LibrarydbHelper = new MyDatabaseHelper(context);
        database = LibrarydbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        LibrarydbHelper.close();
    }

    // ----------------------------------- USER CRUD OPERATIONS -----------------------------------

    // Add a new user
    void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.get_username());  // Username
        values.put(KEY_PASSWORD, user.get_password());  // Password
        values.put(KEY_FIRSTNAME, user.get_firstname()); // Firstname
        values.put(KEY_SURNAME, user.get_surname());   // Surname
        values.put(KEY_MOBILE, user.get_mobile());    // Mobile
        values.put(KEY_ADMIN, user.get_admin());     // Admin

        // Inserting Row
        database.insert(TABLE_USERS, null, values);
    }

    // Get a particular user
    public User getUser(String username) {
        String countQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USERNAME + "=?";

        Cursor cursor = database.rawQuery(countQuery, new String[] { username});

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getInt(4), cursor.getInt(5));

        cursor.close();
        return user;
    }

    // Checks if the user exists
    public boolean userExists(String username, String password) {
        String countQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USERNAME + "=? AND "
                + KEY_PASSWORD + "=?";

        Cursor cursor = database.rawQuery(countQuery, new String[] { username, password });

        // return true if the user exists, else false
        return (cursor.getCount() == 1);
    }

    // code to get number of users
    public int getUserCount() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        Cursor cursor = database.rawQuery(selectQuery, null);

        return cursor.getCount();
    }

    // ---------------------------------- BOOKS CRUD OPERATIONS -----------------------------------

    // Add a new book
    void addBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(KEY_ISBN, book.get_isbn());          // ISBN
        values.put(KEY_TITLE, book.get_title());        // Title
        values.put(KEY_AUTHOR, book.get_author());      // Author
        values.put(KEY_EDITION, book.get_edition());    // Edition
        values.put(KEY_YEAR, book.get_year());          // Year
        values.put(KEY_CATEGORY, book.get_category());  // Category
        values.put(KEY_RESERVED, book.get_reserved());  // Reserved

        // Inserting Row
        database.insert(TABLE_BOOKS, null, values);
    }

    // Update a book
    void updateBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(KEY_ISBN, book.get_isbn());          // ISBN
        values.put(KEY_TITLE, book.get_title());        // Title
        values.put(KEY_AUTHOR, book.get_author());      // Author
        values.put(KEY_EDITION, book.get_edition());    // Edition
        values.put(KEY_YEAR, book.get_year());          // Year
        values.put(KEY_CATEGORY, book.get_category());  // Category
        values.put(KEY_RESERVED, book.get_reserved());  // Reserved

        database.update(TABLE_BOOKS, values, KEY_ISBN + " = ?",
                new String[] { book.get_isbn() });
    }

    // Reserve a book
    void reserveBook(String isbn) {
        ContentValues values = new ContentValues();
        values.put(KEY_RESERVED, 1);

        database.update(TABLE_BOOKS, values, KEY_ISBN + " = ?", new String[] { isbn });
    }

    // Unreserve a book
    void unreserveBook(String isbn) {
        ContentValues values = new ContentValues();
        values.put(KEY_RESERVED, 0);

        database.update(TABLE_BOOKS, values, KEY_ISBN + " = ?", new String[] { isbn });
    }

    // Get list of books
    public Cursor getBooksbyISBN(String isbn, int reserved) {

        String selectQuery = "";
        Cursor cursor = null;

        if (reserved == 0) {
            selectQuery = "SELECT isbn AS _id, * FROM " + TABLE_BOOKS + " WHERE " + KEY_ISBN + " = ?";
            cursor = database.rawQuery(selectQuery, new String[] { isbn } );
        }
        else {
            selectQuery = "SELECT isbn AS _id, * FROM " + TABLE_BOOKS + " WHERE " + KEY_ISBN +
                    " =? AND " + KEY_RESERVED + " =?";
            cursor = database.rawQuery(selectQuery,
                    new String[] { isbn, Integer.toString(0) } );
        }

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public Cursor getBooksbyTitle(String title, int reserved) {

        String selectQuery = "";
        Cursor cursor = null;
        title = "%" + title + "%";

        if (reserved == 0) {
            selectQuery = "SELECT isbn AS _id, * FROM " + TABLE_BOOKS + " WHERE " + KEY_TITLE + " LIKE ?";
            cursor = database.rawQuery(selectQuery, new String[] { title } );
        }
        else {
            selectQuery = "SELECT isbn AS _id, * FROM " + TABLE_BOOKS + " WHERE " + KEY_TITLE +
                    " LIKE ? AND " + KEY_RESERVED + " =?";
            cursor = database.rawQuery(selectQuery,
                    new String[] { title, Integer.toString(0) } );
        }

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public Cursor getBooksbyCategory(String category, int reserved) {

        String selectQuery = "";
        Cursor cursor = null;

        if (reserved == 0) {
            selectQuery = "SELECT isbn AS _id, * FROM " + TABLE_BOOKS + " WHERE " + KEY_CATEGORY + " = ?";
            cursor = database.rawQuery(selectQuery, new String[] { category } );
        }
        else {
            selectQuery = "SELECT isbn AS _id, * FROM " + TABLE_BOOKS + " WHERE " + KEY_CATEGORY +
                    " =? AND " + KEY_RESERVED + " =?";
            cursor = database.rawQuery(selectQuery,
                    new String[] { category, Integer.toString(0) } );
        }

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public Cursor getBooksbyTitleAndCategory(String title, String category, int reserved) {

        String selectQuery = "";
        Cursor cursor = null;
        title = "%" + title + "%";

        if (reserved == 0) {
            selectQuery = "SELECT isbn AS _id, * FROM " + TABLE_BOOKS + " WHERE " + KEY_TITLE
                    + " LIKE ? AND " + KEY_CATEGORY + " = ?";
            cursor = database.rawQuery(selectQuery, new String[] { title, category } );
        }
        else {
            selectQuery = "SELECT isbn AS _id, * FROM " + TABLE_BOOKS + " WHERE " + KEY_TITLE
                    + " LIKE ? AND " + KEY_CATEGORY + " =? AND " + KEY_RESERVED + " =?";
            cursor = database.rawQuery(selectQuery,
                    new String[] {title, category, Integer.toString(0)});
        }

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    // code to get number of books
    public int getBookCount() {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_BOOKS;

        Cursor cursor = database.rawQuery(selectQuery, null);

        return cursor.getCount();
    }

    // Get all distinct categories in the database
    public List<String> getCategories() {

        List<String> categories = new ArrayList<String>();

        String selectQuery = "SELECT DISTINCT " + KEY_CATEGORY + " FROM " + TABLE_BOOKS;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        do {
            categories.add(cursor.getString(0));
        } while (cursor.moveToNext());

        cursor.close();
        return categories;
    }

    // ------------------------------- RESERVATIONS CRUD OPERATIONS -------------------------------

    // Add a new reservation
    void addReservation(String user, String isbn) {
        ContentValues values = new ContentValues();
        values.put(KEY_ISBN, isbn);      // ISBN
        values.put(KEY_USERNAME, user);  // Username

        // Inserting Row
        long i = database.insert(TABLE_RESERVATION, null, values);
        Log.d("add Reserve:", String.valueOf(i));

        reserveBook(isbn);
    }

    void removeReservation(String user, String isbn) {
        // Deleting Row
        database.delete(TABLE_RESERVATION, KEY_ISBN + "=? AND "
                + KEY_USERNAME + "=?", new String[] { isbn, user });

        unreserveBook(isbn);
    }

    // View Reserved books
    public Cursor viewReservedBooks(String user) {
        String selectQuery = "SELECT username AS _id, * FROM " + TABLE_RESERVATION
                + " JOIN " + TABLE_BOOKS + " USING (" + KEY_ISBN + ") WHERE "
                + KEY_USERNAME + " =?";

        Cursor cursor = database.rawQuery(selectQuery, new String[] { user });
        Log.d("Reserved:", String.valueOf(cursor.getCount()));
//        Log.d("Debug:", cursor.getString(1));
        return cursor;
    }
}