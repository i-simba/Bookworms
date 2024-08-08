
# BookWorm by BookWorms
## [Demo Video](https://youtu.be/uE5G-2mVD1o)

&emsp; &emsp; BookWorm was created for book lovers to make book tracking simple. It is capable of categorizing user added books into three main categories:
Reading, Favorites, and Catalog.
Reading contains books the user has denoted they are currently reading.
Favorites contains books the user has denoted are their favorites.
Catalog contains books the user has entered, but are yet to denote them as either "Reading" or Favorites".

## Team Members

- Loren Isenhour
- Ivan Simbulan
- Arturo Perez
- Rosino Guimarey

### Usage

&emsp; To add a book, simply click on the "ADD BOOK" button present on the homescreen.
Pressing this button will take the user to the a screen in which they are able to enter information relevant to the book they are adding:
Book title, Author, Genre, a Synopsis (optional), a favorite Quote (optional), a book picture (optional), and user rating of the book (optional).

&emsp; The added book will automatically be added to the "Catalog" list on the homescreen, in which the user will have the option to move it to either "Reading" or "Favorites".
To move any book within any list, the user can click the "MOVE BOOK" button, which brings up a separate screen with a list of all the books the user has added.
Within this screen, the user can enter the name of a book they want to move inside the TextBox and click either "Reading" or "Favorites" to move that book to their selected list.

&emsp; Navigating back to the homescreen, the user can click on any book within any list to be taken to another screen containing information pertaining to that book.
If the user didn't enter any values marked as (optional) mentioned above, those fields will contain default values, i.e.,
Synopsis will be empty, Favorite Quote will be empty, Rating will be 0 stars, and a default book picture will be displayed.


### Known Issues
- Moving books to lists that they are already added in gives a 'book moved successfully' message rather than checking if that book is already there.\
&emsp; i.e., a Book in reading being moved to reading will be a successful move, rather than informing the user that book is already in reading.\
&emsp; minor issue that doesn't affect usage all that much, but it is an issue none the less.

### TO CLONE THE REPOSITORY:

- You can simply download the .zip file after clicking on the <> Code button located on the repository.
- Alternatively, you can use the command on Git Bash.\
&emsp; &emsp; _git clone https://github.com/UTSA-CS-3443/Bookworms.git_


### IMPORTANT NOTES:

- As is, the app will work as intended but without any books entered.
- There are preloaded books provided within the assets folder contained within the favorites.csv and reading.csv files for testing purposes.
- If you would like to use these provided books, simply uncomment the following lines within the model class, **_Book.java_**:\
&emsp; &emsp; &emsp; _Lines 215 - 232 and Lines 253 - 277._
- Additionally, for testing purposes, you can uncomment the else block located at:\
&emsp; &emsp; &emsp; _Lines 247 - 251._
- This else block deletes the created copyfile.csv, meaning after an app restart, the user added books will be deleted.

**_Finally_**, there are provided book pictures within this repo which corresponds to each preloaded book.
- If you would like to see these images implemented within the app, simply upload the files in the android emulator within the path:\
&emsp; &emsp; &emsp; _data/user/0/edu.utsa.cs3443.bookworm/files/Images_
- If the path specified above isn't showing, simply run the app first and refresh the Device File Explorer.
