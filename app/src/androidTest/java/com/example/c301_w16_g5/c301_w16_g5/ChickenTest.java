package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

public class ChickenTest extends ActivityInstrumentationTestCase2 {

    public ChickenTest() {
        super(HomeActivity.class);
    }

    Chicken chicken;
    User user;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        user = new User("user123", "Joe", "Smith", "jsmith@ualberta.ca",
                "780-555-4321", "Owned chickens for years.");
        chicken = new Chicken("Bob", "Friendly chicken", user.getUsername());
    }

    @Override
    protected void tearDown() throws Exception {
        user.removeAllChickens();
    }

    // US 01.01.01
    public void testAddAThing() {
        user.addChicken(chicken);
        assertFalse(user.getNumberOfChickens() == 0);
    }

    // US 01.04.01
    public void testEditMyThing() {
        user.addChicken(chicken);
        assertFalse(user.getNumberOfChickens() == 0);

        Chicken chicken = user.getChicken(0);
        chicken.update("Tim", "No chicken", Chicken.ChickenStatus.AVAILABLE);

        assertEquals(chicken.getName(), "Tim");
        assertEquals(chicken.getDescription(), "No chicken");
    }

    // US 01.05.01
    public void testDeleteMyThing() {
        user.addChicken(chicken);
        assertFalse(user.getNumberOfChickens() == 0);

        user.deleteChicken(chicken);
        assertEquals(user.getNumberOfChickens(), 0);
    }

    // US 02.01.01
    public void testThingHasStatus() {
        user.addChicken(chicken);
        assertFalse(user.getNumberOfChickens() == 0);

        assertTrue(chicken.getChickenStatus() == Chicken.ChickenStatus.AVAILABLE);
    }
}
