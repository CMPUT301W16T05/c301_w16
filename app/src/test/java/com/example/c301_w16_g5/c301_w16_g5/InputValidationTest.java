package com.example.c301_w16_g5.c301_w16_g5;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class InputValidationTest {

    @Test
    public void testValidateUsername() {
        assertTrue(UserController.validateUsername("test"));
        assertFalse(UserController.validateUsername("tes#t"));
        assertFalse(UserController.validateUsername("test "));
    }

    @Test
    public void testValidateNames() {
        assertTrue(UserController.validateNames("Tom"));
        assertFalse(UserController.validateNames("Tom "));
        assertFalse(UserController.validateNames("T.om "));
    }

    @Test
    public void testValidateEmail() {
        assertTrue(UserController.validateEmail("test@hotmail.com"));
        assertFalse(UserController.validateEmail("Tsd "));
        assertFalse(UserController.validateEmail("T.com "));
    }

    @Test
    public void testValidatePhone() {
        assertTrue(UserController.validatePhoneNumber("780-555-6666"));
        assertFalse(UserController.validatePhoneNumber("780-5556666"));
        assertFalse(UserController.validatePhoneNumber("7805556666"));
        assertFalse(UserController.validatePhoneNumber("56666"));
    }

    @Test
    public void testValidateChickenExperience() {
        assertTrue(UserController.validateChickenExperience("Lots of experience"));
        assertFalse(UserController.validateChickenExperience(""));
    }

    @Test
    public void testValidateChickenName() {
        assertTrue(ChickenController.validateChickenName("Tom"));
        assertFalse(ChickenController.validateChickenName("T om"));
        assertFalse(ChickenController.validateChickenName("T.om"));
    }

    @Test
    public void testValidateChickenDescription() {
        assertTrue(ChickenController.validateChickenDescription("This chicken has read Crime and Punishment"));
        assertFalse(ChickenController.validateChickenDescription(""));
    }

    @Test
    public void testValidateChickenStatus() {
        assertTrue(ChickenController.validateChickenStatus(Chicken.ChickenStatus.AVAILABLE));
        assertTrue(ChickenController.validateChickenStatus(Chicken.ChickenStatus.NOT_AVAILABLE));
    }
}
