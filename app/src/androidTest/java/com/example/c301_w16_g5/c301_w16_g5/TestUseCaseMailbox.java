package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import java.util.Date;

/**
 * US 11: Mailbox
 */
public class TestUseCaseMailbox extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private String senderUsername = "hailey123";
    private String receiverUsername = "satyen";
    private String message = "test message";
    ListView listView;

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
        changeFromSendingToReceivingUser();
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
        // go to view messages
        solo.clickOnView(solo.getView(R.id.notifications_button));
        solo.assertCurrentActivity("Expected Notifications Activity", NotificationsActivity.class);

        // is message visible?
        assertTrue("Message not visible.", solo.searchText(senderUsername + ": " + message));
    }

    private void deleteMessage() {
        // dismiss the notification
        listView = (ListView) solo.getView(R.id.notificationList);
        final int countBefore = listView.getCount();
        View view = listView.getChildAt(countBefore - 1);
        solo.clickOnView(view.findViewById(R.id.dismissNotification));

        // confirm it's gone
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return countBefore - 1 == listView.getCount();
            }
        }, 5);
    }

    private void changeFromSendingToReceivingUser() {
        // sending user logout
        solo.clickOnView(solo.getView(R.id.home_button));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);
        solo.clickOnView(solo.getView(R.id.buttonLogout));
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);

        // receiving user login
        solo.clearEditText((AutoCompleteTextView) solo.getView(R.id.usernameEntered));
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.usernameEntered), receiverUsername);
        solo.clickOnView(solo.getView(R.id.signInButton));
        solo.assertCurrentActivity("Expected Home Activity", HomeActivity.class);
    }
}
