package com.newrdev.photolibrary.ui.base;

/**
 * Created by rudolph on 10/3/16.
 */

public abstract class BasePresenter<V>{
    private V view;

    public V view() {
        return view;
    }

    public void bindView(V view) {
        this.view = view;
    }

    public void unbindView() {
        this.view = null;
    }
}
