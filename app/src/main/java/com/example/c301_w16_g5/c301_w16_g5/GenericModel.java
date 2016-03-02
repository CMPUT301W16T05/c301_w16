package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Provides the basic framework for all models in the application, such that
 * they have views that display them, and ensure these views are updated
 * appropriately.  This is an integral part of our MVC design.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
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
