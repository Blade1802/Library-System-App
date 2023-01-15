## Library Management System
This is an app to manage a library. Customers will have a account to log into.
They can search for books and reserve books they like.
The staff will also have a login, which will give it admin priviliges.
They can add and remove books when needed.

## List of Classes
#### MainActivity
- This is the starting screen of the app.
- It is a login page which forwards the user to the Dashboard.

#### UserHome
- This is the dashboard for normal users.
- It has options for the user to search books, view reserved books, our location and loggout.

#### AdminHome
- This the dashboard for admin users.
- It has additional options for the staff to add, update and remove books.

#### SearchBook
- This is the UI for searching books.
- The user has the options to search the books by using:
- ISBN, Title or Category.
- The user can use any combination of these options to search for books.
- It also has a checkbox to restrict the search result to only "available" books.

#### SearchBookResult
- This is a listview of the search result.
- Each item (book) is clickable which brings an alertDialog box.
- Showing all the information about the book and 2 buttons.
- To reserve the book or cancel selection.

#### ReservedBooks
- This is a ListView of the users reserved books.
- Same as SearchBookResult, every book is clickable.
- The user can unreserve books from here.

#### MapsActivity
- This is a class to show google maps on the screen with a marker.
- The marker is the location of our library's actual location (My house).

#### MyDatabaseHelper
- This is a helper class which extends SQLliteopenhelper.
- It is responsible for creating and updating the database.

#### MyDatabseManager
- This is a class to store CRUD operations for the library database.
- It has an open meathod to instantiate the helper class to use the database.

#### User
- This is a class to store a user entity (details).

#### Book
- This is a class to store a book entity (details).

## Extra Features
#### Custom AlertDialog Boxes
- I added custom alertDialog boxes. Which makes the lists more interactive and make the content concise.
- The list can display just the important details like title and author.
- If the user wants to see more details, he can click on a book and the dialogBox will show additional information with a button to reserve the book if he likes it.
- This was done using AlertDialog.Builder and LayoutInflater to inflate and set the layout for the dialog.

#### Map
- A map was added to the app to show the location of the library.
- The user can move the map around to look for places around the map and the directions to get here.

# Video link
https://youtu.be/jNLTkF0kMUI
[![YouTube](https://img.youtube.com/vi/jNLTkF0kMUI/0.jpg)](https://youtu.be/jNLTkF0kMUI)
