package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.robotium.solo.Solo;

/**
 * Created by Satyen on 2016-04-03.
 */
public class TestUseCaseLocation extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private String username = "hailey123";
    private String username2 = "satyen";

    public TestUseCaseLocation() {
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

    public void testSpecifyLocation() {
        helperPlaceBidAsSecondUser();

        //accept bid
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);
        String tab = solo.getString(R.string.item_profile_owned_with_bids);
        solo.clickOnText(tab);
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.buttonViewBids));
        solo.clickOnView(solo.getView(R.id.acceptBidImageView));

        solo.assertCurrentActivity("Expected Locations Activity", LocationActivity.class);
    }

    public void testViewLocation() {
        //login as second user
        solo.clickOnView(solo.getView(R.id.buttonLogout));
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);
        solo.clearEditText((AutoCompleteTextView) solo.getView(R.id.usernameEntered));
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), username2);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);

        //go to borrowed item
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);
        String tab = solo.getString(R.string.item_profile_borrowed);
        solo.clickOnText(tab);
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.buttonViewLocation));

        solo.assertCurrentActivity("Expected Locations Activity", LocationActivity.class);
    }

    private void helperPlaceBidAsSecondUser() {
        //login as second user
        solo.clickOnView(solo.getView(R.id.buttonLogout));
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);
        solo.clearEditText((AutoCompleteTextView) solo.getView(R.id.usernameEntered));
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), username2);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);

        //search for chickens owned by first user
        solo.clickOnView(solo.getView(R.id.buttonSearch));
        solo.assertCurrentActivity("Expected Search Activity", SearchesActivity.class);
        solo.clickOnView(solo.getView(R.id.search));
        solo.enterText(0, username);
        solo.sendKey(Solo.ENTER);

        //place bid on chicken
        ListView listView = (ListView) solo.getView(R.id.searchResults);
        Chicken chicken = (Chicken) listView.getItemAtPosition(0);
        ChickBidsApplication.getChickenController().setCurrentChicken(chicken);
        try {
            ChickBidsApplication.getChickenController().putBidOnChicken(
                    new Bid(ChickBidsApplication.getUserController().getCurrentUser().getUsername(),
                            chicken.getId(), 500.0),
                    chicken);
        } catch (Exception e) {
            fail();
        }

        //logout login as first user
        solo.goBackToActivity("HomeActivity");
        solo.clickOnView(solo.getView(R.id.buttonLogout));
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);
        solo.clearEditText((AutoCompleteTextView) solo.getView(R.id.usernameEntered));
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), username);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);
    }
}
