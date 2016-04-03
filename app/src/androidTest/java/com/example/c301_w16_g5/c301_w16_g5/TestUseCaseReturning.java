package com.example.c301_w16_g5.c301_w16_g5;

import android.support.design.widget.TabLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

/**
 * Created by Hailey on 2016-04-02.
 */
public class TestUseCaseReturning extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    // TODO: possibly make sure the condition is true is setup
    private String username = "hailey123";  // must be a user with at least 1 chicken borrowed

    public TestUseCaseReturning() {
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

        // go to chicken views
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);
        solo.scrollViewToSide(solo.getView(R.id.tabs), Solo.RIGHT);
    }

    @Override
    public void tearDown() throws Exception {
        // run after each test case
        solo.finishOpenedActivities();
    }

    public void testSetChickenAvailableWhenReturned () {
        // choose the BORROWED tab
        solo.clickOnText(solo.getString(R.string.item_profile_borrowed));
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return ((TabLayout) solo.getView(R.id.tabs)).getTabAt(4).isSelected();
            }
        }, 5);

        int numBorrowedBefore = ((ListView) solo.getView(R.id.chickenList)).getCount();

        // return the chicken
        solo.clickInList(0);
        solo.waitForActivity(OthersChickenDisplayProfileActivity.class);
        Chicken returningChicken = ChickBidsApplication.getChickenController().getCurrentChicken();
        solo.clickOnView(solo.getView(R.id.buttonReturn));
        solo.waitForActivity(ChickenViewsActivity.class);

        // check that the  chicken is now available
        assertEquals(Chicken.ChickenStatus.AVAILABLE, returningChicken.getChickenStatus());
        assertEquals(numBorrowedBefore - 1, ((ListView) solo.getView(R.id.chickenList)).getCount());
    }
}
