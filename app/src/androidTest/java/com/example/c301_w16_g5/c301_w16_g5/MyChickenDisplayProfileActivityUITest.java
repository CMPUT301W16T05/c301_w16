package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;

/**
 * Created by Hailey on 2016-03-29.
 */
public class MyChickenDisplayProfileActivityUITest extends ActivityInstrumentationTestCase2{
    Instrumentation instrumentation;
    Activity activity;

    public MyChickenDisplayProfileActivityUITest() {
        super(MyChickenDisplayProfileActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        User user = new User("un", "f", "l", "abc@email.com", "780-123-4567", "some");
        ChickBidsApplication.getChickenController().setCurrentChicken(new Chicken("Bob", "Friendly chicken", user.getUsername()));

        instrumentation = getInstrumentation();
        activity = getActivity();
    }

    public void testToolbarVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.nav_toolbar));
    }
}
