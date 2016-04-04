package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by Hailey on 2016-03-31.
 */
public class TestUseCaseSearching extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    // TODO: possibly make sure the condition is true is setup
    private String username = "hailey123";  // must be a user with at least 1 chicken

    public TestUseCaseSearching() {
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

    public void testKeywordSearch() {
        // the back-end
        // Is this already tested somewhere else?
    }

    public void testViewSearchResults() {
        // the front-end

        // expected result:
        ArrayList<Chicken> expectedResult = ChickBidsApplication.getSearchController().searchByKeyword("bob");

        // go to search screen
        solo.clickOnView(solo.getView(R.id.buttonSearch));
        solo.assertCurrentActivity("Expected Search Activity", SearchesActivity.class);

        // perform a search
        solo.clickOnView(solo.getView(R.id.search));
        solo.enterText(0, "bob");
        solo.sendKey(Solo.ENTER);
        solo.assertCurrentActivity("Expected Search Activity", SearchesActivity.class);

        // check the result
        ListAdapter adapter = ((ListView) solo.getView(R.id.searchResults)).getAdapter();
        ArrayList<Chicken> actualResult = new ArrayList<>();

        for(int i = 0; i < adapter.getCount(); i++) {
            actualResult.add((Chicken) adapter.getItem(i));
        }

        assertEquals("Search Results Don't Match", expectedResult.size(), actualResult.size());
    }
}
