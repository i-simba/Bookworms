package edu.utsa.cs3443.bookworm;

/**
 * ViewBookActivity: This class extends AppCompatActivity and establishes a
 *                   controller along with the set up of buttons
 *
 * @author bde817, rmy353, epg080, udz391
 */

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import edu.utsa.cs3443.bookworm.controller.MainController;
import edu.utsa.cs3443.bookworm.controller.ViewBookController;

public class ViewBookActivity extends AppCompatActivity {

    private ViewBookController ctrl = null;

    /**
     * onCreate() method: initialized the ViewBookActivity activity and creates objects
     *                    associated with it
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbook);

        String temp = getIntent().getStringExtra("name");
        String lower = MainController.getBookImageName(temp);

        // Assigning TextView to local TextView.
        TextView text = findViewById(R.id.BookTitleViewBook);

        // Assigning ImageView to local ImageViews
        ImageView img = findViewById(R.id.BookCoverViewBook);
        ImageView one = findViewById(R.id.oneStar);
        ImageView two = findViewById(R.id.twoStar);
        ImageView three = findViewById(R.id.threeStar);
        ImageView four = findViewById(R.id.fourStar);
        ImageView five = findViewById(R.id.fiveStar);

        // Initializing ViewBookController with locally allocated Views
        ctrl = new ViewBookController(this,temp, lower, text, img, this,
                                    one, two, three, four, five);
        ctrl.loadBookView(temp);
    }
}