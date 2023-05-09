package edu.utsa.cs3443.bookworm.controller;

/**
 * MainController: this class implements View.OnClickListener and
 *                  listens to the selection made by the user in order
 *                  to communicate on other classes on how to behave.
 *
 * @author bde817, rmy353, epg080, udz391
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import edu.utsa.cs3443.bookworm.AddBookActivity;
import edu.utsa.cs3443.bookworm.MoveBookActivity;
import edu.utsa.cs3443.bookworm.ViewBookActivity;
import edu.utsa.cs3443.bookworm.model.Book;
import edu.utsa.cs3443.bookworm.model.Catalog;
import edu.utsa.cs3443.bookworm.model.CurrentlyReading;
import edu.utsa.cs3443.bookworm.model.FavoritesBook;

public class MainController implements View.OnClickListener {

    private static FavoritesBook favorites = null;          // Favorites object that will contain favorite books.
    private static CurrentlyReading reading = null;         // CurrentlyReading object that will contain currently reading books.
    private static Catalog catalog = null;                  // Catalog object that will contain catalog books.
    private Context con = null;                             // Context of MainActivity.
    private LinearLayout container = null;                  // LinearLayout that will contain the list of books for each category.
    private final ArrayList<ConstraintLayout> layouts;      // ConstraintLayouts that server as 'tabs'.
    private Button addButton = null;                        // Button object the user can click to add a new book.
    private Button moveButton = null;                       // Button object the user can click to move a book.
    static boolean firstRead = true;                        // Determines if first read

    /**
     * Constructor: MainController
     *
     * @param cons assigned to 'container'
     * @param ctx assigned to 'con'
     * @param rC assigned to 'layouts'
     * @param fC assigned to 'layouts'
     * @param cC assigned to 'layouts'
     * @param b assigned to 'addButton'
     * @param m assigned to 'moveButton'
     */
    public MainController(LinearLayout cons, Context ctx, ConstraintLayout rC, ConstraintLayout fC, ConstraintLayout cC, Button b, Button m) throws IOException {

        // Assign ctx to con.
        con = ctx;
        container = cons;

        // Initialize Favorites and CurrentlyReading objects.
        favorites = new FavoritesBook();
        reading = new CurrentlyReading();
        catalog = new Catalog();

        // Initialize control lists.
        layouts = new ArrayList<ConstraintLayout>();

        // Initialize addButton and adding onClick listener.
        addButton = b;
        addButton.setOnClickListener(this);

        // Initialize moveButton and adding onClick listener.
        moveButton = m;
        moveButton.setOnClickListener(this);

        // Set the tags for each ConstraintLayout.
        rC.setTag(1);
        fC.setTag(2);
        cC.setTag(3);

        // Adding controls to lists.
        layouts.add(rC);
        layouts.add(fC);
        layouts.add(cC);

        // Setting listeners to added TextViews.
        for (ConstraintLayout tab : layouts) {
            tab.setOnClickListener(this);
        }

        // Load books into proper category.
        if(firstRead) {
            reading.loadBooksFile(ctx);
            favorites.loadBooksFile(ctx);
            catalog.loadBooksFile(ctx);
            firstRead = false;
        }
        else{
            reading.loadBooksCopyFile(ctx);
            favorites.loadBooksCopyFile(ctx);
            catalog.loadBooksCopyFile(ctx);
        }
    }

    /**
     * onClick() method: listens to clicks on three different TextViews that denotes the category list
     * a user wants to view. i.e., Reading, Favorites, or Catalog.
     * The ConstraintLayout that contains a TextView will be referred to as a 'tab'.
     *
     * @param view Android View.
     */
    public void onClick(View view) {

        // Clear tabs
        container.removeAllViews();

        // Reload book from save file
        if (!firstRead) {
            try {
                reading.loadBooksCopyFile(con);
                favorites.loadBooksCopyFile(con);
                catalog.loadBooksCopyFile(con);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Determining which TextView was clicked.
        for (ConstraintLayout tab : layouts) {
            if (view.getId() == tab.getId()) {

                // Change the Layout color to denote current 'tab' is selected.
                tab.setBackgroundColor(Color.parseColor("#524841"));

                // Add and display books from a given category on the main LinearLayout within MainActivity.
                switch ((int)tab.getTag()) {

                    case 1: // CURRENTLY READING.
                        container.removeAllViews();
                        for (Book book : reading.getCurrentlyReading()) {
                            loadBooks(container, book, reading.getCurrentlyReading().indexOf(book));
                        }
                        break;
                    case 2:// FAVORITES.
                        container.removeAllViews();
                        for (Book book : favorites.getFavBooks()) {
                            loadBooks(container, book, favorites.getFavBooks().indexOf(book));
                        }
                        break;
                    case 3: // CATALOG.
                        container.removeAllViews();
                        for (Book book : catalog.getCatalogList())
                            loadBooks(container, book, catalog.getCatalogList().indexOf(book));
                        break;
                    default:
                        System.out.println("\nERROR: MainController -> onClick() -> switch!");
                        break;
                }
            }
            else {
                // Reset the Layout color for each unselected 'tab'.
                tab.setBackgroundColor(Color.parseColor("#72655A"));
            }
        }

        // Determine if addButton was clicked.
        if (view.getId() == addButton.getId()) {
            Intent intent = new Intent(con, AddBookActivity.class);
            con.startActivity(intent);
        }

        // Determine if moveButton was clicked.
        if (view.getId() == moveButton.getId()) {
            Intent intent = new Intent(con, MoveBookActivity.class);
            con.startActivity(intent);
        }
    }

    /**
     * bookClick() method: listens to clicks on each LinearLayouts added within the main LinearLayout 'container'.
     * For each book clicked, the book's name will be passed to a new ViewBookActivity.
     *
     * @param clicked - the book to be clicked
     */
    public void bookClick(LinearLayout clicked) {

        String bName = null;
        for (int i = 0; i < container.getChildCount(); i++) {
            if (container.getChildAt(i).getId() == clicked.getId()) {
                bName = clicked.getTransitionName();
            }
        }
        Intent intent = new Intent(con, ViewBookActivity.class);
        intent.putExtra("name", bName);
        con.startActivity(intent);
    }

    /**
     * loadBooks() method: builds the Layout that will contain the picture, name, and genre of each books.
     *
     * @param cons Main LinearLayout within MainActivity that will contain each subsequent child Layouts.
     * @param book Book object that contains information used to build each child Layout.
     */
    private void loadBooks(LinearLayout cons, Book book, int i) {

        // Converting book name to match that of the correct image file.
        String bName = getBookImageName(book.getName());

        // Child Layout that will contain information about a given book.
        LinearLayout container = new LinearLayout(con);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setTransitionName(book.getName());

        // ImageView object that will contain a corresponding picture of a given book.
        ImageView pic = new ImageView(con);
        ViewGroup.LayoutParams picParam = new ViewGroup.LayoutParams(132, 200);
        int imageSource = con.getResources().getIdentifier(bName, "drawable", con.getPackageName());
        pic.setLayoutParams(picParam);
        pic.setImageResource(imageSource);
        pic.setPadding(5, 5, 0, 0);

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

        // Setting child views to unClickable.
        pic.setClickable(false);
        textContainer.setClickable(false);
        bookName.setClickable(false);
        bookGenre.setClickable(false);

        // Adding TextViews to 'textContainer'.
        textContainer.addView(bookName);
        textContainer.addView(bookGenre);

        // Adding all other child views to container.
        container.addView(pic);
        container.addView(textContainer);

        // Adding container to the main LinearLayout within MainActivity.
        cons.addView(container);

        // Setting container containing book data to a separate onClick() listener. (bookClick())
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookClick(container);
            }
        });
    }

    /**
     * getBookImageName() method: takes in a string and converts the string to lower case
     * and replace all spaces with "_".
     *
     * @param n book name.
     * @return Converted string of n.
     */
    public static String getBookImageName(String n) {
        if (n == null) return "NULL";
        return n.replaceAll(" ", "_").toLowerCase();
    }

    /**
     * Getter: FavoritesBook, gets a favorites book
     *
     * @return favorites
     */
    public static FavoritesBook getFavorites() {
        return favorites;
    }

    /**
     * Getter: CurrentlyReading, gets a currently reading book
     *
     * @return reading
     */
    public static CurrentlyReading getReading() {
        return reading;
    }

    /**
     * Getter: Catalog, gets a catalog book
     *
     * @return catalog
     */
    public static Catalog getCatalog() {
        return catalog;
    }
}
