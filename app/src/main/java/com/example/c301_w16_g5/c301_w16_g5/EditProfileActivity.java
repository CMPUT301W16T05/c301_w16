package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
public class EditProfileActivity extends UserProfileActivity {

    public static final String CREATE_USER_USERNAME_EXTRA_KEY = "create_user_username_extra_key";
    public static final String UPDATE_USER_EXTRA_KEY = "update_user_extra_key";

    private EditText e_usernameTextView;
    private EditText e_nameTextView;
    private EditText e_emailTextView;
    private EditText e_phoneTextView;
    private EditText e_experienceTextView;

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

        FloatingActionButton editProfileFAB = (FloatingActionButton) findViewById(R.id.fab);
        editProfileFAB.setVisibility(View.GONE);
    }

    private void updateUser() {
        ChickBidsApplication.getUserController().setUser(updated_user);
    }
}
