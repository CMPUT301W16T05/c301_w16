package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

public class BiddingTest extends ActivityInstrumentationTestCase2 {
    public User user1;
    public User user2;
    public User user3;

    public Chicken chicken1;
    public Chicken chicken2;
    public Chicken chicken3;

    public ChickenController chickenController;
    public UserController userController;

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

        chickenController = ChickBidsTestHelper.getChickenController();
        userController = ChickBidsApplication.getUserController();
    }

    // US 05.01.01
    public void testPlaceBid() {
        userController.setUser(user2);

        // test bid place on available item
        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        try {
            chickenController.putBidOnChicken(bid1, chicken1);
        } catch (ChickenException e) {
            fail();
        }

        assertTrue(chicken1.getBids().contains(bid1));

        // test bid on unavailable item
        chicken2.setChickenStatus(Chicken.ChickenStatus.NOT_AVAILABLE);
        Bid bid2 = new Bid(user2.getUsername(), chicken2.getId(), ChickBidsTestHelper.amount_2);

        assertTrue(chicken2.getBids().contains(bid2));
    }

    // US 05.02.01
    public void testGetMyPlacedBids() {

        userController.setUser(user2);
        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        try {
            chickenController.putBidOnChicken(bid1, chicken1);
        } catch (ChickenException e) {
            fail();
        }

        userController.setUser(user1);
        Bid bid2 = new Bid(user1.getUsername(), chicken3.getId(), ChickBidsTestHelper.amount_2);
        try {
            chickenController.putBidOnChicken(bid2, chicken3);
        } catch (ChickenException e) {
            fail();
        }

        userController.setUser(user2);
        ArrayList<Chicken> user2_chicken = chickenController.getMyChickensWithBids();
        assertTrue(user2_chicken.contains(chicken1));
        assertFalse(user2_chicken.contains(chicken3));
    }

    // US 05.03.01
    public void testReceiveBidNotification() {
        assertTrue(user1.getNotifications().size() == 0);

        userController.setUser(user2);
        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        try {
            chickenController.putBidOnChicken(bid1, chicken1);
        } catch (ChickenException e) {
            fail();
        }

        assertTrue(user1.getNotifications().size() == 1);
    }

    // US 05.04.01
    public void testGetBiddedItems() {
        userController.setUser(user1);
        assertFalse(chickenController.getMyChickensWithBids().contains(chicken1));
        assertFalse(chickenController.getMyChickensWithBids().contains(chicken2));

        userController.setUser(user2);
        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        try {
            chickenController.putBidOnChicken(bid1, chicken1);
        } catch (ChickenException e) {
            fail();
        }
        assertTrue(chickenController.getMyChickensWithBids().contains(chicken1));

        Bid bid2 = new Bid(user2.getUsername(), chicken2.getId(), ChickBidsTestHelper.amount_2);
        try {
            chickenController.putBidOnChicken(bid2, chicken2);
        } catch (ChickenException e) {
            fail();
        }
        assertTrue(chickenController.getMyChickensWithBids().contains(chicken2));
    }

    // US 05.05.01
    public void testGetBidsForItem() {
        assertTrue(chicken1.getBids().size() == 0);

        userController.setUser(user2);
        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        try {
            chickenController.putBidOnChicken(bid1, chicken1);
        } catch (ChickenException e) {
            fail();
        }

        assertTrue(chicken1.getBids().contains(bid1));
    }

    // US 05.06.01
    public void testAcceptBid() {
        userController.setUser(user3);
        Bid bid2 = new Bid(user3.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_2);
        try {
            chickenController.putBidOnChicken(bid2, chicken1);
        } catch (ChickenException e) {
            fail();
        }

        userController.setUser(user2);
        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        try {
            chickenController.putBidOnChicken(bid1, chicken1);
        } catch (ChickenException e) {
            fail();
        }
        assertTrue(bid1.getBidStatus() == Bid.BidStatus.UNDECIDED);

        // test non-owner cannot accept bid
        userController.setUser(user2);
        try {
            chickenController.acceptBidForChicken(bid1);
            fail();
        } catch (ChickenException e) {
        }
        assertTrue(bid1.getBidStatus() == Bid.BidStatus.UNDECIDED);

        // test owner can accept bid
        userController.setUser(user1);
        try {
            chickenController.acceptBidForChicken(bid1);
        } catch (ChickenException e) {
            fail();
        }
        assertTrue(bid1.getBidStatus() == Bid.BidStatus.ACCEPTED);
        assertTrue(bid2.getBidStatus() == Bid.BidStatus.REJECTED);

        assertTrue(chicken1.getChickenStatus() == Chicken.ChickenStatus.BORROWED);
    }

    // US 05.07.01
    public void testDeclineBid() {
        userController.setUser(user2);
        Bid bid1 = new Bid(user2.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_2);
        try {
            chickenController.putBidOnChicken(bid1, chicken1);
        } catch (ChickenException e) {
            fail();
        }

        userController.setUser(user3);
        Bid bid2 = new Bid(user3.getUsername(), chicken1.getId(), ChickBidsTestHelper.amount_1);
        try {
            chickenController.putBidOnChicken(bid2, chicken1);
        } catch (ChickenException e) {
            fail();
        }

        assertTrue(bid1.getBidStatus() == Bid.BidStatus.UNDECIDED);
        assertTrue(bid2.getBidStatus() == Bid.BidStatus.UNDECIDED);

        // test non-owners of chicken cannot decline bids
        userController.setUser(user2);
        try {
            chickenController.rejectBidForChicken(bid2);
            fail();
        } catch (ChickenException e) {
        }
        assertTrue(bid2.getBidStatus() == Bid.BidStatus.UNDECIDED);

        // test owner of chicken can decline bids
        userController.setUser(user1);
        try {
            chickenController.rejectBidForChicken(bid1);
        } catch (ChickenException e) {
            fail();
        }
        assertTrue(bid2.getBidStatus() == Bid.BidStatus.UNDECIDED);
        assertTrue(bid1.getBidStatus() == Bid.BidStatus.REJECTED);

        assertTrue(chicken1.getChickenStatus() != Chicken.ChickenStatus.BORROWED);
    }
}
