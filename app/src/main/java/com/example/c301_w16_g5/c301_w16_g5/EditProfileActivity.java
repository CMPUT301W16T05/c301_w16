package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;

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

    public static final int CREATE_USER_EXTRA_REQ_CODE = 11;
    public static final String CREATE_USER_EXTRA_KEY = "create_user_extra_key";
    public static final String CREATE_USER_USERNAME_EXTRA_KEY = "create_user_username_extra_key";

    public static final int UPDATE_USER_EXTRA_REQ_CODE = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_profile);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
