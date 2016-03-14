package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

    public static final String CREATE_CHICKEN_EXTRA_KEY = "create_chicken_extra_key";
    public static final String UPDATE_CHICKEN_EXTRA_KEY = "update_chicken_extra_key";

    private EditText nameEditText;
    private EditText descriptionEditText;
    private Spinner statusSpinner;

    private Chicken chicken;

    private String activityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chicken);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        statusSpinner.setAdapter(new ArrayAdapter<Enum>(this, android.R.layout.simple_spinner_dropdown_item, Chicken.ChickenStatus.values()));
    }

    public void addChicken(View view) {
        try {
            getChickenInfo();
        } catch (UserException e) {
            // display error to user and don't update info
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        finish();
    }


    private void getChickenInfo() throws UserException {
        if (!ChickenController.validateChickenName(nameEditText.getText().toString())) {
            throw new UserException("Invalid Chicken name.");
        } else if (!ChickenController.validateChickenStatus((Chicken.ChickenStatus) statusSpinner.getSelectedItem())) {
            throw new UserException("Invalid status.");
        } else if (!ChickenController.validateChickenDescription(descriptionEditText.getText().toString())) {
            throw new UserException("Invalid Description.");
        } else {
            // TODO: move chicken creation to the controller
            chicken = new Chicken(nameEditText.getText().toString(),
                    descriptionEditText.getText().toString(),
                    ChickBidsApplication.getUserController().getCurrentUser().getUsername());

            ChickBidsApplication.getChickenController().saveChickenForMe(chicken);
        }
    }
}
