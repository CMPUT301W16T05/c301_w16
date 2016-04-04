package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;

/**
 * UI test for the app's SearchController screen.
 *
 * @author  Hailey
 * @version 1.4, 03/07/2016
 * @see     SearchesActivity
 * @see     SearchController
 */

public class SearchesActivityUITest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    Activity activity;
    User user;

    public SearchesActivityUITest() {
        super(SearchesActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        user = new User("un", "f", "l", "abc@email.com", "780-123-4567", "some");
        ChickBidsApplication.getUserController().setUser(user);

        instrumentation = getInstrumentation();
        activity = getActivity();
    }

    public void testViewVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.search));
    }
}

