package com.stustirling.ribotviewerjava.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.stustirling.ribotviewerjava.RibotApplication;
import com.stustirling.ribotviewerjava.shared.di.AppComponent;

import butterknife.ButterKnife;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract @LayoutRes Integer getLayoutRes();
    protected abstract void inject(AppComponent appComponent);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(((RibotApplication)getApplication()).getAppComponent());
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
    }
}
