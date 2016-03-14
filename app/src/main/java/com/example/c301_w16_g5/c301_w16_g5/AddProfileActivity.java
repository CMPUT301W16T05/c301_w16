package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

/**
 * This is the view the user sees when they are initially inputting their profile information.
 * The information will be adding by typing into text fields.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     EditProfileActivity
 * @see     DisplayProfileActivity
 * @see     UserController
 */
public class AddProfileActivity extends ChickBidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

}
