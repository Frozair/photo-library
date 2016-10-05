package com.newrdev.photolibrary;

import android.app.Application;

/**
 * Created by rudolph on 10/5/16.
 */

public class PhotoLibraryApplication extends Application {
    private static PhotoLibraryApplication instance;

    public PhotoLibraryApplication() {
        instance = this;
    }

    public static PhotoLibraryApplication getInstance() {
        return instance;
    }
}
