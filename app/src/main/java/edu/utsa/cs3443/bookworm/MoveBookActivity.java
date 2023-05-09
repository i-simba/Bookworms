package edu.utsa.cs3443.bookworm;

/**
 * MoveBookActivity: This class extends AppCompatActivity and establishes a
 *                    controller along with the set up of buttons
 *
 * @author bde817, rmy353, epg080, udz391
 */

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import edu.utsa.cs3443.bookworm.controller.MoveBookController;

public class MoveBookActivity extends AppCompatActivity {

    private MoveBookController ctrl = null;

    /**
     * onCreate() method: initialized the MoveBookActivity activity and creates objects
     *                    associated with it
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movebook);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Assigning View TextInputEditTexts to local TextInputEditTexts.
        TextInputEditText title = findViewById(R.id.moveTitleText);

        // Assigning View Buttons to local Buttons.
        Button moveFav = findViewById(R.id.moveToFavoritesButton);
        Button moveRead = findViewById(R.id.moveToReadButton);

        // Assigning View LinearLayout to local LinearLayout.
        LinearLayout container = findViewById(R.id.bookListLinearLayout);

        // Initializing MoveBookController with locally allocated Views.
        ctrl = new MoveBookController(this, moveFav, moveRead, title, container);
    }
}
