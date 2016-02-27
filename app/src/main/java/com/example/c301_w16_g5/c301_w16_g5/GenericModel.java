package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Hailey on 2016-02-26.
 */
public abstract class GenericModel<V  extends GenericView> {
    private ArrayList<V> views;

    public GenericModel() {
        views = new ArrayList<V>();
    }

    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    public void deleteView(V view) {
        views.remove(view);
    }

    public void notifyViews() {
        for (V view : views) {
            view.update(this);
        }
    }
}
