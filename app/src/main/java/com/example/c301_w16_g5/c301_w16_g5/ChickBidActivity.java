package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
        Intent intent = null;

        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.home_button:
                intent = new Intent(this, HomeActivity.class);
                // 2016-04-02 http://stackoverflow.com/questions/11460896/button-to-go-back-to-mainactivity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.notifications_button:
                intent = new Intent(this, NotificationsActivity.class);
                break;
        }

        startActivity(intent);
        return true;
    }
}
