package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * <code>DisplayProfileActivity</code> provides a view of a user's profile information.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     UserController
 */
public class DisplayProfileActivity extends ChickBidActivity {

    private TextView usernameTextView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView experienceTextView;

    private User user;

    private Intent editProfileIntent;

    public static final int EDIT_PROFILE_REQUEST = 1;  // The add request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        phoneTextView = (TextView) findViewById(R.id.phoneNumberTextView);
        experienceTextView = (TextView) findViewById(R.id.chickenExperienceTextView);

        editProfileIntent = new Intent(this, EditProfileActivity.class);
        editProfileIntent.putExtra("PROFILE_REQUEST", EditProfileActivity.UPDATE_USER_EXTRA_KEY);

        // TODO: disable fab if user profile is not of logged in user
        // should perform the check and disable in onStart. create a method and call it in onStart
        FloatingActionButton editProfileFAB = (FloatingActionButton) findViewById(R.id.fab);
        editProfileFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivityForResult(editProfileIntent, EDIT_PROFILE_REQUEST);
                startActivity(editProfileIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        user = ChickBidsApplication.getUserController().getCurrentUser();

        usernameTextView.setText(user.getUsername());
        nameTextView.setText("Username: " + user.getFirstName() + " " + user.getLastName());
        emailTextView.setText("Email: " + user.getEmail());
        phoneTextView.setText("Phone: " + user.getPhoneNumber());
        experienceTextView.setText("Experience: " + user.getChickenExperience());
    }

//    /** Called upon return to DisplayProfileActivity */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch(requestCode) {
//            case (EDIT_PROFILE_REQUEST) : {
//                if (resultCode == Activity.RESULT_OK) {
//
//                } else if (resultCode == Activity.RESULT_CANCELED) {}
//                break;
//            }
//        }
//    }
}
