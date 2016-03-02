package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends ChickBidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(myToolbar);

        Button profileButton = (Button) findViewById(R.id.buttonProfile);
        Button itemsButton = (Button) findViewById(R.id.buttonItems);
        Button searchButton = (Button) findViewById(R.id.buttonSearch);

        final Intent intent = new Intent(this, AddProfileActivity.class);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //disable going back to login screen
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    // TODO: reference source
    // used to make home icon disappear
    // http://stackoverflow.com/questions/5440601/android-how-to-enable-disable-option-menu-item-on-button-click answer by Frank
    // accessed on Mar 2 2016 at 3:20 pm by Satyen Akolkar
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.home_button);
        item.setEnabled(false);
        item.getIcon().setAlpha(0);
        return true;
    }
}
