package com.newrdev.photolibrary.ui.base;

/**
 * Created by rudolph on 10/3/16.
 */

public interface BasePresenter<V> {
    V view();
    void bindView(V view);
    void unbindView();
}
