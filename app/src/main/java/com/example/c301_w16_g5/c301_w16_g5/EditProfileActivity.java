package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

/**
 * This is the view the user sees when they are updating their profile information.
 * This will be done in editable text fields.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     AddProfileActivity
 * @see     UserProfileActivity
 * @see     UserController
 */
public class EditProfileActivity extends ChickBidActivity {

    public static final String CREATE_USER_USERNAME_EXTRA_KEY = "create_user_username_extra_key";
    public static final String UPDATE_USER_EXTRA_KEY = "update_user_extra_key";

    private EditText usernameEditText;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText experienceEditText;

    private User updated_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* TODO: Check the incoming intent's extra for either of the
         keys defined above. Edit or create user depending on the
         extra key passed in.
         Ideally, update the updated_user locally and then override
         onPause or something and call updateUser() below. */

        //setContentView(R.layout.activity_edit_profile);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        experienceEditText = (EditText) findViewById(R.id.experienceEditText);
    }

    @Override
    protected void onStart() {
        super.onStart();

        User user = ChickBidsApplication.getUserController().getCurrentUser();

        usernameEditText.setText(user.getUsername());
        nameEditText.setText("Username: " + user.getFirstName() + " " + user.getLastName());
        emailEditText.setText("Email: " + user.getEmail());
        phoneEditText.setText("Phone: " + user.getPhoneNumber());
        experienceEditText.setText("Experience: " + user.getChickenExperience());
    }

    private void updateUser() {
        ChickBidsApplication.getUserController().setUser(updated_user);
    }
}
