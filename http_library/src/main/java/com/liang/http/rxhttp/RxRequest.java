package com.liang.http.rxhttp;

import com.liang.http.BuildConfig;
import com.liang.http.ReqManager;
import com.liang.http.base.ICallBack;
import com.liang.http.base.Request;
import com.liang.http.callback.BitmapCallback;
import com.liang.http.callback.FileCallback;
import com.liang.http.okhttp.HeadersInterceptor;
import com.liang.http.utils.Logger;
import com.liang.http.utils.RxUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jianbo on 2018/4/10.
 */

public abstract class RxRequest extends Request {
    private static final String TAG = RxRequest.class.getSimpleName();
    private OkHttpClient.Builder okHttpBuilder;
    private Retrofit.Builder retrofitBuilder;
    private RxService apiService;

    public RxRequest() {
        okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new HeadersInterceptor(commonHeaders()));
        okHttpBuilder.addInterceptor(addInterceptor());
        okHttpBuilder.connectTimeout(connectTimeout(), TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(readTimeOut(), TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(writeTimeout(), TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
//            okHttpBuilder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));
            okHttpBuilder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        retrofitBuilder = new Retrofit.Builder();

        retrofitBuilder.baseUrl(baseUrl());
        retrofitBuilder.addConverterFactory(converterFactory());

        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());


        retrofitBuilder.client(okHttpBuilder.build());
        apiService = retrofitBuilder.build().create(RxService.class);
    }

    @Override
    public Interceptor addInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Request.Builder builder = chain.request()
                        .newBuilder();
                return chain.proceed(builder.build());
            }
        };
    }

    @Override
    public int connectTimeout() {
        return 10;
    }

    @Override
    public int readTimeOut() {
        return 10;
    }

    @Override
    public int writeTimeout() {
        return 10;
    }

    @Override
    public Map<String, String> commonHeaders() {
        return new HashMap<>();
    }

    protected Converter.Factory converterFactory() {
        return GsonConverterFactory.create();
    }

    @Override
    public <T> void post(String tag, String url, Map<String, String> params, ICallBack<T> callback) {
        post(tag, url, new HashMap<String, String>(), params, callback);
    }

    @Override
    public <T> void post(final String tag, final String url, Map<String, String> headers, final Map<String, String> params, ICallBack<T> callBack) {
        if (isWay(tag, url, params)) return;
        Logger.d(TAG, url);
        apiService.postResponse(url, headers, params)
                .compose(RxUtils.<ResponseBody>observableTransformer())
                .onErrorResumeNext(new RxFactory.HttpResponseFunc<ResponseBody>())
                .subscribe(new DefObserver(tag, callBack) {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ReqManager.getInstance().removeReq(tag, url, params);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        ReqManager.getInstance().removeReq(tag, url, params);
                    }
                });
    }

    @Override
    public <T> void get(String tag, String url, Map<String, String> params, ICallBack<T> callback) {
        get(tag, url, new HashMap<String, String>(), params, callback);
    }

    @Override
    public <T> void get(final String tag, final String url, Map<String, String> headers, final Map<String, String> params, ICallBack<T> callback) {
        if (isWay(tag, url, params)) return;
        apiService.getResponse(url, headers, params)
                .compose(RxUtils.<ResponseBody>observableTransformer())
                .onErrorResumeNext(new RxFactory.HttpResponseFunc<ResponseBody>())
                .subscribe(new DefObserver<T>(tag, callback) {

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ReqManager.getInstance().removeReq(tag, url, params);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        ReqManager.getInstance().removeReq(tag, url, params);
                    }
                });
    }


    @Override
    public <T> void get(final String url, ICallBack<T> callback) {
        if (isWay(url)) return;
        Logger.e(TAG, "get:" + url);
        apiService.get(url)
                .compose(RxUtils.<ResponseBody>observableTransformer())
                .onErrorResumeNext(new RxFactory.HttpResponseFunc<ResponseBody>())
                .subscribe(new DefObserver(url, callback) {

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ReqManager.getInstance().removeRequest(url);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        ReqManager.getInstance().removeRequest(url);
                    }
                });
    }


    @Override
    public void download(String url, FileCallback callback) {
        Logger.e(TAG, "download:" + url);
        get(url, callback);
    }

    @Override
    public void bitmap(String url, BitmapCallback callback) {
        get(url, callback);
    }

    @Override
    public void cancel(String tag) {
        super.cancel(tag);
        RxReqManager.rxCancel(tag);
    }

    @Override
    public void cancelAll() {
        super.cancelAll();
        RxReqManager.rxCancelAll();
    }

}
