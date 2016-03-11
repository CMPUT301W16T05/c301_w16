package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

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
    private TextView mEmailView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (TextView) findViewById(R.id.email);

        final Intent intent = new Intent(this, HomeActivity.class);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                attemptLoginDummy();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLoginDummy() {}
    private void attemptLogin() {
        String username = mEmailView.getText().toString();
        UserController userController = ChickBidsApplication.getUserController();
        User user = userController.getUser(username);

        if (user == null) {
            Intent intent = new Intent(this, AddProfileActivity.class);
            intent.putExtra(EditProfileActivity.CREATE_USER_USERNAME_EXTRA_KEY, username);
            startActivityForResult(intent, EditProfileActivity.CREATE_USER_EXTRA_REQ_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: Get user here back from activity
        User user = null;

        switch (requestCode) {
            case EditProfileActivity.CREATE_USER_EXTRA_REQ_CODE:
                user = data.getExtras().getParcelable(EditProfileActivity.CREATE_USER_EXTRA_KEY);
                break;
            default:
                break;
        }

        UserController userController = ChickBidsApplication.getUserController();
        userController.setUser(user);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
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