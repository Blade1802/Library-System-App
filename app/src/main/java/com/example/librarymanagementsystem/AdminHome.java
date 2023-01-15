/***********************************************
 *
 *    This is the Admin Dashboard.
 *    It gives the user the option to search for books or view his reserved books.
 *    With extra feature like adding, removing and updating a book.
 *    DEC 2022
 *
 *************************************************/
package com.example.librarymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {

    private Button btnSearchBook;
    private Button btnReservedBook;
    private Button btnLogOut;
    private Button btnAddBook;
    private Button btnUpdateBook;
    private Button btnRemoveBook;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        user = getIntent().getStringExtra("user");

        btnSearchBook = (Button)findViewById(R.id.searchBook);
        btnReservedBook = (Button)findViewById(R.id.reservedBooks);
        btnLogOut = (Button)findViewById(R.id.logOut);
        btnAddBook = (Button)findViewById(R.id.addBook);
        btnUpdateBook = (Button)findViewById(R.id.updateBook);
        btnRemoveBook = (Button)findViewById(R.id.removeBook);

        btnSearchBook.setOnClickListener(this);
        btnReservedBook.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        btnAddBook.setOnClickListener(this);
        btnUpdateBook.setOnClickListener(this);
        btnRemoveBook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogOut)
        {
            finish();
        }

        if(v == btnSearchBook)
        {
            Intent searchScreen = new Intent(getApplicationContext(), SearchBook.class);
            searchScreen.putExtra("user", user);
            startActivity(searchScreen);
        }

        if(v == btnReservedBook)
        {
//            Intent reservedScreen = new Intent(getApplicationContext(), ReservedBooks.class);
//            reservedScreen.putExtra("user", user);
//            startActivity(reservedScreen);
        }

        if(v == btnAddBook)
        {
//            Intent reservedScreen = new Intent(getApplicationContext(), AddBook.class);
//            reservedScreen.putExtra("user", user);
//            startActivity(reservedScreen);
        }

        if(v == btnUpdateBook)
        {
//            Intent reservedScreen = new Intent(getApplicationContext(), UpdateBook.class);
//            reservedScreen.putExtra("user", user);
//            startActivity(reservedScreen);
        }

        if(v == btnRemoveBook)
        {
//            Intent reservedScreen = new Intent(getApplicationContext(), RemoveBook.class);
//            reservedScreen.putExtra("user", user);
//            startActivity(reservedScreen);
        }
    }
}