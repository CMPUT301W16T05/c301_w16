package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

public class BiddingTest extends ActivityInstrumentationTestCase2 {
    private User user1;
    private User user2;
    private User user3;

    private Chicken chicken1;
    private Chicken chicken2;
    private Chicken chicken3;

    private ChickenController chickenController;
    private UserController userController;

    public BiddingTest() {
        super(HomeActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        user1 = ChickBidsTestHelper.genericUser1();
        user2 = ChickBidsTestHelper.genericUser2();
        user3 = ChickBidsTestHelper.genericUser3();

        chicken1 = new Chicken(ChickBidsTestHelper.chicken_name_1, ChickBidsTestHelper.description_1, ChickBidsTestHelper.username_1);
        chicken2 = new Chicken(ChickBidsTestHelper.chicken_name_2, ChickBidsTestHelper.description_2, ChickBidsTestHelper.username_1);
        chicken3 = new Chicken(ChickBidsTestHelper.chicken_name_3, ChickBidsTestHelper.description_3, ChickBidsTestHelper.username_2);

        chickenController = ChickBidsApplication.getChickenController();
        userController = ChickBidsApplication.getUserController();
    }

    // US 05.01.01
    public void testPlaceBid() {
        userController.setUser(user2);

        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        chicken1.addBid(bid1);

        assertTrue(chicken1.getBids().contains(bid1));
    }

    // US 05.02.01
    public void testGetMyPlacedBids() {

        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        chicken1.addBid(bid1);
        chicken1.setChickenStatus(Chicken.ChickenStatus.BIDDED);
        user1.addChicken(chicken1);

        Bid bid2 = new Bid(user1.getUsername(), chicken3.getId(), ChickBidsTestHelper.amount_2);
        chicken3.addBid(bid2);
        chicken3.setChickenStatus(Chicken.ChickenStatus.BIDDED);
        user2.addChicken(chicken3);

        userController.setUser(user2);
        ArrayList<Chicken> user2_chicken = chickenController.getMyChickensWithBids();
        assertTrue(user2_chicken.contains(chicken3));
        assertFalse(user2_chicken.contains(chicken1));
    }

    // US 05.03.01
    public void testReceiveBidNotification() {
        assertTrue(user1.getNotifications().size() == 0);

        userController.setUser(user2);
        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        chicken1.addBid(bid1);
        user1.addNotification(new Notification("New bid."));

        assertTrue(user1.getNotifications().size() == 1);
    }

    // US 05.04.01
    public void testGetBiddedItems() {
        userController.setUser(user1);
        assertFalse(chickenController.getMyChickensWithBids().contains(chicken1));
        assertFalse(chickenController.getMyChickensWithBids().contains(chicken2));

        userController.setUser(user2);
        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        chicken1.addBid(bid1);
        chicken1.setChickenStatus(Chicken.ChickenStatus.BIDDED);
        userController.setUser(user1);
        user1.addChicken(chicken1);
        assertTrue(chickenController.getMyChickensWithBids().contains(chicken1));

        userController.setUser(user1);
        Bid bid2 = new Bid(user1.getUsername(), chicken3.getId(), ChickBidsTestHelper.amount_2);
        chicken3.addBid(bid2);
        chicken3.setChickenStatus(Chicken.ChickenStatus.BIDDED);
        userController.setUser(user2);
        user2.addChicken(chicken3);
        assertTrue(chickenController.getMyChickensWithBids().contains(chicken3));
    }

    // US 05.05.01
    public void testGetBidsForItem() {
        assertTrue(chicken1.getBids().size() == 0);

        userController.setUser(user2);
        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        chicken1.addBid(bid1);
        chicken1.setChickenStatus(Chicken.ChickenStatus.BIDDED);
        user1.addChicken(chicken1);

        assertTrue(chicken1.getBids().contains(bid1));
    }
}
