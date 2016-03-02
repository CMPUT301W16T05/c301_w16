package com.example.c301_w16_g5.c301_w16_g5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public abstract class ChickBidActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: add main_actionbar to layout
        getMenuInflater().inflate(R.menu.main_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            // TODO: add home_button to menu
            case R.id.home_button:
                break;

            // TODO: add navigation_button to menu:
            case R.id.notification_button:
                break;
        }
        return false; // FIXME: return more meaningful result
    }
}
