package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hailey on 2016-02-11.
 */
public class SearchTest extends ActivityInstrumentationTestCase2 {
    public SearchTest() {
        super(Search.class);
    }

    public void testSearchByKeywords() {
        Chicken chicken1 = new Chicken("Bob", "Friendly chicken", 13.55,
                Chicken.Status.AVAILABLE);
        Chicken chicken2 = new Chicken("Joe", "Friendly and social chicken",
                13.55, Chicken.Status.AVAILABLE);
        Chicken chicken3 = new Chicken("Fred", "Shy chicken", 15.00,
                Chicken.Status.AVAILABLE);
        Chicken chicken4 = new Chicken("Shirley", "Angry chicken",
                12.05, Chicken.Status.AVAILABLE);
        Chicken chicken5 = new Chicken("Ethel", "Shy chicken", 11.95,
                Chicken.Status.BORROWED);

        // subject to change based on how chickens are stored
        ChickenDatabase allChickens = new ChickenDataBase();
        allChickens.add(chicken1);
        allChickens.add(chicken2);
        allChickens.add(chicken3);
        allChickens.add(chicken4);
        allChickens.add(chicken5);
        Search search = new Search();
        search.setDatabase(allChickens);

        // test normal functionality
        String keywords1 = "Friendly";
        List<Chicken> results = new ArrayList<Chicken>();
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
}
