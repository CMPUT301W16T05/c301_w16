package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;

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

    public LoginActivityUITest() {
        super(LoginActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
    }

    public void testTextFieldsVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.email));

        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.password));
    }

    public void testButtonVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
            activity.findViewById(R.id.email_sign_in_button));
    }

}
