package com.stustirling.ribotviewerjava.ui.allribots;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.stustirling.ribotviewerjava.shared.model.RibotModel;
import com.stustirling.ribotviewerjava.ui.BaseViewModel;
import com.stustirling.ribotviewjava.domain.RefreshTrigger;
import com.stustirling.ribotviewjava.domain.Resource;
import com.stustirling.ribotviewjava.domain.RibotRepository;
import com.stustirling.ribotviewjava.domain.model.Ribot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public class AllRibotViewModel extends BaseViewModel {

    private RefreshTrigger allRibotsRefresh = new RefreshTrigger();

    private MutableLiveData<List<RibotModel>> ribots;
    private MutableLiveData<Throwable> retrievalError;
    private MutableLiveData<Boolean> loading;
    private RibotRepository ribotRepository;

    public AllRibotViewModel(RibotRepository ribotRepository) {
        this.ribotRepository = ribotRepository;

        ribots = new MutableLiveData<>();
        retrievalError = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        loading.setValue(false);

        init();
    }

    private void init() {
        ConnectableFlowable<Resource<List<Ribot>>> connectableRepoCall =
                ribotRepository.getRibots(allRibotsRefresh)
                        .subscribeOn(Schedulers.io())
                        .publish();

        Flowable<List<RibotModel>> success = createSuccessFlowable(connectableRepoCall);

        Flowable<Throwable> error = connectableRepoCall
                .compose(Resource.<List<Ribot>>onlyError())
                .observeOn(AndroidSchedulers.mainThread());

        final Flowable<Object> loadingFlowable = connectableRepoCall
                .compose(Resource.<List<Ribot>>onlyLoading())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.addAll(
                error.subscribe(errorConsumer),
                success.subscribe(successConsumer),
                loadingFlowable.subscribe(loadingConsumer),
                connectableRepoCall.connect()
        );
    }

    private Flowable<List<RibotModel>> createSuccessFlowable(
            ConnectableFlowable<Resource<List<Ribot>>> connectableRepoCall) {
        return connectableRepoCall
                .compose(Resource.<List<Ribot>>onlySuccess())
                .map(new Function<List<Ribot>, List<RibotModel>>() {
                    @Override
                    public List<RibotModel> apply(List<Ribot> ribots) throws Exception {
                        List<RibotModel> convertedRibots = new ArrayList<>();
                        for (Ribot ribot :
                                ribots) {
                            convertedRibots.add(RibotModel.from(ribot));
                        }
                        return convertedRibots;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Consumer<List<RibotModel>> successConsumer = new Consumer<List<RibotModel>>() {
        @Override
        public void accept(List<RibotModel> ribotModels) throws Exception {
            retrievalError.setValue(null);
            ribots.setValue(ribotModels);
            loading.setValue(false);
        }
    };

    private Consumer<Throwable> errorConsumer = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) throws Exception {
            retrievalError.setValue(throwable);
            loading.setValue(false);
        }
    };

    private Consumer<Object> loadingConsumer = new Consumer<Object>() {
        @Override
        public void accept(Object o) throws Exception {
            loading.setValue(true);
        }
    };

    void triggerRefresh() {
        allRibotsRefresh.refresh();
    }

    MutableLiveData<Boolean> getLoadingLiveData() {
        return loading;
    }

    MutableLiveData<List<RibotModel>> getRibotsLiveData() {
        return ribots;
    }

    MutableLiveData<Throwable> getErrorLiveData() {
        return retrievalError;
    }

    static class Factory implements ViewModelProvider.Factory {
        private RibotRepository ribotRepository;

        public Factory(RibotRepository ribotRepository) {
            this.ribotRepository = ribotRepository;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new AllRibotViewModel(ribotRepository);
        }
    }
}
