/***********************************************
 *
 *    This is the normal User Dashboard.
 *    It gives the user the option to search for books or view his reserved books.
 *    DEC 2022
 *
 *************************************************/
package com.example.librarymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserHome extends AppCompatActivity implements View.OnClickListener {

    private Button btnSearchBook;
    private Button btnReservedBook;
    private Button btnLocation;
    private Button btnLogOut;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        user = getIntent().getStringExtra("user");

        btnSearchBook = (Button)findViewById(R.id.searchBook);
        btnReservedBook = (Button)findViewById(R.id.reservedBooks);
        btnLocation = (Button)findViewById(R.id.location);
        btnLogOut = (Button)findViewById(R.id.logOut);

        btnSearchBook.setOnClickListener(this);
        btnReservedBook.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
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
            Intent reservedScreen = new Intent(getApplicationContext(), ReservedBooks.class);
            reservedScreen.putExtra("user", user);
            startActivity(reservedScreen);
        }

        if(v == btnLocation)
        {
            Intent locationScreen = new Intent(getApplicationContext(), MapsActivity.class);
            locationScreen.putExtra("user", user);
            startActivity(locationScreen);
        }
    }
}