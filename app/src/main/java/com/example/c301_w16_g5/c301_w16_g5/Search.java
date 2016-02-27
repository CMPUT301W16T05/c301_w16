package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * Created by Hailey on 2016-02-11.
 */
public class Search {
    private ArrayList<Chicken> searchResults;

    public Search() {
        searchResults = new ArrayList<Chicken>();
    }

    public void searchFor(String searchText) {
        String[] keywords = parse(searchText);

        // search with the keywords here
    }

    private String[] parse(String searchText) {
        // split string on spaces/non-alphanumeric characters
        return null;
    }
}
