package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

import io.searchbox.core.Search;

/**
 * Created by Hailey on 2016-02-11.
 */
public class SearchControllerTest extends ActivityInstrumentationTestCase2 {
    public SearchControllerTest() {
        super(Search.class);
    }
/*
    // US 04.01.01
    public void testSearchByKeywords() {
        Chicken chicken1 = new Chicken("Bob", "Friendly chicken",
                Chicken.ChickenStatus.AVAILABLE, new User("username"));
        Chicken chicken2 = new Chicken("Joe", "Friendly and social chicken",
                Chicken.ChickenStatus.AVAILABLE, new User("username"));
        Chicken chicken3 = new Chicken("Fred", "Shy chicken",
                Chicken.ChickenStatus.AVAILABLE, new User("username"));
        Chicken chicken4 = new Chicken("Shirley", "Angry chicken",
                Chicken.ChickenStatus.AVAILABLE, new User("username"));
        Chicken chicken5 = new Chicken("Ethel", "Shy chicken",
                Chicken.ChickenStatus.BORROWED, new User("username"));

        // subject to change based on how chickens are stored
        ChickenDatabase allChickens = new ChickenDataBase();
        allChickens.add(chicken1);
        allChickens.add(chicken2);
        allChickens.add(chicken3);
        allChickens.add(chicken4);
        allChickens.add(chicken5);
        SearchController search = new SearchController();
        search.setDatabase(allChickens);

        // test normal functionality
        String keywords1 = "Friendly";
        ArrayList<Chicken> results = new ArrayList<Chicken>();
        results.add(chicken1);
        results.add(chicken2);
        assertEquals(results, search.searchFor(keywords1));

        // test no input
        String keywords2 = "";
        try {
            search.searchFor(keywords2);
        } catch (Exception e) {
            // success
        }

        // test search with no match
        String keywords3 = "rooster Bob";
        results = new ArrayList<Chicken>();
        assertEquals(results, search.searchFor(keywords3));

        // test search whose results are reduced because at least one matching item is borrowed
        String keywords4 = "Shy chicken";
        results = new ArrayList<Chicken>();
        results.add(chicken3);
        assertEquals(results, search.searchFor(keywords4));

        // test adding punctuation (ensure it's ignored)
        // and that order doesn't matter
        // and that results are case-insensitive
        String keywords5 = "chicken, friendly";
        results = new ArrayList<Chicken>();
        results.add(chicken1);
        results.add(chicken2);
        assertEquals(results, search.searchFor(keywords5));
    }
    */
}
