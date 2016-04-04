package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;

import com.robotium.solo.Solo;

/**
 * Created by Robin on 2016-04-03.
 */
public class TestUseCasePhotograph extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    private String username = "RobinV";

    public TestUseCasePhotograph() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        // run before each test case
        solo = new Solo(getInstrumentation());
        getActivity();

        // enter the app
        solo.unlockScreen();
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), username);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        // run after each test case
        solo.finishOpenedActivities();
    }

    public void testAddingPhoto(){
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Chicken Views Activity", ChickenViewsActivity.class);
        solo.clickInList(1, 0);
        solo.assertCurrentActivity("Expected Chicken Profile Activity", MyChickenDisplayProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.buttonEdit));
        solo.assertCurrentActivity("Expected Edit Chicken Activity", EditChickenActivity.class);
        solo.clickOnView(solo.getView(R.id.buttonUploadImage));
        //TODO: Assert that the gallery is loaded and image selected
    }

    public void testViewingPhoto() {
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Chicken Views Activity", ChickenViewsActivity.class);
        solo.clickInList(1, 0);
        solo.assertCurrentActivity("Expected Chicken Profile Activity", MyChickenDisplayProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.buttonViewPhoto));
        solo.assertCurrentActivity("Expected View Photo Activity", ViewPhotoActivity.class);
    }
}
