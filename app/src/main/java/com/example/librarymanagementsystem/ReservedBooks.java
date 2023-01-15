package com.example.librarymanagementsystem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ReservedBooks extends ListActivity {

    SimpleCursorAdapter bookAdapter;
    String columnNames[] = new String[] {"title", "author", "category"};
    int[] columnId = new int[] {R.id.title, R.id.author, R.id.category};
    Cursor results = null;
    private MyDatabaseManager db;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_books);
        db = new MyDatabaseManager(this);
        db.open();

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        results = db.viewReservedBooks(user);

        bookAdapter = new SimpleCursorAdapter(this, R.layout.row, results, columnNames, columnId);
        setListAdapter(bookAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Cursor data = (Cursor)l.getItemAtPosition(position);
        String isbn = data.getString(data.getColumnIndexOrThrow("isbn"));

//        data.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(ReservedBooks.this);
        // Get the layout inflater
        LayoutInflater inflater = ReservedBooks.this.getLayoutInflater();

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

        // Add action buttons
        builder.setPositiveButton("Unreserve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                db.removeReservation(user, isbn);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Cancel
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