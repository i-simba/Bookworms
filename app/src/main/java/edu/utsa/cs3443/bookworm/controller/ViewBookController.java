package edu.utsa.cs3443.bookworm.controller;

/**
 * ViewBookController: this class implements View.OnClickListener and
 *                     listens to the selection made by the user in order
 *                     to communicate to other classes on how to behave
 *
 * @author bde817, rmy353, epg080, udz391
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import edu.utsa.cs3443.bookworm.R;

public class ViewBookController implements View.OnClickListener {

    private TextView text = null;                               // TextView for book name
    private ImageView img = null;                               // Imageview for book covers
    private Context con = null;                                 // Context of ViewBookActivity
    private Activity activity;                                  // initialization of activity
    private ArrayList<ImageView> buttons = null;                // ArrayList of image buttons

    /**
     * Constructor: ViewBookController, Takes in 11 parameters activity, name, lower, t, i, ctx,
     *              one, two, three, four, five and assigns them accordingly
     *
     * @param activity assigned to 'activity'
     * @param name
     * @param lower
     * @param t assigned to 'text'
     * @param i assigned to 'image'
     * @param ctx assigned to 'con'
     * @param one assigned to 'buttons arrayList'
     * @param two assigned to 'buttons arrayList'
     * @param three assigned to 'buttons arrayList'
     * @param four assigned to 'buttons arrayList'
     * @param five assigned to 'buttons arrayList'
     */
    public ViewBookController(Activity activity, String name, String lower, TextView t, ImageView i, Context ctx,
                            ImageView one, ImageView two, ImageView three, ImageView four, ImageView five) {
        this.text = t;
        this.img = i;
        this.con = ctx;
        this.activity = activity;
        text.setText(name);
        img.setImageResource(con.getResources().getIdentifier(lower, "drawable", con.getPackageName()));

        buttons = new ArrayList<>();
        buttons.add(one);
        buttons.add(two);
        buttons.add(three);
        buttons.add(four);
        buttons.add(five);
    }

    /**
     * onClick
     *
     * @param view
     */
    public void onClick (View view) {

    }

    /**
     *  loadBookView: Intakes the book title of the clicked on book and outputs the corresponding
     *                information of said book from the copyfile
     *
     * @param bookTitle - name of the user chosen book
     */
    public void loadBookView(String bookTitle){
        TextView tvTitle = (TextView) activity.findViewById(R.id.BookTitleViewBook);
        TextView tvQuote = (TextView) activity.findViewById(R.id.QuoteViewBook);
        TextView tvSynopsis = (TextView) activity.findViewById(R.id.SynopsisViewBook);
        TextView tvGenre = (TextView) activity.findViewById(R.id.BookGenreViewBook);
        ImageView tvImage = (ImageView) activity.findViewById(R.id.BookCoverViewBook);
        tvTitle.setText(bookTitle);
        try {
            InputStream in = con.openFileInput("copyfile.csv");                                 //opening file
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            String comp;
            while ((line = reader.readLine()) != null) {                                            //reading line by line
                String[] itemS = line.split("/");
                comp = itemS[0].toLowerCase();                                                      //grabbing title to lowercase
                if (bookTitle.toLowerCase().equals(comp)) {                                         //comparing user book choice to titles
                    tvSynopsis.setText(itemS[1]);                                                   //Pushes matching Synopsis out to the View
                    tvQuote.setText(itemS[2]);                                                      //Pushes matching Quote out to the View
                    tvGenre.setText(itemS[3]);                                                      //Pushes matching Genre out to the View

                    int rating = 0;
                    rating = Integer.parseInt(itemS[5]);                                            //Processing user chosen rating
                    System.out.println("\n\n" + rating + "\n\n");

                    for (ImageView img : buttons) {
                        for (int i = 0; i < buttons.size(); i++) {
                            if (i < rating)
                                buttons.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                            else
                                buttons.get(i).setImageResource(android.R.drawable.btn_star);
                        }
                    }

                    String bImageName = MainController.getBookImageName(bookTitle);
                    if(bImageName == null)
                        tvImage.setImageResource(R.drawable.book_placeholder);
                    else{
                        File imageFile = new File(con.getFilesDir() + "/Images/" + bImageName +".png");
                        int imageSource = con.getResources().getIdentifier(bImageName, "drawable", con.getPackageName());
                        tvImage.setImageResource(imageSource);
                        if (imageFile.exists()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                            tvImage.setImageBitmap(bitmap);
                        }
                    }
                }
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

