package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * US 04: Searching - also, see
 */
public class TestUseCaseSearching extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    private String username = "hailey123";  // must be a user with at least 1 chicken
    private String searchText = "bob";

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

    /**
     * Gets the list of search results and displays them, covering US 04.01.01 & US 04.02.01.
     */
    public void testPerformSearch() {
        // the front-end

        // expected result:
        ArrayList<Chicken> expectedResult = ChickBidsApplication.getSearchController().searchByKeyword(searchText);

        // go to search screen
        solo.clickOnView(solo.getView(R.id.buttonSearch));
        solo.assertCurrentActivity("Expected Search Activity", SearchesActivity.class);

        // perform a search
        solo.clickOnView(solo.getView(R.id.search));
        solo.enterText(0, searchText);
        solo.sendKey(Solo.ENTER);
        solo.assertCurrentActivity("Expected Search Activity", SearchesActivity.class);

        // check the result
        //   - check size
        //   - check contents
        ListView listView = (ListView) solo.getView(R.id.searchResults);
        ListAdapter adapter = listView.getAdapter();

        int resultCount = adapter.getCount();
        for(int i = 0; i < resultCount; i++) {
            Chicken chicken = (Chicken) adapter.getItem(i);
            String text = "";
            text += chicken.getName() + " ";
            text += chicken.getDescription() + " ";
            text += chicken.getOwnerUsername() + " ";
            assertTrue(text.toLowerCase().contains(searchText));
        }

        assertEquals("Search Results Don't Match", expectedResult.size(), resultCount);
    }
}
