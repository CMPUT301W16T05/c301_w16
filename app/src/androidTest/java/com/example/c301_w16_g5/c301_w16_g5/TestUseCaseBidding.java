package com.example.c301_w16_g5.c301_w16_g5;

import android.support.v7.widget.SearchView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

/**
 * Created by Satyen on 2016-04-03.
 */
public class TestUseCaseBidding extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private String username = "hailey123";

    public TestUseCaseBidding() {
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

    public void testPlaceBid() {
        // go to add chicken screen
        solo.clickOnView(solo.getView(R.id.buttonSearch));
        solo.assertCurrentActivity("Expected Search Activity", SearchesActivity.class);
        solo.clickOnView(solo.getView(R.id.search));
        solo.enterText(0, "Bob");
        solo.sendKey(Solo.ENTER);
        //TODO: select chichken and place bid
    }

    public void testSeeMyBidChickenList() {
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);

        String tab = solo.getString(R.string.item_profile_bids_placed);

        solo.clickOnText(tab);
        assertTrue(solo.getView(R.id.chickenList).getVisibility() == View.VISIBLE);
    }

    public void testReceiveNewBidNotification() {
        
    }
}
