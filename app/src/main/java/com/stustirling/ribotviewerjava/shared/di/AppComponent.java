package com.stustirling.ribotviewerjava.shared.di;

import android.content.Context;

import com.stustirling.ribotviewerjava.shared.di.scopes.ForApplication;
import com.stustirling.ribotviewjava.domain.RibotRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Stu Stirling on 27/09/2017.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ForApplication
    Context applicationContext();
    RibotRepository ribotRepository();

}
