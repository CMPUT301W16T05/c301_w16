package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This view is where a user may add a new chicken their chickens, i.e.
 * the chickens that they may choose to lend out to others for a bidded amount.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     Chicken
 * @see     ChickenController
 * @see     User
 */
public class AddChickenActivity extends ChickBidActivity {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chicken);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEditText = (EditText) findViewById(R.id.name);
        descriptionEditText = (EditText) findViewById(R.id.description);
        addButton = (Button) findViewById(R.id.buttonSave);
    }

    /**
     * Adds a new chicken for the current user based on the input they have
     * provided.
     *
     * @param view  the view associated with button click
     */
    public void addChicken(View view) {
        addButton.setClickable(false);
        Chicken chicken;

        try {
            chicken = getChicken();
        } catch (UserException e) {
            // display error to user and don't update info
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        ChickBidsApplication.getChickenController().saveChickenForMe(chicken);

        if (!ChickBidsApplication.getSearchController().checkOnline()) {
            Toast.makeText(getApplicationContext(),
                    "Chicken saved. Will be pushed to online database once connectivity is restored.",
                    Toast.LENGTH_SHORT).show();
        }

        finish();
    }


    private Chicken getChicken() throws UserException {
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if (!ChickenController.validateChickenName(name)) {
            throw new UserException("Invalid Chicken name.");
        }

        if (!ChickenController.validateChickenDescription(description)) {
            throw new UserException("Invalid Description.");
        }

        return new Chicken(nameEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                ChickBidsApplication.getUserController().getCurrentUser().getUsername());
    }
}
