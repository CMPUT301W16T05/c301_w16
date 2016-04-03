package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * The main screen, from which the user can access their profile or chickens,
 * or perform a search
 *
 * @author  Satyen
 * @version 1.4, 03/02/2016
 * @see     DisplayProfileActivity
 * @see     ChickenViewsActivity
 * @see     SearchesActivity
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
        Button logoutButton = (Button) findViewById(R.id.buttonLogout);

        final Intent profileIntent = new Intent(this, DisplayProfileActivity.class);
        final Intent chickensIntent = new Intent(this, ChickenViewsActivity.class);
        final Intent searchIntent = new Intent(this, SearchesActivity.class);

        ChickBidsApplication.getChickenController().pushOfflineChickens();

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
                if (ChickBidsApplication.getSearchController().checkOnline()) {
                    startActivity(searchIntent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Cannot search offline.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //logout();
            }
        });
    }

    private void logout() {
        ChickBidsApplication.getUserController().setUser(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChickBidsApplication.getChickenController().popupNotificationToast(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        onCreateOptionsMenu(menu);
        menu.removeItem(R.id.home_button);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ChickBidsApplication.getUserController().updateUser(current_user);
    }
}
