package com.liang.http.utils;

import android.app.Activity;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jianbo on 2018/5/4.
 */

public class RxUtils {

    private static Map<Activity, List<Disposable>> disposableMap = new HashMap<>();

    public static void addDisposable(Activity activity, Disposable disposable) {
        if (hasDisposables(activity)) {
            disposableMap.get(activity).add(disposable);
            return;
        }
        List<Disposable> disposables = new ArrayList<>();
        disposables.add(disposable);
        disposableMap.put(activity, disposables);
    }

    public static void removeDisposable(Activity activity) {
        if (hasDisposables(activity)) {
            for (Disposable disposable : disposableMap.get(activity)) {
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                }
                disposableMap.remove(disposable);
            }
        }
    }

    private static boolean hasDisposables(Activity activity) {
        return disposableMap.containsKey(activity);
    }


    public static <T> ObservableTransformer<T, T> observableTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> flyableTransformer() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
