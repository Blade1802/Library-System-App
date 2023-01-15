/*****************************************************************************************
 *
 *     Class to store a user entity
 *     Dec 2022
 *
 *******************************************************************************************/
package com.example.librarymanagementsystem;

public class User
{
    String _username;
    String _password;
    String _firstname;
    String _surname;
    int _mobile;
    int _admin;

    public User() { }

    public User(String _username, String _password, String _firstname, String _surname, int _mobile, int _admin) {
        this._username = _username;
        this._password = _password;
        this._firstname = _firstname;
        this._surname = _surname;
        this._mobile = _mobile;
        this._admin = _admin;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_firstname() {
        return _firstname;
    }

    public void set_firstname(String _firstname) {
        this._firstname = _firstname;
    }

    public String get_surname() {
        return _surname;
    }

    public void set_surname(String _surname) {
        this._surname = _surname;
    }

    public int get_mobile() {
        return _mobile;
    }

    public void set_mobile(int _mobile) {
        this._mobile = _mobile;
    }

    public int get_admin() {
        return _admin;
    }

    public void set_admin(int _admin) {
        this._admin = _admin;
    }
}
