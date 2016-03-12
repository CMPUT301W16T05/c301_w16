package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;

/**
 * UI test for the app's SearchController screen.
 *
 * @author  Hailey
 * @version 1.4, 03/07/2016
 * @see     SearchActivity
 * @see     SearchController
 */

public class SearchActivityUITest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    Activity activity;

    public SearchActivityUITest() {
        super(SearchActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
    }

    public void testViewVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.nav_toolbar));
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.searchView));
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.listView));
    }
/*
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();

        // make these the chickens in the database:
        Chicken chicken1 = new Chicken("Bob", "Friendly chicken", 13.55,
                Chicken.ChickenStatus.AVAILABLE);
        activity.getChickenList().add(chicken1);
        Chicken chicken2 = new Chicken("Joe", "Friendly and social chicken",
                13.55, Chicken.ChickenStatus.AVAILABLE);
        activity.getChickenList().add(chicken2);
        Chicken chicken3 = new Chicken("Fred", "Shy chicken", 15.00,
                Chicken.ChickenStatus.AVAILABLE);
        activity.getChickenList().add(chicken3);
        Chicken chicken4 = new Chicken("Shirley", "Angry chicken",
                12.05, Chicken.ChickenStatus.AVAILABLE);
        activity.getChickenList().add(chicken4);
        Chicken chicken5 = new Chicken("Ethel", "Shy chicken", 11.95,
                Chicken.ChickenStatus.BORROWED);
        activity.getChickenList().add(chicken5);
    }

    public void testViewVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.toolbar));
    }

    @UiThreadTest
    public void testShowSearchResults() {
        SearchActivity sa = (SearchActivity) getActivity();

        // test search that returns multiple results
        String[] keywords1 = {"Friendly"};
        sa.search(keywords1);
        ArrayAdapter<Chicken> arrayAdapter = sa.getAdapter();

        // check the number of results
        assertEquals(2, arrayAdapter.getCount()); // number of results

        assertTrue("Did you Chicken objects?",
                arrayAdapter.getItem(0) instanceof Chicken);
        assertTrue("Did you Chicken objects?",
                arrayAdapter.getItem(1) instanceof Chicken);

        Chicken c1 = arrayAdapter.getItem(0);
        Chicken c2 = arrayAdapter.getItem(0);
        assertEquals("This is not the chicken we expected", "Bob", c1.getName());
        assertEquals("This is not the chicken we expected", "Joes", c2.getName());

        // test search that returns 1 result
        String[] keywords2 = {"social"};
        sa.search(keywords2);
        arrayAdapter = sa.getAdapter();

        // check the number of results
        assertEquals(1, arrayAdapter.getCount()); // number of results

        // test search that returns no results
        String[] keywords3 = {"old"};
        sa.search(keywords3);
        arrayAdapter = sa.getAdapter();

        // check the number of results
        assertEquals(0, arrayAdapter.getCount()); // number of results
    }
    */
}

