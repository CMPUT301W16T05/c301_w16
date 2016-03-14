package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.EditText;

/**
 * UI test for the app's Login screen.
 *
 * @author  Hailey
 * @version 1.4, 03/07/2016
 * @see     LoginActivity
 */
public class LoginActivityUITest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    Activity activity;
    EditText usernameEditText;

    public LoginActivityUITest() {
        super(LoginActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        usernameEditText = (EditText) activity.findViewById(R.id.usernameEntered);
    }

    public void testTextFieldsVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), usernameEditText);

        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.appTitle));
    }

    public void testButtonVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
            activity.findViewById(R.id.signInButton));
    }

}
