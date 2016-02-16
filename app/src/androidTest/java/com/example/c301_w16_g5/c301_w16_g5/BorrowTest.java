package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import java.util.ArrayList;

public class BorrowTest extends ActivityInstrumentationTestCase2 {

    public BorrowTest() {
        super(MainScreen.class);
    }

    private User user = User.getCurrentUser();

    public Chicken chicken1 = new Chicken();
    public Chicken chicken2 = new Chicken();
    public Chicken chicken3 = new Chicken();
    public Chicken chicken4 = new Chicken();
    ArrayList<Chicken> listOfThings = new ArrayList<Chicken>();

    /*
     * Create a list of reference items with which to test.
     */
    protected void setUp() throws Exception {
        super.setUp();

        chicken1.setName("Name");
        chicken1.setDescription("Description");
        chicken1.setStatus(Chicken.Status.AVAILABLE);
        chicken1.setOwner(user);

        chicken2.setName("Name");
        chicken2.setDescription("Description");
        chicken2.setStatus(Chicken.Status.BORROWED);
        chicken2.setOwner(user);

        chicken3.setName("Name");
        chicken3.setDescription("Description");
        chicken3.setStatus(Chicken.Status.AVAILABLE);
        chicken3.setOwner(new User());

        chicken4.setName("Name");
        chicken4.setDescription("Description");
        chicken4.setStatus(Chicken.Status.BORROWED);
        chicken4.setOwner(new User());

        listOfThings.add(chicken1);
        listOfThings.add(chicken2);
        listOfThings.add(chicken3);
        listOfThings.add(chicken4);
    }

    /* US 06.01.01
     * Will require testing an activity and verifying that upon launching the
     * sub-activity only displays items belonging to another user and have status
     * 'borrowed'
     */
    public void testGetBorrowedFromOthers(){
        ArrayList<Chicken> borrowedThings = user.getChickensBorrowed();

        ArrayList<Chicken> expectedCase = new ArrayList<Chicken>();
        expectedCase.add(chicken4);

        assertTrue(borrowedThings.equals(expectedCase));
    }

    /* US 06.02.01
     * Will require testing an activity and verifying that upon launching the
     * sub-activity only displays items belonging to self and have status
     * 'borrowed'
     */
    public void testGetBorrowedFromMe(){
        ArrayList<Chicken> myBorrowedThings = user.getChickensLent();

        ArrayList<Chicken> expectedCase = new ArrayList<Chicken>();
        expectedCase.add(chicken2);

        assertTrue(myBorrowedThings.equals(expectedCase));
    }

}
