package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hailey on 2016-03-29.
 */
public class OthersChickenDisplayProfileActivityTest extends ActivityInstrumentationTestCase2 {
    public OthersChickenDisplayProfileActivityTest() {
        super(OthersChickenDisplayProfileActivity.class);
    }

    public void testStart() throws Exception {
        ChickBidsApplication.getUserController().setUser(new User("un", "f", "l", "abc@email.com", "780-123-4567", "some"));
        Activity activity = getActivity();
    }
}