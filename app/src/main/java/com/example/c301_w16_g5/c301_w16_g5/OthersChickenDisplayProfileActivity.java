package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OthersChickenDisplayProfileActivity extends ChickBidActivity {

    TextView name;
    TextView description;
    TextView status;
    TextView ownerUsername;

    EditText bid;

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

        bid = (EditText) findViewById(R.id.bid);

        buttonViewBids = (Button) findViewById(R.id.buttonViewBids);
        buttonViewPhoto = (Button) findViewById(R.id.buttonViewPhoto);
        buttonViewLocation = (Button) findViewById(R.id.buttonViewLocation);

        final Intent viewPhotoIntent = new Intent(this, ViewPhotoActivity.class);
        buttonViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentChicken.getPicture() != null) {
                    startActivity(viewPhotoIntent);
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
}
