package com.stustirling.ribotviewerjava.shared.di;

import android.content.Context;

import com.stustirling.ribotviewerjava.data.RibotRepositoryImpl;
import com.stustirling.ribotviewerjava.data.api.RibotService;
import com.stustirling.ribotviewerjava.data.api.model.ApiModule;
import com.stustirling.ribotviewerjava.data.local.LocalDataModule;
import com.stustirling.ribotviewerjava.data.local.dao.RibotDao;
import com.stustirling.ribotviewerjava.shared.di.scopes.ForApplication;
import com.stustirling.ribotviewjava.domain.RibotRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

@Module(includes = {ApiModule.class,LocalDataModule.class})
public class AppModule {

    private Context context;

    public AppModule(Context context ) {
        this.context = context;
    }

    @Singleton
    @Provides
    @ForApplication
    Context providesApplicationContext() {
        return context;
    }

    @Singleton
    @Provides
    RibotRepository providesRibotRepository(RibotService ribotService,
                                            RibotDao ribotDao) {
        return new RibotRepositoryImpl(ribotService,ribotDao);
    }

}
