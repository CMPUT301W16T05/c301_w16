package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hailey on 2016-02-11.
 */
public class SearchTest extends ActivityInstrumentationTestCase2 {
    public SearchTest() {
        super(Search.class);
    }

    public void testSearchByKeywords() {
        Chicken chicken1 = new Chicken("Bob", "Friendly chicken", 13.55, Chicken.Status.AVAILABLE);
        Chicken chicken2 = new Chicken("Fred", "Shy chicken", 15.00, Chicken.Status.AVAILABLE);
        Chicken chicken3 = new Chicken("Shirley", "Angry chicken", 12.05, Chicken.Status.AVAILABLE);
        Chicken chicken4 = new Chicken("Ethel", "Shy chicken", 11.95, Chicken.Status.BORROWED);

        // subject to change based on how chickens are stored
        ChickenList allChickens = new ChickenList();
        allChickens.add(chicken1);
        allChickens.add(chicken2);
        allChickens.add(chicken3);
        allChickens.add(chicken4);

        String[] keywords1 = {};

        Search search = new Search();
        search.searchFor(keywords1);
    }
}
