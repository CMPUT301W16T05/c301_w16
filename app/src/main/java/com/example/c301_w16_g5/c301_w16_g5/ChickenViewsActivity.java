package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


/**
 * Displays tab-headed lists of various sets of chickens (owned, bidded, lent,
 * etc.)
 *
 * @author  Shahzeb
 * @version 1.4, 03/08/2016
 * @see     Chicken
 * @see     ChickenController
 */
public class ChickenViewsActivity extends ChickBidActivity  {

    private enum TabCategory {
        POSSESSION,
        OWNED,
        OWNED_WITH_BIDS,
        LENT,
        BORROWED,
        BIDS_PLACED
    }

    private TabLayout tabLayout;

    private TabLayout.Tab tab_possession;
    private TabLayout.Tab tab_owned;
    private TabLayout.Tab tab_owned_with_bids;
    private TabLayout.Tab tab_lent;
    private TabLayout.Tab tab_borrowed;
    private TabLayout.Tab tab_bids_placed;

    private ArrayList<Chicken> listOfChickens = new ArrayList<>();

    private ArrayAdapter<Chicken> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken_views);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        listView = (ListView) findViewById(R.id.chickenList);

        tab_possession = tabLayout.newTab().setText(R.string.item_profile_possession).setTag(TabCategory.POSSESSION);
        tab_owned = tabLayout.newTab().setText(R.string.item_profile_owned).setTag(TabCategory.OWNED);
        tab_owned_with_bids = tabLayout.newTab().setText(R.string.item_profile_owned_with_bids).setTag(TabCategory.OWNED_WITH_BIDS);
        tab_lent = tabLayout.newTab().setText(R.string.item_profile_lent).setTag(TabCategory.LENT);
        tab_borrowed = tabLayout.newTab().setText(R.string.item_profile_borrowed).setTag(TabCategory.BORROWED);
        tab_bids_placed = tabLayout.newTab().setText(R.string.item_profile_bids_placed).setTag(TabCategory.BIDS_PLACED);

        tabLayout.addTab(tab_possession);
        tabLayout.addTab(tab_owned);
        tabLayout.addTab(tab_owned_with_bids);
        tabLayout.addTab(tab_lent);
        tabLayout.addTab(tab_borrowed);
        tabLayout.addTab(tab_bids_placed);

        tabLayout.setOnTabSelectedListener(new ItemTabListener());

        final Intent viewMyChickenIntent = new Intent(this, MyChickenDisplayProfileActivity.class);
        final Intent viewOthersChickenIntent = new Intent(this, OthersChickenDisplayProfileActivity.class);

        registerForContextMenu(listView);

        // Mar 20, 2016 - http://stackoverflow.com/questions/9097723/adding-an-onclicklistener-to-listview-android
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chicken chicken = (Chicken) listView.getItemAtPosition(position);
                ChickBidsApplication.getChickenController().setCurrentChicken(chicken);

                if (ChickBidsApplication.getChickenController().getMyOwnedChickens().contains(chicken)) {
                    startActivity(viewMyChickenIntent);
                } else {
                    startActivity(viewOthersChickenIntent);
                }
            }
        });

        updateForTab(tab_possession);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_chicken_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddChicken();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.delete:
                Chicken chicken = adapter.getItem(info.position);

                if (ChickBidsApplication.getSearchController().checkOnline()) {
                    if (chicken.getOwnerUsername().equals(current_user.getUsername())) {
                        ChickBidsApplication.getChickenController().removeChickenForMe(chicken);
                        updateForTab(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()));
                    } else {
                        Toast.makeText(this, "Cannot delete a borrowed chicken.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Cannot delete a chicken offline.", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChickBidsApplication.getChickenController().popupNotificationToast(this);
        updateForTab(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()));
    }

    private void updateForTab(TabLayout.Tab tab) {
        updateList((TabCategory) tab.getTag());
        refreshTableTitle(tab.getText().toString());
        refreshList();
    }

    private void updateList(TabCategory tabCategory) {
        ChickenController chickenController = ChickBidsApplication.getChickenController();

        switch (tabCategory) {
            case POSSESSION:
                listOfChickens = chickenController.getAllChickensInMyPossession();
                break;
            case OWNED:
                listOfChickens = chickenController.getMyOwnedChickens();
                break;
            case OWNED_WITH_BIDS:
                listOfChickens = chickenController.getMyChickensWithBids();
                break;
            case LENT:
                listOfChickens = chickenController.getChickensLentByMe();
                break;
            case BORROWED:
                listOfChickens = chickenController.getChickensBorrowedFromOthers();
                break;
            case BIDS_PLACED:
                listOfChickens = chickenController.getChickensBiddedOnByMe();
                break;
            default:
                break;
        }
    }

    private void refreshTableTitle(String text) {
        TextView textView = (TextView) findViewById(R.id.chickenListTitle);
        textView.setText(text);
    }

    private void refreshList() {
        adapter = new ChickenAdapter(this, listOfChickens);
        listView.setAdapter(adapter);
    }

    public class ItemTabListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            updateForTab(tab);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    private void launchAddChicken(){
        Intent addChickenIntent = new Intent(this, AddChickenActivity.class);
        startActivity(addChickenIntent);
    }
}
