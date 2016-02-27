package com.example.c301_w16_g5.c301_w16_g5;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ReturningTest {

    private User owner, borrower;
    private Chicken chicken;

    @Before
    public void Initialize(){
        owner = new User();
        borrower = new User();

        chicken = new Chicken("Name", "FriendlyBird", 1.00, Chicken.ChickenStatus.BORROWED );
        borrower.addChicken(chicken);
    }

    @Test
    public void ReturningAChicken(){
        borrower.releaseChicken(chicken);
        assert(owner.getChicken(chicken).getChickenStatus() == Chicken.ChickenStatus.AVAILABLE);
    }
}