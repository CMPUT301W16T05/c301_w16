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

    public void testGetChickenFromDatabase() {
        SearchController sc = new SearchController();
        Chicken chicken = sc.getChickenFromDatabase("3eYlWldXRvyNNq3uhsoH8w");

        assertEquals(chicken.getId(),"3eYlWldXRvyNNq3uhsoH8w");
    }

    public void testGetUserFromDatabase() {
        SearchController sc = new SearchController();
        User user = sc.getUserFromDatabase("test2");
        Chicken chicken = new Chicken("Anne","sweet","test2");

        chicken = sc.addChickenToDatabase(chicken);
        assertFalse(chicken.getId() == null);

        user.addChicken(chicken);
        sc.addUserToDatabase(user);

        User user1 = sc.getUserFromDatabase("test2");
        Chicken chicken1 = user1.getChicken(0);
        Chicken chicken2 = user1.getChicken(1);

        assertFalse(chicken.getId() == null);
        assertFalse(chicken.getId() == null);
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
