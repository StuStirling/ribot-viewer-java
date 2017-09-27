package com.stustirling.ribotviewjava.domain;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class Resource<T> {

    public enum Status {
        SUCCESS,ERROR,LOADING
    }

    @NonNull
    private final Status status;
    @Nullable
    private final T data;
    @Nullable
    private final String message;
    private Throwable throwable;

    private Resource(@NonNull Status status,
                     @Nullable T data,
                     @Nullable String message ) {

        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Status.SUCCESS,data,null);
    }

    public static <T> Resource<T> error(Throwable throwable) {
        Resource<T> resource = new Resource<>(Status.ERROR, null, null);
        resource.setThrowable(throwable);
        return resource;
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(Status.LOADING,null,null);
    }

    void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public static <T> FlowableTransformer<Resource<T>,T> onlySuccess() {
        return new FlowableTransformer<Resource<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<Resource<T>> upstream) {
                return upstream.filter(new Predicate<Resource<T>>() {
                    @Override
                    public boolean test(Resource<T> tResource) throws Exception {
                        return tResource.getStatus() == Status.SUCCESS;
                    }
                }).map(new Function<Resource<T>, T>() {
                    @Override
                    public T apply(Resource<T> tResource) throws Exception {
                        return tResource.data;
                    }
                });
            }
        };
    }

    public static <T> FlowableTransformer<Resource<T>,Throwable> onlyError() {
        return new FlowableTransformer<Resource<T>, Throwable>() {
            @Override
            public Publisher<Throwable> apply(Flowable<Resource<T>> upstream) {
                return upstream.filter(new Predicate<Resource<T>>() {
                    @Override
                    public boolean test(Resource<T> tResource) throws Exception {
                        return tResource.getStatus() == Status.ERROR;
                    }
                }).map(new Function<Resource<T>, Throwable>() {
                    @Override
                    public Throwable apply(Resource<T> tResource) throws Exception {
                        return tResource.throwable;
                    }
                });
            }
        };
    }

    public static <T> FlowableTransformer<Resource<T>,Object> onlyLoading() {
        return new FlowableTransformer<Resource<T>, Object>() {
            @Override
            public Publisher<Object> apply(Flowable<Resource<T>> upstream) {
                return upstream.filter(new Predicate<Resource<T>>() {
                    @Override
                    public boolean test(Resource<T> tResource) throws Exception {
                        return tResource.getStatus() == Status.LOADING;
                    }
                }).map(new Function<Resource<T>, Object>() {
                    @Override
                    public Object apply(Resource<T> tResource) throws Exception {
                        return new Object();
                    }
                });
            }
        };
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", throwable=" + throwable +
                '}';
    }
}
