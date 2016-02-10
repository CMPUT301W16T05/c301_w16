package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class BorrowTest {
    public Chicken chicken1 = new Chicken();
    public Chicken chicken2 = new Chicken();
    public Chicken chicken3 = new Chicken();
    public Chicken chicken4 = new Chicken();
    ArrayList<Chicken> listOfThings = new ArrayList<Chicken>();

    /*
     * Create a list of reference items with which to test.
     */
    @Before
    public void InitializeThings(){
        chicken1.setName("Name");
        chicken1.setDescription("Description");
        chicken1.setStatus("Available");
        chicken1.setOwner(user.getId());

        chicken2.setName("Name");
        chicken2.setDescription("Description");
        chicken2.setStatus("Borrowed");
        chicken2.setOwner(user.getId());

        chicken3.setName("Name");
        chicken3.setDescription("Description");
        chicken3.setStatus("Available");
        chicken3.setOwner("SomeoneElse");

        chicken4.setName("Name");
        chicken4.setDescription("Description");
        chicken4.setStatus("Borrowed");
        chicken4.setOwner("SomeoneElse");

        listOfThings.add(chicken1);
        listOfThings.add(chicken2);
        listOfThings.add(chicken3);
        listOfThings.add(chicken4);
    }

    /*
     * Will require testing an activity and verifying that upon launching the
     * sub-activity only displays items belonging to another user and have status
     * 'borrowed'
     */
    @Test
    public void test_getBorrowedFromOthers(){
        ArrayList<Chicken> borrowedThings = Activity.getBorrowedFromOthers(listOfThings);
        assertEqual(borrowedThings, new ArrayList<Chicken>().add(chicken4));
    }

    /*
     * Will require testing an activity and verifying that upon launching the
     * sub-activity only displays items belonging to self and have status
     * 'borowed'
     */
    @Test
    public void getBorrowedFromMe(){
        ArrayList<Chicken> myBorrowedThings = Activity.getBorrowedFromMe(listOfThings);
        assertEqual(myBorrowedThings, new ArrayList<Chicken>().add(chicken2));
    }
}