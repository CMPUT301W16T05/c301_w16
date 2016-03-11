package com.example.c301_w16_g5.c301_w16_g5;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * sample comment
 */
public class ItemViews extends ChickBidActivity {

    private TabLayout.Tab tab_owned;
    private TabLayout.Tab tab_owned_with_bids;
    private TabLayout.Tab tab_lent;
    private TabLayout.Tab tab_borrowed;
    private TabLayout.Tab tab_bids_placed;

    private ArrayList<Chicken> listOfChickens = new ArrayList<Chicken>();   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_views);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        TabLayout.Tab tab_owned = tabLayout.newTab().setText(R.string.item_profile_owned);
        TabLayout.Tab tab_owned_with_bids = tabLayout.newTab().setText(R.string.item_profile_owned_with_bids);
        TabLayout.Tab tab_lent = tabLayout.newTab().setText(R.string.item_profile_lent);
        TabLayout.Tab tab_borrowed = tabLayout.newTab().setText(R.string.item_profile_borrowed);
        TabLayout.Tab tab_bids_placed = tabLayout.newTab().setText(R.string.item_profile_bids_placed);

        tabLayout.addTab(tab_owned);
        tabLayout.addTab(tab_owned_with_bids);
        tabLayout.addTab(tab_lent);
        tabLayout.addTab(tab_borrowed);
        tabLayout.addTab(tab_bids_placed);

        tabLayout.setOnTabSelectedListener(new ItemTabListener());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_chicken_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_views, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ItemTabListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }
}
