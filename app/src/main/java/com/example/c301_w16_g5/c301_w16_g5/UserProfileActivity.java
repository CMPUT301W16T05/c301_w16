package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * <code>UserProfileActivity</code> provides a view of a user's profile information.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     UserController
 */
public class UserProfileActivity extends ChickBidActivity {

    private User testUser; // TODO: remove

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: remove
        testUser = new User("Test_User", "Test", "User", "test@user.com", "1234", "None");
    }

    @Override
    protected void onStart() {
        super.onStart();

        
    }


}
