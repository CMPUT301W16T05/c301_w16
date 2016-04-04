package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satyen on 2016-04-03.
 */
public class TestUseCaseBidding extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private String username = "hailey123";
    private String username2 = "satyen";

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
        helperPlaceBidAsSecondUser();
    }

    public void testSeeMyBidChicken() {
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);

        String tab = solo.getString(R.string.item_profile_bids_placed);

        solo.clickOnText(tab);
        assertTrue(solo.getView(R.id.chickenList).getVisibility() == View.VISIBLE);
    }

    public void testReceiveNewBidNotification() {
        //see how many notifications before
        solo.clickOnView(solo.getView(R.id.notifications_button));
        solo.assertCurrentActivity("Expected Notifications Activity", NotificationsActivity.class);
        int numNotificationsBefore = ((ListView) solo.getView(R.id.notificationList)).getAdapter().getCount();
        solo.clickOnView(solo.getView(R.id.home_button));

        helperPlaceBidAsSecondUser();

        //see how many notifications after
        solo.clickOnView(solo.getView(R.id.notifications_button));
        solo.assertCurrentActivity("Expected Notifications Activity", NotificationsActivity.class);
        int numNotificationsAfter = ((ListView) solo.getView(R.id.notificationList)).getAdapter().getCount();

        assertEquals(numNotificationsBefore + 1, numNotificationsAfter);
    }

    public void testSeeAllMyChickensWithBid() {
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);

        String tab = solo.getString(R.string.item_profile_owned_with_bids);

        solo.clickOnText(tab);
        assertTrue(solo.getView(R.id.chickenList).getVisibility() == View.VISIBLE);
    }

    public void testViewAllBidsOnMyChicken() {
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);
        String tab = solo.getString(R.string.item_profile_owned_with_bids);
        solo.clickOnText(tab);

        solo.clickInList(0);
        solo.assertCurrentActivity("Expected My Chicken Display Profile Activity", MyChickenDisplayProfileActivity.class);

        solo.clickOnView(solo.getView(R.id.buttonViewBids));
        solo.assertCurrentActivity("Expected View Bids Activity", BidsActivity.class);

        assertTrue(solo.getView(R.id.bidsListView).getVisibility() == View.VISIBLE);
    }

    public void testAcceptBidOnChicken() {
        helperPlaceBidAsSecondUser();

        //accept bid
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);
        String tab = solo.getString(R.string.item_profile_owned_with_bids);
        solo.clickOnText(tab);
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.buttonViewBids));
        solo.clickOnView(solo.getView(R.id.acceptBidImageView));
    }

    public void testDeclineBidOnChicken() {
        helperPlaceBidAsSecondUser();

        //decline bid
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);
        String tab = solo.getString(R.string.item_profile_owned_with_bids);
        solo.clickOnText(tab);
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.buttonViewBids));
        solo.clickOnView(solo.getView(R.id.declineBidImageView));
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
        //FIXME: cant click on search results list for some reason
        ListView listView = (ListView) solo.getView(R.id.searchResults);
        listView.setSelection(0);
        ListAdapter adapter = listView.getAdapter();
        View view = adapter.getView(0, null, null);
        view.performClick();

        solo.clickOnView(view, true);
        solo.clickOnView(solo.getView(R.id.searchResults), true);
        solo.assertCurrentActivity("Expected Chicken Profile Activity", OthersChickenDisplayProfileActivity.class);
        solo.enterText((EditText) solo.getView(R.id.bidEditText), "500");
        solo.clickOnView(solo.getView(R.id.buttonBid));

        //logout login as first user
        solo.clickOnView(solo.getView(R.id.home_button));
        solo.clickOnView(solo.getView(R.id.buttonLogout));
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);
        solo.clearEditText((AutoCompleteTextView) solo.getView(R.id.usernameEntered));
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), username);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);
    }
}
