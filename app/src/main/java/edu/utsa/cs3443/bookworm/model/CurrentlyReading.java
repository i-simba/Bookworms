package edu.utsa.cs3443.bookworm.model;

/**
 * CurrentlyReading: this class extends Book and creates a CurrentlyReading object
 *                   and represents a book category
 *
 * @author bde817, rmy353, epg080, udz391
 */

import java.util.ArrayList;

public class CurrentlyReading extends Book {
    private int currentPage;        // current page of the book

    /**
     * Constructor: CurrentlyReading, Takes in 7 parameters that represent the currently reading book's name,
     *              synopsis, quote, genre, author, rating, and category.
     *
     * @param name:     name of book
     * @param synopsis: synopsis of book
     * @param quote:    quote from book
     * @param genre:    genre of book
     * @param author:   author of book
     * @param rate:     rating of book
     * @param category: category of book (reading, favorites, catalog)
     */
    public CurrentlyReading(String name, String synopsis, String quote, String genre, String author, int rate, String category) {
        super(name, synopsis, quote, genre, author, rate, category);
        this.currentPage = currentPage;
    }

    /**
     * Constructor: CurrentlyReading
     *
     * Initializes CurrentlyReading list.
     */
    public CurrentlyReading() {
        currentlyReading = this.getCurrentlyReading();
    }

    /**
     * toString() method: returns the string representation of the CurrentlyReading class.
     * Returns the count of currently reading books, and the name of each book within.
     *
     * @return temp
     */
    public String toString() {
        StringBuilder temp = new StringBuilder("Number of Currently Reading Books: " + "\n");
        for (Book book : currentlyReading) {
            temp.append(book.getName()).append("\n");
        }
        return temp.toString();
    }

    /**
     * Getter: CurrentlyReading, gets a book in currentlyReading
     *
     * @return currentlyReading
     */
    public ArrayList<Book> getCurrentlyReading() {
        return currentlyReading;
    }

    /**
     * Setter: CurrentlyReading, sets a book in currentlyReading
     *
     * @param currentlyReading assigned to 'currentlyReading'.
     */
    public void setCurrentlyReading(ArrayList<Book> currentlyReading) {
        this.currentlyReading = currentlyReading;
    }

}