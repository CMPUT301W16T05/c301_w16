package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

/**
 * Created by Hailey on 2016-03-31.
 */
public class TestUseCaseUserProfile extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private String username = "hailey123";  // must be a user with at least 1 chicken
    private String newUsername = "newHailey";

    public TestUseCaseUserProfile() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        // run before each test case
        solo = new Solo(getInstrumentation());
        getActivity();

        ChickBidsApplication.getSearchController().removeUserFromDatabase(newUsername);
    }

    @Override
    public void tearDown() throws Exception {
        // run after each test case
        solo.finishOpenedActivities();
    }

    public void testCreateProfile() {
        // enter the app
        solo.unlockScreen();
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), newUsername);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Edit (Add) Profile Activity", EditProfileActivity.class);

        assertEquals(((EditText) solo.getView(R.id.username)).getText().toString(), newUsername);
        assertEquals(((EditText) solo.getView(R.id.firstName)).getText().toString(), "");
        assertEquals(((EditText) solo.getView(R.id.lastName)).getText().toString(), "");
        assertEquals(((EditText) solo.getView(R.id.email)).getText().toString(), "");
        assertEquals(((EditText) solo.getView(R.id.phone)).getText().toString(), "");
        assertEquals(((EditText) solo.getView(R.id.experience)).getText().toString(), "");

        String firstName = "Hailey";
        String lastName = "Musselman";
        String email = "email@email.com";
        String phone = "321-432-5432";
        String experience = "I recently began raising chickens";
        solo.enterText((EditText) solo.getView(R.id.firstName), firstName);
        solo.enterText((EditText) solo.getView(R.id.lastName), lastName);
        solo.enterText((EditText) solo.getView(R.id.email), email);
        solo.enterText((EditText) solo.getView(R.id.phone), phone);
        solo.enterText((EditText) solo.getView(R.id.experience), experience);

        solo.clickOnView(solo.getView(R.id.buttonSave));
        // TODO: fix the broken app behaviour

        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);

        // check everything saved
        solo.clickOnView(solo.getView(R.id.buttonProfile));
        solo.assertCurrentActivity("Expected Display Profile Activity", DisplayProfileActivity.class);

        assertEquals(((TextView) solo.getView(R.id.usernameTextView)).getText().toString(), newUsername);
        assertEquals(((TextView) solo.getView(R.id.nameTextView)).getText().toString(), firstName + " " + lastName);
        assertEquals(((TextView) solo.getView(R.id.emailTextView)).getText().toString(), email);
        assertEquals(((TextView) solo.getView(R.id.phoneNumberTextView)).getText().toString(), phone);
        assertEquals(((TextView) solo.getView(R.id.chickenExperienceTextView)).getText().toString(), experience);
    }

    public void testEditProfile() {
        // enter the app
        solo.unlockScreen();
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), username);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);

        // go to profile, see what's there
        solo.clickOnView(solo.getView(R.id.buttonProfile));
        solo.assertCurrentActivity("Expected Display My Profile Activity", DisplayProfileActivity.class);

        String newFirstName = "Hai";
        String newLastName = "Owen";
        String newEmail = "email2@email.com";
        String newPhone = "780-432-5432";
        String newExperience = "I changed my name and still have chickens";

        // we make sure the original info is different, so the change can be seen
        String currentFirstName = ((TextView) solo.getView(R.id.nameTextView)).getText().toString().split(" ")[0];
        if (currentFirstName.equals(newFirstName)) {
            newFirstName = "Hailey";
        }

        String currentLastName = ((TextView) solo.getView(R.id.nameTextView)).getText().toString().split(" ")[1];
        if (currentLastName.equals(newLastName)) {
            newLastName = "Musselman";
        }
        String currentEmail = ((TextView) solo.getView(R.id.emailTextView)).getText().toString();
        if (currentEmail.equals(newEmail)) {
            newEmail = "email@email.com";
        }
        String currentPhone = ((TextView) solo.getView(R.id.phoneNumberTextView)).getText().toString();
        if (currentPhone.equals(newPhone)) {
            newPhone = "321-432-5432";
        }
        String currentExperience = ((TextView) solo.getView(R.id.chickenExperienceTextView)).getText().toString();
        if (currentExperience.equals(newExperience)) {
            newExperience = "I recently began raising chickens";
        }

        // edit the info
        solo.clickOnView(solo.getView(R.id.fab));
        solo.assertCurrentActivity("Expected Edit Profile Activity", EditProfileActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.firstName));
        solo.clearEditText((EditText) solo.getView(R.id.lastName));
        solo.clearEditText((EditText) solo.getView(R.id.email));
        solo.clearEditText((EditText) solo.getView(R.id.phone));
        solo.clearEditText((EditText) solo.getView(R.id.experience));

        solo.enterText((EditText) solo.getView(R.id.firstName), newFirstName);
        solo.enterText((EditText) solo.getView(R.id.lastName), newLastName);
        solo.enterText((EditText) solo.getView(R.id.email), newEmail);
        solo.enterText((EditText) solo.getView(R.id.phone), newPhone);
        solo.enterText((EditText) solo.getView(R.id.experience), newExperience);

        // save and check for the update
        solo.clickOnView(solo.getView(R.id.buttonSave));
        solo.assertCurrentActivity("Expected Display My Profile Activity", DisplayProfileActivity.class);

        assertEquals(newFirstName + " " + newLastName, ((TextView) solo.getView(R.id.nameTextView)).getText().toString());
        assertEquals(newEmail, ((TextView) solo.getView(R.id.emailTextView)).getText().toString());
        assertEquals(newPhone, ((TextView) solo.getView(R.id.phoneNumberTextView)).getText().toString());
        assertEquals(newExperience, ((TextView) solo.getView(R.id.chickenExperienceTextView)).getText().toString());
    }

    public void testGetUserForChicken() {
        // enter the app
        solo.unlockScreen();
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), username);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);

        // TODO: add test
    }
}
