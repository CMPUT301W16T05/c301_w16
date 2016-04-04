package com.example.c301_w16_g5.c301_w16_g5;

import android.support.design.widget.TabLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

/**
 * US 06: Borrowing
 */
public class TestUseCaseBorrowing extends ActivityInstrumentationTestCase2{

    private Solo solo;

    // for the best test, this user should have at least one chicken being
    // borrowed from them and be borrowing at least 1 chicken from someone
    // else
    private String username = "hailey123";

    public TestUseCaseBorrowing() {
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

    public void testSeeChickensBorrowedFromOthers() {
        // choose the BORROWED tab
        solo.clickOnText(solo.getString(R.string.item_profile_borrowed));
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return ((TabLayout) solo.getView(R.id.tabs)).getTabAt(4).isSelected();
            }
        }, 5);

        // check each item displayed is borrowed from someone else
        int listSize = ((ListView) solo.getView(R.id.chickenList)).getCount();
        for (int i = 0; i < listSize; i++) {
            solo.clickInList(i);
            solo.assertCurrentActivity("Expected View Other's Chicken Activity",
                    OthersChickenDisplayProfileActivity.class);
            assertEquals("BORROWED", ((TextView) solo.getView(R.id.status)).getText().toString());
            solo.goBack();
            solo.assertCurrentActivity("Expected View Chicken Lists Activity",
                    ChickenViewsActivity.class);
        }
    }

    public void testSeeChickensOthersBorrowingFromMe() {
        // choose the LENT tab
        solo.clickOnText(solo.getString(R.string.item_profile_lent));
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return ((TabLayout) solo.getView(R.id.tabs)).getTabAt(3).isSelected();
            }
        }, 5);

        // check each item displayed is lent to someone else
        int listSize = ((ListView) solo.getView(R.id.chickenList)).getCount();
        for (int i = 0; i < listSize; i++) {
            solo.clickInList(i);
            solo.assertCurrentActivity("Expected View My Chicken Activity",
                    MyChickenDisplayProfileActivity.class);
            assertEquals("BORROWED", ((TextView) solo.getView(R.id.status)).getText().toString());
            solo.goBack();
            solo.assertCurrentActivity("Expected View Chicken Lists Activity",
                    ChickenViewsActivity.class);
        }
    }
}
