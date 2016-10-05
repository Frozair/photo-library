package com.newrdev.photolibrary.data.job;

import rx.Observable;

/**
 * Created by rudolph on 10/5/16.
 */

public interface Job<T> {
    Observable<T> run();
}
