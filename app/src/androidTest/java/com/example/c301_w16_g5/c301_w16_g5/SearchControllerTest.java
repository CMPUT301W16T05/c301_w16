package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import io.searchbox.core.Search;

/**
 * Created by Hailey on 2016-02-11.
 */
public class SearchControllerTest extends ActivityInstrumentationTestCase2 {
    public SearchControllerTest() {
        super(Search.class);
    }

    public void testAddChickenToDatabase() {
        SearchController sc = ChickBidsApplication.getSearchController();
        Chicken chicken = sc.getChickenFromDatabase("qgZvLkclT2WOKa1cn-3dUA");
        Chicken chicken1 = sc.getChickenFromDatabase("ToeU2YdyQYGHHxOTSpRefQ");
        Chicken chicken2 = sc.getChickenFromDatabase("92nz834ESv2E352EIhKJlw");
        Chicken chicken3 = sc.getChickenFromDatabase("uISZ1Tp5QauHI4xLbZjcEA");
        Chicken chicken4 = sc.getChickenFromDatabase("3O_d1YKJRyyjpEhRrOSkNw");

        sc.updateChickenInDatabase(chicken);
        sc.updateChickenInDatabase(chicken1);
        sc.updateChickenInDatabase(chicken2);
        sc.updateChickenInDatabase(chicken3);
        sc.updateChickenInDatabase(chicken4);
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

    public void testGetLettersForUser() {
        SearchController sc = new SearchController();
        User user = sc.getUserFromDatabase("alex");

        assertFalse(user.getId() == null);

        ArrayList<Letter> letters = sc.getLettersForUser(user);

        //assertTrue(letters.size() == 1);
        assertEquals(letters.get(0).getMessage(), "Hey");
        assertEquals(letters.get(0).getToUsername(), "alex");
        assertEquals(letters.get(0).getFromUsername(), "jsmith");

    }

    public void testUpdateUserInDatabase() {
        SearchController sc = new SearchController();
        User user = sc.getUserFromDatabase("hailey12");

        assertTrue(user.getLetters() != null);

        sc.updateUserInDatabase(user);
    }

    public void testGetUserFromDatabase2() {
        SearchController sc = new SearchController();
        Letter letter = new Letter("Hello", "alex", "jsmith");
        User user = sc.getUserFromDatabase("alex");

        assertFalse(user.getId() == null);

        letter = sc.addLetterToDatabase(letter);
        assertFalse(letter.getId() == null);

        user.addLetter(letter);
        sc.updateUserInDatabase(user);

        User user2 = sc.getUserFromDatabase("alex");
        assertEquals(user2.getLetters().get(0).getMessage(), "Hello");
    }

    public void testGetUserOffline() {
        SearchController sc = ChickBidsApplication.getSearchController();
        User user = new User("test3", "test", "test", "test@test.com", "5555555555", "Lots");

        sc.addUserToDatabase(user);
        User user1 = sc.getUserFromDatabase("test3");

        assertEquals(user.getUsername(), user1.getUsername());
        assertEquals(user.getFirstName(), user1.getFirstName());
        assertEquals(user.getLastName(), user1.getLastName());
        assertEquals(user.getEmail(), user1.getEmail());
        assertEquals(user.getPhoneNumber(), user1.getPhoneNumber());
        assertEquals(user.getChickenExperience(), user1.getChickenExperience());
    }

    public void testUpdateUserOffline() {
        SearchController sc = ChickBidsApplication.getSearchController();
        User user = new User("test3", "test", "test", "test@test.com", "5555555555", "Lots");

        sc.addUserToDatabase(user);
        user.setFirstName("t");
        user.setLastName("t");
        user.setPhoneNumber("66666666666");
        sc.updateUserInDatabase(user);
        User user1 = sc.getUserFromDatabase("test3");

        assertEquals(user.getUsername(), user1.getUsername());
        assertEquals(user.getFirstName(), user1.getFirstName());
        assertEquals(user.getLastName(), user1.getLastName());
        assertEquals(user.getEmail(), user1.getEmail());
        assertEquals(user.getPhoneNumber(), user1.getPhoneNumber());
        assertEquals(user.getChickenExperience(), user1.getChickenExperience());
    }

    public void testUpdateBids() {
        SearchController sc = ChickBidsApplication.getSearchController();
        Bid bid = sc.getBidFromDatabase("gsTE_0UcQCmZI89OrHyZPQ");
        Bid bid1 = sc.getBidFromDatabase("hdFvxZACRVeAm0pBxoYT4A");
        Bid bid2 = sc.getBidFromDatabase("bcpZv7DuTgClf8oZwnMqvw");
        Bid bid3 = sc.getBidFromDatabase("wx7X58S-S0SEEaJDCFz8Sg");

        sc.updateBidInDatabase(bid);
        sc.updateBidInDatabase(bid1);
        sc.updateBidInDatabase(bid2);
        sc.updateBidInDatabase(bid3);
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
