package com.stustirling.ribotviewerjava.data.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.stustirling.ribotviewerjava.data.local.dao.RibotDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

@Module
public class LocalDataModule {
    private Context context;

    public LocalDataModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    LocalDatabase providesLocalDatabase() {
        return Room.databaseBuilder(context,
                LocalDatabase.class,
                "ribot").build();
    }

    @Singleton
    @Provides
    RibotDao providesRibotDao(LocalDatabase localDatabase) {
        return localDatabase.ribotDao();
    }
}
