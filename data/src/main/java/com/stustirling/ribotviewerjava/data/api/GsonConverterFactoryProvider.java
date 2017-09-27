package com.stustirling.ribotviewerjava.data.api;

import javax.inject.Inject;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Stu Stirling on 27/09/2017.
 */


public class GsonConverterFactoryProvider {

    @Inject
    public GsonConverterFactoryProvider() {

    }

    public GsonConverterFactory build() {
        return GsonConverterFactory.create();
    }
}
