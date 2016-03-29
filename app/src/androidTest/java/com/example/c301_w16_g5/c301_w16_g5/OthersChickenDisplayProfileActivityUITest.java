package com.example.c301_w16_g5.c301_w16_g5;

/**
 * Created by Hailey on 2016-03-29.
 */

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.TextView;

/**
 * Created by Hailey on 2016-03-29.
 */
public class OthersChickenDisplayProfileActivityUITest extends ActivityInstrumentationTestCase2 {
    Instrumentation instrumentation;
    Activity activity;
    Chicken currentChicken;
    User user;

    public OthersChickenDisplayProfileActivityUITest() {
        super(OthersChickenDisplayProfileActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        user = new User("un", "f", "l", "abc@email.com", "780-123-4567", "some");
        currentChicken = new Chicken("Bob", "Friendly chicken", user.getUsername());
        ChickBidsApplication.getChickenController().setCurrentChicken(currentChicken);

        instrumentation = getInstrumentation();
        activity = getActivity();
    }

    public void testToolbarVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.nav_toolbar));
    }

    public void testTextFieldsVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.name));
        assertEquals(currentChicken.getName(), ((TextView) activity.findViewById(R.id.name)).getText());

        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.status));
        assertEquals(currentChicken.getChickenStatus().toString(), ((TextView) activity.findViewById(R.id.status)).getText());

        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.description));
        assertEquals(currentChicken.getDescription(), ((TextView) activity.findViewById(R.id.description)).getText());

        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.ownerUsername));
        assertTrue(((TextView) activity.findViewById(R.id.ownerUsername)).getText().toString().contains(user.getUsername()));
    }

    public void testButtonsVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.buttonBid));

        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.buttonViewBids));

        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.buttonViewPhoto));

        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.buttonViewLocation));
    }
}
