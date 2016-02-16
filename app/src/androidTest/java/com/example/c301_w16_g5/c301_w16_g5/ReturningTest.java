package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

public class ReturningTest extends ActivityInstrumentationTestCase2 {

    public ReturningTest() {
        super(MainScreen.class);
    }

    private User owner, borrower;
    private Chicken chicken;

    protected void setUp() throws Exception{
        super.setUp();

        owner = new User();
        borrower = new User();

        chicken = new Chicken("Name", "FriendlyBird", 1.00, Chicken.Status.BORROWED );
        borrower.addChicken(chicken);
    }

    // US 07.01.01
    public void testReturningAChicken(){
        borrower.releaseChicken(chicken);
        assertTrue(chicken.getStatus() == Chicken.Status.AVAILABLE);
    }

}
