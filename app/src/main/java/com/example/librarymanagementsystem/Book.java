/*****************************************************************************************
 *
 *     Class to store a book entity
 *     Dec 2022
 *
 *******************************************************************************************/
package com.example.librarymanagementsystem;

public class Book {
    String _isbn;
    String _title;
    String _author;
    int _edition;
    int _year;
    String _category;
    int _reserved;

    public Book() {
    }

    public Book(String _isbn, String _title, String _author, int _edition, int _year, String _category, int _reserved) {
        this._isbn = _isbn;
        this._title = _title;
        this._author = _author;
        this._edition = _edition;
        this._year = _year;
        this._category = _category;
        this._reserved = _reserved;
    }

    public String get_isbn() {
        return _isbn;
    }

    public void set_isbn(String _isbn) {
        this._isbn = _isbn;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_author() {
        return _author;
    }

    public void set_author(String _author) {
        this._author = _author;
    }

    public int get_edition() {
        return _edition;
    }

    public void set_edition(int _edition) {
        this._edition = _edition;
    }

    public int get_year() {
        return _year;
    }

    public void set_year(int _year) {
        this._year = _year;
    }

    public String get_category() {
        return _category;
    }

    public void set_category(String _category) {
        this._category = _category;
    }

    public int get_reserved() {
        return _reserved;
    }

    public void set_reserved(int _reserved) {
        this._reserved = _reserved;
    }
}
