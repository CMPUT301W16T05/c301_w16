package com.example.c301_w16_g5.c301_w16_g5;

import android.support.design.widget.TabLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

/**
 * US 02: Status
 */
public class TestUseCaseStatus extends ActivityInstrumentationTestCase2 {

    private Solo solo;

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
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);

        // check that chickens you own have a status
        solo.clickOnText(solo.getString(R.string.item_profile_owned));
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return ((TabLayout) solo.getView(R.id.tabs)).getTabAt(1).isSelected();
            }
        }, 5);

        ListView listView = (ListView) solo.getView(R.id.chickenList);
        for(int i = 0; i < listView.getCount(); i++) {
            solo.clickInList(i);
            solo.assertCurrentActivity("Expected View My Chicken Activity", MyChickenDisplayProfileActivity.class);

            String currentDescription = ((TextView) solo.getView(R.id.status)).getText().toString();
            assertTrue(currentDescription.equals("AVAILABLE") || currentDescription.equals("BORROWED") ||
                    currentDescription.equals("BIDDED") || currentDescription.equals("NOT_AVAILABLE"));
            solo.goBack();
        }

        // that chickens you are borrowing have a status (and that that status is BORROWED)
        // is tested in TestUseCaseBorrowing
    }
}
