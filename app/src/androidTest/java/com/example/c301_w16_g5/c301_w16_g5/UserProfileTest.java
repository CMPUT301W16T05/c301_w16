package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;


/**
 * Created by Hailey on 2016-02-09.
 */
public class UserProfileTest extends ActivityInstrumentationTestCase2 {
    public UserProfileTest() {
        super(UserProfile.class);
    }

    // US 03.01.01
    public void testAddMyProfile() {
        User user = new User();
        assert(!user.hasUserProfile());
        UserProfile profile = new UserProfile("unique_user123", "First",
                "Last", "user@ualberta.ca", "780-123-4567",
                "I am a dedicated chicken enthusiast");
        user.setUserProfile(profile);
        assert(user.hasUserProfile());
        assertEquals("unique_user123", user.getUserProfile().getUsername());
        assertEquals("First", user.getUserProfile().getFirstName());
        assertEquals("Last", user.getUserProfile().getLastName());
        assertEquals("user@ualberta.ca", user.getUserProfile().getEmail());
        assertEquals("780-123-4567", user.getUserProfile().getPhoneNumber());
        assertEquals("I am a dedicated chicken enthusiast",
                user.getUserProfile().getChickenExperience());

        // username already in use
        User user2 = new User();
        UserProfile profile2 = new UserProfile("unique_user123", "First2",
                "Last2", "user2@ualberta.ca", "780-123-7654",
                "I am also a dedicated chicken enthusiast");
        try {
            user2.setUserProfile(profile2);
            fail("fail on non-unique username");
        }
        catch(Exception e) {
            // success
            assert(!user2.hasUserProfile());
        }

        // leaving a field blank
        UserProfile profile3 = new UserProfile("unique_user2", "",
                "Last", "user2@ualberta.ca", "780-123-4567",
                "I am a dedicated chicken enthusiast");
        try {
            user2.setUserProfile(profile);
            fail("fail on blank field");
        }
        catch(Exception e) {
            // success
            assert(!user2.hasUserProfile());
        }

        // invalidly formatted phone number
        profile = new UserProfile("unique_user2", "First",
                "Last", "user2@ualberta.ca", "7a0-123-4567",
                "I am a dedicated chicken enthusiast");
        try {
            user2.setUserProfile(profile);
            fail("fail on invalid phone number");
        }
        catch(Exception e) {
            // success
            assert(!user2.hasUserProfile());
        }

        // invalidly formatted email
        profile = new UserProfile("unique_user2", "First",
                "Last", "user2#ualberta.ca", "780-123-4567",
                "I am a dedicated chicken enthusiast");
        try {
            user2.setUserProfile(profile);
            fail("fail on invalid email");
        }
        catch(Exception e) {
            // success
            assert(!user2.hasUserProfile());
        }
    }

    // US 03.02.01
    public void testEditMyProfile() {
        User user1 = new User();
        UserProfile profile1 = new UserProfile("unique_user1", "First",
                "Last", "user@ualberta.ca", "780-123-4567",
                "I am a dedicated chicken enthusiast");
        user1.setUserProfile(profile1);

        User user2 = new User();
        UserProfile profile2 = new UserProfile("unique_user2", "Fred",
                "Smith", "fred@ualberta.ca", "780-555-4567",
                "I am another dedicated chicken enthusiast");
        user2.setUserProfile(profile2);

        // username already in use
        try {
            user1.getUserProfile().setUsername("unique_user2");
            fail("fail on non-unique username");
        }
        catch(Exception e) {
            // success
            assertEquals("unique_user1", user1.getUserProfile().getUsername());
        }

        // leaving a field blank
        try {
            user1.getUserProfile().setFirstName("");
            fail("fail on blank field");
        }
        catch(Exception e) {
            // success
            assertEquals("First", user1.getUserProfile().getFirstName());
        }

        // invalidly formatted phone number
        try {
            user1.getUserProfile().setPhoneNumber("abc-222-3456");
            fail("fail on invalid phone number");
        }
        catch(Exception e) {
            // success
            assertEquals("780-123-4567", user1.getUserProfile().getPhoneNumber());
        }

        // invalidly formatted email
        try {
            user1.getUserProfile().setEmail("my-other-email#ualberta.ca");
            fail("fail on invalid email");
        }
        catch(Exception e) {
            // success
            assertEquals("user@ualberta.ca", user1.getUserProfile().getEmail());
        }
    }

    // US 03.03.01
    public void testGetUserForChicken() {
        UserList allUsers = new UserList(); // subject to change based on how users are stored

        User user1 = new User();
        UserProfile profile1 = new UserProfile("unique_user1", "First",
                "Last", "user@ualberta.ca", "780-123-4567",
                "I am a dedicated chicken enthusiast");
        user1.setUserProfile(profile1);

        User user2 = new User();
        UserProfile profile2 = new UserProfile("unique_user2", "Fred",
                "Smith", "fred@ualberta.ca", "780-555-4567",
                "I am another dedicated chicken enthusiast");
        user2.setUserProfile(profile2);

        allUsers.add(user1);
        allUsers.add(user2);

        assertEquals(profile1, allUsers.getUserProfileForUsername("unique_user1"));

        try {
            allUsers.getUserProfileForUsername("not-a-username");
            fail("fail on unused username");
        }
        catch(Exception e) {
            // success
        }
    }
}
