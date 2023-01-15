package com.example.librarymanagementsystem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class SearchBookResult extends ListActivity {

    SimpleCursorAdapter bookAdapter;
    String columnNames[] = new String[] {"title", "author", "category"};
    int[] columnId = new int[] {R.id.title, R.id.author, R.id.category};
    Cursor results = null;
    private MyDatabaseManager db;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book_result);
        db = new MyDatabaseManager(this);
        db.open();

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        switch (intent.getIntExtra("id", 0)) {
            // Case 1: If Isbn and show available are selected
            case 1: {
                results = db.getBooksbyISBN(intent.getStringExtra("isbn"), 1);
                break;
            }

            // Case 2: If only ISBN is selected
            case 2: {
                results = db.getBooksbyISBN(intent.getStringExtra("isbn"), 0);
                break;
            }

            // Case 3: If Title, Category and show available are selected
            case 3: {
                results = db.getBooksbyTitleAndCategory(intent.getStringExtra("title"),
                        intent.getStringExtra("category"), 1);
                break;
            }

            // Case 4: If Title and Category are selected
            case 4: {
                results = db.getBooksbyTitleAndCategory(intent.getStringExtra("title"),
                        intent.getStringExtra("category"), 0);
                break;
            }

            // Case 5: If Title and show available are selected
            case 5: {
                results = db.getBooksbyTitle(intent.getStringExtra("title"), 1);
                break;
            }

            // Case 6: If only Title is selected
            case 6: {
                results = db.getBooksbyTitle(intent.getStringExtra("title"), 0);
                break;
            }

            // Case 7: If Category and show available are selected
            case 7: {
                results = db.getBooksbyCategory(intent.getStringExtra("category"), 1);
                break;
            }

            // Case 8: If only Category is selected
            case 8: {
                results = db.getBooksbyCategory(intent.getStringExtra("category"), 0);
                break;
            }
        }

        bookAdapter = new SimpleCursorAdapter(this, R.layout.row, results, columnNames, columnId);
        setListAdapter(bookAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Cursor data = (Cursor)l.getItemAtPosition(position);
        String isbn = data.getString(data.getColumnIndexOrThrow("isbn"));
        Log.d("ISBN: ", isbn);

//        data.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(SearchBookResult.this);
        // Get the layout inflater
        LayoutInflater inflater = SearchBookResult.this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View dialogView = inflater.inflate(R.layout.dialog_book, null);
        builder.setView(dialogView);

        TextView txtview1 = (TextView) dialogView.findViewById(R.id.dialogisbn);
        TextView txtview2 = (TextView) dialogView.findViewById(R.id.dialogtitle);
        TextView txtview3 = (TextView) dialogView.findViewById(R.id.dialogauthor);
        TextView txtview4 = (TextView) dialogView.findViewById(R.id.dialogedition);
        TextView txtview5 = (TextView) dialogView.findViewById(R.id.dialogyear);
        TextView txtview6 = (TextView) dialogView.findViewById(R.id.dialogcategory);

        Cursor info = db.getBooksbyISBN(isbn, 0);

        txtview1.setText(info.getString(1));
        txtview2.setText(info.getString(2));
        txtview3.setText(info.getString(3));
        txtview4.setText(String.valueOf(info.getInt(4)));
        txtview5.setText(String.valueOf(info.getInt(5)));
        txtview6.setText(info.getString(6));
        int reserved = info.getInt(7);

        if (reserved == 0) {
            // Add action buttons
            builder.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Log.d("ISBN: ", isbn);
                    db.addReservation(user, isbn);
                    // finish();
                }
            });
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}