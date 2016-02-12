package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

/**
 * Created by Hailey on 2016-02-11.
 */
public class EditProfileActivityTest extends ActivityInstrumentationTestCase2 {
    public EditProfileActivityTest() {
        super(EditProfileActivity.class);
    }

    public void testSendProfile() {
        Intent intent = new Intent();
        UserProfile profile = new UserProfile("unique_user123", "First",
                "Last", "user@ualberta.ca", "780-123-4567",
                "I am a dedicated chicken enthusiast");

        intent.putExtra(AddProfileActivity.TEXT_SPECIFYING_PROFILE, profile);
        setActivityIntent(intent);
        AddProfileActivity epa = (AddProfileActivity) getActivity();

        assertEquals("AddProfileActivity should get the user from the intent",
                profile, epa.getUserProfile());
    }
}