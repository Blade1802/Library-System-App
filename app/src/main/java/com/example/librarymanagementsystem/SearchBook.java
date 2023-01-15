package com.example.librarymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SearchBook extends AppCompatActivity {

    private TextInputLayout editTitle;
    private TextInputLayout editIsbn;
    private Spinner spinner;
    private Button button;
    private String category;
    private CheckBox checkBox;
    private MyDatabaseManager db;

    @Override
    public void onBackPressed() {
        finish();
    }

    // Check if Title is supplied
    private boolean verifyTitle() {
        String t = editTitle.getEditText().getText().toString().trim();
        if (t.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    // Check if ISBN is supplied
    private boolean verifyIsbn() {
        String b = editIsbn.getEditText().getText().toString().trim();
        if (b.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    // Check if Category is supplied
    private boolean verifyCategory() {
        if (category.equals("Select Category")) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        db = new MyDatabaseManager(this);
        db.open();

        editIsbn = (TextInputLayout) findViewById(R.id.editIsbn);
        editTitle = (TextInputLayout) findViewById(R.id.editTitle);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);
        checkBox = (CheckBox) findViewById(R.id.onlyAvailable);

        List<String> categories = db.getCategories();
        categories.add(0, "Select Category");

        // Creating and setting an Adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Case 0: If none are selected
                if (!(verifyCategory() | verifyTitle() | verifyIsbn())) {
                    Toast.makeText(SearchBook.this, "Enter at least 1 option!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), SearchBookResult.class);
                intent.putExtra("user", getIntent().getStringExtra("user"));

                // Case 1: If Isbn and show available are selected
                if (verifyIsbn() && checkBox.isChecked()) {

                    intent.putExtra("id", 1);
                    intent.putExtra("isbn", editIsbn.getEditText().getText().toString().trim());
                    startActivity(intent);

                // Case 2: If only ISBN is selected
                } else if (verifyIsbn() && !checkBox.isChecked()) {

                    intent.putExtra("id", 2);
                    intent.putExtra("isbn", editIsbn.getEditText().getText().toString().trim());
                    startActivity(intent);

                // Case 3: If Title, Category and show available are selected
                } else if (verifyTitle() && verifyCategory() && checkBox.isChecked()) {

                    intent.putExtra("id", 3);
                    intent.putExtra("title", editTitle.getEditText().getText().toString().trim());
                    intent.putExtra("category", category);
                    startActivity(intent);

                // Case 4: If Title and Category are selected
                } else if (verifyTitle() && verifyCategory() && !checkBox.isChecked()) {

                    intent.putExtra("id", 4);
                    intent.putExtra("title", editTitle.getEditText().getText().toString().trim());
                    intent.putExtra("category", category);
                    startActivity(intent);

                // Case 5: If Title and show available are selected
                } else if (verifyTitle() && !verifyCategory() && checkBox.isChecked()) {

                    intent.putExtra("id", 5);
                    intent.putExtra("title", editTitle.getEditText().getText().toString().trim());
                    startActivity(intent);

                // Case 6: If only Title is selected
                } else if (verifyTitle() && !verifyCategory() && !checkBox.isChecked()) {

                    intent.putExtra("id", 6);
                    intent.putExtra("title", editTitle.getEditText().getText().toString().trim());
                    startActivity(intent);

                // Case 7: If Category and show available are selected
                } else if (!verifyTitle() && verifyCategory() && checkBox.isChecked()) {

                    intent.putExtra("id", 7);
                    intent.putExtra("category", category);
                    startActivity(intent);

                // Case 8: If only Category is selected
                } else if (!verifyTitle() && verifyCategory() && !checkBox.isChecked()) {

                    intent.putExtra("id", 8);
                    intent.putExtra("category", category);
                    startActivity(intent);

                }
            }
        });
    }
}