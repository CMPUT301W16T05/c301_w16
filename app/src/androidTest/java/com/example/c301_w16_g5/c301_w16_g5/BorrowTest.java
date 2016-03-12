package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import java.util.ArrayList;

public class BorrowTest extends ActivityInstrumentationTestCase2 {

    public BorrowTest() {
        super(HomeActivity.class);
    }

    public User user;

    public Chicken chicken1;
    public Chicken chicken2;
    public Chicken chicken3;
    public Chicken chicken4;

    public ChickenController chickenController;
    public UserController userController;

    /*
     * Create a list of reference items with which to test.
     */
    protected void setUp() throws Exception {
        super.setUp();

        user = ChickBidsTestHelper.genericUser1();

        chicken1 = ChickBidsTestHelper.genericChicken1_AvailUser1();
        chicken2 = ChickBidsTestHelper.genericChicken4_BorrUser4();
        chicken3 = ChickBidsTestHelper.genericChicken6_BidUser1();
        chicken4 = ChickBidsTestHelper.genericChicken5_BorrUser1();

        user.addChicken(chicken1);
        user.addChicken(chicken2);
        user.addChicken(chicken3);
        user.addChicken(chicken4);

        chickenController = ChickBidsTestHelper.getChickenController();
        userController = ChickBidsTestHelper.getUserController();
        userController.setUser(user);
    }

    /* US 06.01.01
     * Will require testing an activity and verifying that upon launching the
     * sub-activity only displays items belonging to another user and have status
     * 'borrowed'
     */
    public void testGetBorrowedFromOthers(){
        ArrayList<Chicken> borrowedThings = chickenController.getChickensBorrowedFromOthers();

        ArrayList<Chicken> expectedCase = new ArrayList<Chicken>();
        expectedCase.add(chicken2);

        assertTrue(borrowedThings.equals(expectedCase));
    }

    /* US 06.02.01
     * Will require testing an activity and verifying that upon launching the
     * sub-activity only displays items belonging to self and have status
     * 'borrowed'
     */
    public void testGetBorrowedFromMe(){
        ArrayList<Chicken> myBorrowedThings = chickenController.getChickensLentByMe();

        ArrayList<Chicken> expectedCase = new ArrayList<Chicken>();
        expectedCase.add(chicken4);

        assertTrue(myBorrowedThings.equals(expectedCase));
    }
}
