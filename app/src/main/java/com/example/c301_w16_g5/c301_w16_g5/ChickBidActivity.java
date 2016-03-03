package com.example.c301_w16_g5.c301_w16_g5;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Responsible for placing the toolbar at the top of every activity screen.
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
}
