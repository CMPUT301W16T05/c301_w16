package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public class ChickenViewsActivityTest extends ActivityInstrumentationTestCase2 {
    public ChickenViewsActivityTest() {
        super(ChickenViewsActivity.class);
    }

    public void testStart() throws Exception {
        ChickBidsApplication.getUserController().setUser(new User("un", "f", "l", "abc@email.com", "780-123-4567", "some"));
        Activity activity = getActivity();
    }

}