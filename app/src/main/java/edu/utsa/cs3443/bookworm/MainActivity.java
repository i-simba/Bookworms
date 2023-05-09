package edu.utsa.cs3443.bookworm;

/**
 *
 * MainActivity: This class extends AppCompatActivity and establishes a
 *                controller along with the set up of buttons
 *
 * @author bde817, rmy353, epg080, udz391
 *
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import java.io.IOException;
import edu.utsa.cs3443.bookworm.controller.MainController;

public class MainActivity extends AppCompatActivity {

    private MainController ctrl = null;

    /**
     * onCreate() method: initialized the MainActivity activity and creates objects
     *                    associated with it
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning View ConstraintLayouts to local ConstraintLayouts.
        ConstraintLayout rC = findViewById(R.id.readingTab);
        ConstraintLayout fC = findViewById(R.id.favoriteTab);
        ConstraintLayout cC = findViewById(R.id.catalogTab);

        // Assigning View Button to local Button.
        Button b = findViewById(R.id.addBookButton);
        Button m = findViewById(R.id.moveBookButton);

        // Assigning LinearLayout with local LinearLayout
        LinearLayout container = findViewById(R.id.bookList);

        try {
            ctrl = new MainController(container, this, rC, fC, cC, b, m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}