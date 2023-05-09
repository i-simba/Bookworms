package edu.utsa.cs3443.bookworm.model;

/**
 * Catalog: this class extends Book and creates a Catalog object
 *          and represents a book category.
 *
 * @author bde817, rmy353, epg080, udz391
 */

import java.util.ArrayList;

public class Catalog extends Book{

    /**
     * Constructor: Catalog, Takes in 7 parameters that represent the catalog book's name,
     *              synopsis, quote, genre, author, rating, and category.
     *
     * @param name
     * @param synopsis
     * @param quote
     * @param genre
     * @param author
     * @param rate
     * @param category
     */
    public Catalog(String name, String synopsis, String quote, String genre, String author, int rate, String category) {
        super(name, synopsis, quote, genre, author, rate, category);
    }

    /**
     * toString() method: returns the string representation of the Catalog class.
     * Returns the count of favorite books, and the name of each book within.
     *
     * @return temp
     */
    public String toString() {
        StringBuilder temp = new StringBuilder("Number of Favorite Books: " + catalogList.size() + "\n");
        for (Book book : catalogList) {
            temp.append(book.getName()).append("\n");
        }
        return temp.toString();
    }

    /**
     * Constructor: Catalog
     *
     * Initializes 'catalogList' list.
     */
    public Catalog() {
        catalogList = new ArrayList<>();
    }

    /**
     * Getter: getCatalogList, gets a book in catalogList
     *
     * @return catalogList
     */
    public ArrayList<Book> getCatalogList() {
        return catalogList;
    }

    /**
     * Setter: setCatalogList, sets a book in catalogList
     *
     * @param catalogList assigned to 'catalogList'
     */
    public void setCatalogList(ArrayList<Book> catalogList) {
        this.catalogList = catalogList;
    }

    /**
     * addCatalogBook: Add Book into 'catalog' list.
     *
     * @param book used to add to list.
     */
    public void addCatalogBook(Book book) {
        this.catalogList.add(book);
    }

}

