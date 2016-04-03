package com.example.c301_w16_g5.c301_w16_g5;

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
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);

        solo.clickOnText(solo.getString(R.string.item_profile_owned));
        int listSizeBefore = ((ListView) solo.getView(R.id.chickenList)).getCount();

        solo.clickOnView(solo.getView(R.id.add_chicken_fab));
        solo.assertCurrentActivity("Expected Add Chicken Activity", AddChickenActivity.class);

        int countChickensBefore = ChickBidsApplication.getUserController().getCurrentUser().getMyChickens().size();
        Chicken testChicken = new Chicken("Bobchicken", "A happy test chicken", username);

        // input a chicken
        solo.enterText((EditText) solo.getView(R.id.name), testChicken.getName());
        solo.enterText((EditText) solo.getView(R.id.description), testChicken.getDescription());
        solo.clickOnView(solo.getView(R.id.buttonSave));

        /// compare old and new list sizes
        solo.waitForActivity(ChickenViewsActivity.class);
        assertEquals(listSizeBefore + 1, ((ListView) solo.getView(R.id.chickenList)).getCount());
    }
}
