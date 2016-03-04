package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends ChickBidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(myToolbar);
        invalidateOptionsMenu();

        Button profileButton = (Button) findViewById(R.id.buttonProfile);
        Button itemsButton = (Button) findViewById(R.id.buttonItems);
        Button searchButton = (Button) findViewById(R.id.buttonSearch);

        final Intent profileIntent = new Intent(this, UserProfileActivity.class);
        final Intent itemsIntent = new Intent(this, ItemViews.class);
        final Intent searchIntent = new Intent(this, SearchActivity.class);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(profileIntent);
            }
        });

        itemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(itemsIntent);
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
