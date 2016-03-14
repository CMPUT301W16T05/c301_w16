package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * This is the view the user sees when they are looking at their notifications,
 * indicating activity related to chickens they own or have bidded on.
 * Notifications are shown in a list format.
 *
 * @author  Hailey
 * @version 1.4, 03/14/2016
 * @see     Notification
 */
public class NotificationsActivity extends ChickBidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        onCreateOptionsMenu(menu);
        menu.removeItem(R.id.notifications_button);
        return true;
    }
}
