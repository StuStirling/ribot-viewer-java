package com.stustirling.ribotviewerjava;

import android.app.Application;

import com.stustirling.ribotviewerjava.data.local.LocalDataModule;
import com.stustirling.ribotviewerjava.shared.di.AppComponent;
import com.stustirling.ribotviewerjava.shared.di.AppModule;
import com.stustirling.ribotviewerjava.shared.di.DaggerAppComponent;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public class RibotApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppComponent getAppComponent() {
        if ( appComponent == null ) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .localDataModule(new LocalDataModule(this))
                    .build();
        }

        return appComponent;
    }
}
