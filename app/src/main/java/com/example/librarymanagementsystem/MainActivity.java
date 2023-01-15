/***********************************************
 *
 *    This is the login page.
 *    It sends the user to a dashboard based on the user admin privileges.
 *    DEC 2022
 *
 *************************************************/
package com.example.librarymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout editID;
    private TextInputLayout editPass;
    private Button btnSignIn;
    private TextView SignUp;
    private MyDatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MyDatabaseManager(this);
        db.open();

        loadData(); // Loads prefilled data into the library database

        editID = findViewById(R.id.editID);
        editPass = findViewById(R.id.editPass);

        btnSignIn = findViewById(R.id.buttonSignIn);
        SignUp = findViewById(R.id.toSignUp);

        btnSignIn.setOnClickListener(this);
        SignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSignIn) {
            signinUser();
        } else if (v == SignUp) {
            // startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        }
    }

    private void signinUser() {

        boolean res = (verifyId() & verifyPass());
        if (res == false) {
            return;
        }

        String id = editID.getEditText().getText().toString().trim();
        String pass = editPass.getEditText().getText().toString().trim();

        if(db.userExists(id, pass)) {
            User user = db.getUser(id);

            // If the user logged in is a normal user send it to User Dashboard
            // If the user is an admin, send it to Admin Dashboard
            if (user.get_admin() == 0) {
                Intent userHomeScreen = new Intent(MainActivity.this, UserHome.class);
                userHomeScreen.putExtra("user", id);
                startActivity(userHomeScreen);
            } else if (user.get_admin() == 1) {
                Intent adminHomeScreen = new Intent(MainActivity.this, AdminHome.class);
                adminHomeScreen.putExtra("user", id);
                startActivity(adminHomeScreen);
            }
        }

    }

    // Checks if id is empty and sets an error if it is, else return true
    private boolean verifyId() {

        String id = editID.getEditText().getText().toString().trim();

        if (id.isEmpty()) {
            editID.setErrorEnabled(true);
            editID.setError("User ID is Required");
            return false;
        } else {
            editID.setErrorEnabled(false);
            return true;
        }
    }

    // Checks if password is empty and sets an error if it is, else return true
    private boolean verifyPass() {

        String pass = editPass.getEditText().getText().toString().trim();

        if (pass.isEmpty()) {
            editPass.setErrorEnabled(true);
            editPass.setError("Password is Required");
            return false;
        } else {
            editPass.setErrorEnabled(false);
            return true;
        }
    }

    // Loads data into the database if not already inserted
    private void loadData() {
        // if table row count = 0, then insert prefilled data
        if (db.getUserCount() == 0) {
            db.addUser(new User("C20396243", "password", "Aayush",
                    "Gaur", 899779185, 0));
            db.addUser(new User("admin", "password", "Aayush",
                    "Gaur", 899779185, 1));
        }
        if (db.getBookCount() == 0) {
            db.addBook(new Book("093-403992", "Computers in Business",
                    "Alicia Oneill", 3, 1997, "Biography", 0));
            db.addBook(new Book("23472-8729", "Exploring Peru",
                    "Stephanie Birchi", 4, 2005, "Travel", 0));
            db.addBook(new Book("237-34823", "Business Strategy",
                    "Joe Peppard", 2, 2002, "Business", 0));
            db.addBook(new Book("23u8-923849", "A guide to nutrition",
                    "John Thorpe", 2, 1997, "Health", 1));
            db.addBook(new Book("2983-3494", "Cooking for children",
                    "Anabelle Sharpe", 1, 2003, "Cookery", 0));
            db.addBook(new Book("82n8-308", "Computers for idiots",
                    "Susan O\'Neill", 5, 1998, "Technology", 0));
            db.addBook(new Book("9823-23984", "My life in picture",
                    "Kevin Graham", 8, 2004, "Health", 1));
            db.addBook(new Book("9823-2403-0", "DaVinci Code",
                    "Dan Brown", 1, 2003, "Fiction", 0));
            db.addBook(new Book("9823-98345", "How to cook Italian food",
                    "Jamie Oliver", 2, 2005, "Cookery", 1));
            db.addBook(new Book("9823-98487", "Optimising your business",
                    "Cleo Blair", 1, 2001, "Business", 0));
            db.addBook(new Book("98234-029384", "My ranch in Texas",
                    "George Bush", 1, 2005, "Health", 1));
            db.addBook(new Book("988745-234", "Tara Road",
                    "Maeve Binchy", 4, 2002, "Fiction", 0));
            db.addBook(new Book("993-004-00", "My life in bits",
                    "John Smith", 1, 2001, "Health", 0));
            db.addBook(new Book("9987-0039882", "Shooting History",
                    "Jon Snow", 1, 2003, "Health", 0));
        }
    }
}

