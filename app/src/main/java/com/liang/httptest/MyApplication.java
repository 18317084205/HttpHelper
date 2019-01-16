package com.liang.httptest;

import android.app.Application;
import com.liang.http.HttpRequest;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        HttpRequest.init(new HttpHelper());
    }
}
