package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.robotium.solo.Solo;

/**
 * Created by Hailey on 2016-03-31.
 */
public class TestUseCaseStatus extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    // TODO: possibly make sure the condition is true is setup
    private String username = "hailey123";  // must be a user with at least 1 chicken

    public TestUseCaseStatus() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        // run before each test case
        solo = new Solo(getInstrumentation());
        getActivity();

        // enter the app
        solo.unlockScreen();
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), username);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        // run after each test case
        solo.finishOpenedActivities();
    }

    public void testChickenHasStatus() {
        // go to chicken lists screen
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ItemViews.class);

        // select a chicken of yours
        solo.clickOnText(solo.getString(R.string.item_profile_owned));
        solo.clickInList(0);
        solo.assertCurrentActivity("Expected View My Chicken Activity", MyChickenDisplayProfileActivity.class);

        String currentDescription = ((TextView) solo.getView(R.id.status)).getText().toString();
        assertTrue(currentDescription.equals("AVAILABLE") || currentDescription.equals("BORROWED") ||
            currentDescription.equals("BIDDED") || currentDescription.equals("NOT_AVAILABLE"));
    }
}
