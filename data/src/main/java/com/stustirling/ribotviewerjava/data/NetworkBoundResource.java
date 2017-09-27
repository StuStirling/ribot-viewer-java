package com.stustirling.ribotviewerjava.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stustirling.ribotviewjava.domain.RefreshTrigger;
import com.stustirling.ribotviewjava.domain.Resource;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

abstract class NetworkBoundResource<LocalType,RemoteType,DomainType> implements RefreshTrigger.Listener {

    @NonNull
    private final FlowableEmitter<Resource<DomainType>> emitter;
    @Nullable
    private final RefreshTrigger refreshTrigger;
    private Disposable localDataDisposable;

    NetworkBoundResource(@NonNull FlowableEmitter<Resource<DomainType>> emitter,
                                @Nullable RefreshTrigger refreshTrigger) {
        this.emitter = emitter;
        this.refreshTrigger = refreshTrigger;

        init();
    }

    private void init() {
        if ( refreshTrigger != null ) {
            refreshTrigger.setListener(this);
        }

        emitter.onNext(Resource.<DomainType>loading());

        localDataDisposable = getLocal();

        refreshRemote();
    }

    private Disposable getLocal() {
        return loadFromDb()
                .subscribeOn(Schedulers.io())
                .map(mapToDomain())
                .subscribe(new Consumer<DomainType>() {
                    @Override
                    public void accept(DomainType domainType) throws Exception {
                        emitter.onNext(Resource.success(domainType));
                    }
                });
    }

    private void refreshRemote() {
        createCall()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        emitter.onNext(Resource.<DomainType>loading());
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(mapToLocal())
                .subscribe(remoteSuccessConsumer,remoteErrorConsumer);
    }

    private Consumer<LocalType> remoteSuccessConsumer = new Consumer<LocalType>() {
        @Override
        public void accept(LocalType localType) throws Exception {
            if ( localDataDisposable != null
                    && !localDataDisposable.isDisposed() ) {
                localDataDisposable.dispose();
            }
            saveCallResult(localType);
            localDataDisposable = getLocal();
            enableRefreshTrigger();
        }
    };

    private Consumer<Throwable> remoteErrorConsumer = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) throws Exception {
            emitter.onNext(Resource.<DomainType>error(throwable));
            enableRefreshTrigger();
        }
    };

    private void enableRefreshTrigger() {
        if ( refreshTrigger != null ) {
            refreshTrigger.setEnabled(true);
        }
    }

    @Override
    public void refreshTriggered() {
        refreshRemote();
    }

    abstract void saveCallResult(LocalType item);
    abstract Flowable<LocalType> loadFromDb();
    abstract Single<RemoteType> createCall();
    abstract Function<RemoteType,LocalType> mapToLocal();
    abstract Function<LocalType,DomainType> mapToDomain();



}
