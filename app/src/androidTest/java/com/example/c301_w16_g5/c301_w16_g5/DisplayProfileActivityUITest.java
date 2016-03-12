package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;

/**
 * UI test for the app's Display Profile screen.
 *
 * @author  Hailey
 * @version 1.4, 03/07/2016
 * @see     EditProfileActivity
 * @see     User
 */
public class DisplayProfileActivityUITest extends ActivityInstrumentationTestCase2 {
    Instrumentation instrumentation;
    Activity activity;

    public DisplayProfileActivityUITest() {
        super(DisplayProfileActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        ChickBidsApplication.getUserController().setUser(new User("un", "f", "l", "abc@email.com", "780-123-4567", "some"));
        activity = getActivity();
    }

    public void testViewVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.nav_toolbar));
    }

    public void testTextVisible() {
        fail();  // TODO
    }

    public void testButtonVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.fab));
    }
}
