package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hailey on 2016-03-29.
 */
public class MyChickenDisplayProfileActivityTest extends ActivityInstrumentationTestCase2 {
    public MyChickenDisplayProfileActivityTest() {
        super(MyChickenDisplayProfileActivity.class);
    }

    public void testStart() throws Exception {
        User user = new User("un", "f", "l", "abc@email.com", "780-123-4567", "some");
        ChickBidsApplication.getChickenController().setCurrentChicken(new Chicken("Bob", "Friendly chicken", user.getUsername()));
        Activity activity = getActivity();
    }
}