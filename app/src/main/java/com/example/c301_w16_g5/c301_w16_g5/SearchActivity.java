package com.example.c301_w16_g5.c301_w16_g5;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * <code>SearchActivity</code> provides the screen from which a user will
 * search for chickens that they may wish to bid on.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     ElasticSearchBackend
 * @see     Chicken
 * @see     User
 */
public class SearchActivity extends ChickBidActivity {

    private SearchView search;

    private ArrayList<Chicken> chickens = new ArrayList<Chicken>();
    private ArrayAdapter<Chicken> adapter;

    private ListView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchResults = (ListView) findViewById(R.id.searchResults);
        doSearch("");
    }

    // TODO: make the search field usable and display real chickens

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//
//        // Get the SearchView and set the searchable configuration
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        //this.searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                doSearch(query);
//                return true;
//            }
//        });
//        return true;
//    }

    public void doSearch(String query) {
        chickens = new ArrayList<Chicken>();
        chickens.add(new Chicken("Bob", "A friendly chicken who gets along with other chickens and will always be there for you",
                ChickBidsApplication.getUserController().getCurrentUser().getUsername()));
        chickens.add(new Chicken("Bob", "Friendly chicken", ChickBidsApplication.getUserController().getCurrentUser().getUsername()));
        chickens.add(new Chicken("Susan", "Ugly chicken", ChickBidsApplication.getUserController().getCurrentUser().getUsername()));
        chickens.add(new Chicken("Bob", "Friendly chicken", ChickBidsApplication.getUserController().getCurrentUser().getUsername()));
        chickens.add(new Chicken("Bob", "Friendly chicken", ChickBidsApplication.getUserController().getCurrentUser().getUsername()));
        chickens.add(new Chicken("Bob", "Friendly chicken", ChickBidsApplication.getUserController().getCurrentUser().getUsername()));
        chickens.add(new Chicken("NewBob", "Another friendly chicken who gets along with other chickens and will always be there for you",
                ChickBidsApplication.getUserController().getCurrentUser().getUsername()));
        chickens.add(new Chicken("Bob", "Friendly chicken", ChickBidsApplication.getUserController().getCurrentUser().getUsername()));

        adapter = new ChickenAdapter(this, chickens); /* NEW! */
        searchResults.setAdapter(adapter);
    }

}
