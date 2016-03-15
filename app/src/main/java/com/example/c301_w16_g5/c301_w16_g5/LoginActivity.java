package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 *
 * @author  Satyen
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     UserController
 */
public class LoginActivity extends ChickBidActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // get username from EditText
                attemptLogin();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        String username = ((EditText) findViewById(R.id.usernameEntered)).getText().toString();
        UserController userController = ChickBidsApplication.getUserController();

        if (username.trim().length() < 1 || userController.validateNames(username) == false) {
            Toast.makeText(getApplicationContext(),
                    "Username should contain letters and numbers only.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        user = userController.getUser(username);

        Intent intent;
        if (user == null) {
            intent = new Intent(this, EditProfileActivity.class);
            intent.putExtra(EditProfileActivity.PROFILE_EXTRA_KEY, EditProfileActivity.CREATE_USER_EXTRA_KEY);
        } else {
            userController.setUser(user);
            intent = new Intent(this, HomeActivity.class);
        }
        startActivity(intent);
    }
}