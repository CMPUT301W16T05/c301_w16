package com.example.c301_w16_g5.c301_w16_g5;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import android.support.design.widget.TabLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Hailey on 2016-03-31.
 */
public class TestUseCaseThings extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private String username = "hailey123";

    public TestUseCaseThings() {
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

    public void testAddChicken() {
        // go to add chicken screen
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);

        solo.clickOnText(solo.getString(R.string.item_profile_owned));
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return ((TabLayout) solo.getView(R.id.tabs)).getTabAt(1).isSelected();
            }
        }, 5);
        int listSizeBefore = ((ListView) solo.getView(R.id.chickenList)).getCount();

        solo.clickOnView(solo.getView(R.id.add_chicken_fab));
        solo.assertCurrentActivity("Expected Add Chicken Activity", AddChickenActivity.class);

        Chicken testChicken = new Chicken("Bobchicken", "A happy test chicken", username);

        // input a chicken
        solo.enterText((EditText) solo.getView(R.id.name), testChicken.getName());
        solo.enterText((EditText) solo.getView(R.id.description), testChicken.getDescription());
        solo.clickOnView(solo.getView(R.id.buttonSave));

        /// compare old and new list sizes
        solo.waitForActivity(ChickenViewsActivity.class);
        solo.clickOnText(solo.getString(R.string.item_profile_owned));
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return ((TabLayout) solo.getView(R.id.tabs)).getTabAt(1).isSelected();
            }
        }, 5);
        assertEquals(listSizeBefore + 1, ((ListView) solo.getView(R.id.chickenList)).getCount());
    }

    public void testViewChickenList() {
        // go to chicken lists screen
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);

        String[] tabs = new String[] {solo.getString(R.string.item_profile_possession),
                solo.getString(R.string.item_profile_lent),
                solo.getString(R.string.item_profile_borrowed),
                solo.getString(R.string.item_profile_owned),
                solo.getString(R.string.item_profile_owned_with_bids),
                solo.getString(R.string.item_profile_bids_placed)};

        for (String tab : tabs) {
            // view list of all chickens under tab
            solo.clickOnText(tab);
            assertTrue(solo.getView(R.id.chickenList).getVisibility() == View.VISIBLE);
        }
    }

    public void testViewChicken() {
        // go to chicken lists screen
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);

        // select a chicken of yours
        solo.clickOnText(solo.getString(R.string.item_profile_owned));
        solo.clickInList(0);
        solo.assertCurrentActivity("Expected View Chicken Activity", MyChickenDisplayProfileActivity.class);

        // view the chicken's info
        assertTrue(solo.getView(R.id.name).getVisibility() == View.VISIBLE);
        assertTrue(solo.getView(R.id.status).getVisibility() == View.VISIBLE);
        assertTrue(solo.getView(R.id.description).getVisibility() == View.VISIBLE);
    }

    public void testEditChicken() {
        // go to chicken lists screen
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);

        // select a chicken of yours
        solo.clickOnText(solo.getString(R.string.item_profile_owned));
        solo.clickInList(0);
        solo.assertCurrentActivity("Expected View My Chicken Activity", MyChickenDisplayProfileActivity.class);

        String newDescription = "A new chicken description";

        // we make sure the original info is different, so the change can be seen
        String currentDescription = ((TextView) solo.getView(R.id.description)).getText().toString();
        if (currentDescription.equals(newDescription)) {
            newDescription = "A newer chicken description";
        }

        // edit the chicken
        solo.clickOnView(solo.getView(R.id.buttonEdit));
        solo.assertCurrentActivity("Expected Edit Chicken Activity", EditChickenActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.description));
        solo.enterText((EditText) solo.getView(R.id.description), newDescription);

        // save and check for the update
        solo.clickOnView(solo.getView(R.id.buttonSave));
        solo.assertCurrentActivity("Expected View My Chicken Activity", MyChickenDisplayProfileActivity.class);
        assertEquals(newDescription, ((TextView) solo.getView(R.id.description)).getText());
    }

    public void testDeleteChicken() {
        // go to chicken lists screen
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);

        // select a chicken of yours
        solo.clickOnText(solo.getString(R.string.item_profile_owned));
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return ((TabLayout) solo.getView(R.id.tabs)).getTabAt(1).isSelected();
            }
        }, 5);
        int listSizeBefore = ((ListView) solo.getView(R.id.chickenList)).getCount();
        solo.clickLongInList(0);
        solo.clickOnText(solo.getString(R.string.contextmenu_delete));

        // compare old and new list sizes
        solo.waitForDialogToClose();
        assertEquals(listSizeBefore - 1, ((ListView) solo.getView(R.id.chickenList)).getCount());
    }
}
