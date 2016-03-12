package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;

public class EditProfileActivityUITest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    Activity activity;

    public EditProfileActivityUITest() {
        super(EditProfileActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        ChickBidsApplication.getUserController().setUser(new User("un", "f", "l", "abc@email.com", "780-123-4567", "some"));
        activity = getActivity();
    }

    public void testViewVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.notifications_button));
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.home_button));
    }

    public void testTextVisible() {
        fail();  // TODO
    }

    public void testEditProfile() {
        fail();  // TODO
    }

    public void testButtonVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.buttonSave));
    }
}
