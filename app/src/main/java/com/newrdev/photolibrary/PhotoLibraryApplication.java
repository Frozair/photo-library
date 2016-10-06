package com.newrdev.photolibrary;

import android.app.Application;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }

    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
