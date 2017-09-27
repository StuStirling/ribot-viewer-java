package com.stustirling.ribotviewerjava.data.api.model;

import com.stustirling.ribotviewerjava.data.api.GsonConverterFactoryProvider;
import com.stustirling.ribotviewerjava.data.api.RetrofitProvider;
import com.stustirling.ribotviewerjava.data.api.RibotService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

@Module
public class ApiModule {

    @Singleton
    @Provides
    GsonConverterFactory providesGsonConverterFactory( GsonConverterFactoryProvider provider) {
        return provider.build();
    }

    @Singleton
    @Provides
    RetrofitProvider providesRetrofitProvider(GsonConverterFactoryProvider gsonProvider) {
        return new RetrofitProvider("https://api.ribot.io/",gsonProvider.build());
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(RetrofitProvider retrofitProvider) {
        return retrofitProvider.build();
    }

    @Singleton
    @Provides
    RibotService providesRibotService(Retrofit retrofit) {
        return retrofit.create(RibotService.class);
    }


}
