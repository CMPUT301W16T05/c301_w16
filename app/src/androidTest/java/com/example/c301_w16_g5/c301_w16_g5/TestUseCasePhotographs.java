package com.example.c301_w16_g5.c301_w16_g5;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.robotium.solo.Solo;

/**
 * US 09: Photographs
 */
public class TestUseCasePhotographs extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    private String username = "RobinV";

    public TestUseCasePhotographs() {
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
        ImageView imageChicken = (ImageView)solo.getView(R.id.imageChicken);

        if (imageChicken.getVisibility() != View.INVISIBLE) {
            fail();
        }
        Bitmap bm = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
        try {
            imageChicken.setImageBitmap(bm);
        }catch(Exception e){
            fail();
        }
    }

    public void testViewingPhoto() {
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Chicken Views Activity", ChickenViewsActivity.class);
        solo.clickInList(1, 0);
        solo.assertCurrentActivity("Expected Chicken Profile Activity", MyChickenDisplayProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.buttonViewPhoto));
        solo.assertCurrentActivity("Expected View Photo Activity", ViewPhotoActivity.class);
    }

    public void testLargePhoto(){
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Chicken Views Activity", ChickenViewsActivity.class);
        solo.clickInList(1, 0);
        solo.assertCurrentActivity("Expected Chicken Profile Activity", MyChickenDisplayProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.buttonEdit));
        solo.assertCurrentActivity("Expected Edit Chicken Activity", EditChickenActivity.class);
        ImageView imageChicken = (ImageView)solo.getView(R.id.imageChicken);

        if (imageChicken.getVisibility() != View.INVISIBLE) {
            fail();
        }
        Bitmap bm = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        try {
            imageChicken.setImageBitmap(bm);
            assertTrue(solo.waitForText("Image is too large."));
        }catch(Exception e){
            fail();
        }
    }
}
