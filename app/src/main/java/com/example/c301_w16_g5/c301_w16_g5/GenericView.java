package com.example.c301_w16_g5.c301_w16_g5;

/**
 * Provides the basic framework for all views in the application, such that
 * they can update when a model whose data they display changes.  This is an
 * integral component of our MVC design.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 */
public interface GenericView<M> {
    void update(M model);
}
