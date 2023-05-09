package edu.utsa.cs3443.bookworm.model;

/**
 * Book: this class represents a Book object and reads in the data form reading.csv
 *       and copyfile.csv in order to place book in correct lists
 *
 * @author bde817, rmy353, epg080
 */

import android.content.Context;
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

public class Book {
    private String name;     // String containing the book's name.
    private String synopsis; // Synopsis of the book.
    private String quote;    // Favorite quote from the book.
    private String genre;    // Genre of the book.
    private String author;   // Author of the book.
    private String category; // category of the book (reading, favorites, catalog)
    private int rating;      // Book's rating based on user.
    private ArrayList<Book>books = new ArrayList<>();       // books arrayList
    ArrayList<Book> currentlyReading = new ArrayList<>();   // currentlyReading arrayList
    ArrayList<Book> favBooks = new ArrayList<>();           // favBooks arrayList
    ArrayList<Book> catalogList = new ArrayList<>();        // catalogList arrayList

    /**
     * Constructor: Book, Takes in 7 parameters that represent the book's name,
     *              synopsis, quote, genre, author, rating, and category.
     *
     * @param name assigned to 'name'
     * @param synopsis assigned to 'synopsis'
     * @param quote assigned to 'quote'
     * @param genre assigned to 'genre'
     * @param author assigned to 'author'
     * @param rating assigned to 'rating'
     * @param category assigned to 'category'
     */
    public Book(String name, String synopsis, String quote, String genre, String author, int rating, String category) {
        this.name = name;
        this.synopsis = synopsis;
        this.quote = quote;
        this.genre = genre;
        this.author = author;
        this.rating = rating;
        this.category = category;
    }

    /**
     * Constructor: Book
     *
     * @param name
     */
    public Book(String name) { //Lo added category
        this.name = name;
    }

    /**
     * Constructor: Book
     *
     */
    public Book() {

    }

    /**
     * toString() method: returns the string representation of the Book class.
     *
     * @return String.
     */
    public String toString() {
        return "Name: " + this.name +
                " Synopsis: " + this.synopsis +
                " Quote: " + this.quote;
    }

    /**
     * Getter: getCategory, gets a books category
     *
     * @return category
     */
    public String getCategory(){ return category;}

    /**
     * Setter: setCatagory
     *
     * @param category assigned to 'category'.
     */
    public void setCategory(String category) { this.category = category; }

    /**
     * Getter: getName, gets a books name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter: setName, sets a books name
     *
     * @param name assigned to 'name'.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter: getSynopsis, gets a books synopsis
     *
     * @return synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Setter: setSynopsis, sets a books synopsis
     *
     * @param synopsis assigned to 'synopsis'.
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Getter: getQuote, gets a books quote
     *
     * @return quote
     */
    public String getQuote() {
        return quote;
    }

    /**
     * Setter: sets a books quote
     *
     * @param quote assigned to 'quote'.
     */
    public void setQuote(String quote) {
        this.quote = quote;
    }

    /**
     * Getter: getGenre, gets a books genre
     * @return genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Setter: setGenre, sets a books genre
     *
     * @param genre assigned to 'genre'.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Getter: getAuthor, gets a books author
     *
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter: setAuthor, sets a books author
     *
     * @param author assigned ot 'author'.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Getter: getRating, gets a books rating
     *
     * @return rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Setter: setRating, sets a books rating
     *
     * @param rating assigned to 'rating'.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * loadBooksFile: creates the file in which added book information will be stored.
     *                For testing purposes, uncomment the commented out line to have
     *                preloaded books in app read from the "reading" file.
     *
     * @param ctx
     * @throws IOException
     */
    public void loadBooksFile(Context ctx) throws IOException {
        /*// InputStream used to read in data within 'reading.csv' file.                            //TODO: uncomment to have pre-loaded books added to app lines 215 - 232.
        InputStream input = null;
        // Scanner object used to scan provided file.
        Scanner inFile = null;
        // Temporary String variables used for I/O.
        String[] arrLine = null;
        String line = null;
        // Temporary Book object.
        Book temp = null;
        // Opening file from asset folder.
        try {
            input = ctx.getAssets().open("reading.csv");
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        // Read opened file line by line.
        inFile = new Scanner(input);
        */

        //deleting the existing file upon creation
        File file = new File(ctx.getFilesDir(), "copyfile.csv");

        //if the file does not exists then create a new onw
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*
        else{                                                                                       // Else block commented out to facilitate saving.
            //if it does exists then delete it and create a new file
            file.delete();
            file.createNewFile();
        }
        */
        /*                                                                                          //TODO: uncomment to have preloaded books read in and added to app lines 253 - 277.
        OutputStream out = ctx.openFileOutput("copyfile.csv", Context.MODE_PRIVATE);                //opening file to write

        while (inFile.hasNextLine()) {
            line = inFile.nextLine();                                                               //reading in line by line
            arrLine = line.split("/");

            Book book = new Book(arrLine[0], arrLine[1], arrLine[2], arrLine[3], arrLine[4], Integer.parseInt(arrLine[5]), arrLine[6]);

            switch (arrLine[6]) {                                                                   //  takes the last token that represents the books category
                case "reading":                                                                     //  and compares it against reading, favorites, or other
                    currentlyReading.add(book);                                                     //  in order to store the book in the correct category
                    break;
                case "favorites":
                    favBooks.add(book);
                    break;
                default:
                    catalogList.add(book);
                    break;
            }
            line += "\n";
            out.write(line.getBytes(StandardCharsets.UTF_8));                                       //  copying into "copyfile"
        }
        inFile.close();
        out.close();*/
    }

    /**
     * loadBooksCopyFile: this method updates the information from within the copyfile, to the
     *                    corresponding arrayList
     *
     * @param ctx
     * @throws IOException
     */
    public void loadBooksCopyFile(Context ctx) throws IOException {

        // Opening file from asset folder.
        try {
            InputStream in = ctx.openFileInput("copyfile.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;

            // Read opened file line by line.
            while ((line = reader.readLine()) != null) {
                String[] arrLine = line.split("/");

                Book book = new Book(arrLine[0], arrLine[1], arrLine[2], arrLine[3], arrLine[4], Integer.parseInt(arrLine[5]), arrLine[6]);

                boolean added = false;
                switch (arrLine[6]) {                                                               // comparing last token which is representative
                    case "reading":                                                                 // of the books category and adding into
                        for (Book b : currentlyReading) {                                           // correct list
                            if (b.getName().equals(book.getName())) {
                                added = true;
                                break;
                            }
                        }
                        if (!added) {
                            currentlyReading.add(book);
                        }
                        break;
                    case "favorites":
                        for (Book b : favBooks) {
                            if (b.getName().equals(book.getName())) {
                                added = true;
                                break;
                            }
                        }
                        if (!added) {
                            favBooks.add(book);
                        }
                        break;
                    default:                                                                        // catalog
                        for (Book b : catalogList) {
                            if (b.getName().equals(book.getName())) {
                                added = true;
                                break;
                            }
                        }
                        if (!added) {
                            catalogList.add(book);
                        }
                        break;
                }
            }
            in.close();
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
}
