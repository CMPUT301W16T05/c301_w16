package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * The main screen, from which the user can access their profile or chickens,
 * or perform a search
 *
 * @author  Satyen
 * @version 1.4, 03/02/2016
 * @see     UserProfileActivity
 * @see     MyChickenListActivity
 * @see     SearchActivity
 */
public class HomeActivity extends ChickBidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(myToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        invalidateOptionsMenu();

        Button profileButton = (Button) findViewById(R.id.buttonProfile);
        Button chickensButton = (Button) findViewById(R.id.buttonChickens);
        Button searchButton = (Button) findViewById(R.id.buttonSearch);

        final Intent profileIntent = new Intent(this, UserProfileActivity.class);
        final Intent chickensIntent = new Intent(this, ItemViews.class);
        final Intent searchIntent = new Intent(this, SearchActivity.class);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(profileIntent);
            }
        });
        chickensButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(chickensIntent);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(searchIntent);
            }
        });
    }
}
