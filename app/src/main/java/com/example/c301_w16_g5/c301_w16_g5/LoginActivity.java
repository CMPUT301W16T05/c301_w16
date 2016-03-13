package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
//    private TextView mEmailView;
//    private View mProgressView;
//    private View mLoginFormView;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // get username from EditText
                attemptLogin();
            }
        });

//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
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
            intent = new Intent(this, AddProfileActivity.class);
//            intent.putExtra(EditProfileActivity.CREATE_USER_USERNAME_EXTRA_KEY, username);
        } else {
            userController.setUser(user);
            intent = new Intent(this, HomeActivity.class);
        }
        startActivity(intent);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;

        UserLoginTask(String email) {
            mEmail = email;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }
}