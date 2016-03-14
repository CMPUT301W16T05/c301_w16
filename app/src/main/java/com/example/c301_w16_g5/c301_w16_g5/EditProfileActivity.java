package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is the view the user sees when they are updating their profile information.
 * This will be done in editable text fields.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     AddProfileActivity
 * @see     DisplayProfileActivity
 * @see     UserController
 */
public class EditProfileActivity extends ChickBidActivity {

    public static final String CREATE_USER_USERNAME_EXTRA_KEY = "create_user_username_extra_key";
    public static final String UPDATE_USER_EXTRA_KEY = "update_user_extra_key";

    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText experienceEditText;

    private User user;

    private String activityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* TODO: Check the incoming intent's extra for either of the
         keys defined above. Edit or create user depending on the
         extra key passed in.
         Ideally, update the updated_user locally and then override
         onPause or something and call updateUser() below. */

        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        activityType = intent.getStringExtra("PROFILE_REQUEST");
        if (activityType.equals(CREATE_USER_USERNAME_EXTRA_KEY)) {
            // source:
            // http://stackoverflow.com/questions/3438276/change-title-bar-text-in-android
            // answered by Paresh Mayani on Aug 9 '10
            // accessed by Hailey on Mar 13 '16
            getSupportActionBar().setTitle("Add Profile");
        } else if (activityType.equals(UPDATE_USER_EXTRA_KEY)) {

        }

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        experienceEditText = (EditText) findViewById(R.id.experienceEditText);
    }

    @Override
    protected void onStart() {
        super.onStart();

        user = ChickBidsApplication.getUserController().getCurrentUser();

        usernameEditText.setText(user.getUsername());
        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        emailEditText.setText(user.getEmail());
        phoneEditText.setText(user.getPhoneNumber());
        experienceEditText.setText(user.getChickenExperience());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (activityType.equals(CREATE_USER_USERNAME_EXTRA_KEY)) {
            menu.clear();
            onCreateOptionsMenu(menu);
            menu.removeItem(R.id.home_button);
            menu.removeItem(R.id.notifications_button);
        }
        return true;
    }

    public void updateUser(View view) {
        try {
            getUpdatedUserInfo();
        } catch (UserException e) {
            // display error to user and don't update info
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent;
        if (activityType.equals(UPDATE_USER_EXTRA_KEY)) {
            intent = new Intent(this, DisplayProfileActivity.class);
            ChickBidsApplication.getUserController().updateUser(user);
            startActivity(intent);
        } else if (activityType.equals(CREATE_USER_USERNAME_EXTRA_KEY)) {
            intent = new Intent(this, HomeActivity.class);
            ChickBidsApplication.getUserController().saveUser(user);
            startActivity(intent);
        }
    }


    private void getUpdatedUserInfo() throws UserException {
        String username = ((EditText) findViewById(R.id.usernameEditText)).getText().toString();

        if (ChickBidsApplication.getUserController().usernameInUse(username)) {
            throw new UserException("Username already in use");
        }

        if (ChickBidsApplication.getUserController().validateNames(username)) {
            user.setUsername(username);
        } else {
            throw new UserException("Invalid username");
        }

        String firstName = ((EditText) findViewById(R.id.firstNameEditText)).getText().toString();
        if (ChickBidsApplication.getUserController().validateNames(firstName)) {
            user.setFirstName(firstName);
        } else {
            throw new UserException("Invalid first name");
        }

        String lastName = ((EditText) findViewById(R.id.lastNameEditText)).getText().toString();
        if (ChickBidsApplication.getUserController().validateNames(lastName)) {
            user.setLastName(lastName);
        } else {
            throw new UserException("Invalid last name");
        }

        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        if (ChickBidsApplication.getUserController().validateEmail(email)) {
            user.setEmail(email);
        } else {
            throw new UserException("Invalid email");
        }

        String phone = ((EditText) findViewById(R.id.phoneEditText)).getText().toString();
        if (ChickBidsApplication.getUserController().validatePhoneNumber(phone)) {
            user.setPhoneNumber(phone);
        } else {
            throw new UserException("Invalid phone number");
        }

        String experience = ((EditText) findViewById(R.id.experienceEditText)).getText().toString();
        if (ChickBidsApplication.getUserController().validateChickenExperience(experience)) {
            user.setChickenExperience(experience);
        } else {
            throw new UserException("Invalid chicken experience description");
        }
    }

}
