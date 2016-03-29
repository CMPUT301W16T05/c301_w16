package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <code>OthersChickenDisplayProfileActivity</code> displays the profile
 * information of a chicken, as well as giving the viewing user the options like
 * bidding on that chicken.  This activity will only be called if the chicken
 * whose profile is being viewed does not belong to the current user.
 *
 * @author  Hailey
 * @version 1.5, 03/27/2016
 * @see     Chicken
 * @see     ChickenController
 * @see     Bid
 * @see     User
 */
public class OthersChickenDisplayProfileActivity extends ChickBidActivity {

    TextView name;
    TextView description;
    TextView status;
    TextView ownerUsername;

    EditText bidAmount;

    Button buttonPlaceBid;
    Button buttonViewBids;
    Button buttonViewPhoto;
    Button buttonViewLocation;

    Chicken currentChicken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_chicken_display_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentChicken = ChickBidsApplication.getChickenController().getCurrentChicken();

        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        status = (TextView) findViewById(R.id.status);
        ownerUsername = (TextView) findViewById((R.id.ownerUsername));

        bidAmount = (EditText) findViewById(R.id.bid);

        buttonPlaceBid = (Button) findViewById(R.id.buttonBid);
        buttonViewBids = (Button) findViewById(R.id.buttonViewBids);
        buttonViewPhoto = (Button) findViewById(R.id.buttonViewPhoto);
        buttonViewLocation = (Button) findViewById(R.id.buttonViewLocation);

        buttonPlaceBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeBid();
            }
        });

        buttonViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentChicken.getPicture() != null) {
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

    private void placeBid() {

        if (currentChicken.getId() == null) {
            Toast.makeText(getApplicationContext(), "Failed To Place Bid", Toast.LENGTH_SHORT).show();
            return;
        }

        //FIXME: should this be in views?
        Bid bid = new Bid( ChickBidsApplication.getUserController().getCurrentUser().getUsername(),
                currentChicken.getId(),
                Double.parseDouble(bidAmount.getText().toString()));
        try {
            ChickBidsApplication.getChickenController().putBidOnChicken(bid, currentChicken);
            Toast.makeText(getApplicationContext(), "Bid Placed", Toast.LENGTH_SHORT).show();
        } catch (ChickenException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTextFields();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTextFields(){
        name.setText(currentChicken.getName());
        description.setText(currentChicken.getDescription());
        status.setText(currentChicken.getChickenStatus().toString());

        ownerUsername.setText("Owned by " + currentChicken.getOwnerUsername());
    }

    private void launchViewPhoto(){
        Intent viewPhotoIntent = new Intent(this, ViewPhotoActivity.class);
        startActivity(viewPhotoIntent);
    }
}
