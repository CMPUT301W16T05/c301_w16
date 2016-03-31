package com.example.c301_w16_g5.c301_w16_g5;

import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hailey on 2016-03-31.
 */
public class TestUseCaseThings extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private String username = "hailey123";
    private String[] tabs = {"Possession", "Lent", "Borrowed", "Owned", "Received Bids", "My Bidded"};

    public TestUseCaseThings() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation());
        getActivity();

        // enter the app
        solo.unlockScreen();
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), username);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testAddChicken() {
        // go to add chicken screen
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ItemViews.class);
        solo.clickOnView(solo.getView(R.id.add_chicken_fab));
        solo.assertCurrentActivity("Expected Add Chicken Activity", AddChickenActivity.class);

        int countChickensBefore = ChickBidsApplication.getUserController().getCurrentUser().getMyChickens().size();
        Chicken testChicken = new Chicken("Bobchicken", "A happy test chicken", username);

        // input a chicken
        solo.enterText((EditText) solo.getView(R.id.name), testChicken.getName());
        solo.enterText((EditText) solo.getView(R.id.description), testChicken.getDescription());
        solo.clickOnView(solo.getView(R.id.buttonSave));

        // TODO
        // This is failing currently because chickens are not being saved to Elasticsearch right
        // when the save button is clicked
        assertEquals(countChickensBefore + 1, ChickBidsApplication.getUserController().getCurrentUser().getMyChickens().size());
        assertTrue("New chicken is found", solo.searchText(testChicken.getName()));
        assertTrue("New chicken is found", solo.searchText(testChicken.getDescription()));
    }

    public void testViewChickenList() {
        // go to chicken lists screen
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ItemViews.class);

        for (String tab : tabs) {
            // view list of all chickens under tab
            solo.clickOnText(tab);
            assertTrue(solo.getView(R.id.chickenList).getVisibility() == View.VISIBLE);
        }
    }

    public void testViewChicken() {
        // go to chicken lists screen
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ItemViews.class);

        // select a chicken of yours
        solo.clickOnText("Owned");
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
        solo.assertCurrentActivity("Expected Item Views Activity", ItemViews.class);

        // select a chicken of yours
        solo.clickOnText("Owned");
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
        // TODO: delete a chicken
    }
}
