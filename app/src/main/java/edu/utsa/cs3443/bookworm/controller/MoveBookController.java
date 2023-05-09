package edu.utsa.cs3443.bookworm.controller;

/**
 * MoveBookController: this class implements View.OnClickListener and
 *                     listens to the selection made by the user in order
 *                     to communicate on other classes on how to behave.
 *
 * @author bde817, rmy353, epg080, udz391
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import edu.utsa.cs3443.bookworm.MoveBookActivity;
import edu.utsa.cs3443.bookworm.model.Book;

public class MoveBookController implements View.OnClickListener {

    private Context con = null;                     // Context of MoveBookActivity
    private Button moveToFavorites = null;          // Button object to move book to favorites
    private Button moveToReading = null;            // Button object to move book to reading
    private TextInputEditText bookTitle = null;     // TextInput to get title of book
    private LinearLayout container = null;          // Linear layout for container that will contain list of books
    private ArrayList<Book> allBooks = null;        // ArrayList for all books


    /**
     * Constructor: MoveBookController, Takes in 5 parameters ctx, moveFav, moveRead, title, container
     *              and assigns them accordingly
     *
     * @param ctx assigned to 'con'
     * @param moveFav assigned to 'moveToFavorites'
     * @param moveRead assigned to 'moveToReading'
     * @param title assigned to 'bookTitle'
     * @param container assigned to 'container'
     */
    public MoveBookController(Context ctx, Button moveFav, Button moveRead, TextInputEditText title, LinearLayout container) {
        this.con = ctx;

        // Initialize list.
        this.allBooks = new ArrayList<Book>();
        try {
            loadBooksToList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize moveToFavorites and moveToReading buttons
        this.moveToFavorites = moveFav;
        this.moveToReading = moveRead;
        this.bookTitle = title;

        // Initialize LinearLayout container.
        this.container = container;

        // Set onClick listeners for moveToFavorite and moveToReading buttons.
        this.moveToFavorites.setOnClickListener(this);
        this.moveToReading.setOnClickListener(this);

        // Loading all passed in books
        for (Book book : allBooks) {
            loadBooks(book);
        }
    }

    /**
     *
     * onClick method:  processes the users text input (book to move) and
     *                  determines whether favorites or reading button was
     *                  clicked, and what actions to take thereafter
     *
     * @param view
     *
     */
    @Override
    public void onClick(View view) {

        //grabbing input text to lowercase
        String temp = this.bookTitle.getText().toString().toLowerCase();
        String toComp = this.bookTitle.getText().toString(); // -Ivan

        //will be used to build an array of copyfile contents
        ArrayList<String> builder = new ArrayList<>();

        //if favorites is clicked
        if ( view == moveToFavorites ) {

            // Validating input
            if (temp.isEmpty()) {
                this.bookTitle.setError("Please enter a book title to be moved!");
                return;
            }

            // If input isn't matched with list
            boolean match = false;
            for (Book book : allBooks) {
                if (toComp.equals(book.getName())) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                this.bookTitle.setError(toComp + " is not in Catalog!");
                return;
            }

            // Remove moved book from all other lists other than 'favorites'
            MainController.getReading().getCurrentlyReading().removeIf(book -> book.getName().equals(toComp));
            MainController.getCatalog().getCatalogList().removeIf(book -> book.getName().equals(toComp));

            try {
                InputStream in = con.openFileInput("copyfile.csv");                             //opening file
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                String[] tok = null;
                String comp;
                while ((line = reader.readLine()) != null) {                                        //reading line by line
                    String[] arrLine = line.split("/");
                    comp = arrLine[0].toLowerCase();                                                //grabbing title to lowercase
                    if (temp.equals(comp)) {                                                        //comparing user book choice to titles
                        line = arrLine[0] + "/" + arrLine[1] + "/" + arrLine[2] + "/" + arrLine[3] + "/"
                                + arrLine[4] + "/" + arrLine[5] + "/favorites\n";                   //updating book to favorites
                        builder.add(line);                                                          //replacing the book with chosen category
                    } else {
                        line += "\n";
                        builder.add(line);
                    }
                }
                reader.close();
                OutputStream out = null;
                out = con.openFileOutput("copyfile.csv", Context.MODE_PRIVATE);                 //opening file to write
                for (String lines : builder)                                                        //writing lines to file form arrayList
                    out.write(lines.getBytes(StandardCharsets.UTF_8));
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Toast.makeText(con, this.bookTitle.getText().toString() +                          //toast for book addition confirmation
                    " was added to favorites", Toast.LENGTH_SHORT).show();

            // finishes child activity
            if (con instanceof MoveBookActivity) {
                ((Activity)con).finish();
            }
        }

        if ( view == moveToReading ) {

            // Validating input
            if (temp.isEmpty()) {
                this.bookTitle.setError("Please enter a book title to be moved!");
                return;
            }

            // If input isn't matched with list.
            boolean match = false;
            for (Book book : allBooks) {
                if (toComp.equals(book.getName())) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                this.bookTitle.setError(toComp + " is not in Catalog!");
                return;
            }

            // Remove moved book from all lists other than 'reading'
            MainController.getFavorites().getFavBooks().removeIf(book -> book.getName().equals(toComp));
            MainController.getCatalog().getCatalogList().removeIf(book -> book.getName().equals(toComp));

            try {
                InputStream in = con.openFileInput("copyfile.csv");                              //opening file
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                String[] tok = null;
                String comp;
                while ((line = reader.readLine()) != null) {                                        //reading in line by line
                    String[] arrLine = line.split("/");
                    comp = arrLine[0].toLowerCase();                                                //grabbing title to lower case
                    if (temp.equals(comp)) {                                                        //comparing user book choice to titles
                        line = arrLine[0] + "/" + arrLine[1] + "/" + arrLine[2] + "/" + arrLine[3] + "/"
                                + arrLine[4] + "/" + arrLine[5] + "/reading\n";                     //updating books as reading
                        builder.add(line);                                                          //replacing book with the chosen category
                    } else {
                        line += "\n";
                        builder.add(line);
                    }
                }
                reader.close();
                OutputStream out = null;
                out = con.openFileOutput("copyfile.csv", Context.MODE_PRIVATE);                  //opening file in write mode
                for (String lines : builder) {
                    out.write(lines.getBytes(StandardCharsets.UTF_8));                              //writing to file the book arraylist
                }
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Toast.makeText(con, this.bookTitle.getText().toString() +                          //toast for confirmation of book addition
                    " was added to reading", Toast.LENGTH_SHORT).show();

            // finishes child activity
             if (con instanceof MoveBookActivity) {
                 ((Activity)con).finish();
            }
        }
    }

    /**
     * loadBooks: loads books to a container for the user to scroll through
     *
     * @param book - title of the book
     */
    private void loadBooks(Book book) {

        // Converting book name to match that of the correct image file.
        String bName = MainController.getBookImageName(book.getName());

        // Child Layout that will contain information about a given book.
        LinearLayout c = new LinearLayout(con);
        c.setOrientation(LinearLayout.HORIZONTAL);
        c.setTransitionName(book.getName());

        // ImageView object that will contain a corresponding picture of a given book.
        ImageView pic = new ImageView(con);
        ViewGroup.LayoutParams picParam = new ViewGroup.LayoutParams(132, 200);
        int imageSource = con.getResources().getIdentifier(bName, "drawable", con.getPackageName());
        pic.setLayoutParams(picParam);
        pic.setImageResource(imageSource);
        pic.setPadding(5,5,0,0);

        // Getting picture for book from "Images".
        File imageFile = new File(con.getFilesDir() + "/Images/" + bName + ".png");
        if (imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            pic.setImageBitmap(bitmap);
        }

        // A child Layout of container that will contain the book's name and genre.
        LinearLayout textContainer = new LinearLayout(con);
        textContainer.setOrientation(LinearLayout.VERTICAL);

        // TextView objects that will contain a given book's name and genre to be added on 'textContainer'.
        TextView bookName = new TextView(con);
        TextView bookGenre = new TextView(con);
        bookName.setTypeface(bookName.getTypeface(), Typeface.BOLD);
        bookName.setTextSize(16);
        bookGenre.setTypeface(bookGenre.getTypeface(), Typeface.ITALIC);
        bookGenre.setTextSize(12);

        // Assigning name and genre.
        bookName.setText(book.getName());
        bookGenre.setText(book.getGenre());

        // Adding TextViews to 'textContainer'.
        textContainer.addView(bookName);
        textContainer.addView(bookGenre);

        // Adding all other child views to c.
        c.addView(pic);
        c.addView(textContainer);

        // Adding c to container.
        container.addView(c);
    }


    /**
     * loadBookToList: loads the updated copyfile information into book object
     *
     * @throws IOException
     */
    private void loadBooksToList() throws IOException {
        // InputStream used to read in data within 'copyfile.csv' file.
        InputStream input = null;

        // Scanner object used to scan provided file.
        Scanner inFile = null;

        // Temporary Book object.
        Book temp = null;

        // Opening file from asset folder.
        try {
            InputStream in = con.openFileInput("copyfile.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;

            // Read opened file line by line and adding to book object
            while ((line = reader.readLine()) != null) {
                String[] arrLine = line.split("/");
                Book book = new Book(arrLine[0], arrLine[1], arrLine[2], arrLine[3], arrLine[4], Integer.parseInt(arrLine[5]),arrLine[6]);
                allBooks.add(book);
            }
            in.close();
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
}
