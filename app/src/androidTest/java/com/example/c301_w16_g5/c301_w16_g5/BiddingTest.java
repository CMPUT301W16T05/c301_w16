package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

public class BiddingTest extends ActivityInstrumentationTestCase2 {
/*
    public User user1 = new User();
    public User user2 = new User();
    public User user3 = new User();

    public Chicken chicken1 = new Chicken();
    public Chicken chicken2 = new Chicken();
    public Chicken chicken3 = new Chicken();
*/
    public BiddingTest() {
        super(HomeActivity.class);
    }
/*
    protected void setUp() throws Exception {
        super.setUp();

        chicken1.setName("Name 1");
        chicken1.setDescription("Description 1");
        chicken1.setChickenStatus(Chicken.ChickenStatus.AVAILABLE);
        chicken1.setOwner(user1);

        chicken2.setName("Name 2");
        chicken2.setDescription("Description 2");
        chicken2.setChickenStatus(Chicken.ChickenStatus.AVAILABLE);
        chicken2.setOwner(user1);

        chicken3.setName("Name 3");
        chicken3.setDescription("Description 3");
        chicken3.setChickenStatus(Chicken.ChickenStatus.AVAILABLE);
        chicken3.setOwner(user2);
    }

    // US 05.01.01
    public void testPlaceBid() {
        user2.setBalance(10.0);

        // test bid place on available item
        Bid bid1 = new Bid(user2, 5.0, chicken1);
        assertTrue(chicken1.hasBid(bid1));

        // test bid on unavailable item
        chicken2.setChickenStatus(Chicken.ChickenStatus.NOT_AVAILABLE);
        Bid bid2 = new Bid(user2, 5.0, chicken2);
        assertFalse(chicken2.hasBid(bid2));

        user2.setBalance(0.0);
        try {
            Bid bid3 = new Bid(user2, 5.0, chicken2);
        } catch (Exception e) {}
    }

    // US 05.02.01
    public void testGetMyPlacedBids() {
        Bid bid1 = new Bid(user2, 4.0, chicken1); // bid owned by user 2
        Bid bid2 = new Bid(user1, 4.0, chicken3); // bid owned by user 1

        ArrayList<Bid> my_bids = user2.getBids();
        assertTrue(my_bids.contains(bid1));
        assertFalse(my_bids.contains(bid2));

        bid2 = new Bid(user2, 5.0, chicken2);
        my_bids = user2.getBids();
        assertTrue(my_bids.contains(bid2));
    }

    // US 05.03.01
    public void testReceiveBidNotification() {
        assertTrue(user1.getNotifications().size() == 0);
        Bid bid1 = new Bid(user2, 4.0, chicken1);
        assertTrue(user1.getNotifications().size() == 1);
    }

    // US 05.04.01
    public void testGetBiddedItems() {
        assertFalse(user1.getMyChickensWithBids().contains(chicken1));
        assertFalse(user1.getMyChickensWithBids().contains(chicken2));

        Bid bid1 = new Bid(user2, 4.0, chicken1);
        assertTrue(user1.getMyChickensWithBids().contains(chicken1));
        Bid bid2 = new Bid(user2, 5.0, chicken2);
        assertTrue(user1.getMyChickensWithBids().contains(chicken2));
    }

    // US 05.05.01
    public void testGetBidsForItem() {
        assertTrue(chicken1.getBids().size() == 0);

        Bid bid1 = new Bid(user2, 4.0, chicken1);
        assertTrue(chicken1.getBids().contains(bid1));
    }

    // US 05.06.01
    public void testAcceptBid() {
        Bid bid1 = new Bid(user2, 4.0, chicken1);
        Bid bid2 = new Bid(user3, 5.0, chicken1);

        assertTrue(bid1.getStatus() == Bid.Status.PENDING_APPROVAL);
        assertTrue(bid2.getStatus() == Bid.Status.PENDING_APPROVAL);

        // test non-owner cannot accept bid
        user2.acceptBidForChicken(bid2);
        assertTrue(bid2.getStatus() == Bid.Status.PENDING_APPROVAL);

        // test owner can accept bid
        user1.acceptBidForChicken(bid1);
        assertTrue(bid1.getStatus() == Bid.Status.ACCEPTED);
        assertTrue(bid2.getStatus() == Bid.Status.DECLINED);

//        assertTrue(chicken1.getPossessor() == user2);
        assertTrue(user2.hasChicken(chicken1));
    }

    // US 05.07.01
    public void testDeclineBid() {
        Bid bid1 = new Bid(user2, 4.0, chicken1);
        Bid bid2 = new Bid(user3, 5.0, chicken1);

        assertTrue(bid1.getStatus() == Bid.Status.PENDING_APPROVAL);
        assertTrue(bid2.getStatus() == Bid.Status.PENDING_APPROVAL);

        // test non-owners of chicken cannot decline bids
        user2.declineBid(bid2);
        assertTrue(bid2.getStatus() == Bid.Status.PENDING_APPROVAL);

        // test owner of chicken can decline bids
        user1.declineBid(bid1);
        assertTrue(bid1.getStatus() == Bid.Status.DECLINED);
        assertTrue(bid2.getStatus() == Bid.Status.PENDING_APPROVAL);

//        assertTrue(chicken1.getPossessor() == user1);
        assertTrue(user1.hasChicken(chicken1));
    }
    */
}
