package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Observer;

/**
 * This activity is responsible for placing the toolbar at the top of every
 * activity screen.  It will be inherited from by the other activities (except
 * the welcome screen).
 *
 * @author  Satyen
 * @version 1.4, 03/02/2016
 */
public abstract class ChickBidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                finish();
                break;
            case R.id.home_button:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.notifications_button:
                startActivity(new Intent(this, NotificationsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
