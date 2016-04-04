package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Context;
import android.net.ConnectivityManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Hailey on 2016-04-03.
 */
public class TestUseCaseOfflineBehaviour extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    private String username = "hailey123";

    public TestUseCaseOfflineBehaviour() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        // run before each test case
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        // run after each test case
        solo.finishOpenedActivities();
    }

    public void testAddChickenOffline() {
        // enter the app
        solo.unlockScreen();
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), username);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);

        // get the original list of chickens
        ArrayList<Chicken> chickensExpected = ChickBidsApplication.getChickenController().getMyOwnedChickens();

        // lose connectivity
        try {
            setMobileDataEnabled(getActivity().getApplicationContext(), false);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return ChickBidsApplication.getSearchController().checkOnline();
            }
        }, 10);

        // go to add chicken screen
        solo.clickOnView(solo.getView(R.id.buttonChickens));
        solo.assertCurrentActivity("Expected Item Views Activity", ChickenViewsActivity.class);
        solo.clickOnView(solo.getView(R.id.add_chicken_fab));
        solo.assertCurrentActivity("Expected Add Chicken Activity", AddChickenActivity.class);

        // add a new chicken
        String name = "Bob";
        String description = "A test chicken";
        solo.enterText((EditText) solo.getView(R.id.name), name);
        solo.enterText((EditText) solo.getView(R.id.description), description);
        solo.clickOnView(solo.getView(R.id.buttonSave));

        // regain connectivity
        try {
            setMobileDataEnabled(getActivity().getApplicationContext(), true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return !ChickBidsApplication.getSearchController().checkOnline();
            }
        }, 10);

        // check - has chicken been saved in the database?
        assertEquals(chickensExpected.size() + 1,
                ChickBidsApplication.getChickenController().getMyOwnedChickens().size());
        assertEquals(name, ChickBidsApplication.getChickenController().getMyOwnedChickens().get(chickensExpected.size()).getName());
        assertEquals(description, ChickBidsApplication.getChickenController().getMyOwnedChickens().get(chickensExpected.size()).getDescription());
        assertEquals(username, ChickBidsApplication.getChickenController().getMyOwnedChickens().get(chickensExpected.size()).getOwnerUsername());
    }

    // http://stackoverflow.com/questions/11555366/enable-disable-data-connection-in-android-programmatically
    // author Tobias Moe Thorstensen
    // accessed by Hailey Musselman on April 3 '16
    private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
    }
}
