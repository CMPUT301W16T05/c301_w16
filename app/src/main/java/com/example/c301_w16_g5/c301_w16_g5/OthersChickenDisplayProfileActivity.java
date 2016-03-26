package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

        Bundle b = getIntent().getExtras();
        currentChicken = b.getParcelable("selectedChicken");

        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        status = (TextView) findViewById(R.id.status);
        ownerUsername = (TextView) findViewById((R.id.ownerUsername));

        bid = (EditText) findViewById(R.id.bid);

        buttonViewBids = (Button) findViewById(R.id.buttonViewBids);
        buttonViewPhoto = (Button) findViewById(R.id.buttonViewPhoto);
        buttonViewLocation = (Button) findViewById(R.id.buttonViewLocation);

        // TODO: on click listeners
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTextFields();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case (RESULT_UPDATE_CHICKEN): {
//                if (resultCode == Activity.RESULT_OK) {
//                    currentChicken = data.getParcelableExtra(EditChickenActivity.EDITED_CHICKEN);
//                    setTextFields(currentChicken);
//                    break;
//                }
//            }
//        }
//    }

    private void setTextFields(){
        name.setText(currentChicken.getName());
        description.setText(currentChicken.getDescription());
        status.setText(currentChicken.getChickenStatus().toString());

        ownerUsername.setText("Owned by " + currentChicken.getOwnerUsername());
    }
}
