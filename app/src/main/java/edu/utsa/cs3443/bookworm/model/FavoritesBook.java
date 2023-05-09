package edu.utsa.cs3443.bookworm.model;

/**
 * FavoritesBook: this class extends Book and creates a FavoritesBook object
 *                and represents a book category.
 *
 * @author bde817, rmy353, epg080, udz391
 */

import java.util.ArrayList;

public class FavoritesBook extends Book {
    private int rating;             // rating of the book

    /**
     * Constructor: FavoritesBook, Takes in 7 parameters that represent the favorite book's name,
     *              synopsis, quote, genre, author, rating, and category.
     *
     * @param name
     * @param synopsis
     * @param quote
     * @param genre
     * @param author
     * @param rate assigned to rating
     * @param category
     */
    public FavoritesBook(String name, String synopsis, String quote, String genre, String author, int rate, String category) {
        super(name, synopsis, quote, genre, author, rate, category);
        this.rating = rate;
    }

    /**
     * Constructor: FavoritesBook
     *
     * Initializes 'favBooks' list.
     */
    public FavoritesBook() {
        favBooks = new ArrayList<>();
    }

    /**
     * Getter: getRating, gets a favBooks rating
     *
     * @return rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Setter: setRating, sets a favBooks rating
     *
     * @param rating assigned to 'rating'.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * toString() method: returns the string representation of the Favorites class.
     * Returns the count of favorite books, and the name of each book within.
     *
     * @return temp
     */
    public String toString() {
        StringBuilder temp = new StringBuilder("Number of Favorite Books: " + favBooks.size() + "\n");
        for (Book book : favBooks) {
            temp.append(book.getName()).append("\n");
        }
        return temp.toString();
    }

    /**
     * Getter: getFavBooks, get a favBook
     *
     * @return favBooks
     */
    public ArrayList<Book> getFavBooks() {
        return favBooks;
    }

    /**
     * Setter: setFavBooks, sets a favBook
     *
     * @param favBooks assigned to 'favBooks'.
     */
    public void setFavBooks(ArrayList<Book> favBooks) {
        this.favBooks = favBooks;
    }
}