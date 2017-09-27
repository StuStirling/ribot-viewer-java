package com.stustirling.ribotviewerjava.ui.allribots;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.stustirling.ribotviewerjava.R;
import com.stustirling.ribotviewerjava.shared.di.AppComponent;
import com.stustirling.ribotviewerjava.shared.di.scopes.ActivityScope;
import com.stustirling.ribotviewerjava.shared.model.RibotModel;
import com.stustirling.ribotviewerjava.ui.BaseActivity;
import com.stustirling.ribotviewerjava.ui.ribot.RibotActivity;
import com.stustirling.ribotviewjava.domain.RibotRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Provides;

public class AllRibotActivity extends BaseActivity{

    @BindView(R.id.rv_ara_ribots)
    RecyclerView recyclerView;
    @BindView(R.id.srl_ara_refresh)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected Integer getLayoutRes() {
        return R.layout.activity_allribot;
    }

    @Inject
    AllRibotViewModel.Factory viewModelFactory;
    private AllRibotViewModel viewModel;
    private AllRibotAdapter ribotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this,viewModelFactory)
                .get(AllRibotViewModel.class);

        setup();
    }

    private void setup() {
        setUpRecyclerView();
        setUpRefreshLayout();
        bindViewModel();
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(
                this,2,GridLayoutManager.VERTICAL,false));
        ribotAdapter = new AllRibotAdapter(new AllRibotAdapter.Listener() {
            @Override
            public void ribotSelected(RibotModel ribotModel, View sharedView) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        AllRibotActivity.this,
                        sharedView,
                        ViewCompat.getTransitionName(sharedView));
                RibotActivity.launchRibot(AllRibotActivity.this,ribotModel,options);
            }
        });
        ribotAdapter.setHasStableIds(true);
        recyclerView.setAdapter(ribotAdapter);
    }

    private void setUpRefreshLayout() {
        refreshLayout.setEnabled(false);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.triggerRefresh();
            }
        });
    }

    private void bindViewModel() {
        viewModel.getLoadingLiveData()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean isLoading) {
                        refreshLayout.setRefreshing(isLoading != null &&
                                isLoading &&
                                !refreshLayout.isRefreshing());
                    }
                });
        viewModel.getRibotsLiveData()
                .observe(this, new Observer<List<RibotModel>>() {
                    @Override
                    public void onChanged(@Nullable List<RibotModel> ribotModels) {
                        if ( ribotModels != null ) {
                            Collections.sort(ribotModels,new Comparator<RibotModel>() {
                                @Override
                                public int compare(RibotModel ribotModel, RibotModel t1) {
                                    return ribotModel.getFirstName().compareTo(t1.getFirstName());
                                }
                            });
                            ribotAdapter.setItems(ribotModels);
                            refreshLayout.setEnabled(true);
                        }
                    }
                });
        viewModel.getErrorLiveData()
                .observe(this, new Observer<Throwable>() {
                    @Override
                    public void onChanged(@Nullable Throwable throwable) {
                        if ( throwable != null ) {
                            refreshLayout.setEnabled(false);
                            Toast.makeText(AllRibotActivity.this,
                                    "Something went wrong. Please try again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void inject(AppComponent appComponent) {
        DaggerAllRibotActivity_Component.builder()
                .appComponent(appComponent)
                .module(new Module())
                .build().inject(this);
    }

    @dagger.Component(
            dependencies = {AppComponent.class},
            modules = {Module.class}
    )
    @ActivityScope
    interface Component {
        void inject(AllRibotActivity allRibotActivity);
    }

    @dagger.Module
    class Module {
        @Provides
        AllRibotViewModel.Factory providesFactory(RibotRepository ribotRepository) {
            return new AllRibotViewModel.Factory(ribotRepository);
        }
    }



}
