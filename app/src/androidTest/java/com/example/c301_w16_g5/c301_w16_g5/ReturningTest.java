package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

public class ReturningTest extends ActivityInstrumentationTestCase2 {

    public ReturningTest() {
        super(HomeActivity.class);
    }

    private User owner, borrower;
    private Chicken chicken;

    private ChickenController chickenController;

    protected void setUp() throws Exception{
        super.setUp();

        owner = ChickBidsTestHelper.genericUser1();
        borrower = ChickBidsTestHelper.genericUser2();

        chicken = new Chicken(ChickBidsTestHelper.chicken_name_1, ChickBidsTestHelper.description_1, owner.getUsername());

        chickenController = ChickBidsTestHelper.getChickenController();
        Bid bid = new Bid(borrower.getUsername(), chicken.getId(), ChickBidsTestHelper.amount_1);

        chickenController.putBidOnChicken(bid, chicken);
        chickenController.acceptBidForChicken(bid);
    }

    // US 07.01.01
    public void testReturningAChicken(){
        chickenController.returnChickenToOwner(chicken);
        assertTrue(chicken.getChickenStatus() == Chicken.ChickenStatus.AVAILABLE);
    }
}
