package com.liang.http.rxhttp;

import io.reactivex.disposables.Disposable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RxReqManager {
    private static Map<String, ConcurrentLinkedQueue<Disposable>> disposables = new HashMap<>();

    public static void addDisposable(Disposable d, String tag) {
        ConcurrentLinkedQueue<Disposable> queue = disposables.get(tag);
        if (queue == null) {
            queue = new ConcurrentLinkedQueue<>();
            disposables.put(tag, queue);
        }
        queue.add(d);
    }

    public static void rxCancel(String tag) {
        ConcurrentLinkedQueue<Disposable> queue = disposables.get(tag);
        if (queue != null) {
            for (Disposable disposable : queue) {
                if (disposable == null && !disposable.isDisposed()) {
                    disposable.dispose();
                    queue.remove(disposable);
                }
            }
            disposables.remove(tag);
        }
    }

    public static void rxCancelAll() {
        for (String tag : disposables.keySet()) {
            rxCancel(tag);
        }
    }
}
