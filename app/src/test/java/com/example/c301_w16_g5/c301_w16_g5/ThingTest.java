package com.example.c301_w16_g5.c301_w16_g5;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ThingTest {
    Chicken chicken;
    User user;

    @Before
    public void initializeVariables() {
        user = new User();
        chicken = new Chicken("Bob", "Friendly chicken", 13.55, Chicken.ChickenStatus.AVAILABLE);
        user.removeAllChickens();
    }

    @Test
    public void addAThing() {
        user.addChicken(chicken);
        assertNotEquals(user.getNumberOfThings(), 0);
    }

    @Test
    public void EditMyThing() {
        user.addChicken(chicken);
        assertNotEquals(user.getNumberOfThings(), 0);

        Chicken chicken = user.getChicken(0);
        chicken.update("Tim", "No chicken", 15.99, Chicken.ChickenStatus.AVAILABLE);

        assertEquals(chicken.getName(), "Tim");
        assertEquals(chicken.getDescription(), "No chicken");
    }

    @Test
    public void DeleteMyThing() {
        user.addChicken(chicken);
        assertNotEquals(user.getNumberOfThings(), 0);

        user.deleteChicken(chicken);
        assertEquals(user.getNumberOfThings(), 0);
    }

    @Test
    public void ThingHasStatus() {
        user.addChicken(chicken);
        assertNotEquals(user.getNumberOfThings(), 0);

        assertNotEquals(chicken.getChickenStatus(), 0);
    }
}