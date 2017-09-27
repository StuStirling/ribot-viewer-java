package com.stustirling.ribotviewerjava.data.api;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public class RetrofitProvider {

    private final String baseUrl;
    private final GsonConverterFactory gsonConverterFactory;

    @Inject
    public RetrofitProvider(String baseUrl,
                            GsonConverterFactory gsonConverterFactory) {

        this.baseUrl = baseUrl;
        this.gsonConverterFactory = gsonConverterFactory;
    }

    public Retrofit build() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();


        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

}
