package com.example.c301_w16_g5.c301_w16_g5;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ThingTest {
    Chicken chicken;
    User user;

    @BeforeClass
    public void initializeVariables() {
        user = new User();
        chicken = new Chicken("Bob", "Friendly chicken", user, AVAILABLE);
    }

    @Before
    public void resetUser() {
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
        chicken.update("Tim", "No chicken", user, AVAILABLE);

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

        assertNotEquals(chicken.getStatus(), 0);
    }
}