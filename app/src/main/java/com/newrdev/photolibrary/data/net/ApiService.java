package com.newrdev.photolibrary.data.net;

import com.newrdev.photolibrary.data.entity.PhotoEntity;
import com.newrdev.photolibrary.util.Constants;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;
import retrofit2.http.GET;

/**
 * Created by newrdev on 10/3/16.
 */

public class ApiService {

    public static PhotoApi getApi(){
        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(PhotoApi.class);
    }

    public interface PhotoApi {
        @GET("/photos")
        Observable<List<PhotoEntity>> getPhotos();

        @GET
        @Streaming
        Observable<ResponseBody> downloadFile(@Url String url);
    }
}
