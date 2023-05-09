package edu.utsa.cs3443.bookworm;

/**
 *
 * AddBookActivity: This class extends AppCompatActivity and establishes a
 *                  controller along with the set up of buttons
 *
 * @author bde817, rmy353, epg080, udz391
 *
 */

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import edu.utsa.cs3443.bookworm.controller.AddBookController;

public class AddBookActivity extends AppCompatActivity {

    private AddBookController ctrl = null;

    /**
     * onCreate() method: initialized the AddBookActivity activity and creates objects
     *                    associated with it
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        // Assigning View TextInputEditTexts to local TextInputEditTexts.
        TextInputEditText titleInput = findViewById(R.id.titleInputEditText);
        TextInputEditText synopsisInput = findViewById(R.id.synopsisInputEditText);
        TextInputEditText quoteInput = findViewById(R.id.quoteInputEditText);
        TextInputEditText authorInput = findViewById(R.id.authorInputEditText);
        TextInputEditText genreInput = findViewById(R.id.genreInputEditText);

        // Assigning View ImageView to local ImageView.
        ImageView img = findViewById(R.id.bookImage);

        // Assigning View Buttons to local Buttons.
        ImageButton oneStar = findViewById(R.id.oneStarButton);
        ImageButton twoStar = findViewById(R.id.twoStarButton);
        ImageButton threeStar = findViewById(R.id.threeStarButton);
        ImageButton fourStar = findViewById(R.id.fourStarButton);
        ImageButton fiveStar = findViewById(R.id.fiveStarButton);
        Button addPic = findViewById(R.id.addPicButton);
        Button addBook = findViewById(R.id.confirmBookAddButton);

        // Initializing AddBookController with locally allocated Views.
        ctrl = new AddBookController(titleInput, synopsisInput, quoteInput, genreInput, authorInput,
                oneStar, twoStar, threeStar, fourStar, fiveStar, addPic, addBook, img, this);
    }

    /**
     * onActivityResult: handles the result of an activity
     *
     * @param requestCode - identifies the request
     * @param resultCode - indicates result of activity
     * @param data - Intent data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ctrl.onActivityResult(requestCode, resultCode, data);
    }
}
