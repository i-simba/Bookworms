package edu.utsa.cs3443.bookworm.controller;

/**
 * AddBookController: this class implements View.OnClickListener and
 *                    listens to the selection made by the user. It also initializes passed
 *                    in information as needed
 *
 *
 *
 * @authors bde817, rmy353, epg080, udz391
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import edu.utsa.cs3443.bookworm.AddBookActivity;
import edu.utsa.cs3443.bookworm.R;

public class AddBookController implements View.OnClickListener {

    private final int TITLE = 0;
    private final int SYNOPSIS = 1;
    private final int QUOTE = 2;
    private final int GENRE = 3;
    private final int AUTHOR = 4;
    private Context con = null;
    private ArrayList<TextInputEditText> textInput = null;
    private ArrayList<ImageButton> starButtons = null;
    private Button addPic = null;
    private Button addBook = null;
    private ImageView img = null;
    private int rating;

    /**
     * Constructor: AddBookController, intakes 14 parameters and initializes them accordingly,
     *              also sets listeners for addBook and addPic
     *
     * @param title assigned to 'textInput'
     * @param synopsis assigned to 'textInput'
     * @param quote assigned to 'textInput'
     * @param genre assigned to 'textInput'
     * @param author assigned to 'textInput'
     * @param oneStar assigned to 'starButtons'
     * @param twoStar assigned to 'starButtons'
     * @param threeStar assigned to 'starButtons'
     * @param fourStar assigned to 'starButtons'
     * @param fiveStar assigned to 'starButtons'
     * @param pic assigned to 'assPic'
     * @param book assigned to 'addBook'
     * @param bookImage assigned to 'img'
     * @param ctx assigned to 'con'
     */
    public AddBookController(TextInputEditText title, TextInputEditText synopsis, TextInputEditText quote, TextInputEditText genre, TextInputEditText author,
                             ImageButton oneStar, ImageButton twoStar, ImageButton threeStar, ImageButton fourStar,
                             ImageButton fiveStar, Button pic, Button book, ImageView bookImage,Context ctx) {
        // Assign ctx to local con.
        this.con = ctx;

        // Initialize rating.
        this.rating = 0;

        // Initialize textInput and add corresponding TextInputEditTexts into list.
        this.textInput = new ArrayList<>();
        this.textInput.add(title);
        this.textInput.add(synopsis);
        this.textInput.add(quote);
        this.textInput.add(genre);
        this.textInput.add(author);

        // Set ImageButtons' tags to correspond with their rating. (Out of 5)
        oneStar.setTag(1);
        twoStar.setTag(2);
        threeStar.setTag(3);
        fourStar.setTag(4);
        fiveStar.setTag(5);

        // Initialize starButtons and add corresponding ImageButtons into list.
        this.starButtons = new ArrayList<>();
        this.starButtons.add(oneStar);
        this.starButtons.add(twoStar);
        this.starButtons.add(threeStar);
        this.starButtons.add(fourStar);
        this.starButtons.add(fiveStar);

        // Assign pic Button to addPic and book Button to addBook.
        this.addPic = pic;
        this.addBook = book;

        // Initialize img.
        this.img = bookImage;

        // Set onClick listeners for each starButtons.
        for (ImageButton img : starButtons){
            img.setOnClickListener(this);
        }

        // Set onClick listeners for addBook button.
        this.addBook.setOnClickListener(this);

        // Set onClick listeners for addPic
        this.addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (con instanceof Activity)
                    ((Activity) con).startActivityForResult(intent, 3);
            }
        });
    }

    /**
     * onActivityResult: handles the result of an activity. If parameters are met then
     *                   user's selected image is set
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            img.setImageURI(selectedImage);
        }
    }

    /**
     * onClick() method: processes which button is clicked and
     *                   what actions to take thereafter
     *
     * @param view
     */
    public void onClick(View view) {

        // Determining which ImageButtons was clicked.
        for (ImageButton img : starButtons) {
            if (view.getId() == img.getId()) {
                for (int i = 0; i < starButtons.size(); i++) {
                    if (i < (int)img.getTag()) {
                        starButtons.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                    }
                    else {
                        starButtons.get(i).setImageResource(android.R.drawable.btn_star);
                    }
                }
                rating = (int)img.getTag();
            }
        }

        // Determine if addBook button was clicked.
        if (view.getId() == addBook.getId()) {

            // Local strings used for input validation.
            String title = textInput.get(TITLE).getText().toString().trim();
            String author = textInput.get(AUTHOR).getText().toString().trim();
            String genre = textInput.get(GENRE).getText().toString().trim();

            // Input validation for the following fields - Title/Author/Genre.
            if (title.isEmpty()) {
                textInput.get(TITLE).setError("Please enter a book title!");
                return;
            }
            if (author.isEmpty()) {
                textInput.get(AUTHOR).setError("Please enter a book author!");
                return;
            }
            if (genre.isEmpty()) {
                textInput.get(GENRE).setError("Please enter a book genre!");
                return;
            }

            // Saving book entry with user input data.
            String build = this.textInput.get(TITLE).getText().toString() + "/" + this.textInput.get(SYNOPSIS).getText().toString() + "/" +
                    this.textInput.get(QUOTE).getText().toString() + "/" + this.textInput.get(GENRE).getText().toString() + "/" +
                    this.textInput.get(AUTHOR).getText().toString() + "/" + rating + "/catalog\n";
            OutputStream out = null;
            try {
                out = con.openFileOutput("copyfile.csv", Context.MODE_APPEND);
                out.write(build.getBytes(StandardCharsets.UTF_8));
                out.close();
                saveImage(img);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Toast.makeText(con, this.textInput.get(0).getText().toString() + " was added", Toast.LENGTH_SHORT).show();
            
            // finishes child activity
            if (con instanceof AddBookActivity) {
                ((Activity)con).finish();
            }
        }
    }

    /**
     * saveImage: creates and assigns space for an added image
     *
     * @param imageView
     */
    private void saveImage(ImageView imageView) {

        // ImageView input validation.
        if (imageView.getDrawable() == null) {
            img.setImageResource(R.drawable.book_placeholder);
        }

        // Get the bitmap from the ImageView
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

        // Create the Images directory
        File dir = new File(con.getFilesDir(), "Images");
        if (!dir.exists()) {
            dir.mkdir();
        }

        String bName = MainController.getBookImageName(textInput.get(TITLE).getText().toString());

        // Create a file for the image
        File file = new File(dir, bName + ".png");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            // Save the bitmap to the file
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
