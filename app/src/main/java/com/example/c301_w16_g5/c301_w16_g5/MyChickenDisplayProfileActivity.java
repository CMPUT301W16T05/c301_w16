package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <code>MyChickenDisplayProfileActivity</code> displays the profile
 * information of a chicken, and gives the viewing user options such as
 * editing that chicken.  This activity will only be called if the chicken
 * whose profile is being viewed belongs to the current user.
 *
 * @author  Robin, Hailey
 * @version 1.5, 03/27/2016
 * @see     Chicken
 * @see     ChickenController
 */
public class MyChickenDisplayProfileActivity extends ChickBidActivity {

    public static final int RESULT_LOAD_IMAGE = 1;
    public static final int RESULT_UPDATE_CHICKEN = 2;

    TextView name;
    TextView description;
    TextView status;

    Button buttonEdit;
    Button buttonViewBids;
    Button buttonViewPhoto;

    Chicken currentChicken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chicken_display_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        status = (TextView) findViewById(R.id.status);

        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonViewBids = (Button) findViewById(R.id.buttonViewBids);
        buttonViewPhoto = (Button) findViewById(R.id.buttonViewPhoto);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ChickBidsApplication.getSearchController().checkOnline()) {
                    launchEditChicken();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Cannot edit chickens offline.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Intent viewBidsIntent = new Intent(this, BidsActivity.class);
        buttonViewBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ChickBidsApplication.getSearchController().checkOnline()) {
                    startActivity(viewBidsIntent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Cannot view bids offline.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentChicken.getPhoto() != null) {
                    launchViewPhoto();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "No photo available.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentChicken = ChickBidsApplication.getChickenController().getCurrentChicken();
        setTextFields();
    }

    private void setTextFields() {
        name.setText(currentChicken.getName());
        description.setText(currentChicken.getDescription());
        status.setText(currentChicken.getChickenStatus().toString());
    }

    private void launchEditChicken(){
        Intent editChickenIntent = new Intent(this, EditChickenActivity.class);
        startActivity(editChickenIntent);
    }
    private void launchViewPhoto(){
        Intent viewPhotoIntent = new Intent(this, ViewPhotoActivity.class);
        startActivity(viewPhotoIntent);
    }
}