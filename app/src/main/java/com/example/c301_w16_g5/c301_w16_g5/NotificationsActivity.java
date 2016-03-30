package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

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

    ListView listView;
    ArrayAdapter<Notification> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        invalidateOptionsMenu();

        listView = (ListView) findViewById(R.id.notificationList);

        final Intent sendMessageIntent = new Intent(this, SendMessageActivity.class);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.dismissAllNotifications);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChickBidsApplication.getSearchController().checkOnline()) {
                    startActivity(sendMessageIntent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Cannot send message offline.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        refreshView();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        onCreateOptionsMenu(menu);
        menu.removeItem(R.id.notifications_button);
        return true;
    }

    private void refreshView() {
        User user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Notification> notifications = user.getNotifications();

        adapter = new NotificationAdapter(this, notifications);
        listView.setAdapter(adapter);
    }


    private class NotificationAdapter extends ArrayAdapter<Notification> {
        public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
            super(context, 0, notifications);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Notification notification = getItem(position);
            String message = notification.getMessage();
            Date date = notification.getDate();

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification, parent, false);
            }

            TextView notificationMessage = (TextView) convertView.findViewById(R.id.notificationMessage);
            TextView notificationDate = (TextView) convertView.findViewById(R.id.notificationDate);
            ImageButton dismissButton = (ImageButton) convertView.findViewById(R.id.dismissNotification);

            // Populate the data into the template view using the data object

            notificationMessage.setText(message);
            notificationDate.setText(date.toString());
            dismissButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChickenController chickenController = ChickBidsApplication.getChickenController();
                    chickenController.dismissNotification(notification);
                    refreshView();
                }
            });

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
