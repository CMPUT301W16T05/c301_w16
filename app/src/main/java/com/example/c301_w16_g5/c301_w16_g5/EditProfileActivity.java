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

    private User testUser; // TODO: remove

    public static final int CREATE_USER_EXTRA_REQ_CODE = 11;
    public static final String CREATE_USER_EXTRA_KEY = "create_user_extra_key";
    public static final String CREATE_USER_USERNAME_EXTRA_KEY = "create_user_username_extra_key";

    public static final int UPDATE_USER_EXTRA_REQ_CODE = 21;

    private EditText usernameEditText;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText experienceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: remove
        testUser = new User("Test_User", "Test", "User", "test@user.com", "1234", "None");

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        experienceEditText = (EditText) findViewById(R.id.experienceEditText);
    }

    @Override
    protected void onStart() {
        super.onStart();

        usernameEditText.setText(testUser.getUsername());
        nameEditText.setText("Username: " + testUser.getFirstName() + " " + testUser.getLastName());
        emailEditText.setText("Email: " + testUser.getEmail());
        phoneEditText.setText("Phone: " + testUser.getPhoneNumber());
        experienceEditText.setText("Experience: " + testUser.getChickenExperience());
    }
}
