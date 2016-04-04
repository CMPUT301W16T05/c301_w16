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
        Chicken chicken = sc.getChickenFromDatabase("Igl8TAz7R-SC5M1FSxJLEg");

        sc.updateChickenInDatabase(chicken);
    }

    public void testGetChickenFromDatabase() {
        SearchController sc = new SearchController();
        Chicken chicken = sc.getChickenFromDatabase("Igl8TAz7R-SC5M1FSxJLEg");

        assertEquals(chicken.getId(),"Igl8TAz7R-SC5M1FSxJLEg");
    }

    public void testGetUserFromDatabase() {
        SearchController sc = new SearchController();
        User user = sc.getUserFromDatabase("hailey123");
        Chicken chicken = new Chicken("Anne","sweet","test2");

        chicken = sc.addChickenToDatabase(chicken);
        assertFalse(chicken.getId() == null);

        user.addChicken(chicken);
        sc.addUserToDatabase(user);

        User user1 = sc.getUserFromDatabase("hailey123");
        Chicken chicken1 = user1.getChicken(0);
        Chicken chicken2 = user1.getChicken(1);

        assertFalse(chicken1.getId() == null);
        assertFalse(chicken.getId() == null);
    }

    public void testUpdateUserInDatabase() {
        SearchController sc = new SearchController();
        User user = sc.getUserFromDatabase("hailey123");

        assertTrue(user.getLetters() != null);

        sc.updateUserInDatabase(user);
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
}
