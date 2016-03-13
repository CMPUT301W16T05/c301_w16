package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hailey on 2016-03-12.
 */
public class DisplayProfileActivityTest extends ActivityInstrumentationTestCase2{

    public DisplayProfileActivityTest() {
        super(DisplayProfileActivity.class);
    }

    public void testStart() throws Exception {
        ChickBidsApplication.getUserController().setUser(new User("un", "f", "l", "abc@email.com", "780-123-4567", "some"));
        Activity activity = getActivity();
    }
}
