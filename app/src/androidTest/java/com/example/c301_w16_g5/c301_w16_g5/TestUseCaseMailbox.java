package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.util.Date;

/**
 * Created by Hailey on 2016-04-04.
 */
public class TestUseCaseMailbox extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private String senderUsername = "hailey123";
    private String receiverUsername = "jsmith";
    private String message = "test message";
    private Date sentDate;

    public TestUseCaseMailbox() {
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
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), senderUsername);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        // run after each test case
        solo.finishOpenedActivities();
    }

    public void testMessageExchangeProcess() {
        sendMessage();
        viewMessage();
        deleteMessage();
    }

    private void sendMessage() {
        // go to send message screen
        solo.clickOnView(solo.getView(R.id.notifications_button));
        solo.assertCurrentActivity("Expected Notifications Activity", NotificationsActivity.class);
        solo.clickOnView(solo.getView(R.id.sendMessage));
        solo.assertCurrentActivity("Expected Send Message Activity", SendMessageActivity.class);

        // enter message
        solo.enterText((EditText) solo.getView(R.id.username), receiverUsername);
        solo.enterText((EditText) solo.getView(R.id.messageEditText), message);
        solo.clickOnView(solo.getView(R.id.buttonSend));
    }

    private void viewMessage() {

    }

    private void deleteMessage() {

    }

    private void changeFromSendingToReceivingUser() {

    }
}
