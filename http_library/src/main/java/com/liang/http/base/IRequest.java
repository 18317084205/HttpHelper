package com.liang.http.base;

import okhttp3.Interceptor;

import java.util.Map;

/**
 * Created by Jianbo on 2018/4/3.
 */

public abstract class IRequest {

    public abstract int connectTimeout();

    public abstract int readTimeOut();

    public abstract int writeTimeout();

    public abstract Interceptor addInterceptor();

    public abstract String baseUrl();

    public abstract Map<String, String> commonHeaders();

    public abstract <T> void post(String tag, String url, Map<String, String> params, ICallBack<T> callback);

    public abstract <T> void post(String tag, String url, Map<String, String> headers, Map<String, String> params, ICallBack<T> callback);

    public abstract <T> void get(String tag, String url, Map<String, String> params, ICallBack<T> callback);

    public abstract <T> void get(String tag, String url, Map<String, String> headers, Map<String, String> params, ICallBack<T> callback);

    public abstract <T> void get(String url, ICallBack<T> callback);

    public abstract boolean isWay(String url);

    public abstract boolean isWay(String tag, String url, Map<String, String> params);

    public abstract void cancel(String tag);

    public abstract void cancelAll();

}
