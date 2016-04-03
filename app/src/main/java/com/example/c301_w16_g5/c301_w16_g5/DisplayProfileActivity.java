package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <code>DisplayProfileActivity</code> provides a view of a user's profile information.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     UserController
 */
public class
        DisplayProfileActivity extends ChickBidActivity {

    public static final String KEY_DISPLAY_USER = "displayUser";
    public static final int CURR_USER = 1;
    public static final int OTHER_USER = 2;

    private TextView usernameTextView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView experienceTextView;

    Button sendMessageButton;
    FloatingActionButton editProfileFAB;

    private User user;

    private Intent sendMessageIntent;
    private Intent editProfileIntent;

//    public static final int EDIT_PROFILE_REQUEST = 1;  // The add request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        phoneTextView = (TextView) findViewById(R.id.phoneNumberTextView);
        experienceTextView = (TextView) findViewById(R.id.chickenExperienceTextView);

        sendMessageIntent = new Intent(this, SendMessageActivity.class);
        sendMessageIntent.putExtra(SendMessageActivity.USERNAME_KEY, SendMessageActivity.A_USERNAME);

        editProfileIntent = new Intent(this, EditProfileActivity.class);
        editProfileIntent.putExtra(EditProfileActivity.PROFILE_EXTRA_KEY, EditProfileActivity.UPDATE_USER_EXTRA_KEY);

        // TODO: disable fab if user profile is not of logged in user
        // should perform the check and disable in onStart. create a method and call it in onStart

        sendMessageButton = (Button) findViewById(R.id.buttonSendMessage);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivityForResult(editProfileIntent, EDIT_PROFILE_REQUEST);
                if (ChickBidsApplication.getSearchController().checkOnline()) {
                    startActivity(sendMessageIntent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Cannot send message offline.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        editProfileFAB = (FloatingActionButton) findViewById(R.id.fab);
        editProfileFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivityForResult(editProfileIntent, EDIT_PROFILE_REQUEST);
                if (ChickBidsApplication.getSearchController().checkOnline()) {
                    startActivity(editProfileIntent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Cannot edit profile offline.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle b = getIntent().getExtras();

        if (b != null && b.getInt(KEY_DISPLAY_USER) == OTHER_USER) {
            String username = b.getString(OthersChickenDisplayProfileActivity.OTHER_USERNAME);
            user = ChickBidsApplication.getSearchController().getUserFromDatabase(username);
            editProfileFAB.setVisibility(View.INVISIBLE);
            editProfileFAB.setClickable(false);
            sendMessageButton.setVisibility(View.VISIBLE);
            sendMessageButton.setClickable(true);
            sendMessageIntent.putExtra(SendMessageActivity.USERNAME, user.getUsername());
        } else {
            user = ChickBidsApplication.getUserController().getCurrentUser();
        }
        usernameTextView.setText(user.getUsername());
        nameTextView.setText(user.getFirstName() + " " + user.getLastName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhoneNumber());
        experienceTextView.setText(user.getChickenExperience());
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChickBidsApplication.getChickenController().popupNotificationToast(this);
    }
}
